package app.com.authenticationwithfacebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

/**
 * Created by leidycarolinazuluagabastidas on 24/11/17.
 */

public class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_basic);
        //LoginManager.getInstance().logOut();
    }
}
