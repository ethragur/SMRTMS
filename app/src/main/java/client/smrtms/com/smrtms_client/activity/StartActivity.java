package client.smrtms.com.smrtms_client.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.TimePicker;
import android.text.format.Time;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.controller.Client;
import client.smrtms.com.smrtms_client.controller.JSONParser;
import client.smrtms.com.smrtms_client.controller.LoginUser;
import client.smrtms.com.smrtms_client.tokens.FriendListToken;

public class StartActivity extends Activity{
    final Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_one_button);

        LoginUser.getInstance().setmContext(this.context);
        LoginUser.getInstance().serverTask.getNewFriendList();
	    buildAlertMessageNoGps();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    public void changeToMainScreen(View view)
    {
        TimePicker t = (TimePicker) findViewById(R.id.timePicker);
        Integer Hour;
        Integer Min;
        Integer remainingTime = 120;

        Hour = t.getCurrentHour();
        Min = t.getCurrentMinute();
        Time now = new Time();
        now.setToNow();
        remainingTime = ((Hour - now.hour) * 60) + (Min - now.minute);
        if (remainingTime < 0) {
            remainingTime += 12 * 60;
        }
        if(remainingTime == 0)
        {
            remainingTime = 120;
        }

        LoginUser.getInstance().setRemainingTime(remainingTime);
        LoginUser.getInstance().setIsLogin(true);
        LoginUser.getInstance().serverTask.startUpdates();
        Intent myIntent = new Intent(StartActivity.this, MainScreen.class);
        StartActivity.this.startActivity(myIntent);
        finish();
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
                        Intent myIntent = new Intent(StartActivity.this, LoginActivity.class);
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
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            logoutDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void buildAlertMessageNoGps()
    {
        if(!LoginUser.getInstance().serverTask.getGpsTracker().canGetLocation()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }

    }

}
