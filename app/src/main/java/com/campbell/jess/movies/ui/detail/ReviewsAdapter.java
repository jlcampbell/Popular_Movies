package com.campbell.jess.movies.ui.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.campbell.jess.movies.R;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsAdapterViewHolder> {

    private String[] mReviewStrings;

    public class ReviewsAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView mReview;


        public ReviewsAdapterViewHolder(View view){
            super(view);
            mReview = view.findViewById(R.id.tv_review_item);

        }
    }



    @NonNull
    @Override
    public ReviewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_review;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = layoutInflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ReviewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapterViewHolder holder, int position) {
        TextView review = holder.mReview;
        String reviewBody = mReviewStrings[position];
        review.setText(reviewBody);
    }

    @Override
    public int getItemCount() {
        if (null == mReviewStrings) return 0;
        return mReviewStrings.length;
    }

    public void setReviewStrings(String[] reviewStrings){
        mReviewStrings = reviewStrings;
        notifyDataSetChanged();
    }
}
