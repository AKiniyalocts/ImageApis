package com.example.anthony.wedpics;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by anthony on 7/16/15.
 */
public class ImagesApp extends Application {

    public static LruCache<String, Bitmap> mMemoryCache;

    @Override
    public void onCreate() {
        super.onCreate();



    }

    public static LruCache<String, Bitmap> getMemoryCache(){
        if(mMemoryCache != null){
            return mMemoryCache;
        }

        else {
            initMemoryCache();
        }

        return mMemoryCache;
    }

    private static void initMemoryCache(){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {

                return bitmap.getByteCount() / 1024;
            }
        };
    }
}
