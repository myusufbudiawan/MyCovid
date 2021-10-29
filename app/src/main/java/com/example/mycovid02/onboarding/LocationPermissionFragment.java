package com.example.mycovid02.onboarding;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycovid02.R;

public class LocationPermissionFragment extends Fragment {

    private Button locationBtn;
    private Button continueBtn;

    public static LocationPermissionFragment newInstance() {
        return  new LocationPermissionFragment();
    }

    public LocationPermissionFragment(){
        super(R.layout.location_permission_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        locationBtn = view.findViewById(R.id.location_button);
        continueBtn = view.findViewById(R.id.location_continue_button);

        locationBtn.setOnClickListener(v -> {
            String[] permission = new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
        });

        continueBtn.setOnClickListener(v -> ((OnboardingActivity) getActivity()).toNextPage());


    }
}
