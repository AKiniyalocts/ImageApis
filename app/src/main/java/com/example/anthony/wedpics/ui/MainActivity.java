package com.example.anthony.wedpics.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.anthony.wedpics.R;
import com.example.anthony.wedpics.helpers.UriHelper;
import com.example.anthony.wedpics.model.Picture;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private final static int RESULT_CAPTURED_IMAGE = 100;

    private final static int RESULT_PICKED_IMAGE = 200;


    @InjectView(R.id.main_toolbar)
    Toolbar mToolbar;


    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initToolbar();
    }

    /*
        Handle intents for capturing images from the camera, and choosing them from the gallery.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /*
            Capturing with camera.
         */
        if(requestCode == RESULT_CAPTURED_IMAGE){

            if(resultCode == RESULT_OK) {

                File finalFile = new File(getRealPathFromURI(imageUri));

                /*
                    Save to local data store
                 */
                final Picture picture = new Picture();
                picture.setDateCreated(new Date());
                picture.setPicturePath(finalFile.getAbsolutePath());

                Realm realm = Realm.getInstance(this);

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(picture);
                    }
                });

                /*
                    Start the gallery
                 */
                onGalleryClick();
            }
            else{

            }
        }

        /*
            Image picked from gallery
         */
        else if(requestCode == RESULT_PICKED_IMAGE){


            if(resultCode == RESULT_OK){

                    imageUri = data.getData();

                    /*
                        Get the right file Uri. Handles new Document Uri's from KK as well.
                     */
                    File finalFile = new File(UriHelper.getPath(this, imageUri));

                    final Picture picture = new Picture();
                    picture.setDateCreated(new Date());
                    picture.setPicturePath(finalFile.getAbsolutePath());

                    Realm realm = Realm.getInstance(this);

                    /*
                        Write to local data store
                     */
                    realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                     realm.copyToRealmOrUpdate(picture);
                    }
                    });

                    /*
                        Start the gallery
                     */
                    onGalleryClick();

                }

            }

            else {

            }



    }

    private void initToolbar(){
        mToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);
    }

    @OnClick(R.id.button_capture)
    public void onCaptureClick(){

        /*
            If there is a camera, open the camera, else, open the chooser.
         */
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){

            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, getString(R.string.app_name));
            values.put(MediaStore.Images.Media.DESCRIPTION, "from " + getString(R.string.app_name));

            imageUri =  getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(captureIntent, RESULT_CAPTURED_IMAGE);

        } else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, RESULT_PICKED_IMAGE);

        }

    }


    @OnClick(R.id.button_gallery)
    public void onGalleryClick(){
        Intent galleryIntent = new Intent(this, GalleryActivity.class);
        galleryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(galleryIntent);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


}
