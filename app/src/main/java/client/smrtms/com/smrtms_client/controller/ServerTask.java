package client.smrtms.com.smrtms_client.controller;

import android.content.Context;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import client.smrtms.com.smrtms_client.tokens.FriendListToken;
import client.smrtms.com.smrtms_client.tokens.UserUpdateToken;

/**
 * Created by effi on 6/9/15.
 * This class sends periodic Tokens to the Server
 */
public class ServerTask
{
    private GPSTracker gpsTracker;

    public Timer userUpdateTimer;
    public Timer friendListUpdateTimer;

    public ServerTask(Context context)
    {
        gpsTracker = new GPSTracker(context);
    }

    public void getNewFriendList()
    {
        FriendListToken reqFL = new FriendListToken();
        JSONParser<FriendListToken> FLjson = new JSONParser<>();
        String request = FLjson.JSONWriter(reqFL);
        Client.getInstance().WriteMsg(request);
    }

    public void userUpdates()
    {
            UserUpdateToken userUpdateToken = new UserUpdateToken(LoginUser.getInstance().getLatitude(), LoginUser.getInstance().getLongitude());
            JSONParser<UserUpdateToken> Writer = new JSONParser<>();
            String toSend = Writer.JSONWriter(userUpdateToken);
            Client.getInstance().WriteMsg(toSend);
    }

    public void refreshUserCoord()
    {
        if (gpsTracker.canGetLocation())
        {
            LoginUser.getInstance().setLongitude(gpsTracker.getLongitude());
            LoginUser.getInstance().setLatitude(gpsTracker.getLatitude());
        }
        else
        {
            //ToDo: throw some exception when it can't get GPS Data
            Log.d("Error", "Can't get GPS data");
        }
    }

    public void startUpdates()
    {
        userUpdateTimer = new Timer();

        userUpdateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refreshUserCoord();
                userUpdates();
            }
        }, 0, 10000);

        friendListUpdateTimer = new Timer();

        friendListUpdateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getNewFriendList();
            }
        }, 4000, 60000);
    }

    public void stopUpdates()
    {
        userUpdateTimer.cancel();
        friendListUpdateTimer.cancel();
    }

    public GPSTracker getGpsTracker() {
        return gpsTracker;
    }

    public void setGpsTracker(GPSTracker gpsTracker) {
        this.gpsTracker = gpsTracker;
    }
}
