package com.example.mycovid02.statistics;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mycovid02.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardTotalCases extends Fragment {


    public CardTotalCases() {
        super(R.layout.card_totalcases);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView date_active_view = view.findViewById(R.id.total_case_date);
        TextView case_active_view = view.findViewById(R.id.number_totalcase);

        System.out.println("salam dari malang");

       /* date_active_view.setText(date);
        case_active_view.setText(active_case);*/
    }

}
