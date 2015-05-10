package client.smrtms.com.smrtms_client.tokens;

// Okay, I know this is a horrible way of doing this, but by linking the Token classes
// from the client software to the eclipse projects of the server, they share
// the same files, so there is no need to worry about differences between the client and
// server version of the token classes

// However, java is making this difficult, since the packages don't align

// I'm thinking about creating the client.smrtms.com.... package chain in the server
// just so Java will leave me alone...

/**
 * Created by effi on 4/29/15.
 */
public class Token
{
    private String sTag;

    public Token(String tag)
    {
        sTag = tag;
    }

}
