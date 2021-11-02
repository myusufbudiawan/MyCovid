package com.example.mycovid02.statistics;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mycovid02.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class StatisticsFragment extends Fragment {

    public static String[] new_case;
    public static String[] active_case;
    public static String[] daily_deaths;
    public static String[] date;
    public static int total_line = 0;
    public static int total_line2 = 0;

    public static StatisticsFragment newInstance() {
        Bundle args = new Bundle();
        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics, container, false);

    }

    public static void getData() {

        Thread thread = new Thread(() -> {

            String case_csv = "https://raw.githubusercontent.com/MoH-Malaysia/covid19-public/main/epidemic/cases_malaysia.csv";
            String deaths_csv = "https://raw.githubusercontent.com/MoH-Malaysia/covid19-public/62d4869d82a963980172968e5ce1a99ed84fea26/epidemic/deaths_malaysia.csv";

            List<List<String>> records = new ArrayList<>();

            try {
                URL cases_url = new URL(case_csv);
                BufferedReader in = new BufferedReader(new InputStreamReader(cases_url.openStream()));
                String s = null;
                while ((s = in.readLine()) != null) {

                    String[] values = s.split(",");
                    records.add(Arrays.asList(values));
                }

                total_line = records.size();

                new_case = new String[total_line];
                date = new String[total_line];
                active_case = new String[total_line];

                int lineNo = 1;

                for (List<String> line : records) {
                    int columnNo = 1;

                        for (String value : line) {

                            if (columnNo == 1) {
                                date[lineNo-1] = value;
                            }

                            if (columnNo == 2) {
                                new_case[lineNo-1] = value;
                            }

                            if (columnNo == 5) {
                                active_case[lineNo-1] = value;
                            }

                            columnNo++;
                        }
                    lineNo++;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                URL cases_url = new URL(deaths_csv);
                BufferedReader in = new BufferedReader(new InputStreamReader(cases_url.openStream()));
                String s = null;
                while ((s = in.readLine()) != null) {

                    String[] values = s.split(",");
                    records.add(Arrays.asList(values));
                }

                total_line2 = records.size();

                daily_deaths = new String[total_line2];

                int lineNo = 1;

                for (List<String> line : records) {
                    int columnNo = 1;

                    for (String value : line) {

                        if (columnNo == 2) {
                            daily_deaths[lineNo-1] = value;
                        }

                        columnNo++;
                    }
                    lineNo++;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }


    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int sum_active_case = 0;
        int avg_active_case;
        String avg_active_case_text;

        View cardActiveCases = view.findViewById(R.id.card_total_active);
        View cardDailyCases = view.findViewById(R.id.card_daily_case);
        View cardGraph = view.findViewById(R.id.card_graph);

        View dailyCases = view.findViewById(R.id.new_cases);
        View dailyDeath = view.findViewById(R.id.death);

        View avgCases = view.findViewById(R.id.avg_cases);
        View vsYesterday = view.findViewById(R.id.vs_yesterday);

        TextView totalCaseTV = cardActiveCases.findViewById(R.id.number_totalcase);
        TextView dateActiveTV = cardActiveCases.findViewById(R.id.total_case_date);

        TextView dateTodayTV = cardDailyCases.findViewById(R.id.date_today_text);

        TextView titleDaily1 = dailyCases.findViewById(R.id.title1);
        TextView titleDaily2 = dailyDeath.findViewById(R.id.title1);
        TextView totalDaily1 = dailyCases.findViewById(R.id.total_daily);
        TextView totalDaily2 = dailyDeath.findViewById(R.id.total_daily);

        TextView avgCasesTV = avgCases.findViewById(R.id.title1);
        TextView relativeYesterdayTV = vsYesterday.findViewById(R.id.title1);

        TextView totalAvgCases = avgCases.findViewById(R.id.total_daily);
        TextView percentRelativeYesterday = vsYesterday.findViewById(R.id.total_daily);

        for (int i = new_case.length-1 ; i > new_case.length - 8; i--) {

            sum_active_case += Integer.parseInt(new_case[i]);

        }

        float startVal = Float.parseFloat(new_case[total_line-2]);
        float finalVal = Float.parseFloat(new_case[total_line-1]);
        String percentYesterdayStr;

        float percentYesterday = ((finalVal - startVal) / startVal) * 100;

        percentYesterdayStr = String.format("%.2f", percentYesterday);

        avg_active_case = sum_active_case/7;
        avg_active_case_text = Integer.toString(avg_active_case);

        totalCaseTV.setText(active_case[total_line-1]);
        dateActiveTV.setText(date[total_line-1]);
        dateTodayTV.setText(date[total_line-1]);

        titleDaily1.setText("New cases");
        titleDaily2.setText("Deaths");
        totalDaily1.setText(new_case[total_line-1]);
        totalDaily2.setText(daily_deaths[total_line2-1]);

        avgCasesTV.setText("7-day average");
        relativeYesterdayTV.setText("vs yesterday");
        totalAvgCases.setText(avg_active_case_text);
        percentRelativeYesterday.setText(percentYesterdayStr);


    }

}