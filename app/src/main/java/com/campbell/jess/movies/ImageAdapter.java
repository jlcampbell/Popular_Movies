package com.campbell.jess.movies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * used by gridView to populate user interface with movie posters. a new ImageAdapter is created each time
 * new data is loaded
 */

public class ImageAdapter extends BaseAdapter {

    private final Context mContext;
    private String[] mThumbPaths;

    //this grid view adapter heavily references the developer guide at https://developer.android.com/guide/topics/ui/layout/gridview#java

    /**
     * ImageAdapter constructor
     *
     * @param context
     * @param thumbPaths - the paths to get movie posters online, obtained from the moviedb api response
     */
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

    /**
     * used to populate the movie grid in the main activity
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 800));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);

        } else {
            imageView = (ImageView) view;
        }
        Picasso.get().load(mThumbPaths[i]).into(imageView);
        return imageView;
    }

}
