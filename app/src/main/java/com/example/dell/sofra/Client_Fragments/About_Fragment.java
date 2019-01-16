package com.example.dell.sofra.Client_Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.sofra.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class About_Fragment extends Fragment {

View view;
    public About_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_about_, container, false);
        return view;
    }
}
