package com.fsdeveloper.jobmanager.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.adapter.ClientListJobsAssociatesAdapter;
import com.fsdeveloper.jobmanager.adapter.ClientListPhoneAdapter;
import com.fsdeveloper.jobmanager.bean.Client;
import com.fsdeveloper.jobmanager.bean.Job;
import com.fsdeveloper.jobmanager.bean.Phone;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;
import com.fsdeveloper.jobmanager.fragments.GenericDialogFragment;
import com.fsdeveloper.jobmanager.manager.Manager;
import com.fsdeveloper.jobmanager.tool.MyDataTime;
import com.fsdeveloper.jobmanager.tool.MyStringsTool;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The preview client
 *
 * @author Created by Douglas Rafael on 21/05/2016.
 * @version 1.0
 */
public class ClientPreview extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, GenericDialogFragment.OnClickDialogListener {
    private final String TAG = "ClientPreview";

    public final int DIALOG_REMOVE = 2;
    public static final int REQUEST_CLIENT_UPDATE = 3;
    public static final String RESULT_CLIENT = "client";

    private TextView textName, textEmail, textAddress, textDateCreation;
    private ListView listViewPhones, listViewJobs;
    private Client client;
    private Manager manager;
    private ClientListJobsAssociatesAdapter adapterJobs;
    private ClientListPhoneAdapter adapterPhones;
    private LinearLayout linearLayoutBoxPhones, linearLayoutBoxJobs;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_preview);
        setupToolBar();

        textName = (TextView) findViewById(R.id.text_name_client_preview);
        textEmail = (TextView) findViewById(R.id.text_email_client_preview);
        textAddress = (TextView) findViewById(R.id.text_address_client_preview);
        textDateCreation = (TextView) findViewById(R.id.text_created_client_preview);
        listViewPhones = (ListView) findViewById(R.id.list_phones_client);
        listViewJobs = (ListView) findViewById(R.id.list_associates_jobs);
        linearLayoutBoxPhones = (LinearLayout) findViewById(R.id.box_list_phones_client);
        linearLayoutBoxJobs = (LinearLayout) findViewById(R.id.box_list_jobs_client);

        listViewPhones.setOnItemClickListener(this);
        listViewJobs.setOnItemClickListener(this);

        FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fab_edit_client);
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fab_remove_client);
        FloatingActionButton fabShare = (FloatingActionButton) findViewById(R.id.fab_share_client);
        fabEdit.setOnClickListener(this);
        fabDelete.setOnClickListener(this);
        fabShare.setOnClickListener(this);

        /**
         * Instance of the DAO methods manager
         */
        try {
            manager = new Manager(ClientPreview.this);
        } catch (JobManagerException e) {
            Toast.makeText(this, getResources().getString(R.string.error_system), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ConnectionException e) {
            Toast.makeText(this, getResources().getString(R.string.error_bd), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        /**
         * Get the client of Intent that called this Activity.
         *
         * If the client has id, form will editing.
         * If the client is null, form will register.
         */
        client = (Client) getIntent().getSerializableExtra(RESULT_CLIENT);
        if (client != null && client.getId() > 0) {
            // create view content the client
            createPreview(client);
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
            case R.id.fab_edit_client:
                Intent intent = new Intent(this, ClientFormActivity.class);
                intent.putExtra(ClientFormActivity.RESULT_CLIENT, client);
                startActivityForResult(intent, ClientFormActivity.REQUEST_CLIENT_UPDATE);
                break;
            case R.id.fab_remove_client:
                // Open dialog and and treats the return in onActivityResult
                GenericDialogFragment dialogRemove = GenericDialogFragment.newDialog(
                        DIALOG_REMOVE, R.string.action_confirm_delete, new int[]{android.R.string.ok, android.R.string.cancel}, null);
                dialogRemove.show(getSupportFragmentManager());
                break;
            case R.id.fab_share_client:
                shareClient(client);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Checks whether there were changes in ClientFormActivity
        if (resultCode == RESULT_OK && requestCode == ClientFormActivity.REQUEST_CLIENT_UPDATE) {
            Client clientUpdate = (Client) getIntent().getSerializableExtra(ClientFormActivity.RESULT_CLIENT);
            updatePreview(clientUpdate);
        }
    }

    @Override
    public void onClickDialog(int id, int button) {
        if (button == DialogInterface.BUTTON_POSITIVE && id == DIALOG_REMOVE) {
            if (client.getTotalOfJobs() == 0) {
                if (removeClient()) {
                    Toast.makeText(this, getResources().getQuantityString(R.plurals.success_delete_client, 1, 1), Toast.LENGTH_SHORT).show();
                    // was change
                    Intent it = new Intent();
                    it.putExtra(RESULT_CLIENT, client);
                    setResult(RESULT_OK, it);

                    finish();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.error_delete_client), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.error_delete_client_validation), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView.getId() == R.id.list_phones_client) {
            Phone phone = (Phone) adapterView.getItemAtPosition(position);
            if (phone.getNumber() != null || phone.getNumber().length() > 0) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone.getNumber()));
                startActivity(intent);
            }
        } else if (adapterView.getId() == R.id.list_associates_jobs) {
            Job job = (Job) adapterView.getItemAtPosition(position);

            Intent intent = new Intent(this, JobPreview.class);
            intent.putExtra(JobPreview.RESULT_JOB, job);
            startActivity(intent);
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
     * Remove the client.
     *
     * @return True or False
     */
    public boolean removeClient() {
        try {
            Manager manager = new Manager(ClientPreview.this);

            if (client != null) {
                return manager.deleteClient(client);
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
     * @param clientUpdate The client
     */
    private void updatePreview(Client clientUpdate) {
        try {
            client = manager.getClient(clientUpdate.getId());
            if (client != null) {
                createPreview(client);
            }
        } catch (JobManagerException e) {
            e.printStackTrace();
        }

    }

    /**
     * Create view the preview client
     */
    private void createPreview(Client c) {
        String name = c.getName();
        String email = c.getEmail();
        String address = c.getAddress();
        String dateCreated = c.getCreated_at();
        List<Phone> listOfPhone = new ArrayList<Phone>(c.getPhoneList());
        int totalAssociateJob = client.getTotalOfJobs();

        textName.setText(name);

        // Setting date creating
        int year = Integer.parseInt(dateCreated.substring(0, 4));
        int month = Integer.parseInt(dateCreated.substring(5, 7)) - 1;
        int day = Integer.parseInt(dateCreated.substring(8, 10));
        int hour = Integer.parseInt(dateCreated.substring(11, 13));
        int minute = Integer.parseInt(dateCreated.substring(14, 16));
        textDateCreation.setText(MyDataTime.getDataTime(year, month, day, hour, minute, getResources().getString(R.string.date_time)));

        /**
         * Setting values that can be displayed or not
         */
        if (MyStringsTool.isEmpty(email)) {
            findViewById(R.id.textView1).setVisibility(View.GONE);
            textEmail.setVisibility(View.GONE);
        } else {
            findViewById(R.id.textView1).setVisibility(View.VISIBLE);
            textEmail.setVisibility(View.VISIBLE);
            textEmail.setText(email);
        }

        if (MyStringsTool.isEmpty(address)) {
            findViewById(R.id.textView2).setVisibility(View.GONE);
            textAddress.setVisibility(View.GONE);
        } else {
            findViewById(R.id.textView2).setVisibility(View.VISIBLE);
            textAddress.setVisibility(View.VISIBLE);
            textAddress.setText(address);
        }

        /**
         * ListViews
         */
        try {
            // List the phones
            if (listOfPhone.size() > 0) {
                linearLayoutBoxPhones.setVisibility(View.VISIBLE);

                adapterPhones = new ClientListPhoneAdapter(this, listOfPhone);
                listViewPhones.setAdapter(adapterPhones);
                justifyListViewHeightBasedOnChildren(listViewPhones);
            } else {
                linearLayoutBoxPhones.setVisibility(View.GONE);
            }

            // List the jobs associates
            List<Job> listOfJobs = new ArrayList<Job>(manager.listJobsAssociateClient(c.getId()));
            if (listOfJobs.size() > 0) {
                linearLayoutBoxJobs.setVisibility(View.VISIBLE);

                adapterJobs = new ClientListJobsAssociatesAdapter(this, listOfJobs);
                listViewJobs.setAdapter(adapterJobs);
                justifyListViewHeightBasedOnChildren(listViewJobs);
            } else {
                linearLayoutBoxJobs.setVisibility(View.GONE);
            }
        } catch (JobManagerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Share the client
     */
    private void shareClient(Client client) {
        if (client != null) {
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            StringBuilder shareHtmlText = new StringBuilder();

            // Setting the title
            shareHtmlText.append("<h2>" + client.getName() + "</h2>");

            // Setting email if exist
            if (!MyStringsTool.isEmpty(client.getEmail())) {
                shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.client_email), client.getEmail()));
            }

            // Setting address if exist
            if (!MyStringsTool.isEmpty(client.getAddress())) {
                shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.client_address), client.getAddress()));
            }

            // Setting phone if exist
            if (client.getPhoneList().size() > 0) {
                StringBuilder htmlPhones = new StringBuilder();
                for (Phone phone : client.getPhoneList()) {
                    htmlPhones.append("<a href='tel:" + phone.getNumber() + "'>" + phone.getNumber() + "</a>,&nbsp;<i>" + phone.getType().getTitle() + "</i><br />");
                }

                shareHtmlText.append(MyStringsTool.setStyleSimpleBox(getResources().getString(R.string.client_phone), String.valueOf(htmlPhones)));
            }

            // Setting date creating
            String dateCreated = client.getCreated_at();

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
                    .setSubject(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.client))
                    .getIntent();

            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_client)));
            } else {
                GenericDialogFragment alertDialog = GenericDialogFragment.newDialog(1, R.string.no_support_functionality,
                        new int[]{android.R.string.ok}, null);
                alertDialog.show(getSupportFragmentManager());
            }
        }
    }

    /**
     * Justify layout the ListView
     *
     * @param listView The listView
     */
    public void justifyListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
}
