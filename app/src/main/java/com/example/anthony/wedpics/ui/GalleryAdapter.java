package com.example.anthony.wedpics.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anthony.wedpics.R;
import com.example.anthony.wedpics.model.Picture;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by anthony on 7/12/15.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private final static String TAG = GalleryAdapter.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Picture> mPictures;

    public GalleryAdapter(Context mContext, List<Picture> mPictures) {
        this.mContext = mContext;
        this.mPictures = mPictures;
        mInflater = LayoutInflater.from(mContext);
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
            Maintain aspect ratio, fit to screen size, and show a place holder while loading.
         */
        Picasso.with(mContext)
                .load(new File(picture.getPicturePath()))
                .fit().centerInside()
                .placeholder(R.drawable.ic_gallery)
                .error(android.R.drawable.stat_notify_error)
                .into(galleryViewHolder.mImage);
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
}
