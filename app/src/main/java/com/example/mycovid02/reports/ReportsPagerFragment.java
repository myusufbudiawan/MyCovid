package com.example.mycovid02.reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycovid02.R;
import com.example.mycovid02.utility.DateUtility;

public class ReportsPagerFragment extends Fragment {
    private static final String ARG_TYPE = "ARG_TYPE";
    private static final String ARG_TIMESTAMP = "ARG_TIMESTAMP";
    private static final String ARG_SHOWANIMCONTROLS = "ARG_SHOWANIMATIONCONTROLS";

    public static ReportsPagerFragment newInstance(@NonNull Type type, long timestamp, boolean showAnimControl) {
        ReportsPagerFragment reportsPagerFragment = new ReportsPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type.ordinal());
        args.putLong(ARG_TIMESTAMP, timestamp);
        args.putBoolean(ARG_SHOWANIMCONTROLS, showAnimControl);
        reportsPagerFragment.setArguments(args);
        return reportsPagerFragment;
    }

    public enum Type {
        NO_REPORTS,
        POSSIBLE_INFECTION,
        NEW_CONTACT,
        POSITIVE_TESTED
    }

    private Type type;
    private long timestamp;
    private boolean showAnimControl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = Type.values()[getArguments().getInt(ARG_TYPE)];
        timestamp = getArguments().getLong(ARG_TIMESTAMP);
        showAnimControl = getArguments().getBoolean(ARG_SHOWANIMCONTROLS);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;

        switch (type) {
            case NO_REPORTS:
                view = inflater.inflate(R.layout.pager_no_reports, container, false);
                break;
            case POSSIBLE_INFECTION:
                view = inflater.inflate(R.layout.pager_possible_infection, container, false);

                break;
            case NEW_CONTACT:
                view = inflater.inflate(R.layout.pager_possible_infection, container, false);
                TextView title = view.findViewById(R.id.report_pager_title);
                TextView subTitle = view.findViewById(R.id.report_pager_subtitle);
                title.setText(R.string.new_contact_detail_title);
                subTitle.setText(R.string.new_contact_detail_subtitle);
                break;
            case POSITIVE_TESTED:
                view = inflater.inflate(R.layout.pager_positive_tested, container, false);
                break;
        }
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (timestamp != 0) {
            TextView date = view.findViewById(R.id.report_pager_date);
            String dateString = DateUtility.getFormattedDate(timestamp) + " / ";
            int daysDiff = DateUtility.getDaysDiff(timestamp);

            if (daysDiff == 0) {
                dateString += "Today";
            } else if (daysDiff == 1) {
                dateString += "1 day ago";
            } else {
                dateString += "{COUNT} days ago". replace("{COUNT}", String.valueOf(daysDiff));
            }

            date.setText(dateString);
        }

        if (type == Type.POSSIBLE_INFECTION || type == Type.NEW_CONTACT) {
            if (showAnimControl) {
                View info = view.findViewById(R.id.report_pager_info);
                View image = view.findViewById(R.id.report_pager_image);
                Button button = view.findViewById(R.id.report_pager_button);

                info.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);

                button.setOnClickListener(view1 -> {
                    ((ReportsFragment) getParentFragment()).doHeaderAnimation(info,image, button);
                });
            }
        }
    }
}
