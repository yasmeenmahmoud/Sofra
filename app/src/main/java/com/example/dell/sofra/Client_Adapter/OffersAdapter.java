package com.example.dell.sofra.Client_Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.sofra.Model.MyOffers_Data;
import com.example.dell.sofra.Model.OffersList_Data;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.dell.sofra.Helper.SharedPereferenceClass.SELL;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.Filesviewholder> {
    private Context context;
    private List<OffersList_Data> data;
    private LayoutInflater layoutInflater;
    private List<MyOffers_Data> mydata;
    int resourcelayoue;

    public OffersAdapter(Context context, List<OffersList_Data> data) {
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    SharedPereferenceClass sharedPereferenceClass = new SharedPereferenceClass();

    public OffersAdapter(Context context, int resource, List<MyOffers_Data> offerslist) {
        this.context = context;
        this.mydata = offerslist;
        layoutInflater = LayoutInflater.from(context);
        this.resourcelayoue = resource;
    }

    @NonNull
    @Override
    public OffersAdapter.Filesviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = layoutInflater.inflate(R.layout.offers_listitemview, null);
        OffersAdapter.Filesviewholder filesviewholder = new OffersAdapter.Filesviewholder(view);
        return filesviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OffersAdapter.Filesviewholder filesviewholder, int i) {

        if (sharedPereferenceClass.getStoredKey(context, SELL).equals(SELL)) {
            MyOffers_Data position = mydata.get(i);
            String starin = position.getStartingAt();
            String[] start = starin.split(" ");
            String endin = position.getEndingAt();
            String[] end = endin.split(" ");
            filesviewholder.offertitle.setText(position.getRestaurant().getName());
            filesviewholder.offerrestaurant.setText(position.getDescription());
            filesviewholder.offertime.setText(start[0]);
            filesviewholder.offerendtime.setText(end[0]);
            String image = "http://ipda3.com/sofra/" + position.getPhoto();
            filesviewholder.offerprice.setText(position.getPrice());
            Picasso.get().load(image)
                    .into(filesviewholder.offerimage);
        } else {
            final OffersList_Data currentposition = data.get(i);
            String starin = currentposition.getStartingAt();
            String[] start = starin.split(" ");
            String endin = currentposition.getEndingAt();
            String[] end = endin.split(" ");
            filesviewholder.offertitle.setText(currentposition.getRestaurant().getName());
            filesviewholder.offerrestaurant.setText(currentposition.getDescription());
            filesviewholder.offertime.setText(start[0]);
            filesviewholder.offerendtime.setText(end[0]);
            String image = "http://ipda3.com/sofra/" + currentposition.getPhoto();
            filesviewholder.offerprice.setText(currentposition.getPrice());
            Picasso.get().load(image)
                    .into(filesviewholder.offerimage);
        }
    }

    @Override
    public int getItemCount() {
        if (sharedPereferenceClass.getStoredKey(context, SELL).equals(SELL)) {
            return mydata.size();
        } else {
            return data.size();
        }
    }

    public class Filesviewholder extends RecyclerView.ViewHolder {
        TextView offertitle, offerrestaurant, offertime, offerprice, offerendtime;
        ImageView offerimage;

        public Filesviewholder(@NonNull View itemView) {
            super(itemView);
            offertitle = itemView.findViewById(R.id.offertitle);
            offerrestaurant = itemView.findViewById(R.id.restaurantoffer);
            offertime = itemView.findViewById(R.id.offertime);
            offerprice = itemView.findViewById(R.id.offerprice);
            offerendtime = itemView.findViewById(R.id.endtime);
            offerimage = itemView.findViewById(R.id.offermealimage);
        }
    }
}
