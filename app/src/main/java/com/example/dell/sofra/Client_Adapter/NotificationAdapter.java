package com.example.dell.sofra.Client_Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.sofra.Model.Notification_Data;
import com.example.dell.sofra.Model.UserNotification_Data;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Helper.SharedPereferenceClass;

import java.util.List;

import static com.example.dell.sofra.Helper.SharedPereferenceClass.SELL;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Filesviewholder> {
    private Context context;
    private List<Notification_Data> data;
    private LayoutInflater layoutInflater;
    private List<UserNotification_Data> mydata;
    int resourcelayoue;

    public NotificationAdapter(Context context, List<Notification_Data> data) {
        this.context = context;
        this.data = data;
        layoutInflater = layoutInflater.from(context);
    }
    SharedPereferenceClass sharedPereferenceClass = new SharedPereferenceClass();

    public NotificationAdapter(Context context, int resource, List<UserNotification_Data> mydata) {
        this.context = context;
        this.mydata = mydata;
        layoutInflater = LayoutInflater.from(context);
        this.resourcelayoue = resource;
    }
    @NonNull
    @Override
    public NotificationAdapter.Filesviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = layoutInflater.inflate(R.layout.notification_listitemview, null);
        NotificationAdapter.Filesviewholder filesviewholder = new NotificationAdapter.Filesviewholder(view);
        return filesviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationAdapter.Filesviewholder filesviewholder, int i) {
        if (sharedPereferenceClass.getStoredKey(context, SELL).equals(SELL)) {

        final Notification_Data currentposition = data.get(i);
        filesviewholder.notificationtitle.setText(currentposition.getTitle());
        filesviewholder.notificationdate.setText(currentposition.getUpdatedAt());}
        else{

            final UserNotification_Data position = mydata.get(i);
            filesviewholder.notificationtitle.setText(position.getContent());
            filesviewholder.notificationdate.setText(position.getUpdatedAt());
        //    filesviewholder.notificationtime.setText(position.getTitleEn());

        }
       // filesviewholder.notificationtime.setText(currentposition.getContent());
       // filesviewholder.notificationimage.setImageResource(currentposition.());
    }

    @Override
    public int getItemCount() {
        if (sharedPereferenceClass.getStoredKey(context, SELL).equals(SELL)) {
            return data.size();}
            else {            return mydata.size();
    }
    }

    public class Filesviewholder extends RecyclerView.ViewHolder {
        TextView notificationtitle, notificationdate, notificationtime;
      //  ImageView notificationimage;

        public Filesviewholder(@NonNull View itemView) {
            super(itemView);
            notificationtitle = itemView.findViewById(R.id.notificaiontitle);
            notificationdate = itemView.findViewById(R.id.notificaiondate);
            //notificationtime = itemView.findViewById(R.id.notificaiontime);
          //  notificationimage = itemView.findViewById(R.id.notificationicon);

        }
    }
}
