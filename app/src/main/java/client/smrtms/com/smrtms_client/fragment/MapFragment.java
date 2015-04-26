package client.smrtms.com.smrtms_client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.activity.MapsActivity;


public class MapFragment extends Fragment {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    rootView = null;
        Intent myIntent = new Intent(getActivity(),MapsActivity.class);
        getActivity().startNextMatchingActivity(myIntent);
        rootView = inflater.inflate(R.layout.activity_maps, container, false);

        return rootView;
    }

}
