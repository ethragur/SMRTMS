package client.smrtms.com.smrtms_client.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.androidchat.ChatActivity;

import client.smrtms.com.smrtms_client.GPSTracker;
import client.smrtms.com.smrtms_client.LoginUser;
import client.smrtms.com.smrtms_client.OnSwipeTouchListener;
import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.fragment.TabsFragment;


public class MainScreen extends ActionBarActivity {
    public OnSwipeTouchListener onSwipeTouchListener;
    LoginUser activeUser;
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        if (savedInstanceState == null) {


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            TabsFragment fragment = new TabsFragment();
            transaction.replace(R.id.content_fragment, fragment);
            transaction.commit();
        }

        gps = new GPSTracker(MainScreen.this);

        // Check if GPS enabled
        if(gps.canGetLocation()) {

            LoginUser.getInstance().latitude = gps.getLatitude();
            LoginUser.getInstance().longitude = gps.getLongitude();



        }



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

        //noinspection SimplifiableIfStatement
        if (id == R.id.maps) {
            Intent myIntent = new Intent(MainScreen.this,MapsActivity.class);
            MainScreen.this.startActivity(myIntent);
            return true;
        }
        if (id == R.id.chat) {
            Intent myIntent = new Intent(this, ChatActivity.class);
            startActivity(myIntent);
            return true;
        }
        if (id == R.id.settings) {
            Intent myIntent = new Intent(this, SettingsActivity.class);
            startActivity(myIntent);
            return true;
        }
        if (id == R.id.logout) {
            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
