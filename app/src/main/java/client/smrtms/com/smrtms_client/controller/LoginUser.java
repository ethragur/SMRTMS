package client.smrtms.com.smrtms_client.controller;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by effi on 4/13/15.
 * Singleton of the current active User
 * When instance is created User gets new GPS location every 10 sec
 */
public class LoginUser extends User
{

    private static LoginUser inst;
    private GPSTracker gpsTracker;
    private Context mContext;

    private List<User> friendList;

    public LoginUser(String Username, String ID, Double Latitude, Double Longitude, Context Context)
    {
        super(Username, ID, 47.268409, 11.3932439);
        mContext = Context;
        gpsTracker = new GPSTracker(Context);
        friendList = new ArrayList<User>();
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getNewCoordinates();
            }
        }, 0, 10000);


    }

    public static void createInstance(String Username, String ID, Context Context)
    {
        inst = new LoginUser("TestUser", "0001", 47.268409, 11.3932439, Context);
    }

    public static LoginUser getInstance()
    {
        if(inst == null)
        {
            //ToDo: throw exception when Singleton not Initialized
            //throw new NullPointerException();
        }

        return inst;
    }
    // get current GPS Data
    private synchronized void getNewCoordinates()
    {
        if (gpsTracker.canGetLocation()) {
            this.setLatitude(gpsTracker.getLatitude());
            this.setLongitude(gpsTracker.getLongitude());

        }
        else
        {
            //ToDo: throw some exception when it can't get GPS Data
            Log.d("Error", "Can't get GPS data");
        }
    }

    public List<User> getFriendList()
    {
        return friendList;
    }

    public void addFriend(User newFriend) {
        friendList.add(newFriend);
    }

}
