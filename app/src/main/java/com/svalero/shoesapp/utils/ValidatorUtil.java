package com.svalero.shoesapp.utils;

import android.widget.EditText;


public class ValidatorUtil {


    public static boolean isEditTextNotEmpty(EditText editText) {
        String text = editText.getText().toString().trim();
        return !text.isEmpty();
    }

    public static boolean areEditTextsValid(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (!isEditTextNotEmpty(editText)) {
                return false;
            }
        }
        return true;
    }

}


