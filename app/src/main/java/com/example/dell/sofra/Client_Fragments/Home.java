package com.example.dell.sofra.Client_Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dell.sofra.Activities.Splash;
import com.example.dell.sofra.Login_Fragment;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Register_Fragment;
import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.example.dell.sofra.Restaurants_Fragments.TheMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dell.sofra.Helper.SharedPereferenceClass.USER;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content)
    ConstraintLayout content;
    @BindView(R.id.flContent)
    FrameLayout flContent;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private int mCurrentSelectedPosition = 0;
    Fragment fr;
    private FragmentManager fragmentManager;
    private Fragment homeitem_Fragment;
    public static SharedPereferenceClass sharedPereferenceClass;
    private Fragment fragmentCurrent;

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        sharedPereferenceClass = new SharedPereferenceClass();
        sharedPereferenceClass.getStoredKey(this, USER);
        homeitem_Fragment = new Homeitem_Fragment();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        fr = new Fragment();
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.mipmap.drawericon);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView clientname = header.findViewById(R.id.clientname);
        clientname.setText("مرحبا بك " + "\n" + sharedPereferenceClass.getStoredKey(this, "CLIENT_NAME"));
        ImageButton imageButton = header.findViewById(R.id.setting);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login_Fragment login_fragment = new Login_Fragment();
                LoadFragment(login_fragment);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

    }

    private void addFragment(Fragment fragment) {
        fragmentCurrent = fragment;
        getSupportFragmentManager().beginTransaction().add(R.id.flContent, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mCurrentSelectedPosition < 7 && mCurrentSelectedPosition > 0) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                LoadFragment(homeitem_Fragment);
                getSupportActionBar().setTitle("طلب طعام ");
                mCurrentSelectedPosition = 0;
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.backicon) {
            onBackPressed();
            return true;
        }
        if (id == R.id.basket) {
            LoadFragment(new OrderBasket_Fragment());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.homeicon:
                mCurrentSelectedPosition = 0;
                fr = homeitem_Fragment;
                addFragment(homeitem_Fragment);
                break;
            case R.id.ordersicon:
                mCurrentSelectedPosition = 1;
                fr = new Orders();
                getSupportActionBar().setTitle("طلبات حاليه ");
                break;
            case R.id.notificationicon:
                mCurrentSelectedPosition = 2;
                fr = new Notification_fragment();
                getSupportActionBar().setTitle("التنبيهات");
                break;
            case R.id.offersicon:
                mCurrentSelectedPosition = 3;
                fr = new Offers_Fragment();
                getSupportActionBar().setTitle("جديد العروض");
                break;
            case R.id.abouticon:
                mCurrentSelectedPosition = 4;
                fr = new About_Fragment();
                getSupportActionBar().setTitle("عن التطبيق ");
                break;
            case R.id.shareicon:
                mCurrentSelectedPosition = 5;
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;

            case R.id.callusicon:
                mCurrentSelectedPosition = 6;
                fr = new ContactUs_Fragment();
                getSupportActionBar().setTitle("تواصل معنا ");
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void LoadFragment(Fragment fr) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flContent, fr);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //    @Override
//    public void onAttachFragment(Fragment fragment) {
//
//          if (fragment instanceof Conditions_Fragment) {
//            getSupportActionBar().setTitle("التعليقات والتقييم ");
//        } else if (fragment instanceof Playstore_Fragment) {
//            getSupportActionBar().setTitle("معلومات المتجر");
//        } else if (fragment instanceof MealDetails_Fragment) {
//            getSupportActionBar().setTitle("تفاصيل الوجبه ");
//        } else if (fragment instanceof OrderBasket_Fragment) {
//            getSupportActionBar().setTitle("سلة الطلبات ");
//        } else if (fragment instanceof Login_Fragment) {
//            getSupportActionBar().setTitle("تسجيل الدخول ");
//        } else if (fragment instanceof Register_Fragment) {
//            getSupportActionBar().setTitle("انشاء حساب جديد");
//        } else if (fragment instanceof AddNewOrder_Fragment) {
//            getSupportActionBar().setTitle("اضف طلب جديد");
//
//        }
//
//    }
    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setTitle("طلب طعام");

    }

}
