package com.fsdeveloper.jobmanager.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.Job;
import com.fsdeveloper.jobmanager.bean.JobCategory;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;
import com.fsdeveloper.jobmanager.fragments.GenericDialogFragment;
import com.fsdeveloper.jobmanager.manager.Manager;
import com.fsdeveloper.jobmanager.tool.MyDataTime;
import com.fsdeveloper.jobmanager.tool.MyStringsTool;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by Douglas Rafael on 17/05/2016.
 * @version 1.0
 */
public class JobPreview extends AppCompatActivity implements View.OnClickListener, GenericDialogFragment.OnClickDialogListener {
    private final String TAG = "JobPreview";

    public static final String RESULT_JOB = "job";
    private TextView textProtocol, textTitle, textDescription, textNote, textPrice, textExpense,
            textClient, textCategories, textIsFinalized, textDateFinalized, textDateCreation;
    private Job job;
    private Manager manager;
    private NumberFormat numberFormat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_preview);
        setupToolBar();

        textProtocol = (TextView) findViewById(R.id.text_protocol_job_preview);
        textTitle = (TextView) findViewById(R.id.text_title_job_preview);
        textDescription = (TextView) findViewById(R.id.text_desc_job_preview);
        textNote = (TextView) findViewById(R.id.text_note_job_preview);
        textPrice = (TextView) findViewById(R.id.text_price_job_preview);
        textExpense = (TextView) findViewById(R.id.text_expense_job_preview);
        textClient = (TextView) findViewById(R.id.text_client_job_preview);
        textCategories = (TextView) findViewById(R.id.text_categories_job_preview);
        textIsFinalized = (TextView) findViewById(R.id.text_is_finalized_job_preview);
        textDateFinalized = (TextView) findViewById(R.id.text_finalized_job_preview);
        textDateCreation = (TextView) findViewById(R.id.text_created_job_preview);
        numberFormat = NumberFormat.getNumberInstance();

        FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fab_edit_job);
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fab_remove_job);
        FloatingActionButton fabShare = (FloatingActionButton) findViewById(R.id.fab_share_job);
        fabEdit.setOnClickListener(this);
        fabDelete.setOnClickListener(this);
        fabShare.setOnClickListener(this);
        numberFormat = NumberFormat.getCurrencyInstance();


        /**
         * Instance of the DAO methods manager
         */
        try {
            manager = new Manager(JobPreview.this);
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
        job = (Job) getIntent().getSerializableExtra(RESULT_JOB);
        if (job != null && job.getProtocol() != null) {
            // create view content the job
            createPreview(job);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_edit_job:
                Intent intent = new Intent(this, JobFormActivity.class);
                intent.putExtra(JobFormActivity.RESULT_JOB, job);
                startActivityForResult(intent, JobFormActivity.REQUEST_JOB_UPDATE);
                break;
            case R.id.fab_remove_job:
                // Open dialog and and treats the return in onActivityResult
                GenericDialogFragment dialogRemove = GenericDialogFragment.newDialog(
                        1, R.string.action_confirm_delete, new int[]{android.R.string.ok, android.R.string.cancel}, null);
                dialogRemove.show(getSupportFragmentManager());

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Checks whether there were changes in JobFormActivity
        if (resultCode == RESULT_OK && requestCode == JobFormActivity.REQUEST_JOB_UPDATE) {
            Job jobUpdate = (Job) getIntent().getSerializableExtra(JobFormActivity.RESULT_JOB);
            if (updatePreview(jobUpdate)) {
                Toast.makeText(this, getResources().getString(R.string.success_edit_job), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.error_edit_job), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClickDialog(int id, int button) {
        if (button == DialogInterface.BUTTON_POSITIVE) {
            if (removeJob()) {
                Toast.makeText(this, getResources().getQuantityString(R.plurals.success_delete_job, 1, 1), Toast.LENGTH_SHORT).show();
                // was change
                Intent it = new Intent();
                it.putExtra(RESULT_JOB, job);
                setResult(RESULT_OK, it);

                finish();
            } else {
                Toast.makeText(this, getResources().getString(R.string.error_delete_job), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Setup toolbar.
     */
    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);
    }

    /**
     * Remove the job.
     *
     * @return
     */
    public boolean removeJob() {
        try {
            Manager manager = new Manager(JobPreview.this);

            if (job != null) {
                return manager.deleteJob(job);
            }
            return false;
        } catch (JobManagerException e) {
            e.printStackTrace();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Update the view
     *
     * @param jobUpdate The job
     * @return
     */
    private boolean updatePreview(Job jobUpdate) {
        try {
            job = manager.getJobByProtocol(jobUpdate.getProtocol());
            if (job != null) {
                createPreview(job);
                return true;
            }

            return false;
        } catch (JobManagerException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Create view the preview job
     */
    private void createPreview(Job j) {
        String protocol = j.getProtocol();
        String title = j.getTitle();
        String description = j.getDescription();
        String note = j.getNote();
        BigDecimal price = new BigDecimal(j.getPrice());
        BigDecimal expense = new BigDecimal(j.getExpense());
        String clientName = j.getClient().getName();
        String dateCreated = j.getCreated_at();
        String dataFinalized = j.getFinalized_at();

        /**
         * Prepare the categories
         */
        List<String> categories = new ArrayList<>();
        for (JobCategory c : j.getCategories()) {
            categories.add(c.getName());
        }

        /**
         * Setting the values fixed in layout
         */
        textProtocol.setText(protocol);
        textTitle.setText(title);
        textPrice.setText(numberFormat.format(price));
        textExpense.setText(numberFormat.format(expense));
        textCategories.setText(MyStringsTool.join(categories, ", "));
        textClient.setText(clientName);

        int year = Integer.parseInt(dateCreated.substring(0, 4));
        int month = Integer.parseInt(dateCreated.substring(5, 7)) - 1;
        int day = Integer.parseInt(dateCreated.substring(8, 10));
        int hour = Integer.parseInt(dateCreated.substring(11, 13));
        int minute = Integer.parseInt(dateCreated.substring(14, 16));
        textDateCreation.setText(MyDataTime.getDataTime(year, month, day, hour, minute, getResources().getString(R.string.date_time)));

        /**
         * Setting values that can be displayed or not
         */
        if (isEmpty(description)) {
            findViewById(R.id.textView2).setVisibility(View.GONE);
            textDescription.setVisibility(View.GONE);
        } else {
            findViewById(R.id.textView2).setVisibility(View.VISIBLE);
            textDescription.setVisibility(View.VISIBLE);
            textDescription.setText(description);
        }

        if (isEmpty(note)) {
            findViewById(R.id.textView3).setVisibility(View.GONE);
            textNote.setVisibility(View.GONE);
        } else {
            findViewById(R.id.textView3).setVisibility(View.VISIBLE);
            textNote.setVisibility(View.VISIBLE);
            textNote.setText(note);
        }

        if (job.isFinalized()) {
            year = Integer.parseInt(dataFinalized.substring(0, 4));
            month = Integer.parseInt(dataFinalized.substring(5, 7)) - 1;
            day = Integer.parseInt(dataFinalized.substring(8, 10));
            hour = Integer.parseInt(dataFinalized.substring(11, 13));
            minute = Integer.parseInt(dataFinalized.substring(14, 16));

            findViewById(R.id.textView9).setVisibility(View.VISIBLE);
            textDateFinalized.setVisibility(View.VISIBLE);

            textIsFinalized.setText(getResources().getString(R.string.yes));
            textDateFinalized.setText(MyDataTime.getDataTime(year, month, day, hour, minute, getResources().getString(R.string.date_time)));
        } else {
            textIsFinalized.setText(getResources().getString(R.string.no));
            findViewById(R.id.textView9).setVisibility(View.GONE);
            textDateFinalized.setVisibility(View.GONE);
        }
    }

    /**
     * Check if is empty
     *
     * @param s The String
     * @return The values boolean
     */
    private boolean isEmpty(String s) {
        if (s != null && s.length() > 0) {
            return false;
        }
        return true;
    }
}
