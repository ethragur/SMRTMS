package client.smrtms.com.smrtms_client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.androidchat.ChatActivity;
import com.google.android.gms.maps.model.LatLng;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.activity.StartActivity;
import client.smrtms.com.smrtms_client.controller.Client;
import client.smrtms.com.smrtms_client.controller.Event;
import client.smrtms.com.smrtms_client.controller.EventListAdapter;
import client.smrtms.com.smrtms_client.controller.JSONParser;
import client.smrtms.com.smrtms_client.controller.LoginUser;
import client.smrtms.com.smrtms_client.controller.ServerControl;
import client.smrtms.com.smrtms_client.controller.sendCoordinates;
import client.smrtms.com.smrtms_client.tokens.AddEventToken;
import client.smrtms.com.smrtms_client.tokens.JoinEventToken;


public class EventFragment extends Fragment {

    private Event selectedEvent;
    ArrayList<Event> events;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_event, container, false);
        listView = (ListView) getActivity().findViewById(R.id.listEvent);

        return rootView;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser )
        {
	        LoginUser.getInstance().serverTask.getGpsTracker().getLocation();
            // Construct the data source
            if(ServerControl.gotNewEventList)
            {
                events = new ArrayList<>();
                setUpEventList();
                ServerControl.gotNewEventList = false;
            }

        }
        else
        {
            // fragment is no longer visible
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        listView = (ListView) getActivity().findViewById(R.id.listEvent);
        super.onViewCreated(view, savedInstanceState);
        if(LoginUser.getInstance() != null)
        {
            LoginUser.getInstance().checkPendingFriendReq();
        }

        // Construct the data source;
        events = new ArrayList<>();
        setUpEventList();




    }

    @Override
    public void onResume()
    {
        super.onResume();
        listView = (ListView) getActivity().findViewById(R.id.listEvent);
        events = new ArrayList<>();
        setUpEventList();
        ServerControl.gotNewEventList = false;
    }



    private void setUpEventList()
    {
        for(Event event: LoginUser.getInstance().getEventList())
        {
            event.setDistance(Math.round(LoginUser.getInstance().getServerTask().getGpsTracker().calculateDistance(event.getLatitude(), event.getLongitude()) * 1000) / 1000.0);
            events.add(event);
        }

        Collections.sort(events, new Comparator<Event>() {
            public int compare(Event o1, Event o2) {
                if (o1.getDistance() == o2.getDistance())
                    return 0;
                return o1.getDistance() < o2.getDistance() ? -1 : 1;
            }
        });

        // Create the adapter to convert the array to views
        EventListAdapter adapter = new EventListAdapter(getActivity(), events);
        // Attach the adapter to a ListView

        listView.setAdapter(adapter);


        ActionItem join     = new ActionItem(1, "join", getResources().getDrawable(R.drawable.join));
        ActionItem leave     = new ActionItem(2, "Chat", getResources().getDrawable(R.drawable.chat));
        ActionItem map     = new ActionItem(3, "map", getResources().getDrawable(R.drawable.globe));

        //create QuickAction. Use QuickAction.VERTICAL or QuickAction.HORIZONTAL param to define layout
        //orientation
        final QuickAction quickAction = new QuickAction(getActivity(), QuickAction.HORIZONTAL);

        //add action items into QuickAction
        quickAction.addActionItem(join);
        quickAction.addActionItem(leave);
        quickAction.addActionItem(map);

        //Set listener for action item clicked
        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(QuickAction source, int pos, int actionId) {
                //here we can filter which action item was clicked with pos or actionId parameter
                ActionItem actionItem = quickAction.getActionItem(pos);

                /* join is selected */
                if (actionItem.getActionId() == 1)
                {
                    JSONParser<JoinEventToken> reader = new JSONParser<>();
                    JoinEventToken jet = new JoinEventToken(selectedEvent.getName());

                    String addEvent = reader.JSONWriter(jet);

                    Client.getInstance().WriteMsg(addEvent);


                /* leave is selected */
                } else if (actionItem.getActionId() == 2) {
                    //TODO leave event
	                Intent myIntent = new Intent(getActivity(), ChatActivity.class);
	                myIntent.putExtra("UserKey", "Event");
	                myIntent.putExtra("EventName", selectedEvent.getName());
	                getActivity().startActivity(myIntent);

                /* map is selected */
                } else if (actionItem.getActionId() == 3) {
                    // send message
                    sendCoordinates.setCoordinates(new LatLng(selectedEvent.getLatitude(), selectedEvent.getLongitude()));
                    // switch t Map fragment
                    TabsFragment tf = (TabsFragment) getParentFragment();
                    tf.setTabPostion(0);

                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                quickAction.show(view);
                selectedEvent = (Event) parent.getItemAtPosition(position);
                Toast.makeText(getActivity().getApplicationContext(), selectedEvent.getName(), Toast.LENGTH_SHORT).show();


            }

        });

    }


}
