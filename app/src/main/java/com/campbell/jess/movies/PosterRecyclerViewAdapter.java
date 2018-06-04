package com.campbell.jess.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.campbell.jess.movies.R;
import com.squareup.picasso.Picasso;

/**
 * PosterRecyclerViewAdapter exposes a list of movie posters to the RecyclerView
 */
public class PosterRecyclerViewAdapter extends RecyclerView.Adapter<PosterRecyclerViewAdapter.PosterRecyclerViewAdapterViewHolder> {
    private String[] mThumbPaths;
    //private final Context mContext;

    private final PosterAdapterOnClickHandler mClickHandler;

    public interface PosterAdapterOnClickHandler {
        void onClick(int position);
    }

    public PosterRecyclerViewAdapter(PosterAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a poster grid item
     */

    public class PosterRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMoviePoster;

        public PosterRecyclerViewAdapterViewHolder(View view) {
            super(view);
            mMoviePoster = (ImageView) view.findViewById(R.id.iv_grid_poster_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }

    /**
     * called when each new ViewHolder is created. Enough viewholders will be created to fill the screen
     *
     * @param parent The ViewGroup that these ViewHolders are contained in
     * @param viewType If your RecyclerView has more than one type of item, use this to
     *                 provides a different layout
     * @return A new PosterRecyclerViewAdapterViewHolder that holds the View for each
     * list item
     */

    @NonNull
    @Override
    public PosterRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.grid_item_poster;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new PosterRecyclerViewAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PosterRecyclerViewAdapterViewHolder viewHolder, int position) {
        String posterUrlForThisMovie = mThumbPaths[position];
        ImageView imageView = viewHolder.mMoviePoster;
        Picasso.get().load(posterUrlForThisMovie).into(imageView);
    }

    @Override
    public int getItemCount() {
        if (null == mThumbPaths) return 0;
        return mThumbPaths.length;
    }

    /**
     * This method used to set the movie posters on a PosterAdapter
     * @param thumbPaths The new thumbnails to display
     */
    public void setmThumbPaths(String[] thumbPaths) {
        mThumbPaths = thumbPaths;
        notifyDataSetChanged();
    }


}
