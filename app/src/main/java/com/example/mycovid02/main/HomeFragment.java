package com.example.mycovid02.main;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycovid02.MyDatabase;
import com.example.mycovid02.R;
import com.example.mycovid02.reports.ReportsFragment;
import com.example.mycovid02.tracing.TracingViewModel;

public class HomeFragment extends Fragment {

    private TracingViewModel tracingViewModel;

    private View infobox;
    private View tracingCard;
    private View reportStatusBubble;
    private View reportStatusView;
    private View reportErrorView;
    private View cardReport;

    public HomeFragment() {
        super(R.layout.home_fragment);
    }

    public static HomeFragment newInstance() {

        String ass = "ass";
        Log.i( ass,"Saya di Home Fragment");

        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyDatabase myDatabase = MyDatabase.getInstance(getContext());
        tracingViewModel = new ViewModelProvider(requireActivity()).get(TracingViewModel.class);

        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.status_container, TracingBoxFragment.newInstance(true))
                .commit();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        reportStatusBubble = view.findViewById(R.id.report_status_bubble);
        reportStatusView = reportStatusBubble.findViewById(R.id.report_status);
        reportErrorView = reportStatusBubble.findViewById(R.id.report_errors);
        cardReport = view.findViewById(R.id.card_report);
        ScrollView scrollView = view.findViewById(R.id.home_scrollview);
        tracingCard = view.findViewById(R.id.card_tracing);

        setupTracingView();
        setupReports();
        setupPositiveTested();

    }

    private void setupPositiveTested() {
    }

    @Override
    public void onStart() {
        super.onStart();
        tracingViewModel.invalidateTracingStatus();
    }

    private void setupTracingView() {

        TypedValue value = new TypedValue();
        getContext().getTheme().resolveAttribute(
                android.R.attr.selectableItemBackground, value, true
        );
        //tracingCard.setForeground(getContext().getDrawable(value.resourceId));

        tracingViewModel.getAppStatusLiveData().observe(getViewLifecycleOwner(), tracingStatusInterface ->{
            if (tracingStatusInterface.isReportedAsInfected()) {
                tracingCard.setOnClickListener(null);
                tracingCard.setForeground(null);
            }else {
                tracingCard.setOnClickListener(v -> showContactFragment());
            }
        } );
    }

    private void showContactFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, ContactFragment.newInstance())
                .addToBackStack(ContactFragment.class.getCanonicalName())
                .commit();
    }

    private void setupReports() {
        cardReport.setOnClickListener(v -> getParentFragmentManager().beginTransaction()
        .replace(R.id.main_fragment_container, ReportsFragment.newInstance())
        .addToBackStack(ReportsFragment.class.getCanonicalName())
        .commit());
    }
}
