package com.campbell.jess.movies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
public class ImageAdapter extends BaseAdapter {

    //TODO refactor class name
    //TODO add onclick to adapter

    private Context mContext;
    private String[] mThumbPaths;

    public ImageAdapter(Context context, String[] thumbPaths) {

        mContext = context;
        mThumbPaths = thumbPaths;
    }

    @Override
    public int getCount() {
        return mThumbPaths.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(400, 400));
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) view;
        }
        Picasso.get().load(mThumbPaths[i]).into(imageView);
        return imageView;
    }

}
