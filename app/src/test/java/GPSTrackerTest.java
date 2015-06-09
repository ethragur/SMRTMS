import android.location.Location;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import client.smrtms.com.smrtms_client.controller.GPSTracker;

/**
 * Created by effi on 5/4/15.
 */
public class GPSTrackerTest extends TestCase
{

    @Mock
    GPSTracker testGPSTracker;

    @Test
    public void testGPSdata()
    {
        testGPSTracker = Mockito.mock(GPSTracker.class);
        Mockito.when(testGPSTracker.canGetLocation()).thenReturn(true);
        Location n = Mockito.mock(Location.class);
        n.setLatitude(47.265);
        n.setLongitude(11.395);
        Mockito.when(testGPSTracker.getLatitude()).thenReturn(47.265);
        Mockito.when(testGPSTracker.getLongitude()).thenReturn(11.395);
        Mockito.when(testGPSTracker.getLocation()).thenReturn(n);
        Mockito.when(testGPSTracker.calculateDistance(Mockito.anyDouble(),Mockito.anyDouble())).thenCallRealMethod();
        testGPSTracker.location = n;

        assertTrue(testGPSTracker.calculateDistance( 47.273333, 11.241389) > 10 &&
                testGPSTracker.calculateDistance(47.273333, 11.241389) < 12);


    }

}
