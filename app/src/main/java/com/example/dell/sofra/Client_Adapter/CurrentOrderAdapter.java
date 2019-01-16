package com.example.dell.sofra.Client_Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.sofra.Client_Fragments.OrdersModelClass;
import com.example.dell.sofra.Model.MyOrders_Data;
import com.example.dell.sofra.R;

import java.util.ArrayList;
import java.util.List;

public class CurrentOrderAdapter extends RecyclerView.Adapter<CurrentOrderAdapter.Filesviewholder> {
    private Context context;
    private List<MyOrders_Data> data;
    private LayoutInflater layoutInflater;

    public CurrentOrderAdapter(Context context, List<MyOrders_Data> data) {
        this.context = context;
        this.data = data;
        layoutInflater = layoutInflater.from(context);
    }

    @NonNull
    @Override
    public CurrentOrderAdapter.Filesviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = layoutInflater.inflate(R.layout.currentorder_listitemview, null);
        CurrentOrderAdapter.Filesviewholder filesviewholder = new CurrentOrderAdapter.Filesviewholder(view);
        return filesviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CurrentOrderAdapter.Filesviewholder filesviewholder, int i) {

        final MyOrders_Data currentposition = data.get(i);
        filesviewholder.ordername.setText(currentposition.getRestaurant().getName());
        filesviewholder.orderdetails.setText(currentposition.getCost());
        filesviewholder.quentity.setText(currentposition.getDeliveryCost());
        filesviewholder.price.setText(currentposition.getTotal());
        filesviewholder.ordernumber.setText("رقم الطلب : "+currentposition.getClientId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Filesviewholder extends RecyclerView.ViewHolder {
        TextView ordername, orderdetails, quentity, price, ordernumber;
        ImageView orderimage;

        public Filesviewholder(@NonNull View itemView) {
            super(itemView);
            ordername = itemView.findViewById(R.id.O_theordernamr);
            orderdetails = itemView.findViewById(R.id.O_orderdetail);
            quentity = itemView.findViewById(R.id.O_quentity);
            price = itemView.findViewById(R.id.O_price_);
            ordernumber = itemView.findViewById(R.id.O_ordernumber);
            orderimage = itemView.findViewById(R.id.O_mealimage);

        }
    }
}
