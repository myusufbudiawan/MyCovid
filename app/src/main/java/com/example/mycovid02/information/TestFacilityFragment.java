package com.example.mycovid02.information;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mycovid02.R;

public class TestFacilityFragment extends Fragment {

    public static TestFacilityFragment newInstance() {
        return new TestFacilityFragment();
    }

    public TestFacilityFragment() {
        super(R.layout.test_facility_fragment);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView info_title = view.findViewById(R.id.info_title);
        TextView info_text = view.findViewById(R.id.info_text);

        TextView info1_title = view.findViewById(R.id.info_title1);
        TextView info1_text = view.findViewById(R.id.info_text1);

        TextView info2_title = view.findViewById(R.id.info_title2);
        TextView info2_text = view.findViewById(R.id.info_text2);

        ImageView illu = view.findViewById(R.id.imageView4);

        TextView link = view.findViewById(R.id.link);

        link.setVisibility(View.GONE);

        info2_title.setVisibility(View.GONE);
        info2_text.setVisibility(View.GONE);

        info_title.setText(R.string.test_info_title);
        info_text.setText(R.string.test_info_desc);
        info_text.setMovementMethod(LinkMovementMethod.getInstance());

        info1_title.setText(R.string.test_info_title2);
        info1_text.setText(R.string.test_info_desc2);
        info1_text.setMovementMethod(LinkMovementMethod.getInstance());


        illu.setVisibility(View.VISIBLE);
        illu.setImageResource(R.drawable.hospital_img);

    }
}
