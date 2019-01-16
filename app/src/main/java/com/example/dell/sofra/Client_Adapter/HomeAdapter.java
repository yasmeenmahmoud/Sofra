package com.example.dell.sofra.Client_Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dell.sofra.Client_Fragments.Home;
import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.example.dell.sofra.Model.RestaurantItems_Data;
import com.example.dell.sofra.Model.RestaurantsList_Data;
import com.example.dell.sofra.Model.RestaurantsList_Response;
import com.example.dell.sofra.Presenter.RestaurantsList_Presenter;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Client_Fragments.RestaurantDetails_fragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.dell.sofra.Client_Fragments.Homeitem_Fragment.home;
import static com.example.dell.sofra.Client_Fragments.Homeitem_Fragment.restaurantsList_response;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Filesviewholder> implements Filterable {
    private Context context;
    private List<RestaurantsList_Data> data;
    private LayoutInflater layoutInflater;
    private List<RestaurantsList_Data> itemsCopy;

    public HomeAdapter(Context context, List<RestaurantsList_Data> data) {
        this.context = context;
        this.data = data;
        this.itemsCopy = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Filesviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = layoutInflater.inflate(R.layout.home_listitemview, null);
        Filesviewholder filesviewholder = new Filesviewholder(view);
        return filesviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Filesviewholder filesviewholder, int i) {
        final RestaurantsList_Data currentposition = data.get(i);
        if (currentposition.getRate() != 0) {
            filesviewholder.ratingBar.setNumStars(currentposition.getRate());
            filesviewholder.ratingBar.setRating(currentposition.getRate());
        }
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (int j = 0; j < currentposition.getCategories().size(); j++) {
            sb.append(prefix);
            prefix = ",";
            sb.append(currentposition.getCategories().get(j).getName());
            filesviewholder.meals.setText(sb);
        }

        filesviewholder.minimumorder.setText(currentposition.getMinimumCharger());
        filesviewholder.arrivalcost.setText(currentposition.getDeliveryCost());
        filesviewholder.opennow.setText(currentposition.getAvailability());
        filesviewholder.rstrauntName.setText(currentposition.getName());
        Picasso.get().load(currentposition.getPhotoUrl())
                .into(filesviewholder.restaurant_image);
        filesviewholder.itemView.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                                                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                                                            Bundle bundle = new Bundle();
                                                            bundle.putInt("restaurant_id", currentposition.getId());
                                                            SharedPereferenceClass sharedPereferenceClass = new SharedPereferenceClass();
                                                            sharedPereferenceClass.storeKey(context, "menuItem_deliverycost", currentposition.getDeliveryCost());
                                                            RestaurantDetails_fragment restaurantDetails_fragment = new RestaurantDetails_fragment();
                                                            restaurantDetails_fragment.setArguments(bundle);
                                                            transaction.replace(R.id.flContent, restaurantDetails_fragment);
                                                            transaction.addToBackStack(null);
                                                            transaction.commit();
                                                        }
                                                    }
        );
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (List<RestaurantsList_Data>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<RestaurantsList_Data> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = itemsCopy;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<RestaurantsList_Data> getFilteredResults(String constraint) {
        List<RestaurantsList_Data> results = new ArrayList<>();

        for (RestaurantsList_Data item : itemsCopy) {
            if (item.getName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }

    public class Filesviewholder extends RecyclerView.ViewHolder {
        TextView rstrauntName, meals, minimumorder, arrivalcost, opennow;
        ImageView restaurant_image;
        RatingBar ratingBar;

        public Filesviewholder(@NonNull View itemView) {
            super(itemView);
            rstrauntName = itemView.findViewById(R.id.H_text1);
            meals = itemView.findViewById(R.id.H_text2);
            minimumorder = itemView.findViewById(R.id.H_text4);
            arrivalcost = itemView.findViewById(R.id.H_text5);
            opennow = itemView.findViewById(R.id.H_text3);
            restaurant_image = itemView.findViewById(R.id.H_imageview);
            ratingBar = itemView.findViewById(R.id.ratingbar);
        }
    }
}
