package com.fsdeveloper.jobmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Created by Douglas Rafael on 17/05/2016.
 * @version 1.0
 */
public class JobPreview extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "JobPreview";

    public static final String RESULT_JOB = "job";
    private Job job;
    private TextView textProtocol, textTitle, textDescription, textNote, textPrice, textExpense,
            textClient, textCategories, textIsFinalized, textDateFinalized, textDateCreation;

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

        FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fab_edit_job);
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fab_remove_job);
        FloatingActionButton fabShare = (FloatingActionButton) findViewById(R.id.fab_share_job);
        fabEdit.setOnClickListener(this);
        fabDelete.setOnClickListener(this);
        fabShare.setOnClickListener(this);


        /**
         * Get the job of Intent that called this Activity.
         *
         * If the job has protocol, form will editing.
         * If the job is null, form will register.
         */
        job = (Job) getIntent().getSerializableExtra(RESULT_JOB);
        if (job != null && job.getProtocol() != null) {
            // create view content the job
            createPreview();
        }
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_edit_job:
                Intent intent = new Intent(JobPreview.this, JobFormActivity.class);
                intent.putExtra(JobFormActivity.RESULT_JOB, job);
                startActivityForResult(intent, JobFormActivity.REQUEST_JOB);
                break;
            case R.id.fab_remove_job:
                checkRemoveJob();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == JobFormActivity.REQUEST_JOB) {
            Job newJob = (Job) getIntent().getSerializableExtra(RESULT_JOB);
            if (newJob != null) {
                Log.i(TAG, "Ok edit"+ newJob);
                // create view content the job
                createPreview();
            }
        } else if (requestCode == Activity.RESULT_OK && resultCode == GenericDialogFragment.REQUEST_DIALOG) {
            Log.i(TAG, "Ok remove");
            if (removeJob()) {
                String message = getResources().getQuantityString(R.plurals.success_delete_job, 1, 1);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_remove_mini);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);
    }


    /**
     * Remove the job
     */
    private void checkRemoveJob() {
        // Open dialog and and treats the return in onActivityResult
        GenericDialogFragment dialogRemove = new GenericDialogFragment().newDialog(1, R.string.action_confirm_delete, new int[]{android.R.string.ok, android.R.string.cancel});
        dialogRemove.setTargetFragment(new Fragment(), GenericDialogFragment.REQUEST_DIALOG);
        dialogRemove.show(getSupportFragmentManager());
    }

    public boolean removeJob() {
        try {
            Manager manager = new Manager(JobPreview.this);

            if (job != null) {
                return manager.deleteJob(job);
            } else {
                return false;
            }
        } catch (JobManagerException e) {
            e.printStackTrace();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Create view the preview job
     */
    private void createPreview() {
        Log.i(TAG, "createPreview");

        String protocol = job.getProtocol();
        String title = job.getTitle();
        String description = job.getDescription();
        String note = job.getNote();
        double price = job.getPrice();
        double expense  = job.getExpense();
        String clientName = job.getClient().getName();
        String dateCreated = job.getCreated_at();
        String dataFinalized = job.getFinalized_at();

        /**
         * Prepare the categories
         */
        List<String> categories = new ArrayList<>();
        for (JobCategory c : job.getCategories()) {
            categories.add(c.getName());
        }

        /**
         * Setting the values fixed in layout
         */
        textProtocol.setText(protocol);
        textTitle.setText(title);
        textPrice.setText(getResources().getString(R.string.currency_symbol, price));
        textExpense.setText(getResources().getString(R.string.currency_symbol, expense));
        textCategories.setText(MyStringsTool.join(categories, ", "));
        textClient.setText(clientName);
        textDateCreation.setText(MyDataTime.getFormatDataTimeDB(job.getCreated_at(), getResources().getString(R.string.date_time), true, true));

        /**
         * Setting values that can be displayed or not
         */
        if(isEmpty(description)) {
            findViewById(R.id.textView2).setVisibility(View.GONE);
            textDescription.setVisibility(View.GONE);
        } else {
            textDescription.setText(description);
        }

        if(isEmpty(note)) {
            findViewById(R.id.textView3).setVisibility(View.GONE);;
            textNote.setVisibility(View.GONE);
        } else {
            textNote.setText(note);
        }

        if (job.isFinalized()) {
            textIsFinalized.setText(getResources().getString(R.string.yes));
            textDateFinalized.setText(MyDataTime.getFormatDataTimeDB(job.getFinalized_at(), getResources().getString(R.string.date_time), true, true));
        } else {
            textIsFinalized.setText(getResources().getString(R.string.no));
            findViewById(R.id.textView9).setVisibility(View.GONE);;
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
        if(s!= null && s.length() > 0) {
            return false;
        }
        return true;
    }
}
