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

import com.example.dell.sofra.Client_Adapter.PreviousOrderAdapter;
import com.example.dell.sofra.Model.MyOrders_Data;
import com.example.dell.sofra.Model.MyOrders_Response;
import com.example.dell.sofra.R;
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
public class PreviousOrders_Fragment extends Fragment {
    PreviousOrderAdapter previousOrderAdapter;
    List<MyOrders_Data> previousorders = new ArrayList<>();
    RecyclerView previousorderrecucle;
    View myview;
    SharedPereferenceClass sharedPereferenceClass;
   public static String api_token;
   public static MyOrders_Response myOrders_response;
   public static int order_id;
    public PreviousOrders_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_previous_orders_, container, false);
//        sharedPereferenceClass=new SharedPereferenceClass();
//        if(sharedPereferenceClass.getStoredKey(getContext(),SharedPereferenceClass.API_TOKEN)!=null)
//        { api_token=sharedPereferenceClass.getStoredKey(getContext(),SharedPereferenceClass.API_TOKEN);
//            Toast.makeText(getContext(), api_token, Toast.LENGTH_SHORT).show();
//        }
//        else{            Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
//        }
        api_token = "K1X6AzRlJFeVbGnHwGYsdCu0ETP1BqYC7DpMTZ3zLvKgU5feHMvsEEnKTpzh";

        addpreviousOrders();
        return myview;
    }

    private void addpreviousOrders() {

        previousorderrecucle = myview.findViewById(R.id.previousorderrecycle);
        previousorderrecucle.setLayoutManager(new LinearLayoutManager(getContext()));
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<MyOrders_Response> call = apiInterface.getOrderssList(api_token);
        call.enqueue(new Callback<MyOrders_Response>() {
            @Override
            public void onResponse(Call<MyOrders_Response> call, Response<MyOrders_Response> response) {
                 myOrders_response = response.body();
                if (myOrders_response != null) {
                    previousorders = myOrders_response.getData().getData();

                }
                Log.i("MYRESPONSEMESSAGE", myOrders_response.getMsg());
                previousOrderAdapter = new PreviousOrderAdapter(getContext(), previousorders);
                previousorderrecucle.setAdapter(previousOrderAdapter);
            }

            @Override
            public void onFailure(Call<MyOrders_Response> call, Throwable t) {
                Toast.makeText(getContext(), "request faild ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
