package client.smrtms.com.smrtms_client.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import client.smrtms.com.smrtms_client.R;


public class EventFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_event, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // This will create the LinearLayout
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);

        // Configuring the width and height of the linear layout.
        LinearLayout.LayoutParams llLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(llLP);

        // Configuring the width and height of the buttons
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        //add friend button
        Button addFriend = new Button(getActivity());
        addFriend.setText("+ Event");
        addFriend.setLayoutParams(lp);
        ll.addView(addFriend);


        ViewGroup viewGroup = (ViewGroup) view;
        viewGroup.addView(ll);
    }
    }
