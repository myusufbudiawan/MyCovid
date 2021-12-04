package com.example.mycovid02;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.mycovid02.information.InformationFragment;
import com.example.mycovid02.main.HomeFragment;
import com.example.mycovid02.onboarding.OnboardingActivity;
import com.example.mycovid02.reports.ReportsFragment;
import com.example.mycovid02.statistics.CardTotalCases;
import com.example.mycovid02.statistics.StatisticsFragment;
import com.example.mycovid02.tracing.TracingViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_CONSUMED_EXPOSED_INTENT = "STATE_CONSUMED_EXPOSED_INTENT";
    public static final String ACTION_STOP_TRACING = "ACTION_STOP_TRACING";

    private MyDatabase myDatabase;

    private TracingViewModel tracingViewModel;
    private boolean consumedExposedIntent;

    ActivityResultLauncher<Intent> mGetActivity;

    String ass = "ass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatisticsFragment.getData();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, HomeFragment.newInstance())
                            .commit();
                    break;
                case R.id.nav_statistics:
                    getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, StatisticsFragment.newInstance())
                            .commit();
                    break;
                case R.id.nav_information:
                    getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, InformationFragment.newInstance())
                            .commit();
                    break;
            }

            return true;

        });

        myDatabase = MyDatabase.getInstance(this);

        Log.i( ass,"Saya di MainActivity");

        //startActivityForResult(new Intent(this, OnboardingActivity.class), 12);
        //startActivity(new Intent(this, OnboardingActivity.class));

        mGetActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            myDatabase.setOnboardingDone(true);
                            showHomeFragment();
                        } else {
                            finish();
                        }
                    }
                });

        if (savedInstanceState == null) {
            boolean onboardingDone = myDatabase.getOnboardingDone();

            if (onboardingDone) {
                showHomeFragment();
            } else {
                //startActivity(new Intent(this, OnboardingActivity.class));

                mGetActivity.launch(new Intent(this, OnboardingActivity.class));
            }
        } else {
            consumedExposedIntent = savedInstanceState.getBoolean(STATE_CONSUMED_EXPOSED_INTENT);
        }

        tracingViewModel = new ViewModelProvider(this).get(TracingViewModel.class);
        tracingViewModel.sync();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkIntentForActions();
    }

    private void checkIntentForActions() {
        Intent intent = getIntent();
        String intentAction = intent.getAction();
        boolean launchFromHistory = (intent.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0;

        if (ACTION_STOP_TRACING.equals(intentAction) && !launchFromHistory && !consumedExposedIntent) {
            consumedExposedIntent = true;
            showReportsFragment();
            intent.setAction(null);
            setIntent(intent);

        }
    }

    private void showReportsFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, ReportsFragment.newInstance())
                .commit();
    }

    private void showHomeFragment() {
        Log.i( ass,"Saya di showHomeFragment()");

        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, HomeFragment.newInstance())
                .commit();
    }

/*    @Override
    protected void onActivityResult(int reqCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(reqCode,resultCode,data);
        if (resultCode == RESULT_OK) {
            myDatabase.setOnboardingDone(true);
            showHomeFragment();
        } else {
            finish();
        }
    }*/


}