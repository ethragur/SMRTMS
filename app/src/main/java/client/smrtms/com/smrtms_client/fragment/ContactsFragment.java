package client.smrtms.com.smrtms_client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.firebase.androidchat.ChatActivity;

import java.util.ArrayList;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.activity.RegisterActivity;
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

        // This will create the LinearLayout
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);

        // Configuring the width and height of the linear layout.
        LinearLayout.LayoutParams llLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(llLP);

        // Configuring the width and height of the buttons
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for(final User friend: LoginUser.getInstance().getFriendList()) {
            Button temp = new Button(getActivity());
            temp.setText(friend.getUsername());
            temp.setLayoutParams(lp);
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(getActivity(), ChatActivity.class);
                    myIntent.putExtra("UserKey", friend.getID());
                    getActivity().startActivity(myIntent);
                }
            });
            ll.addView(temp);
        }
        //add friend button
        Button addFriend = new Button(getActivity());
        addFriend.setText("+ Friend");
        addFriend.setLayoutParams(lp);
        ll.addView(addFriend);


        ViewGroup viewGroup = (ViewGroup) view;
        viewGroup.addView(ll);
    }
}
