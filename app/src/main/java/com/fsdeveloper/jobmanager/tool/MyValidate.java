package com.fsdeveloper.jobmanager.tool;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fsdeveloper.jobmanager.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains useful methods for validation.
 *
 * @author Created by Douglas Rafael on 13/05/2016.
 * @version 1.0
 */
public class MyValidate {
    private Context context;

    public MyValidate(Context context) {
        this.context = context;
    }

    /**
     * Check the email address is valid.
     *
     * @param editText       The email to validate.
     * @param errorMessageId The id of the error message.
     * @return True if valid, False otherwise.
     */
    public boolean validEmail(EditText editText, int errorMessageId) {
        CharSequence email = editText.getText();
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setError(editText, context.getString(errorMessageId));

            return false;
        }

        return true;
    }

    /**
     * Checks if the EditText is empty.
     *
     * @param editText       The EditText to validate.
     * @param errorMessageId The id of the error message.
     * @return True if empty or False otherwise.
     */
    public boolean isEmpty(EditText editText, int errorMessageId) {
        if (!(String.valueOf(editText.getText()).trim().length() == 0)) {
            return false;
        }
        setError(editText, context.getString(errorMessageId));
        return true;
    }

    /**
     * Verifies that contains the minimum size.
     *
     * @param editText       The EditText to validate.
     * @param min            The value min.
     * @param errorMessageId The id of the error message.
     * @return True if valid, False otherwise.
     */
    public boolean min(EditText editText, int min, int errorMessageId) {
        if (!(String.valueOf(editText.getText()).trim().length() >= min)) {
            setError(editText, context.getString(errorMessageId));
            return false;
        }
        return true;
    }

    /**
     * Validate the client of the job. It can not be empty.
     *
     * @param editText       The EditText to validate.
     * @param o              The client
     * @param errorMessageId The id of the error message.
     * @return True if valid, False otherwise.
     */
    public boolean isValidClient(EditText editText, Object o, int errorMessageId) {
        if ((String.valueOf(editText.getText()).trim().length() == 0) || o == null) {
            setError(editText, context.getString(errorMessageId));
            return false;
        }
        return true;
    }

    /**
     * @param spinner
     * @param errorMessageId
     * @return
     */
    public boolean isValidCategories(MultiSelectSpinner spinner, int errorMessageId) {
        TextView textView = (TextView) spinner.getSelectedView();
        if (spinner.getSelectedStrings().size() == 0) {
            textView.setFocusable(true);
            textView.setError(context.getString(errorMessageId));
            return false;
        }
        return true;
    }

    /**
     * Set the error message in EditText
     *
     * @param editText     The EditText.
     * @param errorMessage The error message.
     */
    public void setError(EditText editText, String errorMessage) {
        editText.setError(errorMessage);
        editText.requestFocus();
    }
}
