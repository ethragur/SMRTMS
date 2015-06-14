package server.tokens;

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
        super("FriendRequest", "0");
        friendsname = search;
        accept = false;
    }
}
