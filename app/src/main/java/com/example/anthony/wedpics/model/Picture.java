package com.example.anthony.wedpics.model;

import java.util.Date;


/**
 * Created by anthony on 7/12/15.
 *
 * Simple model class for a picture object. We have a date and a path to the image.
 *
 */
public class Picture{


    private String picturePath;

    private Date dateCreated;

    private boolean isPlaceholder = false;

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isPlaceholder() {
        return isPlaceholder;
    }

    public void setIsPlaceholder(boolean isPlaceholder) {
        this.isPlaceholder = isPlaceholder;
    }
}
