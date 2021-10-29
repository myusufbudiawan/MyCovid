package com.example.mycovid02.tracing;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.example.mycovid02.R;

import org.dpppt.android.sdk.TracingStatus;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TracingErrorStateHelper {

    private static final List<TracingStatus.ErrorState> possibleErrorStatesOrderedByPriority = Arrays.asList(
            TracingStatus.ErrorState.BLE_NOT_SUPPORTED,
            TracingStatus.ErrorState.MISSING_LOCATION_PERMISSION,
            TracingStatus.ErrorState.BLE_DISABLED,
            TracingStatus.ErrorState.LOCATION_SERVICE_DISABLED,
            TracingStatus.ErrorState.BATTERY_OPTIMIZER_ENABLED,
            TracingStatus.ErrorState.SYNC_ERROR_TIMING,
            TracingStatus.ErrorState.BLE_INTERNAL_ERROR,
            TracingStatus.ErrorState.BLE_ADVERTISING_ERROR,
            TracingStatus.ErrorState.BLE_SCANNER_ERROR);

    private static final List<TracingStatus.ErrorState> possibleNotificationErrorStatesOrderedByPriority = Arrays.asList(
            TracingStatus.ErrorState.SYNC_ERROR_DATABASE,
            TracingStatus.ErrorState.SYNC_ERROR_SERVER,
            TracingStatus.ErrorState.SYNC_ERROR_NETWORK,
            TracingStatus.ErrorState.SYNC_ERROR_SIGNATURE
    );

    @StringRes
    private static int getTitle(TracingStatus.ErrorState tracingErrorState) {
        switch (tracingErrorState) {
            /*case LOCATION_SERVICE_DISABLED:
                return R.string.error_location_title;*/
            case BLE_DISABLED:
                return R.string.error_ble_title;
            case BATTERY_OPTIMIZER_ENABLED:
                return R.string.error_battery_title;
            case SYNC_ERROR_TIMING:
                return R.string.error_time_title;
            case SYNC_ERROR_SERVER:
            case SYNC_ERROR_NETWORK:
            case SYNC_ERROR_SIGNATURE:
            case SYNC_ERROR_DATABASE:
                return R.string.error_general_title;
            case BLE_NOT_SUPPORTED:
            case BLE_INTERNAL_ERROR:
            case BLE_ADVERTISING_ERROR:
            case BLE_SCANNER_ERROR:
            default:
                return R.string.error_BLE_title;
        }
    }

    @StringRes
    private static int getText(TracingStatus.ErrorState tracingErrorState) {
        return tracingErrorState.getErrorString();
    }

    @StringRes
    private static int getButtonText(TracingStatus.ErrorState errorState) {
        switch (errorState) {
            /*case LOCATION_SERVICE_DISABLED:
                return R.string.error_location_services_button;*/
            case BLE_DISABLED:
                return R.string.pls_ble_title;
            case MISSING_LOCATION_PERMISSION:
            case BATTERY_OPTIMIZER_ENABLED:
                return R.string.allow_button;
            case SYNC_ERROR_SERVER:
            case SYNC_ERROR_NETWORK:
            case SYNC_ERROR_DATABASE:
            case SYNC_ERROR_SIGNATURE:
                return R.string.error_retry_button;
            case SYNC_ERROR_TIMING:
            case BLE_NOT_SUPPORTED:
            case BLE_INTERNAL_ERROR:
            case BLE_ADVERTISING_ERROR:
            case BLE_SCANNER_ERROR:
            default:
                return -1;
        }
    }

    public static TracingStatus.ErrorState getErrorState(Collection<TracingStatus.ErrorState> errors) {
        for (TracingStatus.ErrorState errorState : possibleErrorStatesOrderedByPriority) {
            if (errors.contains(errorState)) {
                return errorState;
            }
        }
        return null;
    }

    public static boolean isTracingErrorState(TracingStatus.ErrorState error) {
        return possibleErrorStatesOrderedByPriority.contains(error);
    }

    public static void updateErrorView(View tracingErrorView, TracingStatus.ErrorState errorState) {
        if (errorState == null) {
            tracingErrorView.setVisibility(View.GONE);
            return;
        }

        tracingErrorView.setVisibility(View.VISIBLE);
        ImageView iconView = tracingErrorView.findViewById(R.id.error_status_image);
        TextView titleView = tracingErrorView.findViewById(R.id.error_status_title);
        TextView textView = tracingErrorView.findViewById(R.id.error_status_text);
        TextView buttonView = tracingErrorView.findViewById(R.id.error_status_button);

        titleView.setText(TracingErrorStateHelper.getTitle(errorState));
        titleView.setVisibility(View.VISIBLE);

        if (TracingErrorStateHelper.getText(errorState) != -1) {
            textView.setText(TracingErrorStateHelper.getText(errorState));
            textView.setVisibility(View.VISIBLE);

        } else {
            textView.setVisibility(View.GONE);
        }

        if (TracingErrorStateHelper.getButtonText(errorState) != -1) {
            buttonView.setText(TracingErrorStateHelper.getButtonText(errorState));
            buttonView.setVisibility(View.VISIBLE);
            buttonView.setPaintFlags(buttonView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        } else {
            buttonView.setVisibility(View.GONE);
        }

    }

}
