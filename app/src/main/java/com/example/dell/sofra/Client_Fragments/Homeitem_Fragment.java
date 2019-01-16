package com.example.dell.sofra.Client_Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dell.sofra.Helper.AddressHelper;
import com.example.dell.sofra.Client_Adapter.HomeAdapter;
import com.example.dell.sofra.Helper.EndlessRecyclerViewScrollListener;
import com.example.dell.sofra.Model.RestaurantsList_Data;
import com.example.dell.sofra.Model.RestaurantsList_Response;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Homeitem_Fragment extends Fragment {
    HomeAdapter homeAdapter;
    Spinner cityspinner;
    List<RestaurantsList_Data> restaurants = new ArrayList<>();
    RecyclerView restrauntsrecyclerview;
    View myview;
    int restaurant_Page = 1;
    public static RestaurantsList_Response restaurantsList_response;
    SearchView searchView;
    public static final String HOME_FRAGMENT = "home_fragment";
    @BindView(R.id.simpleProgressBar)
    ProgressBar simpleProgressBar;
    Unbinder unbinder;
    @BindView(R.id.search)
    SearchView search;
    String cityname;
    public static Home home;

    public Homeitem_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.fragment_homeitem_, container, false);
        cityspinner = myview.findViewById(R.id.cityspinner);
        restrauntsrecyclerview = myview.findViewById(R.id.homerecyclerview);
        home = new Home();
        addRestaurantsData(restaurant_Page);
        searchView = myview.findViewById(R.id.search);
        unbinder = ButterKnife.bind(this, myview);
        simpleProgressBar.setVisibility(View.VISIBLE);
        homeAdapter = new HomeAdapter(getContext(), restaurants);
        AddressHelper addressHelper =new AddressHelper(getContext(),cityspinner);
        addressHelper.homecitySpinner();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // homeAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                homeAdapter.getFilter().filter(s);
                return true;
            }
        });

        return myview;
    }
    @Override
    public void onResume() {
        super.onResume();
        Home home = (Home) getActivity();
        home.  getSupportActionBar().setTitle("طلب طعام");

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        restrauntsrecyclerview.setLayoutManager(linearLayoutManager);
        restrauntsrecyclerview.setItemAnimator(new DefaultItemAnimator());
        restrauntsrecyclerview.setAdapter(homeAdapter);
        restrauntsrecyclerview.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                restaurant_Page++;
                addRestaurantsData(restaurant_Page);
            }
        });
    }

    private void addRestaurantsData(int page) {
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<RestaurantsList_Response> call = apiInterface.getRestaurantlist(page);
        call.enqueue(new Callback<RestaurantsList_Response>() {
            @Override
            public void onResponse(Call<RestaurantsList_Response> call, Response<RestaurantsList_Response> response) {
                restaurantsList_response = response.body();
                try {

                    if (restaurantsList_response != null) {
                        restaurants.addAll(restaurantsList_response.getData().getData());
                        homeAdapter.notifyItemChanged(homeAdapter.getItemCount(), restaurants.size() - 1);
                        simpleProgressBar.setVisibility(View.GONE);
                        //to filter using city name
                        for (int i = 0; i < restaurantsList_response.getData().getData().size(); i++) {
                            cityname = restaurantsList_response.getData().getData().get(i).getRegion().getCity().getName();
                        }
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<RestaurantsList_Response> call, Throwable t) {
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
