package pl.placemap;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import pl.placemap.connection.SignIn;

public class MainActivity extends ActionBarActivity {

    private static MainActivity instance;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        signInWithFacebook();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void signInWithFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends", "user_tagged_places"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        new SignIn().execute();
                        startActivity(new Intent(MainActivity.getInstance(), MapsActivity.class));
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "Login attempt canceled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException e) {
                        Toast.makeText(MainActivity.this, "Login attempt failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        if (isLoggedIn()) {
            new SignIn().execute();
            startActivity(new Intent(MainActivity.getInstance(), MapsActivity.class));
            finish();
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public static MainActivity getInstance() {
        return instance;
    }

}


