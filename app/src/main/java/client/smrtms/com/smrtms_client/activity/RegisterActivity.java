package client.smrtms.com.smrtms_client.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import client.smrtms.com.smrtms_client.R;
import client.smrtms.com.smrtms_client.controller.Client;
import client.smrtms.com.smrtms_client.controller.JSONParser;
import client.smrtms.com.smrtms_client.tokens.RegistrationToken;


public class RegisterActivity extends ActionBarActivity
{
    public static int regSuc = 0;

    private EditText mUsernameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordReView;

    private View mProgressView;
    private View mAuthFormView;

    private WaitForServerResponse mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUsernameView =(EditText) findViewById(R.id.editUsername);
        mEmailView = (EditText) findViewById(R.id.editEmail);
        mPasswordView = (EditText) findViewById(R.id.editPW);
        mPasswordReView = (EditText) findViewById(R.id.editPWre);

        mAuthFormView = findViewById(R.id.auth_form);
        mProgressView = findViewById(R.id.auth_progress);
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
        String PasswordRe = mPasswordReView.getText().toString();
        if(Password.compareTo(PasswordRe) == 0)
        {
            try {
                RegistrationToken rt = new RegistrationToken();
                rt.email = Email;
                rt.username = Username;
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                messageDigest.update(Password.getBytes());
                String hashedPW = new String(messageDigest.digest());
                rt.password = hashedPW;
                rt.result = false;
                JSONParser<RegistrationToken> writer = new JSONParser<>();
                String toSend;
                toSend = writer.JSONWriter(rt);

                if (Client.getInstance().WriteMsg(toSend)) {
                    showProgress(true);
                    mAuthTask = new WaitForServerResponse();
                    mAuthTask.execute((Void) null);
                } else {
                    Toast.makeText(this.getApplicationContext(), "Client is not connected to Server", Toast.LENGTH_SHORT).show();
                }
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(this.getApplicationContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mAuthFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mAuthFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAuthFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mAuthFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private class WaitForServerResponse extends AsyncTask<Void, Void, Boolean>
    {
        public WaitForServerResponse()
        {}

        protected Boolean doInBackground(Void... params)
        {
            while(regSuc == 0)
            {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(regSuc == 1)
                {
                    regSuc = 0;
                    Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    RegisterActivity.this.startActivity(myIntent);
                    return true;
                }
                if(regSuc == -1)
                {
                    regSuc = 0;
                    return false;
                }

            }
            return false;
        }

        protected void onPostExecute(final Boolean success)
        {
            showProgress(false);

            if (success) {
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(myIntent);
                finish();
            } else {
                mEmailView.setError("Email or Username already in use");
                mEmailView.requestFocus();
            }
        }
    }
}
