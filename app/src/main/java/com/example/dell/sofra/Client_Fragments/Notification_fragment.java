package com.example.dell.sofra.Client_Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.sofra.Client_Adapter.NotificationAdapter;
import com.example.dell.sofra.Model.Notification_Data;
import com.example.dell.sofra.Model.Notification_Response;
import com.example.dell.sofra.Model.UserNotification_Data;
import com.example.dell.sofra.Model.UserNotification_Response;
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

import static com.example.dell.sofra.Helper.SharedPereferenceClass.SELL;


/**
 * A simple {@link Fragment} subclass.
 */
public class Notification_fragment extends Fragment {
    NotificationAdapter notificationAdapter;
    List<Notification_Data> notificationlist = new ArrayList<>();
    List<UserNotification_Data> usernotificationlist = new ArrayList<>();
    SharedPereferenceClass sharedPereferenceClass;
    RecyclerView notificationrecycler;
    View myview;
    @BindView(R.id.simpleProgressBar)
    ProgressBar simpleProgressBar;
    Unbinder unbinder;

    public Notification_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_notification_fragment, container, false);
        unbinder = ButterKnife.bind(this, myview);
        sharedPereferenceClass = new SharedPereferenceClass();
        if (sharedPereferenceClass.getStoredKey(getContext(), SELL).equals(SELL)) {
            simpleProgressBar.setVisibility(View.VISIBLE);
            addnotificationlist();
        } else {
            simpleProgressBar.setVisibility(View.VISIBLE);
            addUsernotificationlist();
        }
        return myview;
    }

    private void addnotificationlist() {
        notificationrecycler = myview.findViewById(R.id.notificationrecycle);
        notificationrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<Notification_Response> call = apiInterface.getNotificationList();
        call.enqueue(new Callback<Notification_Response>() {
            @Override
            public void onResponse(Call<Notification_Response> call, Response<Notification_Response> response) {
                Notification_Response notification_response = response.body();
                if (notification_response != null) {
                    notificationlist = notification_response.getData().getData();
                    Log.i("MESSAGE", notification_response.getMsg());
                    simpleProgressBar.setVisibility(View.GONE);

                    try {
                        notificationAdapter = new NotificationAdapter(getContext(), notificationlist);
                        notificationrecycler.setAdapter(notificationAdapter);

                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onFailure(Call<Notification_Response> call, Throwable t) {
                Toast.makeText(getContext(), "request faild ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addUsernotificationlist() {
        notificationrecycler = myview.findViewById(R.id.notificationrecycle);
        notificationrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<UserNotification_Response> call = apiInterface.getUserNotificationList();
        call.enqueue(new Callback<UserNotification_Response>() {
            @Override
            public void onResponse(Call<UserNotification_Response> call, Response<UserNotification_Response> response) {
                UserNotification_Response userNotification_response = response.body();
                if (userNotification_response != null) {
                    usernotificationlist = userNotification_response.getData().getData();
                    Log.i("MESSAGE", userNotification_response.getMsg());
                    try {
                        notificationAdapter = new NotificationAdapter(getContext(), R.layout.notification_listitemview, usernotificationlist);
                        notificationrecycler.setAdapter(notificationAdapter);
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<UserNotification_Response> call, Throwable t) {
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
