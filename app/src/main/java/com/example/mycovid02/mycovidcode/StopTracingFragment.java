package com.example.mycovid02.mycovidcode;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycovid02.R;

public class StopTracingFragment extends Fragment {
    public StopTracingFragment() {
        super(R.layout.fragment_stop_tracing);
    }

    public static Fragment newInstance() {
        return new StopTracingFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((InsertCodeActivity) requireActivity()).allowBackButton(false);
        view.findViewById(R.id.tracing_finish_button).setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_pop_enter,
                            R.anim.slide_pop_exit).replace(R.id.code_fragment_container, GetWellFragment.newInstance())
                    .commit();
        });
    }
}
