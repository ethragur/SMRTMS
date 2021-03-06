package client.smrtms.com.smrtms_client.controller;



import com.google.gson.Gson;

import client.smrtms.com.smrtms_client.tokens.Token;

/**
 * Created by effi on 4/26/15.
 */


public class JSONParser<T extends Token>
{
    //need to pass Class<T> because you cant call T.class
    public T readJson(String file, Class<T> sryForThat)
    {
        Gson gson = new Gson();

        //convert json to object

        T obj = gson.fromJson(file, sryForThat );
        return obj;
    }

    //Takes an Object and Converts it to a JSON string
    public String JSONWriter(T toGson)
    {

        Gson gson = new Gson();


        String json = gson.toJson(toGson);


        return json;
    }

}
