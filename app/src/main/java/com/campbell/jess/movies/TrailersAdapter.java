package com.campbell.jess.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerAdapterViewHolder> {
    private String[] trailerIDs;

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
        String idForThisMovie = trailerIDs[position];
        trailer.setText(idForThisMovie);
    }

    @Override
    public int getItemCount() {
        if (null == trailerIDs) return 0;
        return trailerIDs.length;
    }

    public void setTrailerIDs(String[] iDs) {
        trailerIDs = iDs;
        notifyDataSetChanged();
    }
}
