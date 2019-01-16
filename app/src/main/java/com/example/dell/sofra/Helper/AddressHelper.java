package com.example.dell.sofra.Helper;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.example.dell.sofra.Model.CityList_Response;
import com.example.dell.sofra.Model.Region_Response;
import com.example.dell.sofra.Model.RestaurantItems_Data;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dell.sofra.Register_Fragment.region_response;

public class AddressHelper {
    public Spinner regionspinner;
    public Spinner cityspinner;
    private Context context;
    private int id;
   private ArrayList<String> citiessname = new ArrayList<>();
  private   SharedPereferenceClass sharedPereferenceClass=new SharedPereferenceClass();

    public AddressHelper(Context context, Spinner regionspinner, Spinner cityspinner) {
        this.context = context;
        this.regionspinner = regionspinner;
        this.cityspinner = cityspinner;

    }

    public AddressHelper(Context context, Spinner cityspinner) {
        this.context = context;
        this.cityspinner = cityspinner;

    }

    public void regionSpinner(int id) {
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<Region_Response> call = apiInterface.getRegionList(id);
        call.enqueue(new Callback<Region_Response>() {
            @Override
            public void onResponse(Call<Region_Response> call, Response<Region_Response> response) {
                region_response = response.body();
                ArrayList<String> regionsname = new ArrayList<>();
                final ArrayList<Integer> regionsid = new ArrayList<>();
                regionsname.add("اخترالحى.... ");
                for (int i = 0; i < region_response.getData().getData().size(); i++) {
                    regionsname.add(region_response.getData().getData().get(i).getName());
                    Log.i("City", region_response.getData().getData().get(i).getName());
                    regionsid.add(region_response.getData().getData().get(i).getId());
                }
                regionspinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, regionsname));
                regionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Region_Response> call, Throwable t) {
            }
        });

    }
    public void regionSpinner(final Spinner regionspinner) {
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<Region_Response> call = apiInterface.getRegionList(id);
        call.enqueue(new Callback<Region_Response>() {
            @Override
            public void onResponse(Call<Region_Response> call, Response<Region_Response> response) {
                region_response = response.body();
                ArrayList<String> regionsname = new ArrayList<>();
                final ArrayList<Integer> regionsid = new ArrayList<>();
                regionsname.add( sharedPereferenceClass.getStoredKey(context, "RESTAURANTADDRESS"));
                for (int i = 0; i < region_response.getData().getData().size(); i++) {
                    regionsname.add(region_response.getData().getData().get(i).getName());
                    Log.i("City", region_response.getData().getData().get(i).getName());
                    regionsid.add(region_response.getData().getData().get(i).getId());
                }
                regionspinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, regionsname));
                regionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Region_Response> call, Throwable t) {
            }
        });

    }

    public void citySpinner() {
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<CityList_Response> call = apiInterface.getCityList();
        call.enqueue(new Callback<CityList_Response>() {
            @Override
            public void onResponse(Call<CityList_Response> call, Response<CityList_Response> response) {
                final CityList_Response cityList_response = response.body();
                ArrayList<String> citiessname = new ArrayList<>();
                final ArrayList<Integer> citiesid = new ArrayList<>();
                citiessname.add("اخترالمدينه.... ");
                for (int i = 0; i < cityList_response.getData().getData().size(); i++) {
                    citiessname.add(cityList_response.getData().getData().get(i).getName());
                    Log.i("City", cityList_response.getData().getData().get(i).getName());
                    citiesid.add(cityList_response.getData().getData().get(i).getId());
                }
                cityspinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, citiessname));
                cityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {
                        if (position != 0) {
                            int id = citiesid.get(position - 1);
                            regionSpinner(id);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<CityList_Response> call, Throwable t) {
            }
        });

    }

    public void homecitySpinner() {
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<CityList_Response> call = apiInterface.getCityList();
        call.enqueue(new Callback<CityList_Response>() {
            @Override
            public void onResponse(Call<CityList_Response> call, Response<CityList_Response> response) {
                final CityList_Response cityList_response = response.body();
                final ArrayList<Integer> citiesid = new ArrayList<>();
                citiessname.add("اخترالمدينه.... ");
                for (int i = 0; i < cityList_response.getData().getData().size(); i++) {
                    citiessname.add(cityList_response.getData().getData().get(i).getName());
                    Log.i("City", cityList_response.getData().getData().get(i).getName());
                    citiesid.add(cityList_response.getData().getData().get(i).getId());
                }
                cityspinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, citiessname));
                cityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<CityList_Response> call, Throwable t) {
            }
        });

    }
    public void SetSpinnerSelection(Spinner spinner,String text) {
        for (int i = 0; i < citiessname.size(); i++) {
            if (citiessname.get(i).equals(text)) {
                citiessname.add(text);
                spinner.setSelection(i);
            }
        }
    }
    public void selectCitySpinner() {
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<CityList_Response> call = apiInterface.getCityList();
        call.enqueue(new Callback<CityList_Response>() {
            @Override
            public void onResponse(Call<CityList_Response> call, Response<CityList_Response> response) {
                final CityList_Response cityList_response = response.body();
                ArrayList<String> citiessname = new ArrayList<>();
                final ArrayList<Integer> citiesid = new ArrayList<>();
                citiessname.add(sharedPereferenceClass.getStoredKey(context, "RESTAURANTCITY"));
                for (int i = 0; i < cityList_response.getData().getData().size(); i++) {
                    citiessname.add(cityList_response.getData().getData().get(i).getName());
                    Log.i("City", cityList_response.getData().getData().get(i).getName());
                    citiesid.add(cityList_response.getData().getData().get(i).getId());
                }
                cityspinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, citiessname));
                cityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {
                        if (position != 0) {
                             id = citiesid.get(position - 1);
                            regionSpinner(id);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<CityList_Response> call, Throwable t) {
            }
        });

    }
    }
