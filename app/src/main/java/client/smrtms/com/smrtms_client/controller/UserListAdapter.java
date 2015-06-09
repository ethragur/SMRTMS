package client.smrtms.com.smrtms_client.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import client.smrtms.com.smrtms_client.R;


public class UserListAdapter extends ArrayAdapter<User>{

    private static class ViewHolder {
        TextView name;
        TextView distance;
    }

    private Context context;
    private ArrayList<User> data;

    public UserListAdapter(Context context, ArrayList<User> data) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        User item = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_user, parent,false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.distance = (TextView) convertView.findViewById(R.id.distance);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(item.getUsername());
        viewHolder.distance.setText("Distance: " + Math.round(LoginUser.getInstance().getServerTask().getGpsTracker().calculateDistance(item.getLatitude(), item.getLongitude()) * 1000) / 1000.0 + " km") ;
        return convertView;
    }
}
