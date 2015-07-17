package com.example.anthony.wedpics.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.anthony.wedpics.ImagesApp;
import com.example.anthony.wedpics.model.Picture;

import java.lang.ref.WeakReference;

/**
 * Created by anthony on 7/16/15.
 *
 * Checks if bitmap is in cache.
 * Decodes bitmaps from a file. Adds to cache if not there already.
 *
 * Then sets to imageview
 */
public class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {

    private final WeakReference<ImageView> imageViewReference;

    private Picture picture;

    public BitmapWorkerTask(Picture picture, ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.picture = picture;
    }


    @Override
    protected Bitmap doInBackground(Integer... params) {
        Bitmap bm = getBitmapFromMemCache(picture.getPicturePath());

        if(bm == null) {
            bm = BitmapFactory.decodeFile(picture.getPicturePath());
            addBitmapToMemoryCache(picture.getPicturePath(), bm);

            return bm;
        }

        return bm;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            ImagesApp.getMemoryCache().put(key, bitmap);
        }
    }
    public Bitmap getBitmapFromMemCache(String key) {
        return ImagesApp.getMemoryCache().get(key);
    }
}
