package com.example.anthony.wedpics.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anthony.wedpics.R;
import com.example.anthony.wedpics.model.Picture;
import com.example.anthony.wedpics.tasks.BitmapWorkerTask;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by anthony on 7/12/15.
 *
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private final static String TAG = GalleryAdapter.class.getSimpleName();

    private LayoutInflater mInflater;
    private List<Picture> mPictures;
    private Context mContext;

    public GalleryAdapter(Context mContext, List<Picture> mPictures) {
        this.mPictures = mPictures;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    /*
        Updates imageview with actual image loaded from
        a bitmap provided after we resize the photo.
     */
    public void update(Picture picture){
        if(picture != null) {
            mPictures.set(0, picture);
            this.notifyItemChanged(0);
        }
    }

    /*
        Adds a dummy picture to the list,
        later updated in update()
     */
    public void add(Picture picture){
        if(picture != null) {
            mPictures.add(0, picture);
            this.notifyItemInserted(0);
        }
    }


    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.gallery_item, viewGroup, false);

        return new GalleryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder galleryViewHolder, int i) {
        Picture picture = mPictures.get(i);

        /*
            Show placeholder icon, or show actual photo
         */
        if(picture.isPlaceholder())
            galleryViewHolder.mImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_gallery));
        else
            loadBitmap(picture, galleryViewHolder.mImage);
    }

    @Override
    public int getItemCount() {
        return mPictures.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.gallery_image) ImageView mImage;
        public GalleryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    /*
        Call our AsyncTask for loading the bitmap into our imageview
     */
    public void loadBitmap(Picture picture, ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(picture, imageView);
        task.execute();
    }
}
