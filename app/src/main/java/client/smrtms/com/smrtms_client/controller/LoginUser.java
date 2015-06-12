package client.smrtms.com.smrtms_client.controller;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.activity.LoginActivity;
import client.smrtms.com.smrtms_client.activity.MainScreen;
import client.smrtms.com.smrtms_client.tokens.FriendReqToken;
import client.smrtms.com.smrtms_client.tokens.LogoutToken;

/**
 * Created by effi on 4/13/15.
 * Singleton of the current active User
 */
public class LoginUser extends User
{

    private static LoginUser inst;
    private Context mContext;
    private Boolean isLogin;
    private int remainingTime = 0;
    private NotificationManager notificationManager;

    public ServerTask serverTask;

    Timer logoutTimer;
    private List<User> friendList;
    private List<Event> eventList;
    private LinkedList<FriendReqToken> pendingFriendReq;

    public LoginUser(String Username, String ID, Context Context)
    {
        super(Username, ID, new Double(0), new Double(0));
        isLogin = false;
        mContext = Context;
        friendList = new ArrayList<User>();
        eventList = new ArrayList<Event>();
        pendingFriendReq = new LinkedList<FriendReqToken>();
        serverTask = new ServerTask(Context);
    }


    //start sending user updates to server


    public static void createInstance(String Username, String ID, Context Context)
    {
        inst = new LoginUser("Username", "0", Context);
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
    // get current GPS Data and send it to the server

    public List<User> getFriendList()
    {
        return friendList;
    }

    public void addFriend(User newFriend) {
        for(User n : friendList)
        {
            if(n.getID().equals(newFriend.getID()))
            {
                n.setLatitude(newFriend.getLatitude());
                n.setLongitude(newFriend.getLongitude());
                n.setOnline(newFriend.isOnline());
                return;
            }
        }
        friendList.add(newFriend);
    }

    public void logout()
    {
        isLogin = false;
        LogoutToken lt = new LogoutToken();
        //Send Server a logout token
        JSONParser<LogoutToken> Writer = new JSONParser<>();
        String toSend = Writer.JSONWriter(lt);
        Client.getInstance().WriteMsg(toSend);
        //cancel update timer
        serverTask.stopUpdates();
        if(logoutTimer!=null) {
            logoutTimer.cancel();
        }
        //clear Instance
        inst = null;
    }

    public Boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    public void setFriendList(List<User> fl)
    {
        this.friendList = fl;
    }

    public void FriendReqIn(String name)
    {
        for(FriendReqToken tmp : pendingFriendReq)
        {
            if(tmp.friendsname.equals(name))
            {
                //already got that one
                return;
            }
        }

        Intent tmp = new Intent(mContext,MainScreen.class);
        PendingIntent mainScr = PendingIntent.getActivity(mContext, 0, tmp, 0);

        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        final Notification notification = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.join)
                .setContentTitle("You got a new friend Request")
                .setContentIntent(mainScr)
                .build();
        notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(1000/*some int*/, notification);

        FriendReqToken frReq = new FriendReqToken(name);
        frReq.id = this.getID();
        frReq.accept = true;

        pendingFriendReq.add(frReq);

        Log.i("NewFriendReq", name + " added to FriendReqQueue");
    }

    public void setmContext(Context c)
    {
        mContext = c;
    }

    public void setRemainingTime(Integer remTim)
    {
        remainingTime = remTim;
        if(remainingTime == 0)
        {
            logout();
        }
        logoutTimer = new Timer();

        logoutTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                remainingTime--;
                if (remainingTime < 0) {
                    Log.i("Logout", "Doing Logout and Stuff");
                    Intent myIntent = new Intent(mContext, LoginActivity.class);
                    Context tmp = mContext;
                    logout();
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    tmp.startActivity(myIntent);
                    logoutTimer.cancel();
                }
            }
        }, 0, 60000);

    }

    public boolean isFriendReqEmpty()
    {
        return pendingFriendReq.isEmpty();
    }

    /*
     * whenever an activity is resumed check all Friendrequests
     */
    public void checkPendingFriendReq()
    {
        if(isLogin)
        {
            if(pendingFriendReq != null && !pendingFriendReq.isEmpty())
            {
                while(!pendingFriendReq.isEmpty())
                {
                    final FriendReqToken x = pendingFriendReq.pop();
                    if (x != null) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

                        alert.setTitle("New Friend Request");
                        alert.setMessage("User: " + x.friendsname + " wants to add you as a Friend");


                        alert.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                JSONParser<FriendReqToken> reader = new JSONParser<>();
                                String friendReq = reader.JSONWriter(x);

                                Client.getInstance().WriteMsg(friendReq);

                                //nothing to do in here ;)
                                //but cancel that Notification
                                notificationManager.cancel(1000);

                                serverTask.getNewFriendList();

                            }
                        });

                        alert.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //nothing to do in here ;)
                                //but cancel that Notification
                                notificationManager.cancel(1000);
                            }
                        });

                        alert.show();
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void addEvent(Event event) {
        eventList.add(event);
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public ServerTask getServerTask() {
        return serverTask;
    }

    public void setServerTask(ServerTask serverTask) {
        this.serverTask = serverTask;
    }
}
