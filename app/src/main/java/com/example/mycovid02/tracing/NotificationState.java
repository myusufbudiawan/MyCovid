package com.example.mycovid02.tracing;

import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;

import com.example.mycovid02.R;

public enum NotificationState {
    NO_REPORTS,
    EXPOSED,
    POSITIVE_TESTED;

    @StringRes public static int getTitle(NotificationState notificationState) {
        switch (notificationState) {
            case NO_REPORTS:
                return R.string.no_reports_title;
            case EXPOSED:
                return R.string.exposed_title;
            case POSITIVE_TESTED:
                return R.string.test_positive_title;
        }
        return -1;
    }

    @StringRes public static int getText(NotificationState notificationState) {
        switch (notificationState) {
            case NO_REPORTS:
                return R.string.no_report_subtitle;
            case EXPOSED:
                return R.string.exposed_subtitle;
            case POSITIVE_TESTED:
                return R.string.test_positive_subtitle;
        }
        return -1;
    }

    @ColorRes
    public static int getBackgroundColor(NotificationState notificationState) {
        switch (notificationState) {
            case NO_REPORTS:
                return R.color.status_green_bg;
            case EXPOSED:
                return R.color.blue_main;
            case POSITIVE_TESTED:
                return R.color.purple_main;
        }
        return -1;
    }

    public static int getIllu(NotificationState state) {
        switch (state) {
            case NO_REPORTS:
                return R.drawable.person1;
            case EXPOSED:
            case POSITIVE_TESTED:
            default:
                return -1;
        }
    }
}
