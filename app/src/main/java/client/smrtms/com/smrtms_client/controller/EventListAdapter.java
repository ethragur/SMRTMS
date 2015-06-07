package client.smrtms.com.smrtms_client.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import client.smrtms.com.smrtms_client.R;


public class EventListAdapter extends ArrayAdapter<Event>{

    private static class ViewHolder {
        TextView name;
        TextView description;
        TextView coordinates;
    }

    private Context context;
    private ArrayList<Event> data;

    public EventListAdapter(Context context, ArrayList<Event> data) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Event item = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_event, parent,false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.coordinates = (TextView) convertView.findViewById(R.id.coordinates);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DateFormat df;

        viewHolder.name.setText(item.getName());
        viewHolder.description.setText(item.getDescription());
        viewHolder.coordinates.setText(item.getLongitude() + " " + item.getLatitude());

        return convertView;
    }
}
