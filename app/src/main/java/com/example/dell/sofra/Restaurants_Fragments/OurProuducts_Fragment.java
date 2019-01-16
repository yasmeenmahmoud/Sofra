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

import com.example.dell.sofra.Model.MyItems_Data;
import com.example.dell.sofra.Model.MyItems_Response;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Restaurant_Adapter.OurProductsAdapter;
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
public class OurProuducts_Fragment extends Fragment {
    Button addnewproduct;
    OurProductsAdapter ourProductsAdapter;
    List<MyItems_Data> meals = new ArrayList<>();
    RecyclerView ourproductsrecyclerview;
    View myview;
    private String api_token;
    SharedPereferenceClass sharedPereferenceClass;

    public OurProuducts_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.fragment_our_prouducts_, container, false);
        addnewproduct = myview.findViewById(R.id.addnewproduct);
        api_token = "EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv";
        addnewproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.f2Content, new AddNewProduct_Fragment());
                //  transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        // Inflate the layout for this fragment
        addRestaurantsmeals();
        return myview;
    }

    private void addRestaurantsmeals() {
        ourproductsrecyclerview = myview.findViewById(R.id.ourproductsrecyclerview);
        ourproductsrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<MyItems_Response> call = apiInterface.getMenuItems(api_token);
        call.enqueue(new Callback<MyItems_Response>() {
            @Override
            public void onResponse(Call<MyItems_Response> call, Response<MyItems_Response> response) {
                MyItems_Response myItems_response = response.body();
                try {


                    if (myItems_response != null) {
                        meals = myItems_response.getData().getData();

                        Log.i("MESSAGE", myItems_response.getMsg());
                        ourProductsAdapter = new OurProductsAdapter(getContext(), meals);
                        ourproductsrecyclerview.setAdapter(ourProductsAdapter);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<MyItems_Response> call, Throwable t) {
                Toast.makeText(getContext(), "request faild ", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
