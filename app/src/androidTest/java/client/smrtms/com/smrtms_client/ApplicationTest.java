package client.smrtms.com.smrtms_client;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MotionEvent;
import android.view.View;

import client.smrtms.com.smrtms_client.activity.MainScreen;
//import org.junit.*;

import com.firebase.androidchat.ChatActivity;


public class ApplicationTest extends ActivityInstrumentationTestCase2<MainScreen> {


    public ApplicationTest()
    {
        super(MainScreen.class);
    }



    public void testActivitySwitch()
    {
        Activity ac = getActivity();
        Intent myIntent = new Intent(ac, ChatActivity.class);

        assertNotNull(myIntent);


        ac.startActivity(myIntent);
    }

    public void testSwipeActivies()
    {
        Activity ac = getActivity();
        OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(ac) {
            public void onSwipeTop() {

            }
            public void onSwipeRight() {

            }
            public void onSwipeLeft() {

            }
            public void onSwipeBottom() {

            }
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };

        assertNotNull(onSwipeTouchListener);

    }


    public void succLogin()
    {
        assertNotNull(LoginUser.getInstance());
    }






}