package com.example.mycovid02.main;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycovid02.R;
import com.example.mycovid02.tracing.TracingState;
import com.example.mycovid02.tracing.TracingViewModel;
import com.example.mycovid02.utility.FeatureCheck;

import org.dpppt.android.sdk.TracingStatus;

public class TracingBoxFragment extends Fragment {

    private static final int REQ_CODE_BLE_INTENT = 330;
    private static final int REQ_CODE_BATTERY_INTENT = 420;
    private static String ARG_TRACING = "isHomeFragment";

    private TracingViewModel tracingViewModel;

    private View tracingStatusView, tracingErrorView;

    private boolean isHomeFragment;

    public TracingBoxFragment() {
        super(R.layout.fragment_tracing_box);
    }

    public static TracingBoxFragment newInstance(boolean isTracingFragment) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_TRACING, isTracingFragment);
        TracingBoxFragment fragment = new TracingBoxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tracingViewModel = new ViewModelProvider(requireActivity()).get(TracingViewModel.class);
        isHomeFragment = getArguments().getBoolean(ARG_TRACING);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tracingErrorView = view.findViewById(R.id.tracing_error);
        tracingStatusView = view.findViewById(R.id.tracing_status);

        showStatus();
    }

    private void showStatus() {

        tracingViewModel.getAppStatusLiveData().observe(getViewLifecycleOwner(),
                tracingStatusInterface -> {
            boolean isTracing = tracingStatusInterface.getTracingState().equals(TracingState.ACTIVE);

            TracingStatus.ErrorState errorState = tracingStatusInterface.getTracingErrorState();
            if (isTracing && errorState != null) {
                handleErrorState(errorState);

            } else if (tracingStatusInterface.isReportedAsInfected()) {
                tracingStatusView.setVisibility(View.VISIBLE);
                tracingErrorView.setVisibility(View.GONE);

            } else if (!isTracing) {
                tracingStatusView.setVisibility(View.GONE);
                tracingErrorView.setVisibility(View.VISIBLE);

            } else {
                tracingStatusView.setVisibility(View.VISIBLE);
                tracingErrorView.setVisibility(View.GONE);
            }
        });

    }

    private void handleErrorState(TracingStatus.ErrorState errorState) {
        tracingStatusView.setVisibility(View.GONE);
        tracingErrorView.setVisibility(View.VISIBLE);
        tracingErrorView.findViewById(R.id.error_status_button).setOnClickListener(v -> {
            switch (errorState) {
                case BLE_DISABLED:
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent bleIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(bleIntent, REQ_CODE_BLE_INTENT);
                    }
                    break;
                case BATTERY_OPTIMIZER_ENABLED:
                    String packageName = requireActivity().getPackageName();
                    Intent batteryIntent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    batteryIntent.setData(Uri.parse("package:" + packageName));
                    startActivityForResult(batteryIntent, REQ_CODE_BATTERY_INTENT);
                    break;
            }
        });
    }


}
