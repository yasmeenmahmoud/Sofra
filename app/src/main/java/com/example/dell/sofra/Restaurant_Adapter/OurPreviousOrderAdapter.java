package com.example.dell.sofra.Restaurant_Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.sofra.Model.RestaurantOrders_Data;
import com.example.dell.sofra.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OurPreviousOrderAdapter extends RecyclerView.Adapter<OurPreviousOrderAdapter.Filesviewholder> {
    private Context context;
    private List<RestaurantOrders_Data> data;
    private LayoutInflater layoutInflater;

    public OurPreviousOrderAdapter(Context context, List<RestaurantOrders_Data> data) {
        this.context = context;
        this.data = data;
        layoutInflater = layoutInflater.from(context);
    }

    @NonNull
    @Override
    public OurPreviousOrderAdapter.Filesviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = layoutInflater.inflate(R.layout.ourpreviousorders_listviewitems, null);
        OurPreviousOrderAdapter.Filesviewholder filesviewholder = new OurPreviousOrderAdapter.Filesviewholder(view);
        return filesviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OurPreviousOrderAdapter.Filesviewholder filesviewholder, int i) {

        final RestaurantOrders_Data currentposition = data.get(i);
        filesviewholder.clientname.setText(currentposition.getClient().getName());
        filesviewholder.price.setText(currentposition.getCost());
        filesviewholder.ordernumber.setText(currentposition.getRestaurant().getDeliveryCost());
        filesviewholder.arrivalprice.setText(currentposition.getDeliveryCost());
        filesviewholder.recievetime.setText(currentposition.getTotal());
        filesviewholder.receiveaddress.setText(currentposition.getClient().getAddress());
        Picasso.get().load(currentposition.getRestaurant().getPhotoUrl()).into(filesviewholder.orderimage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Filesviewholder extends RecyclerView.ViewHolder {
        TextView clientname,arrivalprice, recievetime, price, ordernumber,receiveaddress
               ;
        ImageView orderimage;
        public Filesviewholder(@NonNull View itemView) {
            super(itemView);
            clientname = itemView.findViewById(R.id.clientname);
            price = itemView.findViewById(R.id.price);
            ordernumber = itemView.findViewById(R.id.ordernumber);
            arrivalprice = itemView.findViewById(R.id.arrivalmony);
            recievetime = itemView.findViewById(R.id.gettotal);
            receiveaddress = itemView.findViewById(R.id.receiveorderaddress);
            orderimage = itemView.findViewById(R.id.imageeat);

        }
    }
}
