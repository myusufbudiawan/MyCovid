package com.example.mycovid02.reports;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.AutoTransition;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mycovid02.MyDatabase;
import com.example.mycovid02.R;
import com.example.mycovid02.tracing.TracingViewModel;
import com.example.mycovid02.utility.DateUtility;
import com.example.mycovid02.utility.NotificationUtility;

import org.dpppt.android.sdk.internal.database.models.ExposureDay;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class ReportsFragment extends Fragment{
    public static ReportsFragment newInstance() {
        return new ReportsFragment();
    }

    private TracingViewModel tracingViewModel;
    private MyDatabase myDatabase;

    private ViewPager2 mainViewPager;
    private ReportSlidePageAdapter pagerAdapter;

    private View healthyView;
    private View callUsView;
    private View infectedView;
    private View scrollViewFirstchild;

    private LockableScrollView scrollView;

    private int originalFirstChildPadding = 0;

    private TextView callUsText;
    private boolean justCalled = false;


    public ReportsFragment(){
        super(R.layout.fragment_reports);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tracingViewModel = new ViewModelProvider(requireActivity()).get(TracingViewModel.class);
        myDatabase = MyDatabase.getInstance(getContext());

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        Toolbar toolbar = view.findViewById(R.id.report_toolbar);
//        toolbar.setNavigationOnClickListener(v -> getParentFragmentManager().popBackStack());

        mainViewPager = view.findViewById(R.id.reports_header_viewpager);
        scrollViewFirstchild = view.findViewById(R.id.reports_scrollview_firstChild);

        scrollView = view.findViewById(R.id.reports_scrollview);

        healthyView = view.findViewById(R.id.reports_healthy);
        callUsView = view.findViewById(R.id.reports_call);
        infectedView = view.findViewById(R.id.reports_infected);

        Button callBtn = callUsView.findViewById(R.id.report_call_button);

        callBtn.setOnClickListener(view1 -> callInfoline());

        View link_1 = healthyView.findViewById(R.id.card_encounters_link);
        View link_2 = infectedView.findViewById(R.id.card_encounters_link);

        link_1.setOnClickListener(v -> openLink(R.string.government_recommend_link));
        link_2.setOnClickListener(v -> openLink(R.string.how_self_quarantine));

        pagerAdapter = new ReportSlidePageAdapter();
        mainViewPager.setAdapter(pagerAdapter);

        tracingViewModel.getAppStatusLiveData().observe(getViewLifecycleOwner(), tracingStatusInterface -> {

            healthyView.setVisibility(View.GONE);
            callUsView.setVisibility(View.GONE);
            infectedView.setVisibility(View.GONE);

            List<Pair<ReportsPagerFragment.Type, Long>> items = new ArrayList<>();
            if (tracingStatusInterface.isReportedAsInfected()) {
                infectedView.setVisibility(View.VISIBLE);
                items.add(new Pair<>(ReportsPagerFragment.Type.POSITIVE_TESTED, myDatabase.getInfectedDate()));
            } else if (tracingStatusInterface.wasContactReportedAsExposed()) {
                List<ExposureDay> exposureDays = tracingStatusInterface.getExposureDays();
                boolean isHotlineCallPending = myDatabase.isCallPending();
                if (isHotlineCallPending) {
                    callUsView.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < exposureDays.size(); i++) {
                    ExposureDay exposureDay = exposureDays.get(i);
                    long exposureTimestamp = exposureDay.getExposedDate().getStartOfDay(TimeZone.getDefault());
                    if (i == 0) {
                        items.add(new Pair<>(ReportsPagerFragment.Type.POSSIBLE_INFECTION, exposureTimestamp));
                        int daysDiff = DateUtility.getDaysDiffUntil(exposureTimestamp, 10);
                        if (daysDiff == 1) {
//                            daysLeftText1.setText(R.string.date_in_one_day);
//                            daysLeftText2.setText(R.string.date_in_one_day);
//                            daysLeftText1.setVisibility(View.VISIBLE);
//                            daysLeftText2.setVisibility(View.VISIBLE);
                            System.out.println("days diff is 1");
                        } else if (daysDiff > 1) {
//                            String dateStr = getString(R.string.date_in_days).replace("{COUNT}", String.valueOf(daysDiff));
//                            daysLeftText1.setText(dateStr);
//                            daysLeftText2.setText(dateStr);
//                            daysLeftText1.setVisibility(View.VISIBLE);
//                            daysLeftText2.setVisibility(View.VISIBLE);
                            System.out.println("days diff is more than 1");

                        }
                    } else {
                        items.add(new Pair<>(ReportsPagerFragment.Type.NEW_CONTACT, exposureTimestamp));
                    }
                }
            } else {
                healthyView.setVisibility(View.VISIBLE);
                items.add(new Pair<>(ReportsPagerFragment.Type.NO_REPORTS, null));
            }

			//Debug items
			items.clear();
//			items.add(new Pair<>(ReportsPagerFragment.Type.NO_REPORTS, null));
//			items.add(new Pair<>(ReportsPagerFragment.Type.POSSIBLE_INFECTION, 1585835019000L));
//			items.add(new Pair<>(ReportsPagerFragment.Type.NEW_CONTACT, 1585835019000L));
			items.add(new Pair<>(ReportsPagerFragment.Type.POSITIVE_TESTED, 1585835019000L));

            pagerAdapter.updateItem(items);
        });

        NotificationManager notificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NotificationUtility.NOTIFICATION_ID_CONTACT);

    }

    private void callInfoline() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + getString(R.string.infoline_number)));
            startActivity(intent);
    }

    private void openLink(@StringRes int stringRes) {
        Intent browserInt = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(stringRes)));
        startActivity(browserInt);
    }

    public void doHeaderAnimation(View info, View image, Button button) {
        myDatabase.setReportsHeaderAnimationPending(false);

        ViewGroup rootView = (ViewGroup) getView();

        scrollViewFirstchild.setPadding(scrollViewFirstchild.getPaddingLeft(),
                rootView.getHeight(),
                scrollViewFirstchild.getPaddingRight(), scrollViewFirstchild.getPaddingBottom());
        scrollViewFirstchild.setVisibility(View.VISIBLE);

        rootView.post(() -> {

            AutoTransition autoTransition = new AutoTransition();
            autoTransition.setDuration(300);
            autoTransition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(@NonNull Transition transition) {

                }

                @Override
                public void onTransitionEnd(@NonNull Transition transition) {
                    mainViewPager.post(() -> {
                        setupScrollBehavior();
                    });
                }

                @Override
                public void onTransitionCancel(@NonNull Transition transition) {

                }

                @Override
                public void onTransitionPause(@NonNull Transition transition) {

                }

                @Override
                public void onTransitionResume(@NonNull Transition transition) {

                }
            });

            TransitionManager.beginDelayedTransition(rootView, autoTransition);

            updateHeaderSize(false);

            scrollViewFirstchild.setPadding(scrollViewFirstchild.getPaddingLeft(),
                    originalFirstChildPadding,
                    scrollViewFirstchild.getPaddingRight(), scrollViewFirstchild.getPaddingBottom());

            info.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
            button.setVisibility(View.GONE);

//            circlePageIndicator.setVisibility(View.VISIBLE);
            mainViewPager.setUserInputEnabled(true);
        });
    }

    //adapter class for viewpager
    public class ReportSlidePageAdapter extends FragmentStateAdapter {

        List<Pair<ReportsPagerFragment.Type, Long>> items = new ArrayList<>();

        boolean isReportHeaderAnimationPending = false;

        public ReportSlidePageAdapter() {
            super(ReportsFragment.this);
        }

        void updateItem(List<Pair<ReportsPagerFragment.Type, Long>> items) {

            isReportHeaderAnimationPending = myDatabase.isReportsHeaderAnimationPending();

            this.items.clear();
            this.items.addAll(items);
            notifyDataSetChanged();

            if (items.size() > 1) {
                ViewGroup.LayoutParams lp = mainViewPager.getLayoutParams();
                lp.height = getResources().getDimensionPixelSize(R.dimen.header_height_reports_with_indicator);
                mainViewPager.setLayoutParams(lp);
                scrollViewFirstchild.setPadding(scrollViewFirstchild.getPaddingLeft(),
                        getResources().getDimensionPixelSize(R.dimen.top_item_padding_reports_width_indicator),
                        scrollViewFirstchild.getPaddingRight(), scrollViewFirstchild.getPaddingBottom());
            } else {
                ViewGroup.LayoutParams lp = mainViewPager.getLayoutParams();
                lp.height = getResources().getDimensionPixelSize(R.dimen.header_height_reports);
                mainViewPager.setLayoutParams(lp);
                scrollViewFirstchild.setPadding(scrollViewFirstchild.getPaddingLeft(),
                        getResources().getDimensionPixelSize(R.dimen.top_item_padding_reports),
                        scrollViewFirstchild.getPaddingRight(), scrollViewFirstchild.getPaddingBottom());
            }

            updateHeaderSize(isReportHeaderAnimationPending);

            if (isReportHeaderAnimationPending) {
                mainViewPager.setUserInputEnabled(false);

                int originalFirstChildPadding = scrollViewFirstchild.getPaddingTop();

                scrollViewFirstchild.setVisibility(View.GONE);
            }

            mainViewPager.post(() -> {
                mainViewPager.setCurrentItem(items.size() - 1, false);

                setupScrollBehavior();
            });

        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Pair<ReportsPagerFragment.Type, Long> item = items.get(position);
            ReportsPagerFragment.Type type = item.first;
            long timestamp = item.second == null ? 0 : item.second;

            boolean showAnimationControls = isReportHeaderAnimationPending && position == items.size() - 1;

            switch (type) {
                case NO_REPORTS:
                    return ReportsPagerFragment.newInstance(ReportsPagerFragment.Type.NO_REPORTS, 0, false);
                case POSSIBLE_INFECTION:
                    return ReportsPagerFragment
                            .newInstance(ReportsPagerFragment.Type.POSSIBLE_INFECTION, timestamp, showAnimationControls);
                case NEW_CONTACT:
                    return ReportsPagerFragment
                            .newInstance(ReportsPagerFragment.Type.NEW_CONTACT, timestamp, showAnimationControls);
                case POSITIVE_TESTED:
                    return ReportsPagerFragment.newInstance(ReportsPagerFragment.Type.POSITIVE_TESTED, timestamp, false);
            }

            throw new IllegalArgumentException();        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private void setupScrollBehavior() {
        if (!isVisible()) return;

        Rect rect = new Rect();
        mainViewPager.getDrawingRect(rect);
        scrollView.setScrollPreventRect(rect);

        View headerParent = (View) mainViewPager.getParent();

        int scrollRangePx = scrollViewFirstchild.getPaddingTop();
        int translationRangePx = -getResources().getDimensionPixelSize(R.dimen.spacing_huge);
        scrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            float progress = computeScrollAnimProgress(scrollY, scrollRangePx);
            headerParent.setAlpha(1 - progress);
            headerParent.setTranslationY(progress * translationRangePx);
        });
        scrollView.post(() -> {
            float progress = computeScrollAnimProgress(scrollView.getScrollY(), scrollRangePx);
            headerParent.setAlpha(1 - progress);
            headerParent.setTranslationY(progress * translationRangePx);
        });
    }

    private float computeScrollAnimProgress(int scrollY, int scrollRangePx) {
        return Math.min(scrollY, scrollRangePx) / (float) scrollRangePx;

    }

    private void updateHeaderSize(boolean isReportHeaderAnimationPending) {
        ViewGroup.LayoutParams mainViewPagerLp = mainViewPager.getLayoutParams();
        FrameLayout mainViewPagerLayout = (FrameLayout) mainViewPager.getParent();
        ViewGroup.LayoutParams mainViewPagerLayoutLp = mainViewPagerLayout.getLayoutParams();

        if (isReportHeaderAnimationPending) {
            mainViewPagerLp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            mainViewPagerLp.height = getResources().getDimensionPixelSize(R.dimen.header_height_reports);
            mainViewPagerLayoutLp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        mainViewPager.setLayoutParams(mainViewPagerLp);
        mainViewPagerLayout.setLayoutParams(mainViewPagerLayoutLp);
    }

}
