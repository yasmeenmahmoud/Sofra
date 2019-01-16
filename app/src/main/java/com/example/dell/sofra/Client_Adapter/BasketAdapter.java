package com.example.dell.sofra.Client_Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.sofra.Model.RestaurantItems_Data;
import com.example.dell.sofra.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.Filesviewholder> {
    private TextView sumtext;
    private Context context;
    private List<RestaurantItems_Data> data;
    private LayoutInflater layoutInflater;
    private int itemid;
    public static double sum;
    private int quentity;
    private double total;
    private double price;
    public static int totalquentity;
    public static String deliverycost;

    public BasketAdapter(Context context, List<RestaurantItems_Data> data, TextView sumtext) {
        this.context = context;
        this.data = data;
        layoutInflater = layoutInflater.from(context);
        this.sumtext = sumtext;
    }

    @NonNull
    @Override
    public BasketAdapter.Filesviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = layoutInflater.inflate(R.layout.basket_listitemview, null);
        BasketAdapter.Filesviewholder filesviewholder = new BasketAdapter.Filesviewholder(view);
        return filesviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BasketAdapter.Filesviewholder filesviewholder, int i) {

        final RestaurantItems_Data currentposition = data.get(i);
        filesviewholder.b_ordername.setText(currentposition.getName());
        Picasso.get().load(currentposition.getPhotoUrl()).into(filesviewholder.b_orderimage);
        filesviewholder.b_orderprice.setText(currentposition.getPrice());
        filesviewholder.orderquentity.setText(currentposition.getQuentity());
        deliverycost = currentposition.getDeliverycost();
        price = Double.parseDouble(currentposition.getPrice());
        quentity = Integer.parseInt(currentposition.getQuentity());
        List<String> describition = new ArrayList<>();
        String describe = currentposition.getDescription();
        for (int y = 0; y < getItemCount(); y++) {
            describition.add(describe);
        }
        total = price * quentity;
        sum = 0;
        totalquentity = 0;
        filesviewholder.totaltext.setText("" + total);
        for (int y = 0; y < getItemCount(); y++) {
            sum = sum + total;
            }
        sumtext.setText(String.valueOf(sum));
        for (int y = 0; y < getItemCount(); y++) {
            totalquentity = quentity + totalquentity;
        }

        filesviewholder.addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quentity = quentity + 1;
                filesviewholder.orderquentity.setText("" + quentity);
                total = price * quentity;
                filesviewholder.totaltext.setText("" + total);
                sum = sum + total;
                Toast.makeText(context, "" + sum, Toast.LENGTH_SHORT).show();

            }
        });

        filesviewholder.minusorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quentity <= 1) {
                    filesviewholder.orderquentity.setText("" + quentity);
                } else if (quentity >= 1) {
                    quentity = quentity - 1;
                    filesviewholder.orderquentity.setText("" + quentity);
                    total = price * quentity;

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Filesviewholder extends RecyclerView.ViewHolder {
        TextView b_ordername, b_orderprice, totaltext;
        EditText orderquentity;
        ImageView b_orderimage;
        Button addorder, minusorder;

        public Filesviewholder(@NonNull View itemView) {
            super(itemView);
            b_ordername = itemView.findViewById(R.id.b_ordername);
            b_orderprice = itemView.findViewById(R.id.b_price);
            orderquentity = itemView.findViewById(R.id.b_quentity_edt);
            b_orderimage = itemView.findViewById(R.id.b_image);
            addorder = itemView.findViewById(R.id.b_add);
            minusorder = itemView.findViewById(R.id.b_minus);
            totaltext = itemView.findViewById(R.id.total);

        }

    }


}
