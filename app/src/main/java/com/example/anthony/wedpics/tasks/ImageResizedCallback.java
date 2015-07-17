package com.example.anthony.wedpics.tasks;

/**
 * Created by anthony on 7/14/15.
 *
 * Simple callback for when to show placeholder image and
 * when the bitmap is done being resized.
 */
public interface ImageResizedCallback {

    public void onShowPlaceHolder();
    public void onImageResized(String filePath);
}
