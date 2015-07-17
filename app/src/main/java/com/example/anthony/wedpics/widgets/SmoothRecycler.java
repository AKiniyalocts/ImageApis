package com.example.anthony.wedpics.widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by anthony on 7/16/15.
 */
public class SmoothRecycler extends RecyclerView {
    public SmoothRecycler(Context context) {
        super(context);
    }

    public SmoothRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmoothRecycler(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void smoothScrollToPosition(int position) {
        super.smoothScrollToPosition(position);
    }
}
