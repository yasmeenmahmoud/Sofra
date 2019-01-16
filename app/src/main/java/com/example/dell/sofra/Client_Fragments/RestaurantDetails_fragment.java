package com.example.dell.sofra.Client_Fragments;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.sofra.Model.RestaurantDetails_Response;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Restaurants_Fragments.TheMainActivity;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;
import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dell.sofra.Helper.SharedPereferenceClass.SELL;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDetails_fragment extends Fragment {
    Button menusign;
    public TextView rstrauntName, meals, minimumorder, arrivalcost, ordertime, opennow;
    ImageView restaurant_image;
    RatingBar ratingBar;
    int restaurant_id;
    String minimum;
    int myrestaurantid;
    SharedPereferenceClass sharedPereferenceClass;

    public RestaurantDetails_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_restaurant_detailss_, container, false);
        rstrauntName = view.findViewById(R.id.H_text1);
        meals = view.findViewById(R.id.H_text2);
        minimumorder = view.findViewById(R.id.H_text4);
        arrivalcost = view.findViewById(R.id.H_text5);
        opennow = view.findViewById(R.id.H_text3);
        restaurant_image = view.findViewById(R.id.H_imageview);
        ratingBar = view.findViewById(R.id.ratingbar);
        final TabLayout mytabs = view.findViewById(R.id.tabs);
        final ViewPager pager = view.findViewById(R.id.viewpager);
        sharedPereferenceClass = new SharedPereferenceClass();
        if (sharedPereferenceClass.getStoredKey(getActivity(), SELL).equals(SELL)) {
            rstrauntName.setText(sharedPereferenceClass.getStoredKey(getContext(), "RESTAURANTNAME"));
            meals.setText(sharedPereferenceClass.getStoredKey(getContext(), "RESTAURANTCATEGORY"));
            minimumorder.setText(sharedPereferenceClass.getStoredKey(getContext(), "RESTAURANTMINIMAMCHARGE"));
            arrivalcost.setText(sharedPereferenceClass.getStoredKey(getContext(), "RESTAURANTDELIVERYCOST"));
            opennow.setText(sharedPereferenceClass.getStoredKey(getContext(), "RESTAURANTAVAILABILITY"));
            Uri imageuri = Uri.parse(sharedPereferenceClass.getStoredKey(getContext(), "RESTAURANT_IMAGE"));
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageuri);
                restaurant_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        setupViewPager(pager);
        mytabs.setupWithViewPager(pager);
        mytabs.getTabAt(0).select();
        if (getArguments() != null) {
            restaurant_id = getArguments().getInt("restaurant_id");
            sharedPereferenceClass.storeKey(getContext(), "restaurant_id", restaurant_id);
        }
        addRestaurantsDetailsData();
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    getActivity().setTitle("قائمة الطعام");
                } else if (position == 1) {
                    getActivity().setTitle("التعليقات والتقييم");
                } else if (position == 2) {
                    getActivity().setTitle("معلومات المتجر");
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedPereferenceClass.getStoredKey(getActivity(), SELL).equals(SELL)) {
            TheMainActivity theMainActivity = (TheMainActivity) getActivity();
            theMainActivity.getSupportActionBar().setTitle("قائمة الطعام");
        } else {
            Home home = (Home) getActivity();
            home.getSupportActionBar().setTitle("قائمة الطعام");
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new MenuFragment(), getResources().getString(R.string.menu));
        adapter.addFragment(new Comments_Fragment(), getResources().getString(R.string.comments));
        Playstore_Fragment playstore_fragment = new Playstore_Fragment();
        adapter.addFragment(playstore_fragment, getResources().getString(R.string.playstoreinf));
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    private void addRestaurantsDetailsData() {
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<RestaurantDetails_Response> call = apiInterface.getRestaurantDetails(restaurant_id);
        call.enqueue(new Callback<RestaurantDetails_Response>() {
            @Override
            public void onResponse(Call<RestaurantDetails_Response> call, Response<RestaurantDetails_Response> response) {
                RestaurantDetails_Response restaurantDetails_response = response.body();
                try {
                    minimumorder.setText(restaurantDetails_response.getData().getMinimumCharger());
                    arrivalcost.setText(restaurantDetails_response.getData().getDeliveryCost());
                    opennow.setText(restaurantDetails_response.getData().getAvailability());
                    rstrauntName.setText(restaurantDetails_response.getData().getName());
                    StringBuilder sb = new StringBuilder();
                    String prefix = "";
                    for (int i = 0; i < restaurantDetails_response.getData().getCategories().size(); i++) {
                        sb.append(prefix);
                        prefix = ",";
                        sb.append(restaurantDetails_response.getData().getCategories().get(i).getName());
                        meals.setText(sb);

                    }

                    Picasso.get().load(restaurantDetails_response.getData().getPhotoUrl())
                            .into(restaurant_image);
                    ratingBar.setNumStars(restaurantDetails_response.getData().getRate());
                    ratingBar.setRating(restaurantDetails_response.getData().getRate());
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<RestaurantDetails_Response> call, Throwable t) {
                Toast.makeText(getContext(), "request faild ", Toast.LENGTH_SHORT).show();

            }

        });
    }

}

