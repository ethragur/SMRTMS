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
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;

import client.smrtms.com.smrtms_client.controller.Client;
import client.smrtms.com.smrtms_client.controller.JSONParser;
import client.smrtms.com.smrtms_client.controller.LoginUser;
import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.controller.ServerControl;
import client.smrtms.com.smrtms_client.fragment.TabsFragment;
import client.smrtms.com.smrtms_client.tokens.AddEventToken;
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
        if(LoginUser.getInstance() != null) {
            LoginUser.getInstance().setmContext(this.context);
            LoginUser.getInstance().checkPendingFriendReq();
        }
        LoginUser.getInstance().checkPendingFriendReq();


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
        if (id == R.id.refresh) {
            refresh();
        } else if (id == R.id.logout) {
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

    @Override
    public void onResume()
    {
        super.onResume();
        LoginUser.getInstance().checkPendingFriendReq();
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
                        LoginUser.getInstance().logout();
                        Intent myIntent = new Intent(MainScreen.this, LoginActivity.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
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


    //asks for a UserData and Restarts the activity, Timeouts after 10sec
    private void refresh()
    {
        ServerControl.gotNewFriendList = false;
        if(LoginUser.getInstance() != null)
        {
            LoginUser.getInstance().serverTask.getNewFriendList();
        }
        int i = 0;
        while(!ServerControl.gotNewFriendList)
        {
            if(i >= 100)
            {
                Toast.makeText(this, "Something went wrong, Server might be offline", Toast.LENGTH_SHORT).show();
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        Intent refresh = new Intent(this, MainScreen.class);
        this.startActivity(refresh);
        this.finish();
    }


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
                    JSONParser<FriendReqToken> reader = new JSONParser<>();
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
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add Event");
        // Set an EditText view to get user input
        final EditText name = new EditText(this);
        name.setHint("Event Name");


        final EditText descr = new EditText(this);
        descr.setHint("Event Description");

        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);

        lay.addView(name);
        lay.addView(descr);

        alert.setView(lay);


        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (name.getText().toString().matches("") || descr.getText().toString().matches("")) {
                    Toast.makeText(context, "Some Field was left out", Toast.LENGTH_SHORT).show();
                } else {
                    JSONParser<AddEventToken> reader = new JSONParser<>();
                    String eName = name.getText().toString();
                    String eDescr = descr.getText().toString();


                    AddEventToken aET = new AddEventToken(eName, eDescr, 0, LoginUser.getInstance().getLatitude(), LoginUser.getInstance().getLongitude());
                    String addEvent = reader.JSONWriter(aET);

                    Client.getInstance().WriteMsg(addEvent);
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();
    }

   @Override
    protected void onStop() {
       super.onStop();
       if(LoginUser.getInstance() == null)
       {
           Intent intent = new Intent(Intent.ACTION_MAIN);
           intent.addCategory(Intent.CATEGORY_HOME);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(intent);
           finish();
       }
    }
}
