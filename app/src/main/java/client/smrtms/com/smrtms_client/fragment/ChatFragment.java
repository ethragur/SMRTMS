package client.smrtms.com.smrtms_client.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.controller.LoginUser;


public class ChatFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        if(LoginUser.getInstance() != null)
        {
            LoginUser.getInstance().checkPendingFriendReq();
        }
        return rootView;
    }
    

}
