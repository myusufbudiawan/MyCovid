package com.example.mycovid02.onboarding;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycovid02.R;
import com.example.mycovid02.utility.FeatureCheck;

public class BatteryPermissionFragment extends Fragment {

    private Button battBtn;
    private Button continueBtn;

    private boolean wasUserActive = false;

    public static BatteryPermissionFragment newInstance() {
        return new BatteryPermissionFragment();
    }

    public BatteryPermissionFragment() {
        super(R.layout.battery_permission_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstaceState) {
        battBtn = view.findViewById(R.id.battery_permission_button);
        battBtn.setOnClickListener(v -> {
            startActivity(new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                    Uri.parse("package:" + requireContext().getPackageName())));
            wasUserActive = true;
        });
        continueBtn = view.findViewById(R.id.battery_permission_continue_button);
        continueBtn.setOnClickListener(v -> {
            ((OnboardingActivity) getActivity()).toNextPage();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFragmentState();
    }

    private void updateFragmentState() {
        boolean batteryOptDeactivated = FeatureCheck.isBatteryOptDisabled(requireContext());
        if (batteryOptDeactivated) {
            PermissionButton.setButtonOk(battBtn, R.string.android_onboarding_battery_permission_button_deactivated);
        } else {
            PermissionButton.setButtonDefault(battBtn, R.string.android_onboarding_battery_permission_button);
        }
        continueBtn.setVisibility(batteryOptDeactivated || wasUserActive ? View.VISIBLE : View.GONE);

        if (batteryOptDeactivated && wasUserActive) {
            ((OnboardingActivity) getActivity()).toNextPage();
        }
    }
}
