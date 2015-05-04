import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MotionEvent;
import android.view.View;

import client.smrtms.com.smrtms_client.View.OnSwipeTouchListener;
import client.smrtms.com.smrtms_client.activity.MainScreen;
import client.smrtms.com.smrtms_client.controller.LoginUser;
import org.junit.*;

import com.firebase.androidchat.ChatActivity;


public class ApplicationTest extends ActivityInstrumentationTestCase2<MainScreen> {


    public ApplicationTest()
    {
        super(MainScreen.class);
    }


    @Test
    public void testActivitySwitch()
    {
        Activity ac = getActivity();
        Intent myIntent = new Intent(ac, ChatActivity.class);

        assertNotNull(myIntent);


        ac.startActivity(myIntent);
    }

    @Test
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

    @Test
    public void succLogin()
    {
        //assertNotNull(LoginUser.getInstance());
    }






}