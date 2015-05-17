package client.smrtms.com.smrtms_client.controller;

import com.google.android.gms.maps.model.LatLng;

/*
    send coordinates between ContactsFragmentand GmapFragment
 */
public class sendCoordinates {


    private static sendCoordinates instance;
    private static LatLng coordinates;

  //  private sendCoordinates() {}

 /*   public static sendCoordinates getInstance() {
        if (instance == null) {
            instance = new sendCoordinates();
        }
        return instance;
    }*/

    public static void setCoordinates(LatLng ll) {
        coordinates = ll;
    }

    public static LatLng getCoordinates() {
        LatLng temp = coordinates;
        coordinates = null;
        return temp;
    }

}
