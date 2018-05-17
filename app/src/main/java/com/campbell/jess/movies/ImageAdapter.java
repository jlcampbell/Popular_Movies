package com.campbell.jess.movies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
public class ImageAdapter extends BaseAdapter {

    //TODO refactor class name


    private Context mContext;
    private String[] mThumbPaths;

    //this grid view adapter heavily references the developer guide at https://developer.android.com/guide/topics/ui/layout/gridview#java

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


    //TODO adjust image view so height and width of each thumbnail view are not hardcoded
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(800, 1000));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);

        } else {
            imageView = (ImageView) view;
        }
        Picasso.get().load(mThumbPaths[i]).into(imageView);
        return imageView;
    }

}
