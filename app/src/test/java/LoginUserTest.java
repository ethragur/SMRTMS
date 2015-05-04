import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Display;

import junit.framework.TestCase;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import client.smrtms.com.smrtms_client.activity.MainScreen;
import client.smrtms.com.smrtms_client.controller.LoginUser;

/**
 * Created by effi on 5/4/15.
 */
public class LoginUserTest extends ActivityInstrumentationTestCase2<MainScreen>
{


    public LoginUserTest()
    {
        super(MainScreen.class);
    }
    @Test
    public void testInit()
    {
        LoginUser.createInstance("TestUser","TestId", this.getActivity().getApplicationContext());

        assertNotNull(LoginUser.getInstance());
    }


}
