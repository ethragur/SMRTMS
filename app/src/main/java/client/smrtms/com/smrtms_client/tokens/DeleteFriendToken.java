package client.smrtms.com.smrtms_client.tokens;

import client.smrtms.com.smrtms_client.controller.LoginUser;

/**
 * Created by effi on 6/13/15.
 */
public class DeleteFriendToken extends Token
{
    String name;
    public DeleteFriendToken(String username)
    {
        super("DeleteFriend", LoginUser.getInstance().getID());
        name = username;
    }
}
