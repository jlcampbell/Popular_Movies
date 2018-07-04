package com.campbell.jess.movies.ui.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.campbell.jess.movies.R;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerAdapterViewHolder> {
    private String[] trailerIDs;
    private String[] trailerTitles;

    private final TrailersAdapterOnClickHandler mClickHandler;

    public interface TrailersAdapterOnClickHandler {
        void onClick(int position);
    }

    public TrailersAdapter(TrailersAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTrailer;

        public TrailerAdapterViewHolder(View view){
            super(view);
            mTrailer = view.findViewById(R.id.tv_trailer_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }

    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_trailer;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder holder, int position) {
        TextView trailer = holder.mTrailer;
        //String idForThisMovie = trailerIDs[position];
        String trailerTitle = trailerTitles[position];
        trailer.setText(trailerTitle);
    }

    @Override
    public int getItemCount() {
        if (null == trailerTitles) return 0;
        return trailerTitles.length;
    }
/**
    public void setTrailerIDs(String[] iDs) {
        trailerIDs = iDs;
        notifyDataSetChanged();
    }
**/
    public void setTrailerTitles(String[] titles) {
        trailerTitles = titles;
        notifyDataSetChanged();
    }

}
