package client.smrtms.com.smrtms_client.controller;


import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;
/**
 * Created by effi on 4/26/15.
 */


public class JSONReader<T>
{
    //need to passt Class<T> becuase you cant call T.class
    public T readJson(String file, Class<T> sryForThat)
    {
        Gson gson = new Gson();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));


            //convert json to object


            T obj = gson.fromJson(br, sryForThat );
            return obj;

        }
        catch(IOException e)
        {
            Log.d("Error", "Couldn't read JSON file");
            e.printStackTrace();
        }

        return null;
    }

    //Takes an Obejct and Converts it to a JSON string
    public String JSONWriter(T toGson)
    {

        Gson gson = new Gson();


        String json = gson.toJson(toGson);

        return json;
    }

}
