package client.smrtms.com.smrtms_client.controller;

import android.widget.Toast;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class ConnectionManager {
    public void SendFile() {
        HttpEntity resultentity = httpresponse.getEntity();
        InputStream inputstream = resultentity.getContent();
        Header contentencoding = httpresponse.getFirstHeader("Content-Encoding");
        if(contentencoding != null && contentencoding.getValue().equalsIgnoreCase("gzip")) {
            inputstream = new GZIPInputStream(inputstream);
        }
        String resultstring = convertStreamToString(inputstream);
        inputstream.close();
        resultstring = resultstring.substring(1,resultstring.length()-1);
        recvdref.setText(resultstring + "\n\n" + httppostreq.toString().getBytes());
        JSONObject recvdjson = new JSONObject(resultstring);
        recvdref.setText(recvdjson.toString(2));
    }

    private String convertStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Stream Exception", Toast.LENGTH_SHORT).show();
        }
        return total.toString();
    }
}
