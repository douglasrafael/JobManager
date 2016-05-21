package com.fsdeveloper.jobmanager.adapter;

import android.app.Activity;
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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.Job;
import com.fsdeveloper.jobmanager.exception.ConnectionException;
import com.fsdeveloper.jobmanager.exception.JobManagerException;
import com.fsdeveloper.jobmanager.manager.Manager;
import com.fsdeveloper.jobmanager.tool.MyDataTime;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

/**
 * The adapter list the jobs.
 *
 * @author Created by Douglas Rafael on 08/05/2016.
 * @version 1.0
 */
public class JobListAdapter extends BaseAdapter {
    private final String TAG = "JobListAdapter";
    private Context context;
    private List<Job> listOfJobs;
    private Manager manager;

    public JobListAdapter(Context context, List<Job> listOfJobs) {
        this.context = context;
        this.listOfJobs = listOfJobs;
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
            holder.numberFormat = NumberFormat.getCurrencyInstance();

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Job job = listOfJobs.get(position);
        if (job != null) {
            holder.title.setText(job.getTitle());
            holder.description.setText(job.getDescription());
            holder.client.setText(job.getClient().getName());
            holder.price.setText(holder.numberFormat.format(new BigDecimal(job.getPrice())));
            holder.finalized.setChecked(job.isFinalized());

            int color = ((ListView) viewGroup).isItemChecked(position) ? Color.rgb(214, 214, 214) : Color.TRANSPARENT;

            convertView.setBackgroundColor(color);

            final ViewHolder finalHolder = holder;
            holder.finalized.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        manager = new Manager(context);
                        if (finalHolder.finalized.isChecked()) {
                            if (manager.setChangeFinalizedJob(job.getProtocol(), true)) {
                                job.setFinalized_at(MyDataTime.getDataTime(context.getResources().getString(R.string.date_time_bd)));

                                Toast.makeText(context, context.getResources().getString(R.string.success_finalized_job,
                                        MyDataTime.getDataTime(context.getResources().getString(R.string.date_time))), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.error_finalized_job), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (manager.setChangeFinalizedJob(job.getProtocol(), false)) {
                                job.setFinalized_at("");
                                Toast.makeText(context, context.getResources().getString(R.string.success_not_finalized_job), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.error_not_finalized_job), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JobManagerException e) {
                        e.printStackTrace();
                    } catch (ConnectionException e) {
                        e.printStackTrace();
                    }
                }
            });
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
        NumberFormat numberFormat;
    }
}
