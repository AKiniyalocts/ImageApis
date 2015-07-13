package com.example.anthony.wedpics.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by anthony on 7/12/15.
 *
 * Simple model class for a picture object. We have a date and a path to the image.
 *
 * Persisted with Realm.
 */
public class Picture extends RealmObject{

    @PrimaryKey
    private String picturePath;

    private Date dateCreated;

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
}
