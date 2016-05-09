package com.fsdeveloper.jobmanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.adapter.JobsAdapter;
import com.fsdeveloper.jobmanager.bean.Job;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;
import com.fsdeveloper.jobmanager.manager.Manager;

import java.util.List;

/**
 * @author Created by Douglas Rafael on 06/05/2016.
 * @version 1.0
 */
public class JobListFragment extends ListFragment  {
    private final String TAG = "JobListFragment";
    private ListView mListView;
    private ActionMode mActionMode;
    private List<Job> listOfJobs;
    private ArrayAdapter<Job> mAdapter;
    private Manager manager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            manager = new Manager(getContext());
            // TODO Mudar pelo id do user
            listOfJobs = manager.listOfJobs(1);

            setListAdapter(new JobsAdapter(getContext(), listOfJobs));
        } catch (ConnectionException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), R.string.error_bd, Toast.LENGTH_LONG).show();
        } catch (JobManagerException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), R.string.error_system, Toast.LENGTH_LONG).show();
        }
    }

    //    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        try {
//            manager = new Manager(getActivity());
//            mListView = getListView();
//            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories_default, android.R.layout.simple_expandable_list_item_2);
//
//            setListAdapter(adapter);
//            getListView().setOnItemClickListener(this);
////            listJobs();
//        } catch (JobManagerException e) {
//            Log.d(TAG, e.getMessage());
//        } catch (ConnectionException e) {
//            Log.d(TAG, e.getMessage());
//        }
//
//
//    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        try {
//            manager = new Manager(getActivity());
//            mListView = getListView();
//            listJobs();
//        } catch (JobManagerException e) {
//            Log.d(TAG, e.getMessage());
//        } catch (ConnectionException e) {
//            Log.d(TAG, e.getMessage());
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (mActionMode != null) {
            startExclusionMode();
        }
    }


    /**
     * To set adapter array of jobs
     */
    private void listJobs() {
//        listOfJobs = manager.listOfJobs(1);
//        mListView.setOnClickListener(this);
//        mAdapter = new Selec
    }

    /**
     * Clean list of jobs.
     */
    private void clearList() {
        listOfJobs.clear();
        mAdapter.notifyDataSetChanged();
    }

    private void startExclusionMode() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        mActionMode = activity.startSupportActionMode((ActionMode.Callback) this);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }


}
