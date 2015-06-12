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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.activity.StartActivity;
import client.smrtms.com.smrtms_client.controller.Event;
import client.smrtms.com.smrtms_client.controller.ServerControl;
import client.smrtms.com.smrtms_client.controller.UserListAdapter;
import client.smrtms.com.smrtms_client.controller.sendCoordinates;
import client.smrtms.com.smrtms_client.controller.LoginUser;
import client.smrtms.com.smrtms_client.controller.User;


public class ContactsFragment extends Fragment {

    User selectedFriend;
    ListView listView;
    ArrayList<User> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_contacts, container, false);
        listView =  (ListView) getActivity().findViewById(R.id.listFriend);
        return rootView;
    }


    @Override
    public void onResume()
    {
        super.onResume();
        listView =  (ListView) getActivity().findViewById(R.id.listFriend);
        users = new ArrayList<>();
        setUpFriendList();
        ServerControl.gotNewFriendList = false;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser )
        {
	        LoginUser.getInstance().serverTask.getGpsTracker().getLocation();
            if(ServerControl.gotNewFriendList)
            {
                users = new ArrayList<>();
                setUpFriendList();
                ServerControl.gotNewFriendList = false;
            }
                // load data here
        }
        else
        {
            // fragment is no longer visible
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        listView =  (ListView) getActivity().findViewById(R.id.listFriend);
        if(LoginUser.getInstance() != null)
        {
            LoginUser.getInstance().checkPendingFriendReq();
        }
        // Construct the data source
        users = new ArrayList<>();
        setUpFriendList();
        ServerControl.gotNewFriendList = false;


    }

    private void setUpFriendList()
    {
        for(User friend: LoginUser.getInstance().getFriendList()) {
            friend.setDistance(Math.round(LoginUser.getInstance().getServerTask().getGpsTracker().calculateDistance(friend.getLatitude(), friend.getLongitude()) * 1000) / 1000.0);
            users.add(friend);
        }

        Collections.sort(users, new Comparator<User>() {
            public int compare(User o1, User o2) {
                if (o1.getDistance() == o2.getDistance())
                    return 0;
                return o1.getDistance() < o2.getDistance() ? -1 : 1;
            }
        });


        // Create the adapter to convert the array to views
        UserListAdapter adapter = new UserListAdapter(getActivity(), users);
        // Attach the adapter to a ListView

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
    }



}
