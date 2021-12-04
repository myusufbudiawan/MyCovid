package com.example.mycovid02.mycovidcode;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycovid02.MyDatabase;
import com.example.mycovid02.R;

import org.dpppt.android.sdk.DP3T;
import org.dpppt.android.sdk.backend.ResponseCallback;
import org.dpppt.android.sdk.backend.models.ExposeeAuthMethod;
import org.dpppt.android.sdk.backend.models.ExposeeAuthMethodJson;

import java.util.Calendar;
import java.util.Date;

public class InsertCodeFragment extends Fragment{

    Button sendBtn;
    private InputCodeView inputCodeView;
    private static final String REGEX_CODE = "\\d{" + InputCodeView.NUMBER_TOTAL + "}";
    MyDatabase myDatabase;
    private AlertDialog alertDialog;

    public static InsertCodeFragment newInstance() {
        return new InsertCodeFragment();
    }

    public InsertCodeFragment(){
        super(R.layout.fragment_entercode);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDatabase = MyDatabase.getInstance(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //calling activity method from fragment
        ((InsertCodeActivity) requireActivity()).allowBackButton(true);
        sendBtn = view.findViewById(R.id.send_code_button);
        inputCodeView = view.findViewById(R.id.enter_code_view);
        inputCodeView.addTextChangedListener(new InputCodeView.InputCodeListener() {
            @Override
            public void onTextChanged(String input) {
                sendBtn.setEnabled(input.matches(REGEX_CODE));
            }

            @Override
            public void onEditorSendAction() {
                if (sendBtn.isEnabled())
                    sendBtn.callOnClick();
            }
        });

        long latestReqTime = myDatabase.getLatestInformRequestTime();
        String latestCode = myDatabase.getLatestInformCode();
        String latestToken = myDatabase.getLatestInformToken();

        if (System.currentTimeMillis() - latestReqTime < (1000 * 60 * 5)) {
            inputCodeView.setText(latestCode);
        } else if (latestCode != null || latestToken != null) {
            myDatabase.clearInformTimeCodeToken();
        }

        sendBtn.setOnClickListener(v -> {
            sendBtn.setEnabled(false);
            String myCovidCode = inputCodeView.getText();

            alertDialog = setupProgressDialog();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -14);
            informExpose(calendar.getTime(), myCovidCode);
        } );
    }

    private void informExpose(Date time, String myCovidCode) {
        //inform to the DP3T
        DP3T.sendIAmInfected(getContext(), time, new ExposeeAuthMethodJson(myCovidCode)
                , new ResponseCallback<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        if (alertDialog != null && alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                        myDatabase.clearInformTimeCodeToken();
                        getParentFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_enter,
                                        R.anim.slide_exit, R.anim.slide_pop_enter,
                                        R.anim.slide_pop_exit)
                                .replace(R.id.code_fragment_container, acknowledgeFragment.newInstance())
                                .commit();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (alertDialog != null && alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                        Toast.makeText(getContext(),"ERROR UPLOADING TO DP3T!", Toast.LENGTH_SHORT).show();
                        throwable.printStackTrace();
                        sendBtn.setEnabled(true);
                    }

        });
    }


    private AlertDialog setupProgressDialog() {
        return new AlertDialog.Builder(getContext()).setView(R.layout.dialog_progress).show();
    }
}
