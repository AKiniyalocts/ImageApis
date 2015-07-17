package com.example.anthony.wedpics.tasks;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by anthony on 7/14/15.
 *
 * Resizes our images to 1080 x (whatever heigh to maintain AR)
 *
 * Calls back to main thread for when to show Placeholder, and when resize is ready.
 */
public class ResizeImageTask extends AsyncTask<Integer, Void, Bitmap>{

    private static final String TAG = ResizeImageTask.class.getSimpleName();

    private static final int RESIZE_WIDTH = 1080;

    private Bitmap bitmap;

    private String pathToFie;
    private ImageResizedCallback imageResizedCallback;


    public ResizeImageTask(String pathToFie, Activity activity) {
        this.pathToFie = pathToFie;
        imageResizedCallback = (ImageResizedCallback) activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        imageResizedCallback.onShowPlaceHolder();
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        bitmap = null;

        if(pathToFie != null){

            File imageFile = new File(pathToFie);

            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeFile(pathToFie, options);

            FileOutputStream out = null;

            try {

                out = new FileOutputStream(new File(imageFile.getPath()));

                scaleBitmap(bitmap, RESIZE_WIDTH).compress(Bitmap.CompressFormat.PNG, 100, out);

            } catch (Exception e) {

                Log.w(TAG, e.getMessage());

            } finally {

                try {

                    if (out != null) {

                        out.close();

                    }
                } catch (IOException e) {
                    Log.w(TAG, e.getMessage());

                }
            }


        }

        else {

        }

        return bitmap;

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        imageResizedCallback.onImageResized(pathToFie);

        imageResizedCallback = null;

    }

    /**
     *
     * @param bitmap bitmap you wish to resize
     * @param resizedWidth the width to resize to, maintaining AR
     * @return scaledBitmap
     */
    private Bitmap scaleBitmap(Bitmap bitmap, int resizedWidth){

        int orgHeight = bitmap.getHeight();
        int orgWidth = bitmap.getWidth();

        if(orgWidth > orgHeight){

            return Bitmap.createScaledBitmap(bitmap, resizedWidth, (orgHeight * resizedWidth / orgWidth), false);
        }

        else if(orgHeight > orgWidth){
            return Bitmap.createScaledBitmap(bitmap, resizedWidth, (orgHeight * resizedWidth / orgWidth), false);
        }

        else{
            return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedWidth, false);
        }

    }

}
