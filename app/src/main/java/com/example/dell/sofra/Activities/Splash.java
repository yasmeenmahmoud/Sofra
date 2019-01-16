package com.example.dell.sofra.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dell.sofra.R;
import com.example.dell.sofra.Restaurants_Fragments.TheMainActivity;
import com.example.dell.sofra.Client_Fragments.Home;
import com.example.dell.sofra.Helper.SharedPereferenceClass;

import static com.example.dell.sofra.Helper.SharedPereferenceClass.SELL;
import static com.example.dell.sofra.Helper.SharedPereferenceClass.USER;


public class Splash extends AppCompatActivity {
SharedPereferenceClass sharedPereferenceClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPereferenceClass=new SharedPereferenceClass();
        sharedPereferenceClass.storeKey(this,USER,"");
        sharedPereferenceClass.storeKey(this,SELL,"");
    }

    public void orderFood(View view) {
        sharedPereferenceClass.storeKey(this,USER,USER);
        Intent homeintent=new Intent(this,Home.class);
        startActivity(homeintent);
    }

    public void sellorder(View view) {
        sharedPereferenceClass.storeKey(this,SELL,SELL);
        Intent homeintent=new Intent(this,TheMainActivity.class);
        startActivity(homeintent);
    }
}
