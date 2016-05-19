package com.fsdeveloper.jobmanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.ListFragment;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.Job;

import java.util.List;

/**
 * @author Created by Douglas Rafael on 08/05/2016.
 * @version 1.0
 */
public class JobListAdapter extends BaseAdapter {
    private final String TAG = "JobListAdapter";
    Context context;
    List<Job> listOfJobs;
    ListFragment fragment;

    public JobListAdapter(ListFragment fragment, Context context, List<Job> listOfJobs) {
        this.context = context;
        this.listOfJobs = listOfJobs;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return listOfJobs.size();
    }

    @Override
    public Job getItem(int position) {
        return listOfJobs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        View convertView = view;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_job, null);

            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.text_title_job);
            holder.description = (TextView) convertView.findViewById(R.id.text_desc_job);
            holder.price = (TextView) convertView.findViewById(R.id.text_price_job);
            holder.client = (TextView) convertView.findViewById(R.id.text_client_job);
            holder.finalized = (CheckBox) convertView.findViewById(R.id.cb_finalized_job);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Job job = listOfJobs.get(position);

        if (job != null) {
            holder.title.setText(job.getTitle());
            holder.description.setText(job.getDescription());
            holder.client.setText(job.getClient().getName());
            holder.price.setText(context.getResources().getString(R.string.currency_symbol, job.getPrice()));
            holder.finalized.setChecked(job.isFinalized());

            int color = ((ListView) viewGroup).isItemChecked(position) ? Color.rgb(214, 214, 214) : Color.TRANSPARENT;

            convertView.setBackgroundColor(color);
        }

        return convertView;
    }

    /**
     * Optimizes the process of getView. In order not to create instance if it has already been created.
     */
    static class ViewHolder {
        TextView title;
        TextView description;
        TextView price;
        TextView client;
        CheckBox finalized;
    }

}
