package com.fsdeveloper.jobmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.Job;

import java.util.List;

/**
 * @author Created by Douglas Rafael on 08/05/2016.
 * @version 1.0
 */
public class JobsAdapter extends BaseAdapter {
    Context context;
    List<Job> listJobs;

    public JobsAdapter(Context context, List<Job> listJobs) {
        this.context = context;
        this.listJobs = listJobs;
    }

    @Override
    public int getCount() {
        return listJobs.size();
    }

    @Override
    public Object getItem(int position) {
        return listJobs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        View convertView = view;

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_job, null);

            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.text_title);
            holder.description = (TextView) convertView.findViewById(R.id.text_desc);
            holder.price = (TextView) convertView.findViewById(R.id.text_price);
            holder.finalized = (CheckBox) convertView.findViewById(R.id.cb_finalized);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Job job = listJobs.get(position);

        holder.title.setText(job.getTitle());
        holder.description.setText(job.getDescription());
        holder.price.setText(context.getResources().getString(R.string.currency_symbol, job.getPrice()));
        holder.finalized.setChecked(job.isFinalized());

        return convertView;
    }

    /**
     * Optimizes the process of getView. In order not to create instance if it has already been created.
     */
    static class ViewHolder {
        TextView title;
        TextView description;
        TextView price;
        CheckBox finalized;
    }

}
