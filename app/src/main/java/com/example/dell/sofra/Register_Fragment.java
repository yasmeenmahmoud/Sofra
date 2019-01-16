package com.example.dell.sofra;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dell.sofra.Client_Fragments.AddNewOrder_Fragment;
import com.example.dell.sofra.Client_Fragments.Home;
import com.example.dell.sofra.Helper.AddressHelper;
import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.example.dell.sofra.Model.Region_Response;
import com.example.dell.sofra.Presenter.UserRegister_Presenter;
import com.example.dell.sofra.Restaurants_Fragments.FollowRegister_Fragment;
import com.example.dell.sofra.Restaurants_Fragments.TheMainActivity;
import com.example.dell.sofra.View.UserRegister_View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.dell.sofra.Helper.SharedPereferenceClass.SELL;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register_Fragment extends Fragment implements UserRegister_View {
    Button next, follow;
    EditText name, email, password, confirmpassword, phone;
    String _email;
    String _password;
    String _name;
    String _confitmpassword;
    String _phone;
    String _city;
    String _region;
    String restaurant_region;
    String restaunt_city;
    String restaurant_name;
    String restaunt_email;
    String restaurant_password;
    String restaunt_confirmpassword;
    String restaurant_phone;
    View view;
    Spinner cityspinner, regionspinner;
    UserRegister_Presenter userRegister_presenter;
    SharedPereferenceClass sharedPereferenceClass;
    public static Region_Response region_response;
    @BindView(R.id.simpleProgressBar)
    ProgressBar simpleProgressBar;
    public static Login_Fragment login_fragment;
    Unbinder unbinder;
    public static int id;

    public Register_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register_, container, false);
        next = view.findViewById(R.id.next);
        follow = view.findViewById(R.id.folow);
        name = view.findViewById(R.id.r_name);
        password = view.findViewById(R.id.r_password);
        confirmpassword = view.findViewById(R.id.r_confirmpassword);
        email = view.findViewById(R.id.r_email);
        phone = view.findViewById(R.id.r_phone);
        regionspinner = view.findViewById(R.id.reginspinner);
        cityspinner = view.findViewById(R.id.cityspinner);
        AddressHelper addressHelper = new AddressHelper(getContext(), regionspinner, cityspinner);
        addressHelper.citySpinner();
        login_fragment = new Login_Fragment();
        userRegister_presenter = new UserRegister_Presenter(getContext(), this);
        sharedPereferenceClass = new SharedPereferenceClass();

        if (sharedPereferenceClass.getStoredKey(getActivity(), SELL).equals(SELL)) {
            next.setVisibility(View.GONE);
            name.setHint("اسم المطعم");
            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    restaunt_email = email.getText().toString();
                    restaurant_password = password.getText().toString();
                    restaurant_name = name.getText().toString();
                    restaunt_confirmpassword = confirmpassword.getText().toString();
                    restaurant_phone = phone.getText().toString();
                    restaunt_city = cityspinner.getSelectedItem().toString();
                    restaurant_region = regionspinner.getSelectedItem().toString();
                    int region_id = 0;
                    for (int i = 0; i < region_response.getData().getData().size(); i++) {
                        if (region_response.getData().getData().get(i).getName().equals(restaurant_region)) {
                            region_id = region_response.getData().getData().get(i).getId();
                            break;
                        }
                    }
                    sharedPereferenceClass.storeKey(getContext(), "restaurant_regionid", region_id);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", restaurant_name);
                    bundle.putString("email", restaunt_email);
                    bundle.putString("password", restaurant_password);
                    bundle.putString("confirmpassword", restaunt_confirmpassword);
                    bundle.putString("phone", restaurant_phone);
                    bundle.putString("region", restaurant_region);
                    bundle.putString("city", restaunt_city);
                    FragmentManager fragmentManager = getFragmentManager();
                    FollowRegister_Fragment followRegister_fragment = new FollowRegister_Fragment();
                    followRegister_fragment.setArguments(bundle);
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction
                            .replace(R.id.f2Content, followRegister_fragment)
                            .commit();
                }
            });
        } else {
            follow.setVisibility(View.GONE);
            registerMethod();
        }

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedPereferenceClass.getStoredKey(getActivity(), SELL).equals(SELL)) {
            TheMainActivity theMainActivity = (TheMainActivity) getActivity();
            theMainActivity. getSupportActionBar().setTitle("انشاء حساب جديد");}
        else { Home home = (Home) getActivity();
            home. getSupportActionBar().setTitle("انشاء حساب جديد");}

    }
    @Override
    public void Error() {
        Toast.makeText(getContext(), "register failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Success() {
        Toast.makeText(getContext(), "register succcess"
                , Toast.LENGTH_SHORT).show();
        saveRegisteredClient();
        sharedPereferenceClass.storeKey(getContext(), "clientislogged", "true");
        excuteOrder();
    }

    public void registerMethod() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleProgressBar.setVisibility(View.VISIBLE);
                _email = email.getText().toString();
                _password = password.getText().toString();
                _name = name.getText().toString();
                _confitmpassword = confirmpassword.getText().toString();
                _phone = phone.getText().toString();
                _city = cityspinner.getSelectedItem().toString();
                _region = String.valueOf(regionspinner.getSelectedItemId());
                userRegister_presenter.Register(_name, _email, _password, _confitmpassword, _phone, _region, _city);
            }
        });

    }

