package com.fsdeveloper.jobmanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.Client;
import com.fsdeveloper.jobmanager.bean.Job;

import java.util.List;

/**
 * @author Created by Douglas Rafael on 08/05/2016.
 * @version 1.0
 */
public class ClientListAdapter extends BaseAdapter {
    Context context;
    List<Client> listClients;
    ClientListAdapter mAdapter;

    public ClientListAdapter(Context context, List<Client> listClients) {
        this.context = context;
        this.listClients = listClients;
    }

    @Override
    public int getCount() {
        return listClients.size();
    }

    @Override
    public Client getItem(int position) {
        return listClients.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_client, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.text_name_client);
            holder.email = (TextView) convertView.findViewById(R.id.text_email_client);
            holder.countJobs = (TextView) convertView.findViewById(R.id.text_count_jobs);
            holder.image = (ImageView) convertView.findViewById(R.id.image_client);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Client client = listClients.get(position);
        if (client != null) {
            holder.name.setText(client.getName());
            holder.email.setText(client.getEmail());
            holder.countJobs.setText(String.format("%02d", client.getTotalOfJobs()));

            int colorBack = ((ListView) viewGroup).isItemChecked(position) ? Color.rgb(214, 214, 214) : Color.TRANSPARENT;
            convertView.setBackgroundColor(colorBack);

            if(client.getImage() == null) {
            /*
             * Set image
              */
                ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
                // generate color based on a key (same key returns the same color), useful for list/grid views
                int color = generator.getColor(client.getName());

                TextDrawable.IBuilder builder = TextDrawable.builder()
                        .beginConfig()
                        .toUpperCase()
                        .endConfig()
                        .round();
               holder.image.setImageDrawable(builder.build(client.getName().substring(0, 1), color));
            }
        }

        return convertView;
    }

    /**
     * Optimizes the process of getView. In order not to create instance if it has already been created.
     */
    static class ViewHolder {
        TextView name;
        TextView email;
        ImageView image;
        TextView countJobs;
    }


}
