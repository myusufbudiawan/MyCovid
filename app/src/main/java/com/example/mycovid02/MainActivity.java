package com.example.mycovid02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mycovid02.onboarding.OnboardingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String ass = "ass";
        Log.i( ass,"Saya di MainActivity");
        //startActivityForResult(new Intent(this, OnboardingActivity.class), 123);
        startActivity(new Intent(this, OnboardingActivity.class));
    }
}