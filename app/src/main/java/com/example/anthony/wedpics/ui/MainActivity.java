package com.example.anthony.wedpics.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.anthony.wedpics.R;
import com.example.anthony.wedpics.helpers.UriHelper;
import com.example.anthony.wedpics.model.Picture;
import com.example.anthony.wedpics.tasks.ImageResizedCallback;
import com.example.anthony.wedpics.tasks.ResizeImageTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/*
    Main screen. Shows Gallery and allows users to add new photos to it.
 */
public class MainActivity extends AppCompatActivity implements ImageResizedCallback{

    public static final String TAG = MainActivity.class.getSimpleName();

    private final static int RESULT_CAPTURED_IMAGE = 100;

    private final static int RESULT_PICKED_IMAGE = 200;


    @InjectView(R.id.main_toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.gallery_recycler)
    RecyclerView mRecycler;

    @InjectView(R.id.empty_gallery_text)
    TextView mEmptyText;


    private Uri imageUri;

    private List<Picture> mPictures;

    private GalleryAdapter mAdapter;

    private Picture picture;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initToolbar();

        /*
            Only initialize if it has not been laid out already
         */
        if(mRecycler.getLayoutManager() == null)
            initRecycler();

    }

    /*
       Image is ready. Lets replace the placeholder with the actual resized image
    */
    @Override
    public void onImageResized(String filePath) {
        picture = null;

        picture = new Picture();
        picture.setPicturePath(filePath);
        picture.setDateCreated(new Date());


        mAdapter.update(picture);

    }

    /*
        Add dummy picture to adapter to show placeholder.
     */
    @Override
    public void onShowPlaceHolder() {
        picture = new Picture();
        picture.setIsPlaceholder(true);

        mAdapter.add(picture);

        /*
            Hide our empty set text if there are items in gallery
         */
        if(mAdapter.getItemCount() > 0)
            mEmptyText.setVisibility(View.INVISIBLE);

        /*
            Smooth scroll to top to see new image
         */
        mRecycler.smoothScrollToPosition(0);
    }

    /*
                Handle intents for capturing images from the camera, and choosing them from the gallery.
             */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        picture = null;
        /*
            Capturing with camera.
         */
        if(requestCode == RESULT_CAPTURED_IMAGE){

            if(resultCode == RESULT_OK) {

                File finalFile = new File(getRealPathFromURI(imageUri));

                picture = new Picture();
                picture.setDateCreated(new Date());
                picture.setPicturePath(finalFile.getAbsolutePath());


                /*
                    Start the resizing process
                 */
                new ResizeImageTask(picture.getPicturePath(), this).execute();

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

                    picture = new Picture();
                    picture.setDateCreated(new Date());
                    picture.setPicturePath(finalFile.getAbsolutePath());

                /*
                    Start resize process
                 */
                    new ResizeImageTask(picture.getPicturePath(), this).execute();



                }

            }


    }

    private void initToolbar(){
        mToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);
    }

    /*
        Initialize our recycler view with an empty list and a layoutmanager.
     */
    private void initRecycler(){
        mPictures = new ArrayList<Picture>();
        mAdapter = new GalleryAdapter(this, mPictures);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
    }


    @OnLongClick(R.id.new_image)
    public boolean onShowHint(){
        Snackbar.make(mRecycler, getString(R.string.capture_hint), Snackbar.LENGTH_LONG).show();
        return true;
    }

    @OnClick(R.id.new_image)
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


    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


}
