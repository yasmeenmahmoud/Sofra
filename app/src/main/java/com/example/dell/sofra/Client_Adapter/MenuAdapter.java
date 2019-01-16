package com.example.dell.sofra.Client_Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.sofra.Model.MyItems_Data;
import com.example.dell.sofra.Model.RestaurantItems_Data;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.example.dell.sofra.Client_Fragments.MealDetails_Fragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.dell.sofra.Helper.SharedPereferenceClass.USER;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.Filesviewholder> {
    private Context context;
    private List<RestaurantItems_Data> data;
    private List<MyItems_Data> mydata;
    private LayoutInflater layoutInflater;
    public static int menuItemId;
    int resourcelayout;

    public MenuAdapter(Context context, List<RestaurantItems_Data> data) {
        this.context = context;
        this.data = data;
        layoutInflater = layoutInflater.from(context);
    }

    public MenuAdapter(Context context, List<MyItems_Data> data, int resource) {
        this.context = context;
        this.mydata = data;
        layoutInflater = layoutInflater.from(context);
        this.resourcelayout = resource;
    }

    SharedPereferenceClass sharedPereferenceClass = new SharedPereferenceClass();

    @NonNull
    @Override
    public MenuAdapter.Filesviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = layoutInflater.inflate(R.layout.menu_listitem, null);
        MenuAdapter.Filesviewholder filesviewholder = new MenuAdapter.Filesviewholder(view);
        return filesviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuAdapter.Filesviewholder filesviewholder, int i) {
        if (sharedPereferenceClass.getStoredKey(context, USER).equals(USER)) {
            final RestaurantItems_Data currentposition = data.get(i);
            filesviewholder.mealName.setText(currentposition.getName());
            filesviewholder.mealdetails.setText(currentposition.getDescription());
            filesviewholder.price.setText(currentposition.getPrice());
            Picasso.get().load(currentposition.getPhotoUrl())
                    .into(filesviewholder.meal_image);
            filesviewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    MealDetails_Fragment mealDetails_fragment = new MealDetails_Fragment();
                    mealDetails_fragment.restaurantItems_data = currentposition;
                    transaction.replace(R.id.flContent, mealDetails_fragment);
                    menuItemId = currentposition.getId();
                    sharedPereferenceClass.storeKey(context, "menuItemID", menuItemId);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        } else {

            final MyItems_Data currentposition = mydata.get(i);
            filesviewholder.mealName.setText(currentposition.getName());
            filesviewholder.mealdetails.setText(currentposition.getDescription());
            //  filesviewholder.mealmoredetails.setText(currentposition.getMealdetails2());
            filesviewholder.price.setText(currentposition.getPrice());
            Picasso.get().load(currentposition.getPhotoUrl())
                    .into(filesviewholder.meal_image);

        }

    }


    @Override
    public int getItemCount() {
        if (sharedPereferenceClass.getStoredKey(context, USER).equals(USER)) {

            return data.size();
        } else {
            return mydata.size();
        }
    }

    public class Filesviewholder extends RecyclerView.ViewHolder {
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
