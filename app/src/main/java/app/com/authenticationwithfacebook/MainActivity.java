package app.com.authenticationwithfacebook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ImageView imageView;
    private TwitterLoginButton login_button_twitter;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
    /*    Intent intent =  new Intent(this, ExpandableActivity.class);
        startActivity(intent);*/

        callbackManager = CallbackManager.Factory.create();
        loginButton =  findViewById(R.id.login_button);
        login_button_twitter = findViewById(R.id.login_button_twitter);
        imageView =  findViewById(R.id.imageView);
        loginWithFacebook();
        loginWithTwitter();

    }

    private void loginWithTwitter() {
        login_button_twitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                TwitterAuthClient authClient = new TwitterAuthClient();
                //TwitterCore.getInstance().getApiClient(session).getAccountService().verifyCredentials(true, false,  new Callback<User>());
                getEmailTwiiter(session);
                Call<User> userResult = TwitterCore.getInstance().getApiClient(session).getAccountService().verifyCredentials(true,false, true);
                userResult.enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        String name = result.data.description;
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });


            }

            @Override
            public void failure(TwitterException exception) {
                exception.getCause();
            }
        });
    }

    private void getEmailTwiiter(final TwitterSession session){
        Toast.makeText(this, "action", Toast.LENGTH_SHORT).show();
        TwitterAuthClient authClient = new TwitterAuthClient();
        authClient.requestEmail(session, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                Log.i("Twiiter", result.toString());
                String emailText = (String) result.data;
                Toast.makeText(MainActivity.this, emailText, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("Twiiter", exception.getMessage());
            }
        });
    }



    private void loginWithFacebook() {
        loginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginResult.getAccessToken();
                GraphRequest request =  GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            email = object.getString("name");
                            String birthday = object.getString("birthday");
                            String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            Picasso.with(MainActivity.this).load(profilePicUrl).into(imageView);
                            Intent intent = new Intent(MainActivity.this, BasicActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                error.getMessage();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        login_button_twitter.onActivityResult(requestCode, resultCode, data);
    }


}
