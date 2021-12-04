package com.example.mycovid02.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mycovid02.R;
import com.example.mycovid02.mycovidcode.InsertCodeActivity;

public class InformationFragment extends Fragment {

//    public InformationFragment() {
//        super(R.layout.information_fragment);
//    }

    public static InformationFragment newInstance() {
        Bundle args = new Bundle();
        InformationFragment fragment = new InformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.information_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View cardWhatToDo = view.findViewById(R.id.card_what_to_do);
        View cardCommonSymptoms = view.findViewById(R.id.card_common_symptoms);
        View cardTestFacilities = view.findViewById(R.id.card_test_facilities);

        //What to do
        TextView info1_title = cardWhatToDo.findViewById(R.id.info_title);
        TextView info1_text = cardWhatToDo.findViewById(R.id.info_text);
        Button enter_code_btn = cardWhatToDo.findViewById(R.id.enter_code_btn);
        ImageView info1Image = cardWhatToDo.findViewById(R.id.info_image);

        View whatToDoCard = cardWhatToDo.findViewById(R.id.card_information);

        //Common symptoms
        TextView info2_title = cardCommonSymptoms.findViewById(R.id.info_title);
        TextView info2_text = cardCommonSymptoms.findViewById(R.id.info_text);
        ImageView info2Image = cardCommonSymptoms.findViewById(R.id.info_image);

        View commonSymptomsCard = cardCommonSymptoms.findViewById(R.id.card_information);

        //Test facilities
        TextView info3_title = cardTestFacilities.findViewById(R.id.info_title);
        TextView info3_text = cardTestFacilities.findViewById(R.id.info_text);
        ImageView info3Image = cardTestFacilities.findViewById(R.id.info_image);

        View testCard = cardTestFacilities.findViewById(R.id.card_information);

        //setup info page
        info1_title.setText(R.string.what_to_do_title);
        info1_text.setText(R.string.what_to_do_text);
        info1Image.setImageResource(R.drawable.smartphone_img);
        enter_code_btn.setVisibility(View.VISIBLE);

        whatToDoCard.setOnClickListener(v -> showToDoFragment());

        enter_code_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), InsertCodeActivity.class);
            startActivity(intent);
        });

        info2_title.setText(R.string.what_to_do_title1);
        info2_text.setText(R.string.what_to_do_text1);
        info2Image.setImageResource(R.drawable.sick_img);
        commonSymptomsCard.setOnClickListener(v -> showSymptomsFragment());

        info3_title.setText(R.string.what_to_do_title2);
        info3_text.setText(R.string.what_to_do_text2);
        info3Image.setImageResource(R.drawable.hospital_img);
        testCard.setOnClickListener(v -> showTestFragment());

    }

    private void showToDoFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, WhatToDoFragment.newInstance())
                .addToBackStack(WhatToDoFragment.class.getCanonicalName())
                .commit();
    }

    private void showSymptomsFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, SymptomsFragment.newInstance())
                .addToBackStack(SymptomsFragment.class.getCanonicalName())
                .commit();
    }

    private void showTestFragment() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, TestFacilityFragment.newInstance())
                .addToBackStack(TestFacilityFragment.class.getCanonicalName())
                .commit();
    }

}
