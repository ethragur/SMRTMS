package server;

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

        //convert json to object

        T obj = gson.fromJson(file, sryForThat );
        return obj;



    }

    //Takes an Obejct and Converts it to a JSON string
    public String JSONWriter(T toGson)
    {

        Gson gson = new Gson();


        String json = gson.toJson(toGson);


        return json;
    }

}
