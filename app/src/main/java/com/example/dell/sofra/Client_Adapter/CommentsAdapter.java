package com.example.dell.sofra.Client_Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dell.sofra.Model.RestaurantReview_Data;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Client_Fragments.RestaurantDetailsModel;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.Filesviewholder> {
    private Context context;
    private List<RestaurantReview_Data> data;
    private LayoutInflater layoutInflater;

    public CommentsAdapter(Context context, List<RestaurantReview_Data> data) {
        this.context = context;
        this.data = data;
        layoutInflater = layoutInflater.from(context);
    }

    @NonNull
    @Override
    public CommentsAdapter.Filesviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = layoutInflater.inflate(R.layout.comments_listitemview, null);
        CommentsAdapter.Filesviewholder filesviewholder = new CommentsAdapter.Filesviewholder(view);
        return filesviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentsAdapter.Filesviewholder filesviewholder, int i) {

        final RestaurantReview_Data currentposition = data.get(i);
        filesviewholder.commentName.setText(currentposition.getClient().getName());
        filesviewholder.commentdate.setText(currentposition.getCreatedAt());
        filesviewholder.comentcontent.setText(currentposition.getComment());
     //   filesviewholder.commentrating.setRating(currentposition.getCommentrating());
        if (currentposition.getRestaurant().getRate() != 0) {
            filesviewholder.commentrating.setNumStars(currentposition.getRestaurant().getRate());
            filesviewholder.commentrating.setRating(currentposition.getRestaurant().getRate());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Filesviewholder extends RecyclerView.ViewHolder {
        // Button favourite_btn;
        TextView commentName, commentdate, comentcontent;
RatingBar commentrating;
        public Filesviewholder(@NonNull View itemView) {
            super(itemView);
            commentName = itemView.findViewById(R.id.C_commentname);
            commentdate = itemView.findViewById(R.id.C_conmmentdate);
            comentcontent = itemView.findViewById(R.id.C_conmmentcontent);
            commentrating = itemView.findViewById(R.id.C_conmmentrating);

        }
    }
}
