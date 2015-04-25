package client.smrtms.com.smrtms_client.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import client.smrtms.com.smrtms_client.R;

public class StartActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_one_button);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    public void changeToMainScreen(View view) {
        Intent myIntent = new Intent(StartActivity.this, MainScreen.class);
        StartActivity.this.startActivity(myIntent);
    }

}
