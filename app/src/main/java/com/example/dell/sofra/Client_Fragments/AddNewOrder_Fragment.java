package com.example.dell.sofra.Client_Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.example.dell.sofra.Model.AddOrder_Response;
import com.example.dell.sofra.Model.PaymentMethod_Response;
import com.example.dell.sofra.Model.RestaurantItems_Data;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;
import com.example.dell.sofra.Room_DB.MyAppDataBase;
import com.example.dell.sofra.Room_DB.MyDao;

import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static com.example.dell.sofra.Client_Adapter.BasketAdapter.deliverycost;
import static com.example.dell.sofra.Client_Adapter.BasketAdapter.sum;
import static com.example.dell.sofra.Client_Adapter.BasketAdapter.totalquentity;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewOrder_Fragment extends Fragment {

    View view;
    @BindView(R.id.addnotes)
    EditText addnotes;
    @BindView(R.id.myaddress)
    TextView myaddress;
    @BindView(R.id.cashpayment)
    RadioButton cashpayment;
    @BindView(R.id.networkpayment)
    RadioButton networkpayment;
    @BindView(R.id.radiopayment)
    RadioGroup radiopayment;
    @BindView(R.id.mysum)
    TextView mysum;
    @BindView(R.id.mydeliverycost)
    TextView mydeliverycost;
    @BindView(R.id.mytotalsum)
    TextView mytotalsum;
    @BindView(R.id.btn_addneworder)
    Button btnAddneworder;
    Unbinder unbinder;
    String item_note;
    String myregion;
    String myCity;
    String myPhone;
    String clientName;
    int itemid;
    int restaurantid;
    String apitoken;
    PaymentMethod_Response paymentMethods_response;
    SharedPereferenceClass sharedPereferenceClass;
    int selectedId;
    String mynote;
    @BindView(R.id.simpleProgressBar)
    ProgressBar simpleProgressBar;
    String delivercost;

    public AddNewOrder_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addneworder_, container, false);
        unbinder = ButterKnife.bind(this, view);
        sharedPereferenceClass = new SharedPereferenceClass();
        apitoken = "6s9myYlaDULl8Cb78qWAdSwyArssyh4QWIyfaU6l5miUvOou5iS6QMjMi00v";
        myPhone = sharedPereferenceClass.getStoredKey(getContext(), "CLIENT_PHONE");
        myCity = sharedPereferenceClass.getStoredKey(getContext(), "CLIENT_CITY");
        myregion = sharedPereferenceClass.getStoredKey(getContext(), "CLIENT_REGION");
        clientName = sharedPereferenceClass.getStoredKey(getContext(), "CLIENT_NAME");
        itemid = sharedPereferenceClass.getintStoredKey(getContext(), "menuItemID");
        restaurantid = sharedPereferenceClass.getintStoredKey(getContext(), "restaurant_id");
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        sb.append(prefix);
        prefix = ", ";
        sb.append(myCity);
        sb.append(prefix);
        sb.append(myregion);
        myaddress.setText(sb);
        getPaymentmethods();
        addMyNewOrder();
        delivercost = sharedPereferenceClass.getStoredKey(getContext(), "menuItem_deliverycost");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void addMyNewOrder() {
        MyAppDataBase myAppDataBase = MyAppDataBase.getInstance(getActivity());
        final MyDao roomDao = myAppDataBase.myDao();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<RestaurantItems_Data> myorders = roomDao.getOrders();
                for (RestaurantItems_Data myorder : myorders) {
                    mysum.setText("" + sum);
                    mydeliverycost.setText(delivercost);
                    double delivercost_double = Double.parseDouble(delivercost);
                    double tatal = (sum + delivercost_double);
                    mytotalsum.setText(tatal + "");
                    item_note = myorder.getDescription();
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        Home home = (Home) getActivity();
        home. getSupportActionBar().setTitle("اضف طلب جديد");

    }
    @OnClick(R.id.btn_addneworder)
    public void onViewClicked() {
        simpleProgressBar.setVisibility(View.VISIBLE);
        mynote = addnotes.getText().toString();
        AddNewOrder(clientName, mynote, myPhone, selectedId, myCity, restaurantid, apitoken, item_note, itemid, totalquentity);
    }

    public void AddNewOrder(String name, String note, String phone, int paymentmethodid, String address, int restaurantid
            , String apitoken, String itemnote, int itemid, int itemquentity
    ) {

        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<AddOrder_Response> call = apiInterface.addNewOrder(name
                , note, phone, paymentmethodid
                , address, restaurantid, apitoken
                , itemnote, itemid, itemquentity
        );
        call.enqueue(new Callback<AddOrder_Response>() {
            @Override
            public void onResponse(Call<AddOrder_Response> call, Response<AddOrder_Response> response) {
                AddOrder_Response addOrder_response = response.body();
                if (addOrder_response != null) {
                    Log.i("myrespondmessage", addOrder_response.getMsg());
                    Toast.makeText(getContext(), "order added successfully", Toast.LENGTH_SHORT).show();
                    simpleProgressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<AddOrder_Response> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getPaymentmethods() {
        radiopayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.cashpayment) {
                    selectedId = 1;
                } else if (checkedId == R.id.networkpayment) {
                    selectedId = 2;
                }

            }
        });
    }
//        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
//        Call<PaymentMethod_Response> call = apiInterface.getpaymentmethods();
//        call.enqueue(new Callback<PaymentMethod_Response>() {
//            @Override
//            public void onResponse(Call<PaymentMethod_Response> call, Response<PaymentMethod_Response> response) {
//                paymentMethods_response = response.body();
//                //    ArrayList<String> paymentmethodname = new ArrayList<>();
//                //  final ArrayList<Integer> paymentmethodid = new ArrayList<>();
//                int categories_Id = 0;
//                paymentMethods_response.getData().get(0).getName();
////                radiopayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
////                    @Override
////                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                for (int i = 0; i < paymentMethods_response.getData().size(); i++) {
//                    if (paymentMethods_response.getData().get(i).getName().equals(cashpayment.getText())) {
//                        //   selectedId = paymentMethods_response.getData().get(i).getId();
//                        selectedId = 1;
//
//                        Toast.makeText(getContext(), selectedId + "", Toast.LENGTH_SHORT).show();
//                        break;
//                    } else {
//                        selectedId = 2;
//
//                        // selectedId = paymentMethods_response.getData().get(i).getId();
//                        Toast.makeText(getContext(), selectedId + "", Toast.LENGTH_SHORT).show();
//                        break;
//                    }
////                            if (checkedId == R.id.cashpayment) {
////                                selectedId = paymentMethods_response.getData().get(i).getId();
////                                Toast.makeText(getContext(), selectedId, Toast.LENGTH_SHORT).show();
////
////
////                            } else if (checkedId == R.id.networkpayment) {
////                                selectedId = paymentMethods_response.getData().get(i).getId();
////                            }
//                    //    }
//
//                    // }
//                    // });
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PaymentMethod_Response> call, Throwable t) {
//
//            }
//        });


}
