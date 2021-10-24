package com.example.mycovid02.onboarding;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mycovid02.R;
import org.dpppt.android.sdk.DP3T;

public class OnboardingActivity extends FragmentActivity {

    private ViewPager2 mPager;
    private FragmentStateAdapter mpagerAdapter;

    String ass ="ass";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i( ass,"Saya di OnboardingActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        mPager = findViewById(R.id.pager);
        mPager.setUserInputEnabled(false);
        mpagerAdapter = new OnboardingAdapter(this); //content for pager
        mPager.setAdapter(mpagerAdapter);
    }

    public void toNextPage() {
        int item = mPager.getCurrentItem();
        if (item < mpagerAdapter.getItemCount() - 1) {
            mPager.setCurrentItem(item + 1, true);
        } else {
            DP3T.start(this);
            setResult(RESULT_OK);
            Log.i( ass,"Saya finishing OnboardingActivity");
            finish();
        }
    }
}
