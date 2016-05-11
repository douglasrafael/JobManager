package com.fsdeveloper.jobmanager.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.tool.MonetaryMask;
import com.fsdeveloper.jobmanager.tool.MultiSelectSpinner;
import com.fsdeveloper.jobmanager.tool.MyDataTime;
import com.fsdeveloper.jobmanager.tool.MyDatePicker;
import com.fsdeveloper.jobmanager.tool.MyTimePicker;

import java.util.Arrays;
import java.util.List;

public class JobFormActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private EditText textTitle, textDescription, textNote, textPrice, textExpense, textDate, textTime;
    private AutoCompleteTextView textClient;
    private MultiSelectSpinner textCategories;
    private SwitchCompat isFinalized;
    private int[] dateFinalized = new int[3];
    private int[] timeFinalized = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textTitle = (EditText) findViewById(R.id.edt_title);
        textDescription = (EditText) findViewById(R.id.edt_desc);
        textNote = (EditText) findViewById(R.id.edt_note);
        textPrice = (EditText) findViewById(R.id.edt_price);
        textExpense = (EditText) findViewById(R.id.edt_expense);
        textDate = (EditText) findViewById(R.id.edt_date_finalized);
        textTime = (EditText) findViewById(R.id.edt_time_finalized);
        textClient = (AutoCompleteTextView) findViewById(R.id.edt_client);
        textCategories = (MultiSelectSpinner) findViewById(R.id.spinner_categories);
        isFinalized = (SwitchCompat) findViewById(R.id.switch_finalized);

        // Set mask in EditText price and expense
        textPrice.addTextChangedListener(new MonetaryMask(textPrice));
        textExpense.addTextChangedListener(new MonetaryMask(textExpense));

        // Prepare the autocomplete clients
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.categories_default)); // add list the client
        textClient.setAdapter(adapter);

        // Multi spinner categories
        textCategories.setItems(getResources().getStringArray(R.array.categories_default));
        textCategories.setSelection(Arrays.asList(new String[]{}));
//        List<String> selecteds = Arrays.asList(new String[]{"Other", "Construction", "Development"});
//        spinner.setSelection(selecteds);

        // Add event in the SwitchCompat
        isFinalized.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_discard_form:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.layout_box3);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout_box4);

        // Check if switch is clicked
        if (compoundButton.getId() == R.id.switch_finalized && isChecked) {
            // open layouts
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);

            // Set current date and time
            textDate.setText(MyDataTime.getDataTime(getResources().getString(R.string.date_default)));
            textTime.setText(MyDataTime.getDataTime(getResources().getString(R.string.time)));
        } else {
            // close layouts
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
        }
    }

    /**
     * Show data picker.
     *
     * @param v The view
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new MyDatePicker() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                super.onDateSet(datePicker, year, month, day);
                dateFinalized[0] = year;
                dateFinalized[1] = month;
                dateFinalized[2] = day;
                textDate.setText(MyDataTime.getDataTime(year, month, day, 0, 0, getResources().getString(R.string.date_default)));
            }
        };
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Show time picker.
     *
     * @param v The view
     */
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new MyTimePicker() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                super.onTimeSet(timePicker, hourOfDay, minute);
                timeFinalized[0] = hourOfDay;
                timeFinalized[1] = minute;
                textTime.setText(MyDataTime.getDataTime(0, 0, 0, hourOfDay, minute, getResources().getString(R.string.time)));
            }
        };
        newFragment.show(getSupportFragmentManager(), "timePicker");


    }

    /**
     * Returns the datetime in the database format.
     *
     * @return The datetime
     */
    private String getDataTimeFinalized() {
        return MyDataTime.getDataTime(dateFinalized[0], dateFinalized[1], dateFinalized[2],
                timeFinalized[0], timeFinalized[1], getResources().getString(R.string.date_time_bd));
    }
}
