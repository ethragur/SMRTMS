
import android.test.ActivityInstrumentationTestCase2;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

import client.smrtms.com.smrtms_client.activity.MainScreen;
import client.smrtms.com.smrtms_client.controller.LoginUser;

/**
 * Created by effi on 5/4/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginUserTest extends ActivityInstrumentationTestCase2<MainScreen>
{
    @Mock
    MainScreen mockedAc;

    @Mock
    LoginUser testUser;

    public LoginUserTest()
    {
        super(MainScreen.class);
    }


    @Test
    public void testInit()
    {
        mockedAc = Mockito.mock(MainScreen.class);
        testUser = Mockito.mock(LoginUser.class);
        Mockito.when(testUser.getLatitude()).thenReturn(47.0);
        Mockito.when(testUser.getLongitude()).thenReturn(11.0);
        Mockito.when(testUser.getID()).thenReturn("0");

        testUser.setIsLogin(true);
        testUser.setUsername("testUser");

        assertNotNull(testUser);
    }

    @Test
    public void testFriendLists()
    {
        testUser.FriendReqIn("testFriend1");
        testUser.FriendReqIn("testFriend2");
        testUser.FriendReqIn("testFriend3");
        testUser.FriendReqIn("testFriend4");
        assertTrue(!testUser.isFriendReqEmpty());

        testUser.checkPendingFriendReq();
    }

    @Test
    public void testAutoLogout()
    {
        testUser.setRemainingTime(0);
        assertFalse(testUser.isLogin());
    }


}
