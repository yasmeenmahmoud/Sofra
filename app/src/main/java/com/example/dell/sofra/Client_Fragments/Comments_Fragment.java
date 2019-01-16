package com.example.dell.sofra.Client_Fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.dell.sofra.Client_Adapter.CommentsAdapter;
import com.example.dell.sofra.Model.AddReviw_Response;
import com.example.dell.sofra.Model.RestaurantReview_Data;
import com.example.dell.sofra.Model.RestaurantReview_Response;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;
import com.example.dell.sofra.Helper.SharedPereferenceClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Comments_Fragment extends Fragment {
    CommentsAdapter commentsAdapter;
    List<RestaurantReview_Data> comments = new ArrayList<>();
    RecyclerView commentsrecyclerview;
    View myview;
    String api_token;
    int restaurant_id;
    @BindView(R.id.addreview_btn)
    Button addreviewBtn;
    Unbinder unbinder;
    EditText writecomment;
    String comment;
    Dialog myDialog;
    RatingBar ratingBar;
    float rate;
    String apitoken;

    public Comments_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_comments_, container, false);
        unbinder = ButterKnife.bind(this, myview);
        myDialog = new Dialog(getContext());

        SharedPereferenceClass sharedPereferenceClass = new SharedPereferenceClass();
        api_token = "HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB";
        restaurant_id = sharedPereferenceClass.getintStoredKey(getContext(), "restaurant_id");
        addcomments();
        return myview;
    }

    public void addcomments() {
        commentsrecyclerview = myview.findViewById(R.id.commentsrecyclerview);
        commentsrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<RestaurantReview_Response> call = apiInterface.getRestaurantReviews(restaurant_id, api_token);
        call.enqueue(new Callback<RestaurantReview_Response>() {
            @Override
            public void onResponse(Call<RestaurantReview_Response> call, Response<RestaurantReview_Response> response) {
                RestaurantReview_Response restaurantsList_response = response.body();
                try {


                    if (restaurantsList_response != null) {
                        comments = restaurantsList_response.getData().getData();
                    }
                    commentsAdapter = new CommentsAdapter(getContext(), comments);
                    commentsrecyclerview.setAdapter(commentsAdapter);
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<RestaurantReview_Response> call, Throwable t) {
                Toast.makeText(getContext(), "request faild ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addReview() {
        apitoken = "K1X6AzRlJFeVbGnHwGYsdCu0ETP1BqYC7DpMTZ3zLvKgU5feHMvsEEnKTpzh";
        Button addreview, cancel;
        myDialog.setContentView(R.layout.addreview_popup);
        writecomment = (EditText) myDialog.findViewById(R.id.writecomment);
        ratingBar = myDialog.findViewById(R.id.C_conmmentrating);
        addreview = (Button) myDialog.findViewById(R.id.addreview_btn);
        cancel = (Button) myDialog.findViewById(R.id.cancel);
        addreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = writecomment.getText().toString();
                rate = ratingBar.getRating();
                Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
                Call<AddReviw_Response> call = apiInterface.addReview(rate, comment, restaurant_id, apitoken);
                call.enqueue(new Callback<AddReviw_Response>() {
                    @Override
                    public void onResponse(Call<AddReviw_Response> call, Response<AddReviw_Response> response) {
                        AddReviw_Response addReviw_response = response.body();
                        if (addReviw_response != null) {
                            Log.i("myrespondmessage", addReviw_response.getMsg());
                            Toast.makeText(getContext(), addReviw_response.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddReviw_Response> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.toString());
                        Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.addreview_btn)
    public void onViewClicked() {
        addReview();
    }
}
