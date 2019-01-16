package com.example.dell.sofra.Restaurant_Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.sofra.Model.RegectOrder_Response;
import com.example.dell.sofra.Model.RestAcceptOrder_Response;
import com.example.dell.sofra.Model.RestConfirmOrder_Response;
import com.example.dell.sofra.Model.RestaurantOrders_Data;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dell.sofra.Restaurants_Fragments.NewOrders_Fragment.myOrdersRestaurant_response;

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.Filesviewholder> {
    private Context context;
    private List<RestaurantOrders_Data> data;
    private LayoutInflater layoutInflater;
    private String apitoken = "EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv";
    private int orderid;
    private RestAcceptOrder_Response restAcceptOrder_response;

    public NewOrderAdapter(Context context, List<RestaurantOrders_Data> data) {
        this.context = context;
        this.data = data;
        layoutInflater = layoutInflater.from(context);
    }

    @NonNull
    @Override
    public NewOrderAdapter.Filesviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = layoutInflater.inflate(R.layout.neworders_listviewitems, null);
        NewOrderAdapter.Filesviewholder filesviewholder = new NewOrderAdapter.Filesviewholder(view);
        return filesviewholder;
    }


    @Override
    public void onBindViewHolder(@NonNull final NewOrderAdapter.Filesviewholder filesviewholder, int i) {

        final RestaurantOrders_Data currentposition = data.get(i);
        filesviewholder.clientname.setText(currentposition.getClient().getName());
        filesviewholder.price.setText(currentposition.getCost());
        filesviewholder.ordernumber.setText(currentposition.getRestaurant().getDeliveryCost());
        filesviewholder.arrivalprice.setText(currentposition.getDeliveryCost());
        Picasso.get().load(currentposition.getRestaurant().getPhotoUrl()).into(filesviewholder.orderimage);

        filesviewholder.recievetime.setText(currentposition.getTotal());
        filesviewholder.receiveaddress.setText(currentposition.getClient().getAddress());
        filesviewholder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptorder();
            }
        });
        filesviewholder.refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectorder();
            }
        });
        filesviewholder.hotnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restconfirmorder();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Filesviewholder extends RecyclerView.ViewHolder {
        TextView clientname, arrivalprice, recievetime, price, ordernumber, receiveaddress;
        ImageView orderimage;
        Button hotnumber, accept, refuse;

        public Filesviewholder(@NonNull View itemView) {
            super(itemView);
            clientname = itemView.findViewById(R.id.clientname);
            price = itemView.findViewById(R.id.price);
            ordernumber = itemView.findViewById(R.id.ordernumber);
            arrivalprice = itemView.findViewById(R.id.arrivalmony);
            recievetime = itemView.findViewById(R.id.gettotal);
            receiveaddress = itemView.findViewById(R.id.receiveorderaddress);
            hotnumber = itemView.findViewById(R.id.clientphone);
            accept = itemView.findViewById(R.id.accept);
            refuse = itemView.findViewById(R.id.refuse);
            orderimage = itemView.findViewById(R.id.imageeat);
        }
    }

    private void acceptorder() {
        for (int i = 0; i < myOrdersRestaurant_response.getData().getData().size(); i++) {
            orderid = myOrdersRestaurant_response.getData().getData().get(i).getId();

        }
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<RestAcceptOrder_Response> call = apiInterface.acceptOrder(orderid, apitoken);
        call.enqueue(new Callback<RestAcceptOrder_Response>() {
            @Override
            public void onResponse(Call<RestAcceptOrder_Response> call, Response<RestAcceptOrder_Response> response) {
                restAcceptOrder_response = response.body();
                if (restAcceptOrder_response != null) {

                    Log.i("MYRESPONSEMESSAGE", restAcceptOrder_response.getMsg());
                    Toast.makeText(context, restAcceptOrder_response.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<RestAcceptOrder_Response> call, Throwable t) {
                Toast.makeText(context, "request faild ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void rejectorder() {
        for (int i = 0; i < myOrdersRestaurant_response.getData().getData().size(); i++) {
            orderid = myOrdersRestaurant_response.getData().getData().get(i).getId();

        }
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<RegectOrder_Response> call = apiInterface.RejectOrder(orderid, apitoken);
        call.enqueue(new Callback<RegectOrder_Response>() {
            @Override
            public void onResponse(Call<RegectOrder_Response> call, Response<RegectOrder_Response> response) {
                RegectOrder_Response regectOrder_response = response.body();
                if (regectOrder_response != null) {

                    Log.i("MYRESPONSEMESSAGE", regectOrder_response.getMsg());
                    Toast.makeText(context, regectOrder_response.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<RegectOrder_Response> call, Throwable t) {
                Toast.makeText(context, "request faild ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void restconfirmorder() {
        for (int i = 0; i < myOrdersRestaurant_response.getData().getData().size(); i++) {
            orderid = myOrdersRestaurant_response.getData().getData().get(i).getId();

        }
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<RestConfirmOrder_Response> call = apiInterface.RestConfirmOrder(orderid, apitoken);
        call.enqueue(new Callback<RestConfirmOrder_Response>() {
            @Override
            public void onResponse(Call<RestConfirmOrder_Response> call, Response<RestConfirmOrder_Response> response) {
                RestConfirmOrder_Response regectOrder_response = response.body();
                if (regectOrder_response != null) {

                    Log.i("MYRESPONSEMESSAGE", regectOrder_response.getMsg());
                    Toast.makeText(context, regectOrder_response.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<RestConfirmOrder_Response> call, Throwable t) {
                Toast.makeText(context, "request faild ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}