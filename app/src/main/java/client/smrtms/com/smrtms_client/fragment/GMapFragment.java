package client.smrtms.com.smrtms_client.fragment;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.controller.Event;
import client.smrtms.com.smrtms_client.controller.sendCoordinates;
import client.smrtms.com.smrtms_client.controller.LoginUser;
import client.smrtms.com.smrtms_client.controller.User;


public class GMapFragment extends SupportMapFragment
{

    private View rootView;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if(LoginUser.getInstance() != null)
        {
            LoginUser.getInstance().checkPendingFriendReq();
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

    rootView = null;
        super.onActivityCreated(savedInstanceState);

        mMap = getMap();

        setUpMap();

    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap()
    {
        //clear Map, so that all the new Markers will be drawn
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(LoginUser.getInstance().getLatitude(), LoginUser.getInstance().getLongitude())).title("Your Position"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(LoginUser.getInstance().getLatitude(), LoginUser.getInstance().getLongitude())));
        mapZoom(new LatLng(LoginUser.getInstance().getLatitude(), LoginUser.getInstance().getLongitude()));
        //draw map for each Friend

        for(User friend: LoginUser.getInstance().getFriendList())
        {
            mMap.addMarker(new MarkerOptions().position(new LatLng(friend.getLatitude(), friend.getLongitude())).title(friend.getUsername()))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.user));;

        }

        for(Event event: LoginUser.getInstance().getEventList())
        {

            mMap.addMarker(new MarkerOptions().position(new LatLng(event.getLatitude(), event.getLongitude())).title(event.getName()))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.andorid));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMap();

    }

    private void mapZoom(LatLng coordinate) {
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 5);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        mMap.moveCamera(location);
        mMap.animateCamera(zoom);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mMap != null) {
            setUpMap();
            LatLng coordinate;
            if ((coordinate = sendCoordinates.getCoordinates()) != null) {
                mapZoom(coordinate);
            } else {
                mapZoom(new LatLng(LoginUser.getInstance().getLatitude(), LoginUser.getInstance().getLongitude()));
            }
            // load data here
        }else{
            // fragment is no longer visible
        }
    }

}
