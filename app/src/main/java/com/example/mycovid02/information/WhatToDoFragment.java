package com.example.mycovid02.information;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mycovid02.R;

public class WhatToDoFragment extends Fragment {

    public static WhatToDoFragment newInstance() {
        return new WhatToDoFragment();
    }

    public WhatToDoFragment() {
        super(R.layout.what_to_do_fragment);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //What to do
        TextView info_title = view.findViewById(R.id.info_title);
        TextView info_text = view.findViewById(R.id.info_text);

        TextView info1_title = view.findViewById(R.id.info_title1);
        TextView info1_text = view.findViewById(R.id.info_text1);

        TextView info2_title = view.findViewById(R.id.info_title2);
        TextView info2_text = view.findViewById(R.id.info_text2);

        TextView hyperlink = view.findViewById(R.id.link);

        ImageView icon1 = view.findViewById(R.id.imageView);
        ImageView icon2 = view.findViewById(R.id.imageView2);
        ImageView icon3 = view.findViewById(R.id.imageView3);


        //setup info page

        hyperlink.setVisibility(View.GONE);

        info_title.setText(R.string.what_to_do_info_title);
        info_text.setText(R.string.what_to_do_info_text);

        info1_title.setText(R.string.what_to_do_info1_title);
        info1_text.setText(R.string.what_to_do_info1_text);

        info2_title.setText(R.string.what_to_do_info2_title);
        info2_text.setText(R.string.what_to_do_info2_text);

        icon1.setVisibility(View.VISIBLE);
        icon2.setVisibility(View.VISIBLE);
        icon3.setVisibility(View.VISIBLE);


    }

}
