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
    private static final String KEY_PENDING_REPORTS_HEADER_ANIMATION = "pending_reports_header_animation";
    private static final String KEY_INFECTED_DATE = "infected_date";
    private static final String KEY_CALL_PENDING = "call_pending";
    private static final String KEY_INFORM_TIME_REQ = "inform_time_req";
    private static final String KEY_INFORM_CODE_REQ = "inform_code_req";
    private static final String KEY_INFORM_TOKEN_REQ = "inform_token_req";


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

    public boolean isReportsHeaderAnimationPending() {
        return myPreference.getBoolean(KEY_PENDING_REPORTS_HEADER_ANIMATION, false);
    }

    public void setInfectedDate(long date) {
        myPreference.edit().putLong(KEY_INFECTED_DATE, date).apply();
    }

    public long getInfectedDate() {
        return myPreference.getLong(KEY_INFECTED_DATE, 0);
    }

    public boolean isCallPending() {
        return myPreference.getBoolean(KEY_CALL_PENDING, true);
    }

    public void setReportsHeaderAnimationPending(boolean b) {
        myPreference.edit().putBoolean(KEY_PENDING_REPORTS_HEADER_ANIMATION, b).apply();
    }

    public long getLatestInformRequestTime() {
        return myPreference.getLong(KEY_INFORM_TIME_REQ, 0);
    }

    public String getLatestInformCode() {
        return myPreference.getString(KEY_INFORM_CODE_REQ, null);
    }

    public String getLatestInformToken() {
        return myPreference.getString(KEY_INFORM_TOKEN_REQ, null);
    }

    public void clearInformTimeCodeToken() {
        myPreference.edit().remove(KEY_INFORM_TIME_REQ)
                .remove(KEY_INFORM_CODE_REQ)
                .remove(KEY_INFORM_TOKEN_REQ)
                .apply();
    }
}
