package com.example.dell.sofra.Client_Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.sofra.Client_Adapter.BasketAdapter;
import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.example.dell.sofra.Login_Fragment;
import com.example.dell.sofra.Model.RestaurantItems_Data;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Room_DB.MyAppDataBase;
import com.example.dell.sofra.Room_DB.MyDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.dell.sofra.Client_Adapter.BasketAdapter.sum;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderBasket_Fragment extends Fragment {
    BasketAdapter basketAdapter;
    List<RestaurantItems_Data> basketorders = new ArrayList<>();
    RecyclerView basketrecyclerview;
    Button sign_btn;
    View view;

    MyAppDataBase myAppDataBase;
    public TextView sumtext;
    SharedPereferenceClass sharedPereferenceClass;
    @BindView(R.id.addmore_btn)
    Button addmoreBtn;
    Unbinder unbinder;

    public OrderBasket_Fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order_basket_, container, false);
        sign_btn = view.findViewById(R.id.signin_btn);
        sharedPereferenceClass = new SharedPereferenceClass();
        sumtext = view.findViewById(R.id.sum);
        sumtext.setText(sum + "");
        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedPereferenceClass.getStoredKey(getContext(), "clientislogged").equals("true"))
                {  FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.flContent, new AddNewOrder_Fragment());
                transaction.addToBackStack(null);
                transaction.commit();}
                else {FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.flContent, new Login_Fragment());
                    transaction.addToBackStack(null);
                    transaction.commit();}
            }
        });
        addmealsToBasket();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void addmealsToBasket() {
        basketrecyclerview = view.findViewById(R.id.basketrecyclerview);
        basketrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAppDataBase myAppDataBase = MyAppDataBase.getInstance(getActivity());
        final MyDao roomDao = myAppDataBase.myDao();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                basketorders = roomDao.getOrders();
                basketAdapter = new BasketAdapter(getContext(), basketorders, sumtext);
                basketrecyclerview.setAdapter(basketAdapter);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        Home home = (Home) getActivity();
        home. getSupportActionBar().setTitle("سلة الطلبات");

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.addmore_btn)
    public void onViewClicked() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flContent, new RestaurantDetails_fragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

