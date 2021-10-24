package com.example.mycovid02.utility;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.PowerManager;

public class FeatureCheck {

    boolean isBluetoothEnabled(){
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

}
