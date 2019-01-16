package com.example.dell.sofra.Restaurants_Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.dell.sofra.Model.MyOffers_Data;
import com.example.dell.sofra.Model.MyOffers_Response;
import com.example.dell.sofra.Model.OffersList_Data;
import com.example.dell.sofra.Client_Adapter.OffersAdapter;
import com.example.dell.sofra.Model.OffersLists_Response;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OurOffers_Fragment extends Fragment {
    Button addnewoffer;

    OffersAdapter offersAdapter;
    List<MyOffers_Data> offerslist = new ArrayList<>();
    RecyclerView ourofferrecycler;
    View myview;

    public OurOffers_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.fragment_our_offers_, container, false);
        addOfferslist();
        addnewoffer = myview.findViewById(R.id.addnewoffer);
        addnewoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.f2Content, new AddNewOffer_Fragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return myview;

    }

    private void addOfferslist() {
        ourofferrecycler = myview.findViewById(R.id.ouroffersrecyclerview);
        ourofferrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<MyOffers_Response> call = apiInterface.getMyOffersList();
        call.enqueue(new Callback<MyOffers_Response>() {
            @Override
            public void onResponse(Call<MyOffers_Response> call, Response<MyOffers_Response> response) {
                MyOffers_Response myOffers_response = response.body();
                try {
                    if (myOffers_response != null) {
                        offerslist = myOffers_response.getData().getData();

                        Log.i("MESSAGE", myOffers_response.getMsg());
                        offersAdapter = new OffersAdapter(getContext(), R.layout.offers_listitemview, offerslist);
                        ourofferrecycler.setAdapter(offersAdapter);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<MyOffers_Response> call, Throwable t) {
                Toast.makeText(getContext(), "request faild ", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
