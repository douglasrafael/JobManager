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
        View lineView = LayoutInflater.from(context).inflate(R.layout.list_job, null);

        Job job = listJobs.get(position);

        TextView title = (TextView) lineView.findViewById(R.id.text_title);
        TextView description = (TextView) lineView.findViewById(R.id.text_desc);
        TextView price = (TextView) lineView.findViewById(R.id.text_price);
        CheckBox finalized = (CheckBox) lineView.findViewById(R.id.cb_finalized);

        title.setText(job.getTitle());
        description.setText(job.getDescription());
        price.setText(context.getResources().getString(R.string.currency_symbol, job.getPrice()));
        finalized.setChecked(job.isFinalized());

        return lineView;
    }

}
