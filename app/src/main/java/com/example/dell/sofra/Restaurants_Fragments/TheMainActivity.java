package com.example.dell.sofra.Restaurants_Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dell.sofra.Activities.Splash;
import com.example.dell.sofra.Client_Fragments.About_Fragment;
import com.example.dell.sofra.Client_Fragments.AddNewOrder_Fragment;
import com.example.dell.sofra.Client_Fragments.Conditions_Fragment;
import com.example.dell.sofra.Client_Fragments.ContactUs_Fragment;
import com.example.dell.sofra.Client_Fragments.Notification_fragment;
import com.example.dell.sofra.Client_Fragments.Shareapp_Fragment;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Register_Fragment;
import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.example.dell.sofra.Login_Fragment;
import com.example.dell.sofra.Client_Fragments.MenuFragment;
import com.example.dell.sofra.Client_Fragments.Playstore_Fragment;
import com.example.dell.sofra.Client_Fragments.RestaurantDetails_fragment;

import static com.example.dell.sofra.Helper.SharedPereferenceClass.SELL;


public class TheMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int mCurrentSelectedPosition = 0;
    Fragment fr;
    SharedPereferenceClass sharedPereferenceClass;
    private Fragment restaurantDetails_fragment;
    private Fragment fragmentCurrent;
    Login_Fragment login_fragment = new Login_Fragment();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_main);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        restaurantDetails_fragment = new RestaurantDetails_fragment();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        sharedPereferenceClass = new SharedPereferenceClass();
        sharedPereferenceClass.getStoredKey(this, SELL);
        getSupportActionBar().setTitle("بيع طعام");
        fr = new Fragment();
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        View header = navigationView.getHeaderView(0);
        ImageButton imageButton = header.findViewById(R.id.settingtosign);
        TextView restaurantname = header.findViewById(R.id.restaurantname);
        restaurantname.setText(sharedPereferenceClass.getStoredKey(this, "RESTAURANTNAME"));
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadFragment(login_fragment);
                mCurrentSelectedPosition = 11;
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.mipmap.drawericon);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mCurrentSelectedPosition < 9 && mCurrentSelectedPosition > 0 &&
                    sharedPereferenceClass.getStoredKey(this, "restaurant_islogged").equals("true")) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                LoadFragment(restaurantDetails_fragment);
                getSupportActionBar().setTitle("بيع طعام ");
                mCurrentSelectedPosition = 0;
            } else if (!sharedPereferenceClass.getStoredKey(this, "restaurant_islogged").equals("true")) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                LoadFragment(login_fragment);
                getSupportActionBar().setTitle("تسجيل الدخول ");
                startActivity(new Intent(TheMainActivity.this, Splash.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (mCurrentSelectedPosition == 9) {
                LoadFragment(new OurProuducts_Fragment());
                getSupportActionBar().setTitle("منتجاتى ");
                mCurrentSelectedPosition = 1;
            } else if (mCurrentSelectedPosition == 11) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                LoadFragment(restaurantDetails_fragment);
                getSupportActionBar().setTitle("بيع طعام ");
                mCurrentSelectedPosition = 0;
            } else if (mCurrentSelectedPosition == 10) {
                LoadFragment(new OurOffers_Fragment());
                getSupportActionBar().setTitle("عروضى ");
                mCurrentSelectedPosition = 5;

            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.the_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.backicon) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ourhomeicon:
                mCurrentSelectedPosition = 0;
                if ((sharedPereferenceClass.getStoredKey(this, "restaurant_islogged").equals("true"))) {
                    fr = new RestaurantDetails_fragment();
                    addFragment(restaurantDetails_fragment);
                    getSupportActionBar().setTitle("بيع طعام");

                } else {
                    fr = login_fragment;
                    addFragment(login_fragment);
                }
                break;
            case R.id.ourordersicon:
                mCurrentSelectedPosition = 1;
                fr = new OurProuducts_Fragment();
                getSupportActionBar().setTitle("منتجاتى");
                break;
            case R.id.ourorderpresentedicon:
                mCurrentSelectedPosition = 2;
                fr = new PresentedOrdersTabs_Fragment();
                getSupportActionBar().setTitle("الطلبات المقدمه");
                break;
            case R.id.ournotificationicon:
                mCurrentSelectedPosition = 3;
                fr = new Notification_fragment();
                getSupportActionBar().setTitle("التنبيهات");
                break;
            case R.id.monyicon:
                mCurrentSelectedPosition = 4;
                fr = new Commission_Fragment();
                getSupportActionBar().setTitle("العموله");
                break;
            case R.id.oourffersicon:
                mCurrentSelectedPosition = 5;
                fr = new OurOffers_Fragment();
                getSupportActionBar().setTitle("عروضى");
                break;
            case R.id.ourabouticon:
                mCurrentSelectedPosition = 6;
                fr = new About_Fragment();
                getSupportActionBar().setTitle("عن التطبيق");
                break;
            case R.id.ourshareicon:
                mCurrentSelectedPosition = 7;
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            case R.id.ourcallusicon:
                mCurrentSelectedPosition = 8;
                fr = new ContactUs_Fragment();
                getSupportActionBar().setTitle("تواصل معنا");
                break;
            default:
                mCurrentSelectedPosition = 0;
        }
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        item.setChecked(true);
        LoadFragment(fr);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void LoadFragment(Fragment fr) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.f2Content, fr);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof AddNewProduct_Fragment) {
            mCurrentSelectedPosition = 9;
        } else if (fragment instanceof AddNewOffer_Fragment) {
            mCurrentSelectedPosition = 10;
        } else if (fragment instanceof Login_Fragment) {
                mCurrentSelectedPosition = 11;
        } else if (fragment instanceof Register_Fragment) {
            if (sharedPereferenceClass.getStoredKey(this, SELL).equals("SELL")) {
                mCurrentSelectedPosition = 12;
            }
        } else if (fragment instanceof FollowRegister_Fragment)
            if (sharedPereferenceClass.getStoredKey(this, SELL).equals("SELL")) {
                mCurrentSelectedPosition = 13;
            } else {
                mCurrentSelectedPosition = 14;
            } }
    private void addFragment(Fragment fragment) {
        fragmentCurrent = fragment;
        getSupportFragmentManager().beginTransaction().add(R.id.f2Content, fragment).commit();
    }
}
