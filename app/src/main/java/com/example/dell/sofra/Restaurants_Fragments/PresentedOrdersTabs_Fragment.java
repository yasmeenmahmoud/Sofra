package com.example.dell.sofra.Restaurants_Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.sofra.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PresentedOrdersTabs_Fragment extends Fragment {


    public PresentedOrdersTabs_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.fragment_presented_orders_tabs_, container, false);
        final TabLayout mytabs = view.findViewById(R.id.tabs);
        ViewPager pager = view.findViewById(R.id.viewpager);
        setupViewPager(pager);
        mytabs.setupWithViewPager(pager);
        mytabs.getTabAt(0).select();

        return view;

    }
    private void setupViewPager(ViewPager viewPager) {
        PresentedOrdersTabs_Fragment.Adapter adapter = new PresentedOrdersTabs_Fragment.Adapter(getChildFragmentManager());

        // The view has RTL layout
        adapter.addFragment(new NewOrders_Fragment(), getResources().getString(R.string.neworders));
       adapter.addFragment(new OurCurrentOrders_Fragment(),getResources().getString(R.string.ourcurrentorders));
       adapter.addFragment(new OurPreviousOrders_Fragment(),getResources().getString(R.string.ourpreviousorders));

        viewPager.setAdapter(adapter);
    }
    static  class Adapter extends FragmentStatePagerAdapter {
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
}
