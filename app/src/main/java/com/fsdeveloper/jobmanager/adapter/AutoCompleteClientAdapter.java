package com.fsdeveloper.jobmanager.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.Client;
import com.fsdeveloper.jobmanager.tool.MyStringsTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter the autocomplete names clients.
 *
 * @author Created by Douglas Rafael on 14/05/2016.
 * @version 1.0
 */
public class AutoCompleteClientAdapter extends ArrayAdapter<Client> implements Filterable {
    private Context context;
    private int resource, textViewResourceId;
    private List<Client> items, tempItems, suggestions;
    private MyStringsTool stringTool;

    public AutoCompleteClientAdapter(Context context, int resource, int textViewResourceId, List<Client> items) {
        super(context, resource, textViewResourceId, items);

        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();

        stringTool = new MyStringsTool();
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public Client getItem(int position) {
        if (suggestions != null && suggestions.size() > 0)
            return suggestions.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        View convertView = view;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_auto_complete, null);

            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.text_item);
            holder.email = (TextView) convertView.findViewById(R.id.text_sub_item);
            holder.image = (ImageView) convertView.findViewById(R.id.image_circle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Client client = suggestions.get(position);
        if (client != null) {
            holder.title.setText(client.getName());
            holder.email.setText(client.getEmail());

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

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Client) resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String nameClient, emailClient;
            String termSearch = MyStringsTool.removeAccents(String.valueOf(charSequence)).toLowerCase();

            if (charSequence != null) {
                suggestions.clear();
                for (Client client : tempItems) {
                    nameClient = MyStringsTool.removeAccents(client.getName()).toLowerCase();
                    emailClient = client.getEmail().toLowerCase();

                    if (nameClient.indexOf(termSearch) > -1 || emailClient.indexOf(termSearch) > -1) {
                        suggestions.add(client);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            List<Client> filterList = (ArrayList<Client>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (Client client: filterList) {
                    add(client);
                    notifyDataSetChanged();
                }
            }
        }
    };

    /**
     * Optimizes the process of getView. In order not to create instance if it has already been created.
     */
    static class ViewHolder {
        TextView title;
        TextView email;
        ImageView image;
    }
}
