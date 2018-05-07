package com.campbell.jess.movies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
//TODO update ImageAdapter to take in a String[] of thumbnail paths
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mThumbPaths;

    public ImageAdapter(Context c, String[] thumbPaths) {
        mContext = c;
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
            imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) view;
        }
        Picasso.get().load(mThumbPaths[i]).into(imageView);
        //imageView.setImageResource(mThumbIds[i]);
        return imageView;
    }


    //TODO populate mThumbPaths with data from json response
    /**
    private String[] mThumbPaths = {
            "http://i.imgur.com/DvpvklR.png", "http://i.imgur.com/DvpvklR.png",
            "http://i.imgur.com/DvpvklR.png", "http://i.imgur.com/DvpvklR.png",
            "http://i.imgur.com/DvpvklR.png", "http://i.imgur.com/DvpvklR.png",
            "http://i.imgur.com/DvpvklR.png", "http://i.imgur.com/DvpvklR.png",
            "http://i.imgur.com/DvpvklR.png", "http://i.imgur.com/DvpvklR.png",
            "http://i.imgur.com/DvpvklR.png", "http://i.imgur.com/DvpvklR.png",
            "http://i.imgur.com/DvpvklR.png", "http://i.imgur.com/DvpvklR.png",
            "http://i.imgur.com/DvpvklR.png", "http://i.imgur.com/DvpvklR.png",
            "http://i.imgur.com/DvpvklR.png", "http://i.imgur.com/DvpvklR.png",
            "http://i.imgur.com/DvpvklR.png", "http://i.imgur.com/DvpvklR.png"


    };
**/
}
