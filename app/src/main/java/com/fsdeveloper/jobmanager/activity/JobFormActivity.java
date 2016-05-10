package com.fsdeveloper.jobmanager.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.tool.MonetaryMask;
import com.fsdeveloper.jobmanager.tool.MultiSelectSpinner;
import com.fsdeveloper.jobmanager.tool.MyDatePicker;
import com.fsdeveloper.jobmanager.tool.MyTimePicker;

import java.util.Arrays;
import java.util.List;

public class JobFormActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set mask in EditText price and expense
        EditText price = (EditText) findViewById(R.id.edt_price);
        EditText expense = (EditText) findViewById(R.id.edt_expense);
        price.addTextChangedListener(new MonetaryMask(price));
        expense.addTextChangedListener(new MonetaryMask(expense));

        // Prepare the autocomplete clients
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.categories_default)); // add list the client
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.edt_client);
        textView.setAdapter(adapter);

        // Multi spinner
        MultiSelectSpinner spinner = (MultiSelectSpinner) findViewById(R.id.spinner_categories);
        spinner.setItems(getResources().getStringArray(R.array.categories_default));
//        List<String> selecteds = Arrays.asList(new String[]{"Other", "Construction", "Development"});
//        spinner.setSelection(selecteds);

        // add event in the SwitchCompat
        SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id.switch_finalized);
        switchCompat.setOnCheckedChangeListener(this);
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

        if (compoundButton.getId() == R.id.switch_finalized && isChecked) {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
        } else {
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
        DialogFragment newFragment = new MyDatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Show time picker.
     *
     * @param v The view
     */
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new MyTimePicker();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
