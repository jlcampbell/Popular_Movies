package com.campbell.jess.movies.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.campbell.jess.movies.R;
import com.campbell.jess.movies.data.database.MovieEntry;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * PosterRecyclerViewAdapter exposes a list of movie posters to the RecyclerView
 */
public class PosterRecyclerViewAdapter extends RecyclerView.Adapter<PosterRecyclerViewAdapter.PosterRecyclerViewAdapterViewHolder> {
    String TAG = "ADAPTER";

    private String[] mThumbPaths;

    private List<MovieEntry> mMovieEntries;

    private final PosterAdapterOnClickHandler mClickHandler;

    private boolean dataFromApi = true;

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

        //initialize image view
        ImageView imageView = viewHolder.mMoviePoster;
        String posterUrlForThisMovie;

        //if data comes from api
        if (dataFromApi){
            posterUrlForThisMovie = mThumbPaths[position];
            //load url
            Picasso.get().load(posterUrlForThisMovie).into(imageView);
        }

        //if data comes from favorites db
        else {
            try {

                MovieEntry movieEntry = mMovieEntries.get(position);
                posterUrlForThisMovie = movieEntry.getPoster();
                //load url
                Picasso.get().load(posterUrlForThisMovie).into(imageView);
            } catch (IndexOutOfBoundsException e){
                Log.e(TAG, e.toString());
            }
            }

    }

    @Override
    public int getItemCount() {
        if (null == mThumbPaths) return 0;
        return mThumbPaths.length;
    }

    /**
     * This method used to set the movie posters on a PosterAdapter
     * it is used when you have this list of thumbnails from api calls
     * @param thumbPaths The new thumbnails to display
     */
    public void setmThumbPaths(String[] thumbPaths) {
        mThumbPaths = thumbPaths;
        dataFromApi = true;
        notifyDataSetChanged();
    }

    /**
     * This method is used to set poster urls directly from movieEntry from room db
     *
     */
    public void setmMovieEntries(List<MovieEntry> movieEntries){
        dataFromApi = false;
        mMovieEntries = movieEntries;
        notifyDataSetChanged();
    }


}
