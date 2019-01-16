package com.example.dell.sofra.Client_Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.sofra.Helper.AddressHelper;
import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.example.dell.sofra.Model.ChangeState_Response;
import com.example.dell.sofra.Model.EditRestaurantInfoResponse;
import com.example.dell.sofra.Model.RestaurantDetails_Response;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.example.dell.sofra.Helper.SharedPereferenceClass.SELL;
import static com.example.dell.sofra.Register_Fragment.region_response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Playstore_Fragment extends Fragment {
    Button login;
    View view;
    SharedPereferenceClass sharedPereferenceClass;
    @BindView(R.id.home)
    TextView home;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.P_city)
    TextView PCity;
    @BindView(R.id.P_region)
    TextView PRegion;
    @BindView(R.id.P_minimumcharge)
    TextView PMinimumcharge;
    @BindView(R.id.P_deliverycost)
    TextView PDeliverycost;
    Unbinder unbinder;
    int restaurant_id;
    @BindView(R.id.switch1)
    Switch switch1;
    //    @BindView(R.id._city)
//    EditText City;
//    @BindView(R.id.edt_region)
//    EditText edtRegion;
    @BindView(R.id.edt_minimumcharge)
    EditText edtMinimumcharge;
    @BindView(R.id.edt_deliverycost)
    EditText edtDeliverycost;
    //    @BindView(R.id.login2_btn)
//    Button login2Btn;
    String api_token;
    @BindView(R.id.edit_btn)
    Button editBtn;
    String avaliability;
    //    @BindView(R.id.cityspinner)
