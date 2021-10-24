package com.example.mycovid02;

import android.app.Application;
import android.os.Build;

import org.dpppt.android.sdk.DP3T;
import org.dpppt.android.sdk.internal.util.ProcessUtil;
import org.dpppt.android.sdk.util.SignatureUtil;

import java.security.PublicKey;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (ProcessUtil.isMainProcess(this)) {

            PublicKey publicKey = SignatureUtil.getPublicKeyFromBase64OrThrow(BuildConfig.BUCKET_PUBLIC_KEY);

            DP3T.init(this, "com.example.mycovid02", true, publicKey);
        }
    }
}
