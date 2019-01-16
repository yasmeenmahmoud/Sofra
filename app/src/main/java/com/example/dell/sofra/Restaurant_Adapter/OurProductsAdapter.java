package com.example.dell.sofra.Restaurant_Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.sofra.Model.MyItems_Data;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OurProductsAdapter extends RecyclerView.Adapter<OurProductsAdapter.Filesviewholder> {
    private Context context;
    private List<MyItems_Data> data;
    private LayoutInflater layoutInflater;
    SharedPereferenceClass sharedPereferenceClass;

    public OurProductsAdapter(Context context, List<MyItems_Data> data) {
        this.context = context;
        this.data = data;
        layoutInflater = layoutInflater.from(context);
    }

    @NonNull
    @Override
    public OurProductsAdapter.Filesviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = layoutInflater.inflate(R.layout.menu_listitem, null);
        OurProductsAdapter.Filesviewholder filesviewholder = new OurProductsAdapter.Filesviewholder(view);
        return filesviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OurProductsAdapter.Filesviewholder filesviewholder, int i) {
        sharedPereferenceClass = new SharedPereferenceClass();
        final MyItems_Data currentposition = data.get(i);
        filesviewholder.mealName.setText(currentposition.getName());
        filesviewholder.mealdetails.setText(currentposition.getDescription());
        //  filesviewholder.mealmoredetails.setText(currentposition.getMealdetails2());
        filesviewholder.price.setText(currentposition.getPrice());
        Picasso.get().load(currentposition.getPhotoUrl()).into(filesviewholder.meal_image);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Filesviewholder extends RecyclerView.ViewHolder {
        // Button favourite_btn;
        TextView mealName, mealdetails, mealmoredetails, price;
        ImageView meal_image;

        public Filesviewholder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.M_mealname);
            mealdetails = itemView.findViewById(R.id.M_mealdetail);
            mealmoredetails = itemView.findViewById(R.id.M_mealdetails2);
            price = itemView.findViewById(R.id.M_price);
            meal_image = itemView.findViewById(R.id.M_mealimage);
        }
    }
}



