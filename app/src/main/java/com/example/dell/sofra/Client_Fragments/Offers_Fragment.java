package com.example.dell.sofra.Client_Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dell.sofra.Client_Adapter.OffersAdapter;
import com.example.dell.sofra.Model.OffersList_Data;
import com.example.dell.sofra.Model.OffersLists_Response;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Offers_Fragment extends Fragment {
    OffersAdapter offersAdapter;
    List<OffersList_Data> offerslist = new ArrayList<>();
    RecyclerView offerrecycler;
    View myview;

    public Offers_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.fragment_offers_, container, false);
        addOfferslist();
        return myview;
    }

    private void addOfferslist() {

        offerrecycler = myview.findViewById(R.id.offersrecycer);
        offerrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<OffersLists_Response> call = apiInterface.getOffersList();
        call.enqueue(new Callback<OffersLists_Response>() {
            @Override
            public void onResponse(Call<OffersLists_Response> call, Response<OffersLists_Response> response) {
                OffersLists_Response offersLists_response = response.body();
                if (offersLists_response != null) {
                    offerslist = offersLists_response.getData().getData();
                }
                Log.i("MESSAGE", offersLists_response.getMsg());
                offersAdapter = new OffersAdapter(getContext(), offerslist);
                offerrecycler.setAdapter(offersAdapter);
            }

            @Override
            public void onFailure(Call<OffersLists_Response> call, Throwable t) {
                Toast.makeText(getContext(), "request faild ", Toast.LENGTH_SHORT).show();

            }
        });

    }
}