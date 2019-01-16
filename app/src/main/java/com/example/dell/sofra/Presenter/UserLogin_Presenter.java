package com.example.dell.sofra.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.dell.sofra.Model.Login_Response;
import com.example.dell.sofra.Model.RestauranrLogin_Response;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;
import com.example.dell.sofra.View.Login_View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLogin_Presenter {
    Login_View login_view;
    Context context;

    public UserLogin_Presenter(Context context, Login_View login_view) {
        this.login_view = login_view;
        this.context = context;
    }

    public void Login(String email, String password) {
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<Login_Response> call = apiInterface.Login(email, password);
        call.enqueue(new Callback<Login_Response>() {
            @Override
            public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                Login_Response login_response = response.body();
                try {
                    if (response.isSuccessful()) {
                        Log.i("MyLogin", login_response.getMsg());
                        Log.i("getApiToken", login_response.getData().getApiToken());
                        login_view.Success();
                        login_view.apitoken(login_response.getData());
                    } else {
                        login_view.Error();
                    }
                } catch (Exception ex) {
                    ex.getMessage();
                    login_view.Error();
                }
            }

            @Override
            public void onFailure(Call<Login_Response> call, Throwable t) {
                login_view.Error();
            }
        });
    }

    public void RestaurantLogin(String email, String password) {
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<RestauranrLogin_Response> call = apiInterface.restaurantLogin(email, password);
        call.enqueue(new Callback<RestauranrLogin_Response>() {
            @Override
            public void onResponse(Call<RestauranrLogin_Response> call, Response<RestauranrLogin_Response> response) {
                RestauranrLogin_Response restauranrLogin_response = response.body();
                try {
                    if (response.isSuccessful()) {
                        Log.i("MyLogin", restauranrLogin_response.getMsg());
                        Log.i("getApiToken", restauranrLogin_response.getData().getApiToken());
                        login_view.Success();
                        login_view.r_apitoken(restauranrLogin_response.getData());
                    } else {
                        login_view.Error();
                    }
                } catch (Exception ex) {
                    ex.getMessage();
                    login_view.Error();
                }
            }

            @Override
            public void onFailure(Call<RestauranrLogin_Response> call, Throwable t) {
                login_view.Error();
            }
        });
    }
}
