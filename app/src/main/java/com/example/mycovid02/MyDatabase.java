package com.example.mycovid02;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MyDatabase {

    private static final String KEY_ONBOARDING_DONE = "onboarding_completed";

    private static MyDatabase instance;

    private static SharedPreferences myPreference;

    private MyDatabase (@NonNull Context context) {
        try {
            String masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            this.myPreference = EncryptedSharedPreferences
                    .create("MyDatabase", masterKey, context, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

        } catch (GeneralSecurityException|IOException e) {
            e.printStackTrace();
            this.myPreference = null;
        }
    }

    public static MyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new MyDatabase(context);
        }
        return instance;
    }

    public boolean getOnboardingDone() {
        return myPreference.getBoolean(KEY_ONBOARDING_DONE, false);
    }

    public void setOnboardingDone(boolean done) {
        myPreference.edit().putBoolean(KEY_ONBOARDING_DONE, done).apply();
    }
}
