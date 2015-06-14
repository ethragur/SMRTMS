import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MotionEvent;
import android.view.View;

import client.smrtms.com.smrtms_client.View.OnSwipeTouchListener;
import client.smrtms.com.smrtms_client.activity.MainScreen;
import client.smrtms.com.smrtms_client.controller.LoginUser;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.firebase.androidchat.ChatActivity;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainScreen> {


    public ApplicationTest()
    {
        super(MainScreen.class);
    }


    @Test
    public void doNothing()
    {
        
    }

}