package com.example.mycovid02.main;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.mycovid02.R;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        super(R.layout.home_fragment);
    }

    public static HomeFragment newInstance() {

        String ass = "ass";
        Log.i( ass,"Saya di Home Fragment");

        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
