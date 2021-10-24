package com.example.mycovid02.onboarding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.example.mycovid02.R;

public class ContentFragment extends Fragment {

    private static final String HEADING = "HEADING";
    private static final String TITLE = "TITLE";
    private static final String DESCRIPTION_1 = "DESCRIPTION_1";
    private static final String DESCRIPTION_2 = "DESCRIPTION_2";
    private static final String DESC_ICON_1 = "DESC_ICON1";
    private static final String DESC_ICON_2 = "DESC_ICON2";
    private static final String ILLUSTRATION = "ILLUSTRATION";
    private static final String STYLE_GREEN = "ARG_STYLE_GREEN";

    public ContentFragment() {
        super(R.layout.onboarding_content_fragment);
    }

    public static ContentFragment newInstance(@StringRes int heading,
                                              @StringRes int title,
                                              @StringRes int info1,
                                              @StringRes int info2,
                                              boolean style) {

        Bundle args = new Bundle();

        args.putInt(HEADING, heading);
        args.putInt(TITLE, title);
        args.putInt(DESCRIPTION_1, info1);
        args.putInt(DESCRIPTION_2, info2);
        args.putBoolean(STYLE_GREEN, style);

        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Bundle args = requireArguments();

        TextView heading = view.findViewById(R.id.onboarding_heading);
        TextView title = view.findViewById(R.id.onboarding_title);
        TextView desc1 = view.findViewById(R.id.onboarding_desc1);
        TextView desc2 = view.findViewById(R.id.onboarding_desc2);
        boolean style = args.getBoolean(STYLE_GREEN);
        Button continueBtn = view.findViewById(R.id.onboarding_continue_btn);

        heading.setText(args.getInt(HEADING));

        String ass ="heading";

        title.setText(args.getInt(TITLE));
        if (style)
            title.setTextColor(getResources().getColor(R.color.blue_main, null));

        desc1.setText(args.getInt(DESCRIPTION_1));
        desc2.setText(args.getInt(DESCRIPTION_2));
        continueBtn.setOnClickListener(v -> {
            ((OnboardingActivity) getActivity()).toNextPage(); //fahamkan
        });




    }


}
