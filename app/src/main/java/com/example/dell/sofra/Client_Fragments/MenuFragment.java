package com.example.dell.sofra.Client_Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.sofra.Client_Adapter.MenuAdapter;
import com.example.dell.sofra.Model.MyItems_Data;
import com.example.dell.sofra.Model.MyItems_Response;
import com.example.dell.sofra.Model.RestaurantItems_Data;
import com.example.dell.sofra.Model.RestaurantItems_Response;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;
import com.example.dell.sofra.Helper.SharedPereferenceClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dell.sofra.Helper.SharedPereferenceClass.USER;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    MenuAdapter menuAdapter;
    List<RestaurantItems_Data> meals = new ArrayList<>();
    List<MyItems_Data> Mymeals = new ArrayList<>();
    RecyclerView menussrecyclerview;
    View myview;
    int restaurant_id;
    String api_token;
    MyItems_Response myItems_response;
    @BindView(R.id.simpleProgressBar)
    ProgressBar simpleProgressBar;
    Unbinder unbinder;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myview = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this, myview);
        api_token = "EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv";
        SharedPereferenceClass sharedPereferenceClass = new SharedPereferenceClass();
        if (sharedPereferenceClass.getStoredKey(getContext(), USER).equals(USER)) {
            simpleProgressBar.setVisibility(View.VISIBLE);
            restaurant_id = sharedPereferenceClass.getintStoredKey(getContext(), "restaurant_id");
            addRestaurantsmeals();
        } else {
            simpleProgressBar.setVisibility(View.VISIBLE);
            addMyItemsRestaurantsmeals();
        }
        return myview;
    }

    private void addRestaurantsmeals() {
        menussrecyclerview = myview.findViewById(R.id.menurecyclerview);
        menussrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<RestaurantItems_Response> call = apiInterface.getRestaurantItems(restaurant_id);
        call.enqueue(new Callback<RestaurantItems_Response>() {
            @Override
            public void onResponse(Call<RestaurantItems_Response> call, Response<RestaurantItems_Response> response) {
                RestaurantItems_Response restaurantItems_response = response.body();
                if (restaurantItems_response != null) {
                    meals = restaurantItems_response.getData().getData();
                    Log.i("MESSAGE", restaurantItems_response.getMsg());
                    try {
                        menuAdapter = new MenuAdapter(getActivity(), meals);
                        menussrecyclerview.setAdapter(menuAdapter);
                        simpleProgressBar.setVisibility(View.GONE);
                    } catch (Exception e) {

                    }

                }

            }

            @Override
            public void onFailure(Call<RestaurantItems_Response> call, Throwable t) {
                Toast.makeText(getContext(), "request faild ", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void addMyItemsRestaurantsmeals() {
        menussrecyclerview = myview.findViewById(R.id.menurecyclerview);
        menussrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<MyItems_Response> call = apiInterface.getMenuItems(api_token);
        call.enqueue(new Callback<MyItems_Response>() {
            @Override
            public void onResponse(Call<MyItems_Response> call, Response<MyItems_Response> response) {
                myItems_response = response.body();
                if (myItems_response != null) {
                    Mymeals = myItems_response.getData().getData();
                    Log.i("MESSAGE", myItems_response.getMsg());
                    try {
                        menuAdapter = new MenuAdapter(getActivity(), Mymeals, R.layout.menu_listitem);
                        menussrecyclerview.setAdapter(menuAdapter);
                        simpleProgressBar.setVisibility(View.GONE);
                    } catch (Exception e) {

                    }

                }

            }

            @Override
            public void onFailure(Call<MyItems_Response> call, Throwable t) {
                Toast.makeText(getContext(), "request faild ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
