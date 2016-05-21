package com.fsdeveloper.jobmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.Phone;

import java.util.List;

/**
 * The adapter list the phones.
 *
 * @author Created by Douglas Rafael on 21/05/2016.
 * @version 1.0
 */
public class ClientListPhoneAdapter extends BaseAdapter {
    Context context;
    List<Phone> listPhones;

    public ClientListPhoneAdapter(Context context, List<Phone> listPhones) {
        this.context = context;
        this.listPhones = listPhones;
    }

    @Override
    public int getCount() {
        return listPhones.size();
    }

    @Override
    public Phone getItem(int position) {
        return listPhones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        viewHolderPhone holder = null;
        View convertView = view;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_phone_client, null);

            holder = new viewHolderPhone();
            holder.number = (TextView) convertView.findViewById(R.id.text_phone_client_preview);
            holder.type = (TextView) convertView.findViewById(R.id.text_type_phone_client_preview);

            convertView.setTag(holder);
        } else {
            holder = (viewHolderPhone) convertView.getTag();
        }

        Phone phone = listPhones.get(position);
        if (phone != null) {
            holder.number.setText(phone.getNumber());
            holder.type.setText(phone.getType().getTitle());
        }

        return convertView;
    }

    /**
     * Optimizes the process of getView. In order not to create instance if it has already been created.
     */
    static class viewHolderPhone {
        TextView number;
        TextView type;
    }


}