//    Spinner cityspinner;
    @BindView(R.id.regionspinner)
    Spinner regionspinner;
    int region_id;
    @BindView(R.id.cityspinner)
    Spinner cityspinner;
    AddressHelper addressHelper;
    String editavailability;
    String getavailability;
    @BindView(R.id.simpleProgressBar)
    ProgressBar simpleProgressBar;
    @BindView(R.id.relativelayout1)
    RelativeLayout relativelayout1;
    @BindView(R.id.relativelayout2)
    RelativeLayout relativelayout2;

    public Playstore_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playstore_, container, false);
        unbinder = ButterKnife.bind(this, view);
        sharedPereferenceClass = new SharedPereferenceClass();
        if (sharedPereferenceClass.getStoredKey(getActivity(), SELL).equals(SELL)) {
            api_token = sharedPereferenceClass.getStoredKey(getActivity(), "RESTAURANT_TOKEN");
            addressHelper = new AddressHelper(getActivity(), regionspinner, cityspinner);
            status.setVisibility(GONE);
            switch1.setVisibility(View.VISIBLE);
            relativelayout1.setVisibility(View.VISIBLE);
            relativelayout2.setVisibility(View.VISIBLE);
            addressHelper.selectCitySpinner();
            addressHelper.regionSpinner(regionspinner);
            edtDeliverycost.setText(sharedPereferenceClass.getStoredKey(getContext(), "RESTAURANTDELIVERYCOST"));
            edtMinimumcharge.setText(sharedPereferenceClass.getStoredKey(getContext(), "RESTAURANTMINIMAMCHARGE"));
            avaliability = sharedPereferenceClass.getStoredKey(getContext(), "RESTAURANTAVAILABILITY");
            if (avaliability.equals("open")) {
                switch1.setChecked(true);
                switch1.setText("open");
                switch1.setTextColor(Color.GREEN);
            } else {
                switch1.setChecked(false);
                switch1.setText("close");
                switch1.setTextColor(Color.GRAY);
            }
            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (switch1.isChecked()) {
                        switch1.setText("open");
                        switch1.setTextColor(Color.GREEN);
                        editavailability = "open";
                        sharedPereferenceClass.storeKey(getContext(), "RESTAURANTAVAILABILITY", "open");
                    } else {
                        switch1.setText("close");
                        switch1.setTextColor(Color.GRAY);
                        editavailability = "close";
                        sharedPereferenceClass.storeKey(getContext(), "RESTAURANTAVAILABILITY", "close");
                    }
                    Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
                    Call<ChangeState_Response> call = apiInterface.changeState(editavailability, api_token);
                    call.enqueue(new Callback<ChangeState_Response>() {
                        @Override
                        public void onResponse(Call<ChangeState_Response> call, Response<ChangeState_Response> response) {
                            ChangeState_Response changeState_response = response.body();
                            if (changeState_response != null) {
                                getavailability = changeState_response.getData().getAvailability();
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangeState_Response> call, Throwable t) {
                            Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            editBtn.setVisibility(View.VISIBLE);
            PCity.setVisibility(GONE);
            PDeliverycost.setVisibility(GONE);
            PRegion.setVisibility(GONE);
            PMinimumcharge.setVisibility(GONE);

        } else {
            restaurant_id = sharedPereferenceClass.getintStoredKey(getContext(), "restaurant_id");
            relativelayout1.setVisibility(GONE);
            relativelayout2.setVisibility(GONE);
            addRestaurantDetails();
            switch1.setVisibility(GONE);
            editBtn.setVisibility(GONE);
            cityspinner.setVisibility(GONE);
            edtDeliverycost.setVisibility(GONE);
            regionspinner.setVisibility(GONE);
            edtMinimumcharge.setVisibility(GONE);

        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void addRestaurantDetails() {
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<RestaurantDetails_Response> call = apiInterface.getRestaurantDetails(restaurant_id);
        call.enqueue(new Callback<RestaurantDetails_Response>() {
            @Override
            public void onResponse(Call<RestaurantDetails_Response> call, Response<RestaurantDetails_Response> response) {
                RestaurantDetails_Response restaurantDetails_response = response.body();
                if (restaurantDetails_response != null) {
                    try {
                        status.setText(restaurantDetails_response.getData().getAvailability());
                        PCity.setText(restaurantDetails_response.getData().getRegion().getCity().getName());
                        PDeliverycost.setText(restaurantDetails_response.getData().getDeliveryCost());
                        PRegion.setText(restaurantDetails_response.getData().getRegion().getName());
                        PMinimumcharge.setText(restaurantDetails_response.getData().getMinimumCharger());
                        Log.i("MESSAGE", restaurantDetails_response.getMsg());

                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<RestaurantDetails_Response> call, Throwable t) {
                Toast.makeText(getContext(), "request faild ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.edit_btn)
    public void onViewClicked() {
        editRestaurantInfo();
        simpleProgressBar.setVisibility(View.VISIBLE);
    }

    private void editRestaurantInfo() {
        final String edit_city = cityspinner.getSelectedItem().toString();
        final String edit_deliverycost = edtDeliverycost.getText().toString();
        final String edit_minimumcharge = edtMinimumcharge.getText().toString();
        final String edit_regionid = regionspinner.getSelectedItem().toString();
        region_id = 0;
        for (int i = 0; i < region_response.getData().getData().size(); i++) {
            if (region_response.getData().getData().get(i).getName().equals(edit_regionid)) {
                region_id = region_response.getData().getData().get(i).getId();
                break;
            }
            Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
            Call<EditRestaurantInfoResponse> call = apiInterface.EditRestaurantInfo(edit_city, region_id, edit_deliverycost, edit_minimumcharge
                    , getavailability, api_token);
            call.enqueue(new Callback<EditRestaurantInfoResponse>() {
                @Override
                public void onResponse(Call<EditRestaurantInfoResponse> call, Response<EditRestaurantInfoResponse> response) {
                    EditRestaurantInfoResponse editRestaurantInfoResponse = response.body();
                    if (editRestaurantInfoResponse != null) {
                        Log.i("EDITMESSAGE", editRestaurantInfoResponse.getMsg());
                        Toast.makeText(getActivity(), editRestaurantInfoResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        sharedPereferenceClass.storeKey(getContext(), "RESTAURANTCITY", edit_city);
                        sharedPereferenceClass.storeKey(getContext(), "RESTAURANTDELIVERYCOST", edit_deliverycost);
                        sharedPereferenceClass.storeKey(getContext(), "RESTAURANTADDRESS", edit_regionid);
                        sharedPereferenceClass.storeKey(getContext(), "RESTAURANTMINIMAMCHARGE", edit_minimumcharge);
                        sharedPereferenceClass.storeKey(getContext(), "RESTAURANTAVAILABILITY", getavailability);
                        simpleProgressBar.setVisibility(GONE);
                    }
                }

                @Override
                public void onFailure(Call<EditRestaurantInfoResponse> call, Throwable t) {
                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}


