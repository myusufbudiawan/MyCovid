package com.example.mycovid02.utility;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

public class FeatureCheck {

    public static boolean isBluetoothEnabled(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled() && mBluetoothAdapter != null) {
            return true;
        } else
            return false;
    }

    public static boolean isBatteryOptDisabled(Context context){
        String packageName = context.getPackageName();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (pm.isIgnoringBatteryOptimizations(packageName))
            return true;
        else
            return false;
    }

    public static boolean isLocationAllowed(Context requireContext) {
        return ContextCompat.checkSelfPermission(requireContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static void openSettings(FragmentActivity requireActivity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", requireActivity.getPackageName(), null);
        intent.setData(uri);
        requireActivity.startActivity(intent);
    }
}
