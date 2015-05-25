package client.smrtms.com.smrtms_client.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.controller.Client;
import client.smrtms.com.smrtms_client.controller.JSONReader;
import client.smrtms.com.smrtms_client.controller.User;
import client.smrtms.com.smrtms_client.tokens.RegistrationToken;


public class RegisterActivity extends ActionBarActivity
{
    public static int regSuc = 0;

    private EditText mUsernameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordReView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUsernameView =(EditText) findViewById(R.id.editUsername);
        mEmailView = (EditText) findViewById(R.id.editEmail);
        mPasswordView = (EditText) findViewById(R.id.editPW);
        mPasswordReView = (EditText) findViewById(R.id.editPW);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    public void changeToLogin(View view) {
        Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        RegisterActivity.this.startActivity(myIntent);
    }


    public void onRegisterClick(View view)
    {
        String Username = mUsernameView.getText().toString();
        String Email = mEmailView.getText().toString();
        String Password = mPasswordView.getText().toString();
        String PasswrodRe = mPasswordReView.getText().toString();
        if(Password.compareTo(PasswrodRe) == 0)
        {
            RegistrationToken rt = new RegistrationToken();
            rt.email = Email;
            rt.username = Username;
            rt.password = Password;
            rt.result = false;
            JSONReader<RegistrationToken> writer = new JSONReader<>();
            String toSend;
            toSend = writer.JSONWriter(rt);

            Client.getInstance().WriteMsg(toSend);

            while(regSuc == 0)
            {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(regSuc == 1)
            {
                regSuc = 0;
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(myIntent);
            }
            if(regSuc == -1)
            {
                regSuc = 0;
                Log.d("Registration", "Sth went wrong");
            }

        }
    }
}
