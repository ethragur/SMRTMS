package client.smrtms.com.smrtms_client.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

      /*  // This will create the RelativeLayout
        RelativeLayout layout = new RelativeLayout(getActivity());
        layout.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        //add friend button
        Button addFriend = new Button(getActivity());
        addFriend.setText("+ Event");
        RelativeLayout.LayoutParams paramAdd = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        //paramAdd.addRule(RelativeLayout.BELOW,"BUTTON ABOVE");
        addFriend.setLayoutParams(paramAdd);
        layout.addView(addFriend);


        ViewGroup viewGroup = (ViewGroup) view;
        viewGroup.addView(layout);*/
    }
}
