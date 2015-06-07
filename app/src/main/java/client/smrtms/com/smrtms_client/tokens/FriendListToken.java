package client.smrtms.com.smrtms_client.tokens;

import java.util.List;

import client.smrtms.com.smrtms_client.controller.LoginUser;
import client.smrtms.com.smrtms_client.controller.User;

/**
 * Created by effi on 5/25/15.
 */
public class FriendListToken extends Token
{
    public List<User> userList;
    public FriendListToken()
    {
        super("FriendList", LoginUser.getInstance().getID());
    }

}
