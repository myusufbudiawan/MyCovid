package com.example.mycovid02.onboarding;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.example.mycovid02.R;

public class PermissionButton {
    public static void setButtonDefault(Button button, @StringRes int buttonLabel) {
        Context context = button.getContext();
        button.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        button.setTextColor(Color.WHITE);
        button.setText(buttonLabel);
        button.setClickable(true);
        button.setElevation(context.getResources().getDimensionPixelSize(R.dimen.button_elevation));
        button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.teal_200));
    }

    public static void setButtonOk(Button button, @StringRes int grantedLabel) {
        Context context = button.getContext();
        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_circle, 0, 0, 0);
        button.setCompoundDrawableTintList(ContextCompat.getColorStateList(context, R.color.teal_200));
        button.setTextColor(ContextCompat.getColor(context, R.color.teal_200));
        button.setText(grantedLabel);
        button.setClickable(false);
        button.setElevation(0);
        button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.white));
    }
}
