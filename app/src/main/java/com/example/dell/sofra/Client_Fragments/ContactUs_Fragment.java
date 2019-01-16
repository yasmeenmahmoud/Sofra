package com.example.dell.sofra.Client_Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dell.sofra.Model.ContactUs_Response;
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
public class ContactUs_Fragment extends Fragment {


    @BindView(R.id.myname)
    EditText myname;
    @BindView(R.id.mymail)
    EditText mymail;
    @BindView(R.id.myphone)
    EditText myphone;
    @BindView(R.id.mycontent)
    EditText mycontent;
    @BindView(R.id.next)
    Button next;
    Unbinder unbinder;
    //    @BindView(R.id.mytype)
//    EditText mytype;
    @BindView(R.id.spinnertype)
    Spinner spinnertype;

    public ContactUs_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contactus_, container, false);
        unbinder = ButterKnife.bind(this, view);
        addItemsOnSpinner2();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contactus();

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void Contactus() {
        String clint_name = myname.getText().toString();
        String clint_phone = myphone.getText().toString();
        String clint_mail = mymail.getText().toString();
        String clint_content = mycontent.getText().toString();
        String clint_type = spinnertype.getSelectedItem().toString();

        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<ContactUs_Response> call = apiInterface.contactUs(clint_name, clint_mail, clint_phone, clint_content, clint_type);
        call.enqueue(new Callback<ContactUs_Response>() {
            @Override
            public void onResponse(Call<ContactUs_Response> call, Response<ContactUs_Response> response) {
                ContactUs_Response contactUs_response = response.body();
                if (contactUs_response != null) {

                    Log.i("MESSAGE", contactUs_response.getMsg());
                    Toast.makeText(getContext(), contactUs_response.getMsg(), Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onFailure(Call<ContactUs_Response> call, Throwable t) {
                Toast.makeText(getContext(), "request faild ", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void addItemsOnSpinner2() {

        String[] items = new String[]{"Choose Type", "complaint", "suggestion", "inquiry"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinnertype.setAdapter(adapter);
    }
}
