package com.fsdeveloper.jobmanager.tool;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.fsdeveloper.jobmanager.R;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Opens the dialog with the DataPicker.
 *
 * @author Created by Douglas Rafael on 10/05/2016.
 * @version 1.0
 */
public class MyDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();

        // Create a new instance of DatePickerDialog and return it
        // Use the current date as the default date in the picker
        return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}
