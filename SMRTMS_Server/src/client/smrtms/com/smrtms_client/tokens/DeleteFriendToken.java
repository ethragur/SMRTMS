package client.smrtms.com.smrtms_client.tokens;

/**
 * Created by effi on 6/13/15.
 */
public class DeleteFriendToken extends Token
{
    public String name;
    public DeleteFriendToken(String username)
    {
        super("DeleteFriend", "0");
        name = username;
    }
}