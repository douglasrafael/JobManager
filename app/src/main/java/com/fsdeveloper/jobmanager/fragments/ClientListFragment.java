package com.fsdeveloper.jobmanager.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.activity.ClientFormActivity;
import com.fsdeveloper.jobmanager.activity.ClientPreview;
import com.fsdeveloper.jobmanager.activity.JobPreview;
import com.fsdeveloper.jobmanager.adapter.ClientListAdapter;
import com.fsdeveloper.jobmanager.bean.Client;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;
import com.fsdeveloper.jobmanager.manager.Manager;
import com.fsdeveloper.jobmanager.tool.MyStringsTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles listView of the clients
 *
 * @author Created by Douglas Rafael on 20/05/2016.
 * @version 1.0
 */
public class ClientListFragment extends ListFragment implements ActionMode.Callback, AdapterView.OnItemLongClickListener {
    private final String TAG = "ClientListFragment";
    private final String RESULT_CLIENT = "client";
    private final int REQUEST_CLIENT = 1;

    private static Manager manager;
    private static Context context;
    private static ClientListAdapter mAdapter;
    private static List<Client> listOriClients, listOfClients, listFoundClients;

    private ActionMode mActionMode;
    private Menu mMenu;
    private ListView mListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = inflater.getContext();
        return inflater.inflate(R.layout.list_fragment, null, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView = getListView();

        // Instance list empty
        listOriClients = new ArrayList<Client>();
        listFoundClients = new ArrayList<Client>();
        listOfClients = new ArrayList<Client>();

        // Setting Event
        getListView().setOnItemLongClickListener(this);

        // Setting Adapter
        mAdapter = new ClientListAdapter(context, listOfClients);
        setListAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        updateListView();
    }

    @Override
    public void onListItemClick(final ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (mActionMode == null) {
            Client client = (Client) l.getItemAtPosition(position);

            Activity activity = getActivity();
            if (activity instanceof OnClientClick) {
                OnClientClick listener = (OnClientClick) activity;
                listener.onClientClick(client);
            }

            /**
             * Open view detail client
             */
            if (client != null) {
                Intent intent = new Intent(context, ClientPreview.class);
                intent.putExtra(RESULT_CLIENT, client);
                startActivityForResult(intent, REQUEST_CLIENT);
            }
        } else {
            int checkedCount = updateItemsChange(mListView, position);
            if (checkedCount == 0) {
                mActionMode.finish();
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        boolean consumed = (mActionMode == null);

        if (consumed) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();

            mActionMode = activity.startSupportActionMode(this);
            mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            mListView.setItemChecked(i, true);
            updateItemsChange(mListView, i);
        }

        return consumed;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.list_item_menu, menu);

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        this.mMenu = menu;
        changeActionBar(true);
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        SparseBooleanArray checked = mListView.getCheckedItemPositions();
        int position;
        switch (item.getItemId()) {
            case R.id.action_list_edit:
                // Edit item
                position = checked.keyAt(0);
                Client client = (Client) mListView.getItemAtPosition(position);
                if (client != null) {
                    Intent intent = new Intent(context, ClientFormActivity.class);
                    intent.putExtra(ClientFormActivity.RESULT_CLIENT, client);
                    startActivityForResult(intent, ClientFormActivity.REQUEST_CLIENT);
                }

                mActionMode.finish();
                return true;
            case R.id.action_list_delete:
                // Open dialog and and treats the return in onActivityResult
//                GenericDialogFragment dialogRemove = GenericDialogFragment.newDialog(
//                        1, R.string.action_confirm_delete, new int[]{android.R.string.ok, android.R.string.cancel}, ClientListFragment.this);
//                dialogRemove.setTargetFragment(this, GenericDialogFragment.REQUEST_DIALOG);
//                dialogRemove.show(getFragmentManager());
                removeItemsChecked();

                return true;
            case R.id.action_list_select_all:
                selectAllItems(mListView);
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK && requestCode == ClientFormActivity.REQUEST_CLIENT) {
            updateListView();
        } else if (requestCode == GenericDialogFragment.REQUEST_DIALOG && resultCode == DialogInterface.BUTTON_POSITIVE ) {
            removeItemsChecked();
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode = null;
        mListView.clearChoices();
        mListView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        changeActionBar(false);

        updateListView();
    }

    /**
     * Remove items checked in listView
     *
     * @return True if success or False otherwise.
     */
    private boolean removeItemsChecked() {
        SparseBooleanArray checked = mListView.getCheckedItemPositions();
        boolean result = false;
        int position, countNotDeleted = 0;

        for (int i = 0; i < checked.size(); i++) {
            if (checked.valueAt(i)) {
                position = checked.keyAt(i);

                Client c = (Client) mListView.getItemAtPosition(position);
                if (c.getTotalOfJobs() == 0) {
                    result = deleteClient(c);
                } else {
                    countNotDeleted++;
                }
            }
        }

        if (result && countNotDeleted == 0) {
            String message = context.getResources().getQuantityString(R.plurals.success_delete_client, checked.size(), checked.size());
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } else if (result && countNotDeleted > 0) {
            String message = context.getResources().getQuantityString(R.plurals.success_delete_client_partial, countNotDeleted, countNotDeleted);
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.error_delete_client_validation), Toast.LENGTH_LONG).show();
        }
        mActionMode.finish();

