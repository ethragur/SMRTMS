package client.smrtms.com.smrtms_client.tokens;

import client.smrtms.com.smrtms_client.controller.LoginUser;

/**
 * Created by effi on 10.05.15.
 * Friendship Request
 */
public class FriendReqToken extends Token
{

    public String friendsname;
    public boolean accept;
    public FriendReqToken(String search)
    {
        super("FriendRequest",  LoginUser.getInstance().getID());
        friendsname = search;
        accept = false;
    }
}
