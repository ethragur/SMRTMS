package client.smrtms.com.smrtms_client.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.firebase.androidchat.ChatActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.controller.sendCoordinates;
import client.smrtms.com.smrtms_client.controller.LoginUser;
import client.smrtms.com.smrtms_client.controller.User;


public class ContactsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_contacts, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // This will create the LinearLayout Vertical
        RelativeLayout layout = new RelativeLayout(getActivity());
        layout.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        // Getting size of the display
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        final float width = metrics.widthPixels;



        // create Buttons
        ArrayList<Button> fButton = new ArrayList<>();
        ArrayList<Button> mButton = new ArrayList<>();
        int id = 1;
        for(final User friend: LoginUser.getInstance().getFriendList()) {
            // Friend Button
            Button temp = new Button(getActivity());
            temp.setText(friend.getUsername());
            temp.setId(id++);
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(getActivity(), ChatActivity.class);
                    myIntent.putExtra("UserKey", friend.getID());
                    getActivity().startActivity(myIntent);
                }
            });
            fButton.add(temp);

            // Map Button
            temp = new Button(getActivity());
            temp.setText("Map");
            temp.setId(id++);
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // send message
                    sendCoordinates.setCoordinates(new LatLng(friend.getLatitude(), friend.getLongitude()));
                    // switch t Map fragment
                    TabsFragment tf = (TabsFragment) getParentFragment();
                    tf.setTabPostion(0);


                }
            });
            mButton.add(temp);
        }

        boolean firstButton = true; //button on the top
        // set layout to the buttons
        for(int i = 0; i < fButton.size(); i++) {
            // Configuring the width and height of the Friend buttons and Map buttons
            RelativeLayout.LayoutParams paramFriend = new RelativeLayout.LayoutParams((int) (width - width/3),RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams paramMap = new RelativeLayout.LayoutParams((int) (width/3),RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramFriend.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
            paramMap.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);

            if (firstButton) {
                fButton.get(i).setLayoutParams(paramFriend);
                mButton.get(i).setLayoutParams(paramMap);
                firstButton = false;
            } else {
                paramFriend.addRule(RelativeLayout.BELOW,fButton.get(i-1).getId());
                fButton.get(i).setLayoutParams(paramFriend);
                paramMap.addRule(RelativeLayout.BELOW,mButton.get(i-1).getId());
                mButton.get(i).setLayoutParams(paramMap);
            }

            layout.addView(fButton.get(i));
            layout.addView(mButton.get(i));
        }
        //add friend button
        Button addFriend = new Button(getActivity());
        addFriend.setText("+ Friend");
        RelativeLayout.LayoutParams paramAdd = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramAdd.addRule(RelativeLayout.BELOW,fButton.get(fButton.size()-1).getId());
        addFriend.setLayoutParams(paramAdd);
        layout.addView(addFriend);


        ViewGroup viewGroup = (ViewGroup) view;
        viewGroup.addView(layout);


    }
}
