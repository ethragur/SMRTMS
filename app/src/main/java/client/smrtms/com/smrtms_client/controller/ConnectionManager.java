package client.smrtms.com.smrtms_client.controller;

import android.widget.Toast;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import client.smrtms.com.smrtms_client.activity.MainScreen;

public class ConnectionManager {
    public void SendFile() {
        JSONObject jsonobj = new JSONObject();

        try {
            final String wurl = "sepm@phil-m.eu:8080";

            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppostreq = new HttpPost(wurl);
            StringEntity se = new StringEntity(jsonobj.toString());
            se.setContentType("application/json;charset=UTF-8");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
            httppostreq.setEntity(se);
            HttpResponse httpresponse = httpclient.execute(httppostreq);

            HttpEntity resultentity = httpresponse.getEntity();
            InputStream inputstream = resultentity.getContent();
            Header contentencoding = httpresponse.getFirstHeader("Content-Encoding");
            if (contentencoding != null && contentencoding.getValue().equalsIgnoreCase("gzip")) {
                inputstream = new GZIPInputStream(inputstream);
            }
            String resultstring = convertStreamToString(inputstream);

            inputstream.close();

            resultstring = resultstring.substring(1, resultstring.length() - 1);
           // recvdref.setText(resultstring + "\n\n" + httppostreq.toString().getBytes());
            JSONObject recvdjson = new JSONObject(resultstring);
           // recvdref.setText(recvdjson.toString(2));
        }
        catch (Exception e) { e.printStackTrace(); }
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
            //Toast.makeText(THE_CONTEXT_OF_THIS_FREAKIN_APP , "Stream Exception", Toast.LENGTH_SHORT).show();
        }
        return total.toString();
    }
}
