package com.example.dell.sofra.Client_Fragments;


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
public class Orders extends Fragment {


    public Orders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.fragment_orders, container, false);
        final TabLayout mytabs = view.findViewById(R.id.tabs);
        ViewPager pager = view.findViewById(R.id.viewpager);
        setupViewPager(pager);
        mytabs.setupWithViewPager(pager);
        mytabs.getTabAt(0).select();

        return view;

    }

    private void setupViewPager(ViewPager viewPager) {
        Orders.Adapter adapter = new Orders.Adapter(getFragmentManager());

        // The view has RTL layout
        adapter.addFragment(new CurrentOrders_Fragment(), getResources().getString(R.string.currentorders));
        adapter.addFragment(new PreviousOrders_Fragment(),getResources().getString(R.string.previousorders));

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

