package com.example.mycovid02.mycovidcode;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.mycovid02.R;

public class InsertCodeActivity extends FragmentActivity {

    private boolean allowed = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_code);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.code_fragment_container, InsertCodeFragment.newInstance()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (allowed) {
            super.onBackPressed();
        }
    }

    public void allowBackButton(boolean allowed) {
        this.allowed = allowed;
    }
}
