package client.smrtms.com.smrtms_client.fragment;

import android.location.Location;
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


public class GMapFragment extends SupportMapFragment implements GoogleMap.OnMyLocationChangeListener
{
    private Location mLocation;
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
        mLocation = new Location(LoginUser.getInstance().serverTask.getGpsTracker().getLocation());
        setUpMap(mLocation);

    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     * @param location User Location
     */
    private void setUpMap(Location location)
    {
        //clear Map, so that all the new Markers will be drawn
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Your Position"));

        //draw map for each Friend

        for(User friend: LoginUser.getInstance().getFriendList())
        {
            if(friend.isOnline())
            {
                mMap.addMarker(new MarkerOptions().position(new LatLng(friend.getLatitude(), friend.getLongitude())).title(friend.getUsername()))
                        .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.user_map));
                ;
            }
        }

        for(Event event: LoginUser.getInstance().getEventList())
        {

            mMap.addMarker(new MarkerOptions().position(new LatLng(event.getLatitude(), event.getLongitude())).title(event.getName()))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.event));
        }
    }

    private void zoomTo(Location l)
    {
        mapZoom(new LatLng(l.getLatitude(), l.getLongitude()));
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMap(mLocation);
        zoomTo(mLocation);

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
	        LoginUser.getInstance().serverTask.getGpsTracker().getLocation();
            setUpMap(mLocation);
            zoomTo(mLocation);
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


    @Override
    public void onMyLocationChange(Location location)
    {
        mLocation = location;
        setUpMap(mLocation);

    }
}