//    public void citySpinner() {
//        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
//        Call<CityList_Response> call = apiInterface.getCityList();
//        call.enqueue(new Callback<CityList_Response>() {
//            @Override
//            public void onResponse(Call<CityList_Response> call, Response<CityList_Response> response) {
//                final CityList_Response cityList_response = response.body();
//                ArrayList<String> citiessname = new ArrayList<>();
//                final ArrayList<Integer> citiesid = new ArrayList<>();
//                citiessname.add("اخترالمدينه.... ");
//                for (int i = 0; i < cityList_response.getData().getData().size(); i++) {
//                    citiessname.add(cityList_response.getData().getData().get(i).getName());
//                    Log.i("City", cityList_response.getData().getData().get(i).getName());
//                    citiesid.add(cityList_response.getData().getData().get(i).getId());
//                }
//                cityspinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, citiessname));
//                cityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {
//                        if (position != 0) {
//                            int id = citiesid.get(position - 1);
//                            regionSpinner(id);
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<CityList_Response> call, Throwable t) {
//            }
//        });
//
//    }
//
//    public void regionSpinner(int id) {
//        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
//        Call<Region_Response> call = apiInterface.getRegionList(id);
//        call.enqueue(new Callback<Region_Response>() {
//            @Override
//            public void onResponse(Call<Region_Response> call, Response<Region_Response> response) {
//                region_response = response.body();
//                ArrayList<String> regionsname = new ArrayList<>();
//                final ArrayList<Integer> regionsid = new ArrayList<>();
//                regionsname.add("اخترالحى.... ");
//                for (int i = 0; i < region_response.getData().getData().size(); i++) {
//                    regionsname.add(region_response.getData().getData().get(i).getName());
//                    Log.i("City", region_response.getData().getData().get(i).getName());
//                    regionsid.add(region_response.getData().getData().get(i).getId());
//                }
//                regionspinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, regionsname));
//                regionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<Region_Response> call, Throwable t) {
//            }
//        });
//
//    }

    public void excuteOrder() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flContent, new AddNewOrder_Fragment());
        transaction.addToBackStack(null);
        transaction.commit();
        //  login_fragment.loadFragment( new AddNewOrder_Fragment());
    }

    private void saveRegisteredClient() {
        sharedPereferenceClass.storeKey(getContext(), "CLIENT_NAME", _name);
        sharedPereferenceClass.storeKey(getContext(), "CLIENT_PASSWORD", _password);
        sharedPereferenceClass.storeKey(getContext(), "CLIENT_EMAIL", _email);
        sharedPereferenceClass.storeKey(getContext(), "CLIENT_PHONE", _phone);
        sharedPereferenceClass.storeKey(getContext(), "CLIENT_REGION", regionspinner.getSelectedItem().toString());
        sharedPereferenceClass.storeKey(getContext(), "CLIENT_CITY", _city);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

