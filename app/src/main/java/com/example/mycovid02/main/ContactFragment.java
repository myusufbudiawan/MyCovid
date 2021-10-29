package com.example.mycovid02.main;

import android.view.View;
import android.widget.ScrollView;
import android.widget.Switch;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.mycovid02.R;
import com.example.mycovid02.tracing.TracingViewModel;

public class ContactFragment extends Fragment {

    private static final int REQ_CODE_BLE_INTENT = 330;

    private TracingViewModel tracingViewModel;
    private ScrollView scrollView;
    private View tracingStatusView;
    private View tracingErrorView;
    private SwitchCompat OnOffTrace;

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    public ContactFragment() {
        super(R.layout.contact_fragment);
    }

}
