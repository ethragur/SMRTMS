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
import java.util.Date;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.controller.Event;
import client.smrtms.com.smrtms_client.controller.EventListAdapter;
import client.smrtms.com.smrtms_client.controller.LoginUser;
import client.smrtms.com.smrtms_client.controller.User;
import client.smrtms.com.smrtms_client.controller.sendCoordinates;


public class EventFragment extends Fragment {

    private Event selectedEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_event, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        if(LoginUser.getInstance() != null)
        {
            LoginUser.getInstance().checkPendingFriendReq();
        }

        // Construct the data source
        final ArrayList<Event> events = new ArrayList<>();

        for(Event event: LoginUser.getInstance().getEventList()) {
            events.add(event);
        }

        // Create the adapter to convert the array to views
        EventListAdapter adapter = new EventListAdapter(getActivity(), events);
        // Attach the adapter to a ListView
        ListView listView = (ListView) getActivity().findViewById(R.id.listEvent);
        listView.setAdapter(adapter);


        ActionItem join     = new ActionItem(1, "join", getResources().getDrawable(R.drawable.join));
        ActionItem leave     = new ActionItem(2, "leave", getResources().getDrawable(R.drawable.leave));
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


                /* leave is selected */
                } else if (actionItem.getActionId() == 2) {
                    //TODO leave event

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
