package com.example.mycovid02.onboarding;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mycovid02.R;
import com.example.mycovid02.utility.FeatureCheck;

import java.util.Objects;

public class LocationPermissionFragment extends Fragment {

    public static final int REQUEST_CODE_ASK_PERMISSION_FINE_LOCATION = 123;

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
                    Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permission, REQUEST_CODE_ASK_PERMISSION_FINE_LOCATION);
        });

        continueBtn.setOnClickListener(v -> ((OnboardingActivity) getActivity()).toNextPage());

    }

    @Override
    public void onResume(){
        super.onResume();
        updateFragment();
    }

    private void updateFragment() {
        boolean locationAllowed = FeatureCheck.isLocationAllowed(requireContext());
        if (locationAllowed) {
            locationBtn.setClickable(false);
            locationBtn.setBackgroundTintList(requireContext().getColorStateList(R.color.white));
            locationBtn.setElevation(0);
            locationBtn.setText("Enabled");

        } else {
            locationBtn.setClickable(true);
            locationBtn.setBackgroundTintList(requireContext().getColorStateList(R.color.blue_main));
            locationBtn.setElevation(0);
            locationBtn.setText("Allow");

        }
        continueBtn.setVisibility(locationAllowed ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSION_FINE_LOCATION) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat
                        .shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                    new AlertDialog.Builder(requireActivity())
                            .setTitle(R.string.location_btn_permission)
                            .setMessage(R.string.android_foreground_service_notification_error_location_permission)
                            .setPositiveButton(getString(R.string.button_ok),
                                    (dialogInterface, i) -> {
                                        FeatureCheck.openSettings(requireActivity());
                                        dialogInterface.dismiss();
                                    })
                            .create()
                            .show();
                }
            }
            ((OnboardingActivity) getActivity()).toNextPage();
        }
    }
}
