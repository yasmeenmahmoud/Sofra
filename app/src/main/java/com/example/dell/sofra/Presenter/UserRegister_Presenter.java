package com.example.dell.sofra.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.dell.sofra.Model.Register_Response;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;
import com.example.dell.sofra.View.UserRegister_View;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegister_Presenter {
    UserRegister_View userRegister_view;
    Context context;

    public UserRegister_Presenter(Context context, UserRegister_View userRegister_view) {
        this.userRegister_view = userRegister_view;
        this.context = context;
    }

    public void Register(String name, String email, String password, String confirmpassword, String phone, String reginid,String address) {
        Map<String, String> registerqueryMap = new HashMap<>();
        registerqueryMap.put("email", email);
        registerqueryMap.put("password", password);
        registerqueryMap.put("name", name);
        registerqueryMap.put("password_confirmation", confirmpassword);
        registerqueryMap.put("phone", phone);
        registerqueryMap.put("address", address);
        registerqueryMap.put("region_id", reginid);

        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<Register_Response> call = apiInterface.Register(registerqueryMap);
        call.enqueue(new Callback<Register_Response>() {
            @Override
            public void onResponse(Call<Register_Response> call, Response<Register_Response> response) {
                Register_Response register_response = response.body();
                try {
                    if (register_response != null) {
                        Log.i("MyResponse", response.body().toString());
                        Log.i("mymessage", register_response.getMsg());
                        userRegister_view.Success();
                    } else {
                        userRegister_view.Error();
                    }
                } catch (Exception ex) {
                    ex.getMessage();
                    userRegister_view.Error();
                }
            }
            @Override
            public void onFailure(Call<Register_Response> call, Throwable t) {
                userRegister_view.Error();
            }
        });
    }

}
