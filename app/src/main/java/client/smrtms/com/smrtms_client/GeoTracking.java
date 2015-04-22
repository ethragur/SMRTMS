package client.smrtms.com.smrtms_client;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by effi on 4/4/15.
 *
 * Class used to track GPS position of a Person and also returns the current address
 */
public class GeoTracking
{
    public static void GetLocation()
    {
        LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

}
