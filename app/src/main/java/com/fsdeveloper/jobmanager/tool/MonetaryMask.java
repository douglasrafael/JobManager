package com.fsdeveloper.jobmanager.tool;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;

/**
 * Monetary Mask.
 *
 * @author Created by Henrique Lacerda.
 * @version 1.0
 */
public class MonetaryMask implements TextWatcher {

    final EditText field;

    public MonetaryMask(EditText field) {
        super();
        this.field = field;
    }

    private boolean isUpdating = false;
    // Get the formatting of the system, if Brazil R$, EUA $
    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        // Prevents that the method is executed several times.
        if (isUpdating) {
            isUpdating = false;
            return;
        }

        isUpdating = true;
        String str = s.toString();
        // Checks to see if there is a mask in the text.
        boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1) &&
                (str.indexOf(".") > -1 || str.indexOf(",") > -1));
        // Check if the mask
        if (hasMask) {
            // Remove the mask
            str = str.replaceAll("[R$]", "").replaceAll("[,]", "")
                    .replaceAll("[.]", "");
        }

        try {
            // Transform the number that is written in the EditText in money.
            str = nf.format(Double.parseDouble(str) / 100);
            field.setText(str);
            field.setSelection(field.getText().length());
        } catch (NumberFormatException e) {
            s = "";
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Not be used
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Not be used
    }
}
