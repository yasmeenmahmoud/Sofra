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
import com.example.dell.sofra.Restaurant_Adapter.NewOrderAdapter;
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
public class NewOrders_Fragment extends Fragment {
    NewOrderAdapter newOrderAdapter;
    List<RestaurantOrders_Data> neworders = new ArrayList<>();
    RecyclerView neworderrecycle;
    View myview;
String api_token;
public static  MyOrdersRestaurant_Response myOrdersRestaurant_response;
SharedPereferenceClass sharedPereferenceClass;
   // public static final String STATE = "pending";

    public NewOrders_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myview=inflater.inflate(R.layout.fragment_new_orders_, container, false);
        sharedPereferenceClass=new SharedPereferenceClass();
      //  sharedPereferenceClass.storeKey(getContext(),"OURNEWORDERS",STATE);
        api_token = "EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv";

        addCurrentOrders();
        return myview;
    }
    private void addCurrentOrders() {
        neworderrecycle = myview.findViewById(R.id.newordersrecyclerview);
        neworderrecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<MyOrdersRestaurant_Response> call = apiInterface.getrestaurantOrderssList(api_token,"completed");
        call.enqueue(new Callback<MyOrdersRestaurant_Response>() {
            @Override
            public void onResponse(Call<MyOrdersRestaurant_Response> call, Response<MyOrdersRestaurant_Response> response) {
                 myOrdersRestaurant_response = response.body();
                 try {

                if (myOrdersRestaurant_response != null) {
                    neworders = myOrdersRestaurant_response.getData().getData();
                    Log.i("MYRESPONSEMESSAGE", myOrdersRestaurant_response.getMsg());
                    newOrderAdapter = new NewOrderAdapter(getContext(), neworders);
                    neworderrecycle.setAdapter(newOrderAdapter);
                }
                 }catch (Exception e){}

            }

            @Override
            public void onFailure(Call<MyOrdersRestaurant_Response> call, Throwable t) {
                Toast.makeText(getContext(), "request faild ", Toast.LENGTH_SHORT).show();
            }
        });

    }
 }
