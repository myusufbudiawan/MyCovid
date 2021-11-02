package com.example.mycovid02.onboarding;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mycovid02.R;

public class OnboardingAdapter extends FragmentStateAdapter {

    public OnboardingAdapter(FragmentActivity fm) {
        super(fm);
    }
    String ass ="ass";

    @Override
    public Fragment createFragment(int pos) {

        Log.i( ass,"Saya di OnboardingAdapter");

        switch (pos) {
            case 0:
                return ContentFragment.newInstance(
                        R.string.onboarding_aim_heading,
                        R.string.onboarding_aim_title,
                        R.string.onboarding_aim_text1,
                        R.string.onboarding_aim_text2,
                        false
                );

            case 1:
                return ContentFragment.newInstance(
                        R.string.onboarding_privacy_heading,
                        R.string.onboarding_privacy_title,
                        R.string.onboarding_privacy_text1,
                        R.string.onboarding_privacy_text2,
                        true
                );

            case 2:
                return ContentFragment.newInstance(
                        R.string.onboarding_hiw_heading,
                        R.string.onboarding_hiw_title,
                        R.string.onboarding_hiw_text1,
                        R.string.onboarding_hiw_text2,
                        true
                );

            case 3:
                return BatteryPermissionFragment.newInstance();

            case 4:
                return LocationPermissionFragment.newInstance();

            case 5:
                return ContentFragment.newInstance(
                        R.string.onboarding_whatitdoes_heading,
                        R.string.onboarding_whatitdoes_title,
                        R.string.onboarding_whatitdoes_text1,
                        R.string.onboarding_whatitdoes_text2,
                        false);
            case 6:
                Log.i( ass,"Saya di case 5");
                return FinishBoardingFragment.newInstance();

        }
        throw new IllegalArgumentException("There is no fragment for viewpager position " + pos);
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