        return result;
    }

    /**
     * Update listView
     */
    public static void updateListView() {
        listOfClients.clear();
        listOfClients.addAll(getListClients());
        mAdapter.notifyDataSetChanged();
    }


    /**
     * Update items in  ListView
     *
     * @param l        The listView
     * @param position Position the item in listView
     * @return Total items selected
     */
    private int updateItemsChange(ListView l, int position) {
        SparseBooleanArray checked = l.getCheckedItemPositions();
        l.setItemChecked(position, l.isItemChecked(position));
        int checkedCount = 0;
        if (checked != null) {
            for (int i = 0; i < checked.size(); i++) {
                if (checked.valueAt(i)) {
                    checkedCount++;
                }
            }
            processActionMode(checkedCount);
        }

        return checkedCount;
    }

    /**
     * Processes ActionMode.
     * Defines which buttons should appear and the title.
     *
     * @param checkedCount The total items checked in ListView.
     */
    private void processActionMode(int checkedCount) {
        if (checkedCount == 1) {
            mMenu.findItem(R.id.action_list_edit).setVisible(true);
        } else {
            mMenu.findItem(R.id.action_list_edit).setVisible(false);
        }

        // Setting title in mActionMode
        String selected = context.getResources().getQuantityString(R.plurals.number_selected, checkedCount, checkedCount);
        mActionMode.setTitle(selected);
    }

    /**
     * Select all items in ListView
     *
     * @param v The ListView
     */
    private void selectAllItems(ListView v) {
        int checkedCount = v.getCount();
        for (int i = 0; i < checkedCount; i++) {
            v.setItemChecked(i, true);
        }

        processActionMode(checkedCount);
    }

    /**
     * Sets the Action Bar should be shown or not.
     *
     * @param hide True should be removed should be False if it should be shown.
     */
    public void changeActionBar(boolean hide) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();

        if (hide) {
            actionBar.hide();
        } else {
            actionBar.show();
        }
    }

    /**
     * Get list the list of clients
     *
     * @return List the clients
     */
    private static List<Client> getListClients() {
        List<Client> result = new ArrayList<Client>();
        try {
            manager = new Manager(context);

            result = manager.listOfClients(1);  // TODO Mudar pelo id do user
        } catch (ConnectionException e) {
            Toast.makeText(context, R.string.error_bd, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (JobManagerException e) {
            Toast.makeText(context, R.string.error_system, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        listOriClients.clear();
        listOriClients.addAll(result);

        return result;
    }

    /**
     * Delete client selected.
     *
     * @param client The client
     * @return True if success or False otherwise.
     */
    private boolean deleteClient(Client client) {
        try {
            if (manager.deleteClient(client)) {
                return true;
            }
        } catch (JobManagerException e) {
            Toast.makeText(context, context.getResources().getString(R.string.error_delete_client), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Search the client.
     *
     * @param filter The filter
     * @return The list of the clients
     */
    public static List<Client> search(String filter) {
        String termSearch = MyStringsTool.removeAccents(filter).toLowerCase();

        listFoundClients.clear();
        for (Client client : listOriClients) {
            String nameClient = MyStringsTool.removeAccents(client.getName()).toLowerCase();
            String emailClient = MyStringsTool.removeAccents(client.getEmail()).toLowerCase();

            if (emailClient.indexOf(termSearch) != -1 || nameClient.indexOf(termSearch) != -1) {
                listFoundClients.add(client);
            }
        }

        listOfClients.clear();
        if (filter == "") {
            listOfClients.addAll(getListClients());
        } else {
            listOfClients.addAll(listFoundClients);
        }
        mAdapter.notifyDataSetChanged();

        return listFoundClients;
    }

    /**
     * Interface to see if the item was clicked.
     */
    public interface OnClientClick {
        void onClientClick(Client client);
    }
}
