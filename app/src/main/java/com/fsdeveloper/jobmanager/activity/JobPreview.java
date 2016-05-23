package com.fsdeveloper.jobmanager.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.Client;
import com.fsdeveloper.jobmanager.bean.Job;
import com.fsdeveloper.jobmanager.bean.JobCategory;
import com.fsdeveloper.jobmanager.bean.Phone;
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
 * Preview the job.
 *
 * @author Created by Douglas Rafael on 17/05/2016.
 * @version 1.0
 */
public class JobPreview extends AppCompatActivity implements View.OnClickListener, GenericDialogFragment.OnClickDialogListener {
    private final String TAG = "JobPreview";

    public final int DIALOG_REMOVE = 2;
    public static final String RESULT_JOB = "job";

    private TextView textProtocol, textTitle, textDescription, textNote, textPrice, textExpense, textCategories, textIsFinalized, textDateFinalized, textDateCreation;
    private Button textClient;
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
        textClient = (Button) findViewById(R.id.text_client_job_preview);
        textCategories = (TextView) findViewById(R.id.text_categories_job_preview);
        textIsFinalized = (TextView) findViewById(R.id.text_is_finalized_job_preview);
        textDateFinalized = (TextView) findViewById(R.id.text_finalized_job_preview);
        textDateCreation = (TextView) findViewById(R.id.text_created_job_preview);
        numberFormat = NumberFormat.getNumberInstance();

        textClient.setOnClickListener(this);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int idButton = item.getItemId();

        if (idButton == android.R.id.home) {
            // Send to the activity that called and closes the screen
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
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
                        DIALOG_REMOVE, R.string.action_confirm_delete, new int[]{android.R.string.ok, android.R.string.cancel}, null);
                dialogRemove.show(getSupportFragmentManager());
                break;
            case R.id.fab_share_job:
                shareJob(job);
                break;
            case R.id.text_client_job_preview:
                if(job.getClient() != null) {
                    Intent intentClient = new Intent(this, ClientPreview.class);
                    intentClient.putExtra(ClientPreview.RESULT_CLIENT, job.getClient());
                    startActivity(intentClient);
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Checks whether there were changes in JobFormActivity
        if (resultCode == RESULT_OK && requestCode == JobFormActivity.REQUEST_JOB_UPDATE) {
            Job jobUpdate = (Job) getIntent().getSerializableExtra(JobFormActivity.RESULT_JOB);
            updatePreview(jobUpdate);
        }
    }

    @Override
    public void onClickDialog(int id, int button) {
        if (button == DialogInterface.BUTTON_POSITIVE && id == DIALOG_REMOVE) {
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
     */
    private void updatePreview(Job jobUpdate) {
        try {
            job = manager.getJobByProtocol(jobUpdate.getProtocol());
            if (job != null) {
                createPreview(job);
            }
        } catch (JobManagerException e) {
            e.printStackTrace();
        }

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
        if (MyStringsTool.isEmpty(description)) {
            findViewById(R.id.textView2).setVisibility(View.GONE);
            textDescription.setVisibility(View.GONE);
        } else {
            findViewById(R.id.textView2).setVisibility(View.VISIBLE);
            textDescription.setVisibility(View.VISIBLE);
            textDescription.setText(description);
        }

        if (MyStringsTool.isEmpty(note)) {
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
     * Share the job
     */
    private void shareJob(Job job) {
        if (job != null) {
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            StringBuilder shareHtmlText = new StringBuilder();

            // Setting the title
            shareHtmlText.append("<h2>" + job.getTitle() + "</h2>");

            // Setting protocol
            shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.job_protocol), job.getProtocol()));

            // Setting description if exist
            if (!MyStringsTool.isEmpty(job.getDescription())) {
                shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.job_description), job.getDescription()));
            }

            // Setting note if exist
            if (!MyStringsTool.isEmpty(job.getNote())) {
                shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.job_note), job.getNote()));
            }

            // Setting the price and expense
            shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.job_price), numberFormat.format(new BigDecimal(job.getPrice()))));
            shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.job_expense), numberFormat.format(new BigDecimal(job.getExpense()))));

            // Setting client
            StringBuilder htmlClient = new StringBuilder();
            htmlClient.append(job.getClient().getName());
            if (job.getClient().getPhoneList().size() > 0) {
                htmlClient.append("<br />" + getResources().getString(R.string.client_phone) + ":");

                for (Phone phone : job.getClient().getPhoneList()) {
                    htmlClient.append("<br /><a href='tel:" + phone.getNumber() + "'>" + phone.getNumber() + "</a>,&nbsp;<i>" + phone.getType().getTitle() + "</i>");
                }
            }
            shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.job_client), String.valueOf(htmlClient)));

            // Setting categories
            List<String> categories = new ArrayList<>();
            for (JobCategory c : job.getCategories()) {
                categories.add(c.getName());
            }
            shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.job_categories), MyStringsTool.join(categories, ", ")));

            // Setting is finalized
            StringBuilder htmlFinalized = new StringBuilder();
            htmlFinalized.append(job.isFinalized() ? getResources().getString(R.string.yes) : getResources().getString(R.string.no));

            if (job.isFinalized()) {
                String dateFinalized = job.getFinalized_at();

                int year = Integer.parseInt(dateFinalized.substring(0, 4));
                int month = Integer.parseInt(dateFinalized.substring(5, 7)) - 1;
                int day = Integer.parseInt(dateFinalized.substring(8, 10));
                int hour = Integer.parseInt(dateFinalized.substring(11, 13));
                int minute = Integer.parseInt(dateFinalized.substring(14, 16));

                htmlFinalized.append("<br />" + getResources().getString(R.string.job_date_finalized) + ":<br />" +
                        MyDataTime.getDataTime(year, month, day, hour, minute, getResources().getString(R.string.date_time)));
            }

            shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.job_is_finalized), String.valueOf(htmlFinalized)));

            // Setting date creating
            String dateCreated = job.getCreated_at();

            int year = Integer.parseInt(dateCreated.substring(0, 4));
            int month = Integer.parseInt(dateCreated.substring(5, 7)) - 1;
            int day = Integer.parseInt(dateCreated.substring(8, 10));
            int hour = Integer.parseInt(dateCreated.substring(11, 13));
            int minute = Integer.parseInt(dateCreated.substring(14, 16));
            shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.job_date_created),
                    MyDataTime.getDataTime(year, month, day, hour, minute, getResources().getString(R.string.date_time))));

            Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/html")
                    .setHtmlText(String.valueOf(shareHtmlText))
                    .setSubject(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.job) + " [" + job.getProtocol() + "]")
                    .getIntent();

            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_job)));
            } else {
                GenericDialogFragment alertDialog = GenericDialogFragment.newDialog(1, R.string.no_support_functionality,
                        new int[]{android.R.string.ok}, null);
                alertDialog.show(getSupportFragmentManager());
            }
        }
    }
}
