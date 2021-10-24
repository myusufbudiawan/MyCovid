package com.example.mycovid02;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.mycovid02.main.HomeFragment;
import com.example.mycovid02.onboarding.OnboardingActivity;

public class MainActivity extends AppCompatActivity {

    private MyDatabase myDatabase;
    ActivityResultLauncher<Intent> mGetActivity;

    String ass = "ass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        }

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