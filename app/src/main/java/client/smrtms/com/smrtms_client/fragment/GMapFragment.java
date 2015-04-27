package client.smrtms.com.smrtms_client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.activity.MainScreen;
import client.smrtms.com.smrtms_client.activity.MapsActivity;
import client.smrtms.com.smrtms_client.controller.LoginUser;
import client.smrtms.com.smrtms_client.controller.User;


public class GMapFragment extends SupportMapFragment
{

    private View rootView;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
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
        mMap.addMarker(new MarkerOptions().position(new LatLng(LoginUser.getInstance().getLatitude(), LoginUser.getInstance().getLongitude())).title("Your Position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(LoginUser.getInstance().getLatitude(), LoginUser.getInstance().getLongitude())));
        //draw map for each Friend
        for(User friend: LoginUser.getInstance().getFriendList())
        {
            mMap.addMarker(new MarkerOptions().position(new LatLng(friend.getLatitude(), friend.getLongitude())).title(friend.getUsername()));
        }
    }

}
