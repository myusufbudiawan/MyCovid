package com.example.mycovid02.mycovidcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycovid02.MainActivity;
import com.example.mycovid02.R;

public class GetWellFragment extends Fragment {
    public GetWellFragment() {
        super(R.layout.fragment_get_well);
    }

    public static Fragment newInstance() {
        return new StopTracingFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((InsertCodeActivity) requireActivity()).allowBackButton(false);

        view.findViewById(R.id.get_well_finish_button).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setAction(MainActivity.ACTION_STOP_TRACING);
            startActivity(intent);
            getActivity().finish();
        });
    }
}
