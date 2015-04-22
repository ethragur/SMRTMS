package client.smrtms.com.smrtms_client;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.firebase.androidchat.MainActivity;


public class MainScreen extends ActionBarActivity {
    public OnSwipeTouchListener onSwipeTouchListener;
    LoginUser activeUser;
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


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
            Intent myIntent = new Intent(this, MainActivity.class);
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
