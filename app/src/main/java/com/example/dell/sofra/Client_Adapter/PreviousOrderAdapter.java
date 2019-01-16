package com.example.dell.sofra.Client_Adapter;

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

import com.example.dell.sofra.Client_Fragments.OrdersModelClass;
import com.example.dell.sofra.Model.ConfirmOrder_Response;
import com.example.dell.sofra.Model.DeclineOrder_Response;
import com.example.dell.sofra.Model.MyOrders_Data;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dell.sofra.Client_Fragments.PreviousOrders_Fragment.api_token;
import static com.example.dell.sofra.Client_Fragments.PreviousOrders_Fragment.myOrders_response;
import static com.example.dell.sofra.Client_Fragments.PreviousOrders_Fragment.order_id;

public class PreviousOrderAdapter extends RecyclerView.Adapter<PreviousOrderAdapter.Filesviewholder> {
    private Context context;
    private List<MyOrders_Data> data;
    private LayoutInflater layoutInflater;

    public PreviousOrderAdapter(Context context, List<MyOrders_Data> data) {
        this.context = context;
        this.data = data;
        layoutInflater = layoutInflater.from(context);
    }

    @NonNull
    @Override
    public PreviousOrderAdapter.Filesviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = layoutInflater.inflate(R.layout.previosorder_listitemview, null);
        PreviousOrderAdapter.Filesviewholder filesviewholder = new PreviousOrderAdapter.Filesviewholder(view);
        return filesviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PreviousOrderAdapter.Filesviewholder filesviewholder, int i) {

        final MyOrders_Data currentposition = data.get(i);
        filesviewholder.p_ordername.setText(currentposition.getRestaurant().getName());
        filesviewholder.p_orderdetails.setText(currentposition.getCost());
        filesviewholder.p_quentity.setText(currentposition.getDeliveryCost());
        filesviewholder.p_price.setText(currentposition.getTotal());
        filesviewholder.p_ordernumber.setText(currentposition.getClientId());
      // Picasso.get().load(currentposition.getRestaurant().getPhoto()).into(filesviewholder.p_orderimage);
        filesviewholder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmClientOrder();

            }
        });
        filesviewholder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmClientOrder();
                declineClientOrder();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Filesviewholder extends RecyclerView.ViewHolder {
        TextView p_ordername, p_orderdetails, p_quentity, p_price, p_ordernumber;
        ImageView p_orderimage;
        Button confirm, decline;

        public Filesviewholder(@NonNull View itemView) {
            super(itemView);
            p_ordername = itemView.findViewById(R.id.p_ordername);
            p_orderdetails = itemView.findViewById(R.id.p_quentity);
            p_quentity = itemView.findViewById(R.id.O_quentity);
            p_price = itemView.findViewById(R.id.O_price_);
            p_ordernumber = itemView.findViewById(R.id.p_ordernumber);
            p_orderimage = itemView.findViewById(R.id.p_orderimage);
            confirm = itemView.findViewById(R.id.excute);
            decline = itemView.findViewById(R.id.refuse);

        }
    }

    private void confirmClientOrder() {
        for (int i = 0; i < myOrders_response.getData().getData().size(); i++) {
            order_id = myOrders_response.getData().getData().get(i).getId();

        }
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<ConfirmOrder_Response> call = apiInterface.confirmOrder(order_id, api_token);
        call.enqueue(new Callback<ConfirmOrder_Response>() {
            @Override
            public void onResponse(Call<ConfirmOrder_Response> call, Response<ConfirmOrder_Response> response) {
                ConfirmOrder_Response confirmOrder_response = response.body();
                if (confirmOrder_response != null) {

                    Log.i("MYRESPONSEMESSAGE", confirmOrder_response.getMsg());
                    Toast.makeText(context, confirmOrder_response.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ConfirmOrder_Response> call, Throwable t) {
                Toast.makeText(context, "request faild ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void declineClientOrder() {
        for (int i = 0; i < myOrders_response.getData().getData().size(); i++) {
            order_id = myOrders_response.getData().getData().get(i).getId();

        }
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<DeclineOrder_Response> call = apiInterface.declineOrder(order_id, api_token);
        call.enqueue(new Callback<DeclineOrder_Response>() {
            @Override
            public void onResponse(Call<DeclineOrder_Response> call, Response<DeclineOrder_Response> response) {
                DeclineOrder_Response declineOrder_response = response.body();
                if (declineOrder_response != null) {

                    Log.i("MYRESPONSEMESSAGE", declineOrder_response.getMsg());
                    Toast.makeText(context, declineOrder_response.getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<DeclineOrder_Response> call, Throwable t) {
                Toast.makeText(context, "request faild ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
