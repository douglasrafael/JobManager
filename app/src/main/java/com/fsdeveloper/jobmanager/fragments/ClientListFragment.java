package com.fsdeveloper.jobmanager.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.adapter.ClientListAdapter;
import com.fsdeveloper.jobmanager.adapter.JobListAdapter;
import com.fsdeveloper.jobmanager.bean.Client;
import com.fsdeveloper.jobmanager.bean.Job;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;
import com.fsdeveloper.jobmanager.manager.Manager;

import java.util.List;

/**
 * @author Created by Douglas Rafael on 06/05/2016.
 * @version 1.0
 */
public class ClientListFragment extends ListFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private final String TAG = "ClientListFragment";
    private List<Client> listOfClients;
    private Manager manager;
    private Context context;

    public ClientListFragment() {
    }

    public ClientListFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, null, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            manager = new Manager(context);

            listOfClients = manager.listOfClients(1); // TODO Mudar pelo id do user
            getListView().setOnItemLongClickListener(this);
            getListView().setOnItemClickListener(this);
            setListAdapter(new ClientListAdapter(context, listOfClients));
        } catch (ConnectionException e) {
            e.printStackTrace();
            Toast.makeText(context, R.string.error_bd, Toast.LENGTH_LONG).show();
        } catch (JobManagerException e) {
            e.printStackTrace();
            Toast.makeText(context, R.string.error_system, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        Log.i(TAG, "OnResume");
        super.onResume();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(TAG, "onItemLongClick");
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(TAG, "onItemClick");
        Log.i(TAG, "" + listOfClients.get(i));

    }
}
