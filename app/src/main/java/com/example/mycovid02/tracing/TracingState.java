package com.example.mycovid02.tracing;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.example.mycovid02.R;

public enum TracingState {
    ACTIVE,
    NOT_ACTIVE,
    ENDED;

    public static @StringRes
    int getTitle(TracingState tracingState) {
        switch (tracingState) {
            case ACTIVE:
                return R.string.tracing_active_text;
            case NOT_ACTIVE:
                return R.string.tracing_turned_off_text;
            case ENDED:
                return R.string.tracing_ended_text;

        }
        return -1;
    }

    public static @DrawableRes
    int getIcon(TracingState tracingState) {
        switch(tracingState) {
            case ACTIVE:
                return R.drawable.ic_check;
            case NOT_ACTIVE:
                return R.drawable.ic_warning_red;
            case ENDED:
                return R.drawable.ic_block;
        }
        return -1;
    }

    public static @ColorRes
    int getBackgroundColor(TracingState tracingState) {
        switch (tracingState) {
            case ACTIVE:
                return R.color.status_blue_bg;
            case NOT_ACTIVE:
                return R.color.grey_dark_lighter;
            case ENDED:
                return R.color.status_purple_bg;
        }
        return R.color.grey_dark_lighter;
    }

    public static int getIllu(TracingState state) {
        switch (state) {
            case ACTIVE:
                return R.drawable.person1;
            case ENDED:
                return R.drawable.ic_warning_red;
            case NOT_ACTIVE:
            default:
                return -1;
        }
    }
}
