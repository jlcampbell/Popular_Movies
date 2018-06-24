package com.campbell.jess.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsAdapterViewHolder> {


    public class ReviewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mReview;
        public ReviewsAdapterViewHolder(View view){
            super(view);
            mReview = view.findViewById()
        }

        @Override
        public void onClick(View view) {

        }
    }



    @NonNull
    @Override
    public ReviewsAdapter.ReviewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewsAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
