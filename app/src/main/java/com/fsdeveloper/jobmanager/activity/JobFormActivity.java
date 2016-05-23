package com.fsdeveloper.jobmanager.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.adapter.AutoCompleteClientAdapter;
import com.fsdeveloper.jobmanager.bean.Client;
import com.fsdeveloper.jobmanager.bean.Job;
import com.fsdeveloper.jobmanager.bean.JobCategory;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;
import com.fsdeveloper.jobmanager.fragments.GenericDialogFragment;
import com.fsdeveloper.jobmanager.manager.Manager;
import com.fsdeveloper.jobmanager.tool.MonetaryMask;
import com.fsdeveloper.jobmanager.tool.MultiSelectSpinner;
import com.fsdeveloper.jobmanager.tool.MyAnimation;
import com.fsdeveloper.jobmanager.tool.MyDataTime;
import com.fsdeveloper.jobmanager.tool.MyDatePicker;
import com.fsdeveloper.jobmanager.tool.MyTimePicker;
import com.fsdeveloper.jobmanager.tool.MyValidate;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Activity the form job.
 *
 * @author Created by Douglas Rafael
 * @version 1.0
 */
public class JobFormActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,
        AdapterView.OnItemClickListener, TextWatcher, GenericDialogFragment.OnClickDialogListener {
    public static final String TAG = "JobFormActivity";
    public static final int REQUEST_JOB = 1;
    public static final int REQUEST_JOB_UPDATE = 1;
    public final int DIALOG_HAS_CHANGE = 3;
    public static final String RESULT_JOB = "job";

    private int[] dateFinalized = new int[3];
    private int[] timeFinalized = new int[2];
    private EditText textTitle, textDescription, textNote, textPrice, textExpense, textDate, textTime;
    private AutoCompleteTextView textClient;
    private MultiSelectSpinner textCategories;
    private SwitchCompat isFinalized;
    private Button btAddJob;
    private ScrollView scrollView;
    private MyValidate validate;
    private Job jobEdit;
    private Client client;
    private Manager manager;

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
        scrollView = (ScrollView) findViewById(R.id.scrollViewJobForm);

        // Set mask in EditText price and expense
        textPrice.addTextChangedListener(new MonetaryMask(textPrice));
        textExpense.addTextChangedListener(new MonetaryMask(textExpense));

        /**
         * Set MultiSelectSpinner categories
         */
        textCategories.setItems(getResources().getStringArray(R.array.categories_default));
        textCategories.setSelection(Arrays.asList(new String[]{})); // Not selected

        // Add event to the button that opens the activity to register customer
        ImageView btNewClient = (ImageView) findViewById(R.id.bt_new_client);
        btNewClient.setOnClickListener(this);

        // Add event to the button add job
        btAddJob = (Button) findViewById(R.id.bt_add_job);
        btAddJob.setOnClickListener(this);

        // Add event in the SwitchCompat
        isFinalized.setOnCheckedChangeListener(this);

        // Instance the validation
        validate = new MyValidate(this);

        /**
         * Instance of the DAO methods manager
         */
        try {
            manager = new Manager(JobFormActivity.this);

            /**
             * Set auto complete the clients and events
             */
            List<Client> listOfClients = new ArrayList<Client>(manager.listOfClients(1)); // TODO trocar pelo id da sessao
            textClient.setAdapter(new AutoCompleteClientAdapter(this, R.layout.activity_job_form, R.id.text_client, listOfClients));
            textClient.setOnItemClickListener(this);
            textClient.addTextChangedListener(this);
        } catch (JobManagerException e) {
            Toast.makeText(this, getResources().getString(R.string.error_system), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ConnectionException e) {
            Toast.makeText(this, getResources().getString(R.string.error_bd), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        /**
         * Get the job of Intent that called this Activity.
         *
         * If the job has protocol, form will editing.
         * If the job is null, form will register.
         */
        jobEdit = (Job) getIntent().getSerializableExtra(RESULT_JOB);
        if (isEdit() && jobEdit.getProtocol() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.title_job_form_update));
            client = jobEdit.getClient();
            // Fill form
            fillForm(jobEdit);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // It will not be used
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (String.valueOf(charSequence).length() <= 1) {
            client = null;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // It will not be used
    }

    /**
     * Event the button back of the bottom bar.
     */
    @Override
    public void onBackPressed() {
        // Checks for client to be edited (form in edit mode)
        if (hasChanged()) {
            // Open dialog back confirm
            openDialogCheckIfBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Client clientSelected = (Client) adapterView.getAdapter().getItem(i);
        if (clientSelected != null && clientSelected.getId() > 0) {
            client = clientSelected;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_new_client:
                client = null;
                Intent intent = new Intent(this, ClientFormActivity.class);
                intent.putExtra(ClientFormActivity.RESULT_CLIENT, client);
                startActivityForResult(intent, ClientFormActivity.REQUEST_CLIENT);
                break;
            case R.id.bt_add_job:
                if (validateForm()) {
                    boolean resultOk = true;
                    Job job = null;

                    try {
                        job = getJobOfForm();
                        /**
                         * If jobEdit is different from null it is because the form is in edit mode.
                         */
                        if (isEdit()) {
                            // Job update
                            if (manager.updateJob(job)) {
                                Toast.makeText(this, getResources().getString(R.string.success_edit_job), Toast.LENGTH_SHORT).show();
                            } else {
                                resultOk = false;
                            }
                        } else {
                            // Job add
                            if (manager.insertJob(job) > 0) {
                                Toast.makeText(this, getResources().getString(R.string.success_add_job), Toast.LENGTH_SHORT).show();
                            } else {
                                resultOk = false;
                            }
                        }
                    } catch (JobManagerException e) {
                        Toast.makeText(this, getResources().getString(R.string.error_add_job), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                        if (resultOk) {
                            // was change
                            Intent it = new Intent();
                            it.putExtra(RESULT_JOB, job);
                            setResult(RESULT_OK, it);
                        } else {
                            // not change
                            setResult(RESULT_CANCELED, new Intent());
                        }
                        finish();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**
         * Checks for customer to register
         */
        if (resultCode == RESULT_OK && requestCode == ClientFormActivity.REQUEST_CLIENT) {
            client = (Client) data.getSerializableExtra(ClientFormActivity.RESULT_CLIENT);
            textClient.setText(client.getName());
            textClient.requestFocus();
            textClient.setError(null);
        } else {
            client = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idButton = item.getItemId();

        if (idButton == R.id.action_discard_form) {
            super.onBackPressed();
        } else if (idButton == android.R.id.home) {
            // The button back in top bar
            if (hasChanged()) {
                // Open dialog back confirm
                openDialogCheckIfBack();
            } else {
                // Send to the activity that called and closes the screen
                super.onBackPressed();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.layout_box3);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout_box4);

        // Check if switch is clicked
        if (compoundButton.getId() == R.id.switch_finalized && isChecked) {
            // open layouts
            MyAnimation.crossFade(layout1, null, getResources().getInteger(android.R.integer.config_shortAnimTime), View.VISIBLE);
            MyAnimation.crossFade(layout2, null, getResources().getInteger(android.R.integer.config_shortAnimTime), View.VISIBLE);

            /**
             * Put the values into a global array. When you save, retrieve these values
             */
            setDataTimeFinalized(MyDataTime.getDataTime(getResources().getString(R.string.date_time_bd)), true, true);

            // Set current date and time in EditText
            textDate.setText(MyDataTime.getDataTime(getResources().getString(R.string.date_default)));
            textTime.setText(MyDataTime.getDataTime(getResources().getString(R.string.time)));

            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
        } else {
            // close layouts
            MyAnimation.crossFade(layout1, null, getResources().getInteger(android.R.integer.config_shortAnimTime), View.GONE);
            MyAnimation.crossFade(layout2, null, getResources().getInteger(android.R.integer.config_shortAnimTime), View.GONE);
        }
    }

    @Override
    public void onClickDialog(int id, int button) {
        if (id == DIALOG_HAS_CHANGE) {
            if (button == DialogInterface.BUTTON_POSITIVE) {
                super.onBackPressed();
            }
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

                /**
                 * Put the values into a global array. When you save, retrieve these values
                 */
                setDataTimeFinalized(MyDataTime.getDataTime(year, month, day, 0, 0, getResources().getString(R.string.date_time_bd)), true, false);

                /**
                 * Set the date in EditText
                 * Value to be used for MONTH field. 0 is January
                 */
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

                /**
                 * Put the values into a global array. When you save, retrieve these values
                 */
                setDataTimeFinalized(MyDataTime.getDataTime(0, 0, 0, hourOfDay, minute, getResources().getString(R.string.date_time_bd)), false, true);

                /**
                 * Set the hour and minute in EditText
                 */
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
        if (isFinalized.isChecked()) {
            /**
             * Value to be used for MONTH field. 0 is January.
             * For this reason it is necessary to reduce in 1.
             */
            return MyDataTime.getDataTime(dateFinalized[0], dateFinalized[1] - 1, dateFinalized[2],
                    timeFinalized[0], timeFinalized[1], getResources().getString(R.string.date_time_bd));
        }
        return "";
    }

    /**
     * Validation the form.
     *
     * @return True if is valid or False otherwise.
     */
    private boolean validateForm() {
        // Validate the title of the Job. It can not be empty.
        if (validate.isEmpty(textTitle, R.string.validate_title)) {
            return false;
            // Validate the title of the Job. At least 5 characters.
        } else if (!validate.min(textTitle, 5, R.string.validate_title_min)) {
            return false;
            // Validate the price of the Job. It can not be empty.
        } else if (validate.isEmpty(textPrice, R.string.validate_price)) {
            return false;
            // Validate the client of the job. It can not be empty.
        } else if (!validate.isValidClient(textClient, client, R.string.validate_client)) {
            return false;
            // Validates categories of Job. At least one must be selected.
        } else if (!validate.isValidCategories(textCategories, R.string.validate_required)) {
            return false;
        }

        return true;
    }

    /**
     * Takes a string in the format R$X.XX or $X.XX
     * And returns only the value in the type double.
     *
     * @param value The string value.
     * @return The value type double
     */
    private Double getValueCurrency(String value) {
        if (value != null && value.length() > 0) {
            // Get a NumberFormat for the default locale
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            try {
                String arg = value.substring(getResources().getString(R.string.currency_symbol_unique).length(), value.length());
                // Converts a number with commas 2,56 for double
                return numberFormat.parse(arg).doubleValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return 0.0;
    }

    private List<JobCategory> getCategories() {
        List<JobCategory> jobCategoryList = new ArrayList<JobCategory>();
        int id_category;
        String[] cat = getResources().getStringArray(R.array.categories_default);
        for (Integer i : textCategories.getSelectedIndicies()) {
            id_category = i + 1;
            jobCategoryList.add(new JobCategory(id_category, cat[i]));
        }

        return jobCategoryList;
    }

    /**
     * Get the input data and assemble job object.
     *
     * @return The Job
     */
    private Job getJobOfForm() {
        Job j = new Job();
        try {
            j.setProtocol(isEdit() ? jobEdit.getProtocol() : manager.generateProtocol());

            j.setTitle(String.valueOf(textTitle.getText()));
            j.setDescription(String.valueOf(textDescription.getText()));
            j.setNote(String.valueOf(textNote.getText()));
            j.setPrice(getValueCurrency(String.valueOf(textPrice.getText())));
            j.setExpense(getValueCurrency(String.valueOf(textExpense.getText())));
            j.setFinalized_at(getDataTimeFinalized());
            j.setClient(client);
            j.setCategories(getCategories());

            j.setUser_id(1); // TODO trocar pelo id da sessao

        } catch (JobManagerException e) {
            e.printStackTrace();
        }

        return j;
    }

    /**
     * Fill form with the job data for editing.
     *
     * @param jobEdit The job
     */
    private void fillForm(Job jobEdit) {
        if (jobEdit != null) {
            textTitle.setText(jobEdit.getTitle());
            textDescription.setText(jobEdit.getDescription());
            textNote.setText(jobEdit.getNote());
            textPrice.setText(getResources().getString(R.string.currency_symbol_unique) + String.format("%.2f", jobEdit.getPrice()).replace(".", ""));
            textExpense.setText(getResources().getString(R.string.currency_symbol_unique) + String.format("%.2f", jobEdit.getExpense()).replace(".", ""));
            textClient.setText(jobEdit.getClient().getName());

            // Set data time is finalized
            if (jobEdit.isFinalized()) {
                isFinalized.setChecked(true);
                int year = Integer.parseInt(jobEdit.getFinalized_at().substring(0, 4));
                int month = Integer.parseInt(jobEdit.getFinalized_at().substring(5, 7)) - 1;
                int day = Integer.parseInt(jobEdit.getFinalized_at().substring(8, 10));
                int hour = Integer.parseInt(jobEdit.getFinalized_at().substring(11, 13));
                int minute = Integer.parseInt(jobEdit.getFinalized_at().substring(14, 16));

                setDataTimeFinalized(jobEdit.getFinalized_at(), true, true);

                textDate.setText(MyDataTime.getDataTime(year, month, day, 0, 0, getResources().getString(R.string.date_default)));
                textTime.setText(MyDataTime.getDataTime(0, 0, 0, hour, minute, getResources().getString(R.string.time)));
            }

            // Set categories
            List<String> categories = new ArrayList<String>();
            for (JobCategory c : jobEdit.getCategories()) {
                categories.add(c.getName());
            }
            textCategories.setSelection(categories);

            // Set name button
            btAddJob.setText(getResources().getString(R.string.update));

            textTitle.requestFocus();
        }
    }

    /**
     * If jobEdit is different from null it is because the form is in edit mode
     *
     * @returnTrue it is in edit mode and False otherwise
     */
    private boolean isEdit() {
        return jobEdit != null;
    }

    /**
     * Fills the variables that will be used when saving.
     *
     * @param dataTime The datetime or date, time.
     * @param date     If date
     * @param time     If time
     */
    private void setDataTimeFinalized(String dataTime, boolean date, boolean time) {
        if (date) {
            dateFinalized[0] = Integer.parseInt(dataTime.substring(0, 4)); // Year
            dateFinalized[1] = Integer.parseInt(dataTime.substring(5, 7)); // Month
            dateFinalized[2] = Integer.parseInt(dataTime.substring(8, 10)); // Day
        }
        if (time) {
            timeFinalized[0] = Integer.parseInt(dataTime.substring(11, 13)); // Hour
            timeFinalized[1] = Integer.parseInt(dataTime.substring(14, 16)); // Day
        }
    }

    /**
     * Checks whether there were changes in form
     *
     * @return True if yes and False otherwise
     */
    private boolean hasChanged() {
        if (isEdit()) {
            Log.i("Edit", "" + getJobOfForm().equals(jobEdit));
            Log.i("Edit", "" + getJobOfForm() + "\n" + jobEdit);
            return !(getJobOfForm().equals(jobEdit));
        }
        if (textTitle.getText().length() > 0 || textDescription.getText().length() > 0
                || textNote.getText().length() > 0 || textClient.getText().length() > 0)
            return true;

        return false;
    }

    /**
     * Opens the dialog to confirm that you really want to come back and rule changes.
     */
    private void openDialogCheckIfBack() {
        GenericDialogFragment dialogHasChange = GenericDialogFragment.newDialog(DIALOG_HAS_CHANGE,
                R.string.back_confirm, new int[]{android.R.string.ok, android.R.string.cancel}, null);
        dialogHasChange.show(getSupportFragmentManager());
    }
}