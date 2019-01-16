package com.example.dell.sofra.Restaurants_Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dell.sofra.Model.MyOrdersRestaurant_Response;
import com.example.dell.sofra.Model.RestaurantOrders_Data;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Restaurant_Adapter.OurCurrentOrderAdapter;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;
import com.example.dell.sofra.Helper.SharedPereferenceClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OurCurrentOrders_Fragment extends Fragment {
    OurCurrentOrderAdapter ourCurrentOrderAdapter;
    List<RestaurantOrders_Data> ourcurrentorders = new ArrayList<>();
    RecyclerView ourcurrentorderrecycle;
    View myview;
    String api_token;
SharedPereferenceClass sharedPereferenceClass;
   public static final String STATE = "current";
    public OurCurrentOrders_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_ourcurrent_orders_, container, false);
        sharedPereferenceClass=new SharedPereferenceClass();
        sharedPereferenceClass.storeKey(getContext(),"OURCURRENTORDERS",STATE);
        api_token = "EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv";

        addourCurrentOrders();
        return myview;

    }

    private void addourCurrentOrders() {

        ourcurrentorderrecycle = myview.findViewById(R.id.ourcurrentorderrecucler);
        ourcurrentorderrecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<MyOrdersRestaurant_Response> call = apiInterface.getrestaurantOrderssList(api_token, "completed");
        call.enqueue(new Callback<MyOrdersRestaurant_Response>() {
            @Override
            public void onResponse(Call<MyOrdersRestaurant_Response> call, Response<MyOrdersRestaurant_Response> response) {
                MyOrdersRestaurant_Response myOrdersRestaurant_response = response.body();
                try {


                if (myOrdersRestaurant_response != null) {
                    ourcurrentorders = myOrdersRestaurant_response.getData().getData();
                    Log.i("CURRENTRESPONSEMESSAGE", myOrdersRestaurant_response.getMsg());
                    ourCurrentOrderAdapter = new OurCurrentOrderAdapter(getContext(), ourcurrentorders);
                    ourcurrentorderrecycle.setAdapter(ourCurrentOrderAdapter);
                }
                }
                catch (Exception e){}
            }

            @Override
            public void onFailure(Call<MyOrdersRestaurant_Response> call, Throwable t) {
                Toast.makeText(getContext(), "request faild ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}



