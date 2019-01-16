package com.example.dell.sofra.Client_Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.sofra.Model.RestaurantItems_Data;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Room_DB.MyAppDataBase;
import com.example.dell.sofra.Room_DB.MyDao;
import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.dell.sofra.Client_Fragments.Homeitem_Fragment.restaurantsList_response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealDetails_Fragment extends Fragment {
    public RestaurantItems_Data restaurantItems_data;
    int currentvalue ;
    EditText ordervalue;
    Button add_btn, minus_btn;
    View view;
    @BindView(R.id.M_image)
    ImageView MImage;
    @BindView(R.id.M_name)
    TextView MName;
    @BindView(R.id.M_price)
    TextView MPrice;
    @BindView(R.id.M_preparingtime)
    TextView MPreparingtime;
    @BindView(R.id.myorder_edt)
    EditText myorderEdt;
    @BindView(R.id.addtobasket)
    Button addtobasket;
    @BindView(R.id.mealdetailscontent)
    LinearLayout mealdetailscontent;
    String mealName;
    String mealPrice;
    String delivercost;
    Unbinder unbinder;

    SharedPereferenceClass sharedPereferenceClass;
    int item_id;
    @BindView(R.id.descreption)
    TextView descreption;
    @BindView(R.id.minusbutton)
    Button minusbutton;
    String descripe;
    @BindView(R.id.getdescreption)
    EditText getdescreption;

    public MealDetails_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meal_details_, container, false);
        unbinder = ButterKnife.bind(this, view);
        ordervalue = view.findViewById(R.id.myorder_edt);
        addtobasket = view.findViewById(R.id.addtobasket);
        add_btn = view.findViewById(R.id.addbutton);
        sharedPereferenceClass = new SharedPereferenceClass();
        item_id = sharedPereferenceClass.getintStoredKey(getActivity(), "menuItemID");
        minus_btn = view.findViewById(R.id.minusbutton);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordervalue.setText("" + currentvalue++);
            }
        });

        minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (currentvalue > 0) {
                    ordervalue.setText("" + currentvalue--);
                }
               else {ordervalue.setText("" + currentvalue);}

            }
        });
        addRestaurantsmeals();
        addtobasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descripe = getdescreption.getText().toString();
                addOrderDb();

            }
        });
        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        Home home = (Home) getActivity();
        home. getSupportActionBar().setTitle("تفاصيل الوجبه ");

    }

    private void addRestaurantsmeals() {
        mealName = restaurantItems_data.getName();
        MName.setText(mealName);
        MPreparingtime.setText(restaurantItems_data.getPreparingTime());
        mealPrice = restaurantItems_data.getPrice();
        MPrice.setText("السعر"+"\n"+ mealPrice);
        Picasso.get().load(restaurantItems_data.getPhotoUrl())
                .into(MImage);
        for (int i = 0; i < restaurantsList_response.getData().getData().size(); i++) {
            delivercost = restaurantsList_response.getData().getData().get(i).getDeliveryCost();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void addOrderDb() {
        restaurantItems_data.setQuentity(ordervalue.getText().toString());
        restaurantItems_data.setDeliverycost(delivercost);
        restaurantItems_data.setDescription(descripe);
        MyAppDataBase myAppDataBase = MyAppDataBase.getInstance(getActivity());
        final MyDao roomDao = myAppDataBase.myDao();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<RestaurantItems_Data> foodItems = new ArrayList<>();
                foodItems = roomDao.getOrders();
                roomDao.addOrder(restaurantItems_data);
                foodItems = roomDao.getOrders();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                OrderBasket_Fragment orderBasket_fragment = new OrderBasket_Fragment();
                transaction.replace(R.id.flContent, orderBasket_fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}