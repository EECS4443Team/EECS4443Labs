package com.example.eecs4443lab.util;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.eecs4443lab.R;

public class TextMaskToggleUtil {

    private TextMaskToggleUtil() {}

    public static void attach(EditText editText, ImageView toggleIcon) {

        toggleIcon.setOnClickListener(v -> {
            boolean isMasked =
                    editText.getTransformationMethod()
                            instanceof PasswordTransformationMethod;

            if (isMasked) {
                editText.setTransformationMethod(
                        HideReturnsTransformationMethod.getInstance());
                toggleIcon.setImageResource(R.drawable.baseline_visibility_24);
            } else {
                editText.setTransformationMethod(
                        PasswordTransformationMethod.getInstance());
                toggleIcon.setImageResource(R.drawable.baseline_visibility_off_24);
            }

            //Set the cursor loation to the end of the text
            editText.setSelection(editText.getText().length());
        });
    }
}
