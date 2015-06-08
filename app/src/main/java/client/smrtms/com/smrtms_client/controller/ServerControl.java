package client.smrtms.com.smrtms_client.controller;

import android.util.Log;

import client.smrtms.com.smrtms_client.activity.RegisterActivity;
import client.smrtms.com.smrtms_client.tokens.AuthenticationToken;
import client.smrtms.com.smrtms_client.tokens.FriendListToken;
import client.smrtms.com.smrtms_client.tokens.FriendReqToken;
import client.smrtms.com.smrtms_client.tokens.RegistrationToken;
import client.smrtms.com.smrtms_client.tokens.Token;

/**
 * Created by effi on 11.05.15.
 */
public class ServerControl implements Runnable
{
    public String input;
    public Token t;

    public ServerControl(String input, Token t)
    {
        this.input = input;
        this.t = t;
    }

    @Override
    public void run()
    {
        parseString();
    }

    //parses the received string from the Server
    public void parseString()
    {


        switch (t.sTag)
        {
            case "Authentication": //to auth
                handleAuth();
                break;
            case "Registration":
                handleReg();
                break;
            case "FriendList":
                handleFriendList();
                break;
            case "FriendRequest":
                handleFriendReq();
                break;
            default:
                Log.e("ServerMsg", "UnknownToken");
                break;
        }
    }

    private void handleAuth()
    {
        JSONReader<AuthenticationToken> readerA = new JSONReader<AuthenticationToken>();
        AuthenticationToken authT = readerA.readJson(input, AuthenticationToken.class);
        if(authT.access)
        {


                LoginUser.getInstance().setID(authT.id);
                LoginUser.getInstance().setIsLogin(true);
                Log.d("Login", "Login Successful");




        }
        else
        {
            Log.d("Login", "Wrong Username or Password");
            LoginUser.getInstance().setIsLogin(false);
        }

    }

    private void handleReg()
    {
        JSONReader<RegistrationToken> readerR = new JSONReader<>();
        RegistrationToken retT = readerR.readJson(input, RegistrationToken.class);
        if(retT.result)
        {
            RegisterActivity.regSuc = 1;
        }
        else
        {
            RegisterActivity.regSuc = -1;
        }
    }

    private void handleFriendList()
    {
        JSONReader<FriendListToken> readerFL = new JSONReader<>();
        FriendListToken friendT = readerFL.readJson(input, FriendListToken.class);
        LoginUser.getInstance().setFriendList(friendT.userList);

    }

    private void handleFriendReq()
    {
        JSONReader<FriendReqToken> readerFr = new JSONReader<>();
        FriendReqToken frReq = readerFr.readJson(input, FriendReqToken.class);
        if(frReq != null)
        {
            LoginUser.getInstance().FriendReqIn(frReq.friendsname);
        }
    }

}
