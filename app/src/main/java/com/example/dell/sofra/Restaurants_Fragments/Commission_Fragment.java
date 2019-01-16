package com.example.dell.sofra.Restaurants_Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.sofra.Model.Comission_Response;
import com.example.dell.sofra.Model.RestaurantDetails_Response;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Commission_Fragment extends Fragment {


    @BindView(R.id.restaurantsells)
    TextView restaurantsells;
    @BindView(R.id.app_comissions)
    TextView appComissions;
    @BindView(R.id.payments)
    TextView payments;
    @BindView(R.id.net_comissions)
    TextView netComissions;
    Unbinder unbinder;

    public Commission_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_commission_, container, false);
        unbinder = ButterKnife.bind(this, view);
        getComissionsDetails();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getComissionsDetails() {
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<Comission_Response> call = apiInterface.getComissions();
        call.enqueue(new Callback<Comission_Response>() {
            @Override
            public void onResponse(Call<Comission_Response> call, Response<Comission_Response> response) {
                Comission_Response comission_response = response.body();
                if (comission_response != null) {
                    Toast.makeText(getContext(), "request sucess ", Toast.LENGTH_SHORT).show();
                    try {
                        restaurantsells.setText(comission_response.getData().getTotal().toString());
                        payments.setText(comission_response.getData().getPayments().toString());
                        appComissions.setText(comission_response.getData().getCommission());
                        netComissions.setText(comission_response.getData().getNetCommissions().toString());
                        Log.i("MESSAGE", comission_response.getMsg());
                    }catch (Exception e){

                    }
                }
            }
            @Override
            public void onFailure(Call<Comission_Response> call, Throwable t) {
                Toast.makeText(getContext(), "request faild ", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
