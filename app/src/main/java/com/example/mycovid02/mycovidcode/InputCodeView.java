package com.example.mycovid02.mycovidcode;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mycovid02.R;

import java.util.HashSet;
import java.util.Set;

public class InputCodeView extends ConstraintLayout {

    public static final int NUMBER_TOTAL = 12;
    private View textViewGroup;
    private EditText editText;
    private TextView[] textViews = new TextView[NUMBER_TOTAL];

    private Set<InputCodeListener> inputCodeViewListeners = new HashSet<>();

    public InputCodeView(@NonNull Context context) {
        super(context);
        init(context,null,0);
    }

    public InputCodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public InputCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        Configuration c = getResources().getConfiguration();
//        float scale = c.fontScale;

//        if (scale > 1) {
//            textViewGroup = LayoutInflater.from(context).inflate(R.layout.view_input_code, this, true);
//        }

        textViewGroup = LayoutInflater.from(context).inflate(R.layout.view_textinput_code, this, true);

        for (int i = 0; i < NUMBER_TOTAL; i++) {
            textViews[i] = textViewGroup.findViewById(getResources().getIdentifier("edit_text_" + (i + 1), "id", context.getPackageName()));
        }

        editText = new EditText(context);
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setHeight(1);
        editText.setWidth(1);
        editText.setCursorVisible(false);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setImeOptions(EditorInfo.IME_ACTION_SEND);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > NUMBER_TOTAL) {
                    s.delete(NUMBER_TOTAL, s.length());
                }

                updateTextView();
                String input = s.toString();
                for (InputCodeListener listener : inputCodeViewListeners) {
                    listener.onTextChanged(input);
                }
            }
        });

        editText.setOnFocusChangeListener((v, hasFocus) -> {
            updateTextView();
            setKeyboardVisible(hasFocus);
        });

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND && inputCodeViewListeners.size() > 0) {
                for (InputCodeListener listener : inputCodeViewListeners) {
                    listener.onEditorSendAction();
                }
                return true;
            }
            return false;
        });

        addView(editText);

        textViewGroup.setOnClickListener(v -> {
            focusEditText();
            setKeyboardVisible(true);
        });

        textViewGroup.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                focusEditText();
            }
        });

    }

    private void setKeyboardVisible(boolean b) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (b) {
            inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        } else {
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    private void focusEditText() {
        editText.requestFocus();
        editText.setSelection(editText.getText().length());
    }

    private void updateTextView() {
        String input = editText.getText().toString();
        boolean hasFocus = editText.hasFocus();

        for (int i = 0; i < NUMBER_TOTAL; i++) {
            TextView textView = textViews[i];

            if (i < input.length()) {
                textView.setText(String.valueOf(input.charAt(i)));
            } else {
                textView.setText("");
            }

            textView.setSelected(hasFocus && i == Math.min(input.length(), NUMBER_TOTAL - 1));
        }
    }

    public void setText(String text) {
        editText.setText(text);
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void addTextChangedListener(InputCodeListener chainedEditTextListener) {
        inputCodeViewListeners.add(chainedEditTextListener);
    }

    public interface InputCodeListener {
        void onTextChanged(String input);

        void onEditorSendAction();
    }
}
