package server.tokens;

import java.util.List;

import ServerClasses.User;

/**
 * Created by effi on 5/25/15.
 */
public class FriendListToken extends Token
{
    public List<User> userList;
    public FriendListToken()
    {
        super("FriendList", "0");
    }

}
