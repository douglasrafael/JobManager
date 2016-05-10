package com.fsdeveloper.jobmanager.tool;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
//        String date = String.valueOf(year).concat("/").valueOf(month).concat("/").valueOf(day);
//        String result = "";
//        try {
//            return String.valueOf(MyDataTime.toStringDataTime(date, getContext().getString(R.string.date_1)));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return result;
    }
}
