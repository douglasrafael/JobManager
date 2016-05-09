package com.fsdeveloper.jobmanager.adapter;

import android.content.Context;
import android.util.Log;
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
    List<Job> listOfjobs;

    public JobsAdapter(Context context, List<Job> listOfjobs) {
        this.context = context;
        this.listOfjobs = listOfjobs;
    }

    @Override
    public int getCount() {
        return listOfjobs.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfjobs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Log.i("PASOU POR AQUI", "========================= ====================================");
        View lineView = LayoutInflater.from(context).inflate(R.layout.list_job, null);

        Job job = listOfjobs.get(position);

        TextView title = (TextView) lineView.findViewById(R.id.text_title);
        TextView description = (TextView) lineView.findViewById(R.id.text_desc);
        TextView price = (TextView) lineView.findViewById(R.id.text_price);
        CheckBox finalized = (CheckBox) lineView.findViewById(R.id.cb_finalized);

        title.setText(job.getTitle());
        description.setText(job.getDescription());
        price.setText(context.getResources().getString(R.string.currency_symbol) + " " + job.getPrice());
        finalized.setChecked(job.isFinalized());

        return lineView;
    }

}
