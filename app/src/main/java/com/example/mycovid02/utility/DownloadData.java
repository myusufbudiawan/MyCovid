package com.example.mycovid02.utility;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DownloadData extends AsyncTask<URL, Integer, String[]> {

    @Override
    protected String[] doInBackground(URL... urls) {
        return new String[0];
    }


//    Thread thread = new Thread(() -> {
//
//        String case_csv = "https://raw.githubusercontent.com/MoH-Malaysia/covid19-public/main/epidemic/cases_malaysia.csv";
//        String deaths_csv = "https://raw.githubusercontent.com/MoH-Malaysia/covid19-public/62d4869d82a963980172968e5ce1a99ed84fea26/epidemic/deaths_malaysia.csv";
//
//        List<List<String>> records = new ArrayList<>();
//
//        String ass = "assdog";
//
//        try {
//            URL cases_url = new URL(case_csv);
//            BufferedReader in = new BufferedReader(new InputStreamReader(cases_url.openStream()));
//            String s = null;
//            while ((s = in.readLine()) != null) {
//
//                String[] values = s.split(",");
//                records.add(Arrays.asList(values));
//            }
//
//            total_line = records.size();
//
//            new_case = new String[total_line];
//
//            Log.i( ass, String.valueOf(total_line));
//
//            date = new String[total_line];
//            active_case = new String[total_line];
//
//            int lineNo = 1;
//
//            for (List<String> line : records) {
//                int columnNo = 1;
//
//                for (String value : line) {
//
//                    if (columnNo == 1) {
//                        date[lineNo-1] = value;
//                    }
//
//                    if (columnNo == 2) {
//                        new_case[lineNo-1] = value;
//                    }
//
//                    if (columnNo == 5) {
//                        active_case[lineNo-1] = value;
//                    }
//
//                    columnNo++;
//                }
//                lineNo++;
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.i( ass, "Failed to read cases");
//        }
//
//        try {
//            URL cases_url = new URL(deaths_csv);
//            BufferedReader in = new BufferedReader(new InputStreamReader(cases_url.openStream()));
//            String s = null;
//            while ((s = in.readLine()) != null) {
//
//                String[] values = s.split(",");
//                records.add(Arrays.asList(values));
//            }
//
//            total_line2 = records.size();
//
//            daily_deaths = new String[total_line2];
//
//            int lineNo = 1;
//
//            for (List<String> line : records) {
//                int columnNo = 1;
//
//                for (String value : line) {
//
//                    if (columnNo == 2) {
//                        daily_deaths[lineNo-1] = value;
//                    }
//
//                    columnNo++;
//                }
//                lineNo++;
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.i( ass, "Failed to read deaths");
//
//        }
//    });


}
