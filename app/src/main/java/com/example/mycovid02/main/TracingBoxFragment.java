package com.example.mycovid02.main;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycovid02.R;
import com.example.mycovid02.onboarding.LocationPermissionFragment;
import com.example.mycovid02.tracing.TracingState;
import com.example.mycovid02.tracing.TracingViewModel;
import com.example.mycovid02.utility.FeatureCheck;

import org.dpppt.android.sdk.TracingStatus;

public class TracingBoxFragment extends Fragment {

    private static final int REQ_CODE_BLE_INTENT = 330;
    private static final int REQ_CODE_BATTERY_INTENT = 420;
    private static final int REQUEST_CODE_LOCATION_INTENT = 510;
    private static String ARG_TRACING = "isHomeFragment";

    ActivityResultLauncher<Intent> activityResultLauncher;

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

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            tracingViewModel.invalidateService();
                        }
                    }
                }
        );
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

    private void handleErrorState(TracingStatus.ErrorState errorHandling) {
        tracingStatusView.setVisibility(View.GONE);
        tracingErrorView.setVisibility(View.VISIBLE);
        tracingErrorView.findViewById(R.id.error_status_button).setOnClickListener(v -> {
            switch (errorHandling) {
                case BLE_DISABLED:
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent bleIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        activityResultLauncher.launch(bleIntent);
                    }
                    break;
                case MISSING_LOCATION_PERMISSION:
                    if (ActivityCompat
                            .shouldShowRequestPermissionRationale(requireActivity(),
                                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                        String[] permissions = new String[] { Manifest.permission.ACCESS_FINE_LOCATION };
                        requestPermissions(permissions, LocationPermissionFragment.REQUEST_CODE_ASK_PERMISSION_FINE_LOCATION);
                    } else {
                        new AlertDialog.Builder(requireActivity())
                                .setTitle(R.string.button_permission_location)
                                .setMessage(R.string.android_foreground_service_notification_error_location_permission)
                                .setPositiveButton(getString(R.string.button_ok),
                                        (dialogInterface, i) -> {
                                            FeatureCheck.openSettings(requireActivity());
                                            dialogInterface.dismiss();
                                        })
                                .create()
                                .show();
                    }
                    break;

                case LOCATION_SERVICE_DISABLED:
                    Intent locationIntent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(locationIntent, REQUEST_CODE_LOCATION_INTENT);
                    break;
                case BATTERY_OPTIMIZER_ENABLED:
                    String packageName = requireActivity().getPackageName();
                    Intent batteryIntent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    batteryIntent.setData(Uri.parse("package:" + packageName));
                    activityResultLauncher.launch(batteryIntent);
                    break;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LocationPermissionFragment.REQUEST_CODE_ASK_PERMISSION_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tracingViewModel.invalidateService();
            }
        }
    }


}
