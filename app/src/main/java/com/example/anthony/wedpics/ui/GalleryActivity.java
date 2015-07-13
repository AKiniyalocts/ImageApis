package com.example.anthony.wedpics.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.anthony.wedpics.R;
import com.example.anthony.wedpics.model.Picture;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;

public class GalleryActivity extends AppCompatActivity {

    @InjectView(R.id.gallery_recycler)
    RecyclerView mRecycler;

    @InjectView(R.id.gallery_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.inject(this);

        initToolbar();
        initRecycler();
    }

    private void initToolbar(){
        mToolbar.setTitle("Gallery");
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initRecycler(){
        Realm realm = Realm.getInstance(this);

        /*
            Show newest pictures taken by the user first
         */
        List<Picture> pictures = realm.where(Picture.class).findAllSorted("dateCreated", false);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(new GalleryAdapter(this, pictures));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
