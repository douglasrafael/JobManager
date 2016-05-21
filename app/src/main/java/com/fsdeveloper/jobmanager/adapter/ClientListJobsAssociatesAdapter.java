package com.fsdeveloper.jobmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.Job;

import java.util.List;

/**
 * The adapter list the the jobs associates.
 *
 * @author Created by Douglas Rafael on 21/05/2016.
 * @version 1.0
 */
public class ClientListJobsAssociatesAdapter extends BaseAdapter {
    Context context;
    List<Job> listJobs;

    public ClientListJobsAssociatesAdapter(Context context, List<Job> listJobs) {
        this.context = context;
        this.listJobs = listJobs;
    }

    @Override
    public int getCount() {
        return listJobs.size();
    }

    @Override
    public Job getItem(int position) {
        return listJobs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        View convertView = view;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_jobs_client, null);

            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.text_title_job_client_preview);
            holder.desc = (TextView) convertView.findViewById(R.id.text_desc_job_client_preview);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Job job = listJobs.get(position);
        if (job != null) {
            holder.title.setText(job.getTitle());
            holder.desc.setText(job.getDescription());
        }

        return convertView;
    }

    /**
     * Optimizes the process of getView. In order not to create instance if it has already been created.
     */
    static class ViewHolder {
        TextView title;
        TextView desc;
    }


}
