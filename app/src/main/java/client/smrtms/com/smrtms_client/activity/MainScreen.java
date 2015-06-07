package client.smrtms.com.smrtms_client.activity;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.androidchat.ChatActivity;

import client.smrtms.com.smrtms_client.controller.Client;
import client.smrtms.com.smrtms_client.controller.GPSTracker;
import client.smrtms.com.smrtms_client.controller.JSONReader;
import client.smrtms.com.smrtms_client.controller.LoginUser;
import client.smrtms.com.smrtms_client.View.OnSwipeTouchListener;
import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.fragment.TabsFragment;
import client.smrtms.com.smrtms_client.tokens.FriendReqToken;


public class MainScreen extends ActionBarActivity {
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            TabsFragment fragment = new TabsFragment();
            transaction.replace(R.id.content_fragment, fragment);
            transaction.commit();
        }

        LoginUser.getInstance().setmContext(this.context);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent myIntent = new Intent(this, SettingsActivity.class);
            startActivity(myIntent);
            return true;
        }
        else if (id == R.id.logout) {
            logoutDialog();
            return true;
        } else if (id == R.id.addFriend) {
            addFriend(null);
            return true;
        } else if (id == R.id.addEvent) {
            addEvent(null);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void logoutDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title
        //alertDialogBuilder.setTitle("Your Title");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you really want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity and open LoginActivity
                        Intent myIntent = new Intent(MainScreen.this, LoginActivity.class);
                        LoginUser.getInstance().logout();
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(myIntent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            logoutDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    /*public void changeToMaps()
    {
        Intent myIntent = new Intent(MainScreen.this,MapsActivity.class);
        MainScreen.this.startActivity(myIntent);
    }

    public void changeToChat(View view)
    {
        Intent myIntent = new Intent(MainScreen.this, MainActivity.class);
        MainScreen.this.startActivity(myIntent);
    }*/

    public void addFriend(View view)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add Friend");
        alert.setMessage("Please Enter Username:");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(input.getText() != null)
                {
                    JSONReader reader = new JSONReader();
                    FriendReqToken frToken = new FriendReqToken(input.getText().toString());
                    String friendReq = reader.JSONWriter(frToken);

                    Client.getInstance().WriteMsg(friendReq);

                }
                else
                {
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();


    }

        //used right now for testing purposes
    public void addEvent(View view)
    {
        //TODO sth else then interface testing
        Intent tmp = new Intent(context,MainScreen.class);
        PendingIntent mainScr = PendingIntent.getActivity(context, 0, tmp, 0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        final Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("You got a new friend Request"/*your notification title*/)
                .setContentText("test" + " wants to be your friend"/*notifcation message*/)
                .setContentIntent(mainScr)
                .build();
        notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(1000/*some int*/, notification);


        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle("New Friend Request");
        alert.setMessage("User:  wants to add you as a Friend");


        alert.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
    }
}
