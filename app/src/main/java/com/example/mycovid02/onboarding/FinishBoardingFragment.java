package com.example.mycovid02.onboarding;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycovid02.R;

public class FinishBoardingFragment extends Fragment {

    public static FinishBoardingFragment newInstance() {
        return new FinishBoardingFragment();
    }

    public FinishBoardingFragment() {
        super(R.layout.finished_onboarding_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.onboarding_continue_btn)
                .setOnClickListener(v -> {
                    ((OnboardingActivity) getActivity()).toNextPage();
                });
    }
}
