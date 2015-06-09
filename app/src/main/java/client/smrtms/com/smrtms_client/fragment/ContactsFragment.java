package client.smrtms.com.smrtms_client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.Toast;


import com.firebase.androidchat.ChatActivity;
import com.google.android.gms.maps.model.LatLng;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;

import java.util.ArrayList;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.controller.UserListAdapter;
import client.smrtms.com.smrtms_client.controller.sendCoordinates;
import client.smrtms.com.smrtms_client.controller.LoginUser;
import client.smrtms.com.smrtms_client.controller.User;


public class ContactsFragment extends Fragment {

    User selectedFriend;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_contacts, container, false);
        return rootView;
    }
    ArrayList<User> users;

    @Override
    public void onResume()
    {
        users = new ArrayList<>();
        for(User friend: LoginUser.getInstance().getFriendList())
        {
            users.add(friend);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        if(LoginUser.getInstance() != null)
        {
            LoginUser.getInstance().checkPendingFriendReq();
        }
        // Construct the data source
        users = new ArrayList<>();

        for(User friend: LoginUser.getInstance().getFriendList()) {
            users.add(friend);
        }
        // Create the adapter to convert the array to views
        UserListAdapter adapter = new UserListAdapter(getActivity(), users);
        // Attach the adapter to a ListView
        ListView listView = (ListView) getActivity().findViewById(R.id.listFriend);
        listView.setAdapter(adapter);


        ActionItem chat     = new ActionItem(1, "chat", getResources().getDrawable(R.drawable.chat));
        ActionItem map     = new ActionItem(2, "map", getResources().getDrawable(R.drawable.globe));
        ActionItem delete     = new ActionItem(3, "delete", getResources().getDrawable(R.drawable.delete));

        //create QuickAction. Use QuickAction.VERTICAL or QuickAction.HORIZONTAL param to define layout
        //orientation
        final QuickAction quickAction = new QuickAction(getActivity(), QuickAction.HORIZONTAL);

        //add action items into QuickAction
        quickAction.addActionItem(chat);
        quickAction.addActionItem(map);
        quickAction.addActionItem(delete);

        //Set listener for action item clicked
        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(QuickAction source, int pos, int actionId) {
                //here we can filter which action item was clicked with pos or actionId parameter
                ActionItem actionItem = quickAction.getActionItem(pos);

                /* chat is selected */
                if (actionItem.getActionId() == 1) {
                    Intent myIntent = new Intent(getActivity(), ChatActivity.class);
                    myIntent.putExtra("UserKey", selectedFriend.getID());
                    getActivity().startActivity(myIntent);

                /* map is selected */
                } else if (actionItem.getActionId() == 2) {
                    // send message
                    sendCoordinates.setCoordinates(new LatLng(selectedFriend.getLatitude(), selectedFriend.getLongitude()));
                    // switch t Map fragment
                    TabsFragment tf = (TabsFragment) getParentFragment();
                    tf.setTabPostion(0);
                /* delete is selected */
                } else if (actionItem.getActionId() == 3) {
                    //TODO delete friend
                }

            }
        });





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                quickAction.show(view);
                selectedFriend = (User) parent.getItemAtPosition(position);
                Toast.makeText(getActivity().getApplicationContext(), selectedFriend.getUsername(), Toast.LENGTH_SHORT).show();


            }

        });







/*
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
                paramMap.addRule(RelativeLayout.BELOW,mButton.get(i-1).getId());f
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
*/

    }


}
