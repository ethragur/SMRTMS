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
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.activity.LoginActivity;
import client.smrtms.com.smrtms_client.activity.MainScreen;
import client.smrtms.com.smrtms_client.tokens.FriendReqToken;
import client.smrtms.com.smrtms_client.tokens.LogoutToken;
import client.smrtms.com.smrtms_client.tokens.UserUpdateToken;

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
    private Boolean isLogin;
    private int remainingTime = 0;


    Timer timer;
    Timer logoutTimer;
    private List<User> friendList;
    private Queue<FriendReqToken> pendingFriendReq;
    public LoginUser(String Username, String ID, Double Latitude, Double Longitude, Context Context)
    {
        super(Username, ID, new Double(0), new Double(0));
        isLogin = new Boolean(false);
        mContext = Context;
        gpsTracker = new GPSTracker(mContext);
        friendList = new ArrayList<User>();
        pendingFriendReq = new LinkedList<FriendReqToken>();
    }


    //start sending user updates to server
    public void startUpdates()
    {
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getNewCoordinates();
            }
        }, 0, 10000);
    }

    public static void createInstance(String Username, String ID, Context Context)
    {
        inst = new LoginUser("Username", "0",new Double(0), new Double(0), Context);
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
    private synchronized void getNewCoordinates()
    {
        if(isLogin)
        {
            if (gpsTracker.canGetLocation()) {
                this.setLatitude(gpsTracker.getLatitude());
                this.setLongitude(gpsTracker.getLongitude());

                UserUpdateToken userUpdateToken = new UserUpdateToken(this.getLatitude(), this.getLongitude());
                JSONReader<UserUpdateToken> Writer = new JSONReader<>();
                String toSend = Writer.JSONWriter(userUpdateToken);
                Log.i("SendMsg", userUpdateToken.id);
                Client.getInstance().WriteMsg(toSend);

            } else {
                //ToDo: throw some exception when it can't get GPS Data
                Log.d("Error", "Can't get GPS data");
            }
        }
    }

    public List<User> getFriendList()
    {
        return friendList;
    }

    public void addFriend(User newFriend) {
        friendList.add(newFriend);
    }

    public void logout()
    {
        isLogin = false;
        LogoutToken lt = new LogoutToken();
        //Send Server a logout token
        JSONReader<LogoutToken> Writer = new JSONReader<>();
        String toSend = Writer.JSONWriter(lt);
        Client.getInstance().WriteMsg(toSend);
        //cancel update timer
        inst.timer.cancel();
        logoutTimer.cancel();
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
        Intent tmp = new Intent(mContext,MainScreen.class);
        PendingIntent mainScr = PendingIntent.getActivity(mContext, 0, tmp, 0);

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        final Notification notification = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("You got a new friend Request"/*your notification title*/)
                .setContentIntent(mainScr)
                .build();
        notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(1000/*some int*/, notification);

        FriendReqToken frReq = new FriendReqToken(name);
        frReq.id = this.getID();
        frReq.accept = true;

        pendingFriendReq.add(frReq);

        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

        alert.setTitle("New Friend Request");
        alert.setMessage("User: " + name + " wants to add you as a Friend");


        alert.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                JSONReader<FriendReqToken> reader = new JSONReader<>();
                String friendReq = reader.JSONWriter(pendingFriendReq.poll());

                Client.getInstance().WriteMsg(friendReq);

            }
        });

        alert.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();

    }

    public void setmContext(Context c)
    {
        mContext = c;
    }

    public void setRemainingTime(Integer remTim)
    {
        remainingTime = remTim;

        logoutTimer = new Timer();

        logoutTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run()
            {
                remainingTime--;
                if(remainingTime < 0)
                {
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

    /*
     * whenever an activity is resumed check all friendrequests
     */
    public void checkPendingFriendReq()
    {
        if(isLogin) {
            if (pendingFriendReq != null) {
                for (FriendReqToken x : pendingFriendReq) {
                    if (x != null) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

                        alert.setTitle("New Friend Request");
                        alert.setMessage("User: " + x.friendsname + " wants to add you as a Friend");


                        alert.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                JSONReader<FriendReqToken> reader = new JSONReader<>();
                                String friendReq = reader.JSONWriter(pendingFriendReq.poll());

                                Client.getInstance().WriteMsg(friendReq);

                            }
                        });

                        alert.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        });
                    } else {
                        return;
                    }
                }
            }
        }
    }


}
