package teamprogra.app.socialize.socialize;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.orm.SugarContext;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;


import java.util.ArrayList;
import java.util.List;

import teamprogra.app.domain.User;

import teamprogra.app.util.Util;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private ProgressBar progressBar;
    private SocializeApplication app;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 1000;
    private User user;
    private SimpleFacebook mSimpleFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hace la actividad FULLSCREEN
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        configureGoogleApi();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        user = new User();
        app = (SocializeApplication) getApplicationContext();
        progressBar = (ProgressBar)findViewById(R.id.progressBarLogin);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void doSignInFacebook(View view){
        if(Util.isOnline(getApplicationContext())){
            Thread threadAccess = new Thread(){
                public void run(){
                    mSimpleFacebook.login(onLoginListener);
                }
            };
            threadAccess.start();
        }else {
            Util.showToastLong(this,"Verifique su conexión a Internet");
        }

    }

    private OnLoginListener onLoginListener = new OnLoginListener() {
        @Override
        public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {

            PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
            pictureAttributes.setHeight(500);
            pictureAttributes.setWidth(500);
            pictureAttributes.setType(PictureAttributes.PictureType.SQUARE);


            Profile.Properties properties = new Profile.Properties.Builder()
                    .add(Profile.Properties.ID)
                    .add(Profile.Properties.NAME)
                    .add(Profile.Properties.EMAIL)
                    .add(Profile.Properties.PICTURE,pictureAttributes)
                    .add(Profile.Properties.BIRTHDAY)
                    .add(Profile.Properties.GENDER)
                    .add(Profile.Properties.LOCATION)
                    .build();
            mSimpleFacebook.getProfile(properties, onProfileListener);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onException(Throwable throwable) {

        }

        @Override
        public void onFail(String reason) {
            Util.showToastShort(LoginActivity.this,"Ha ocurrido un error, intente nuevamente.");
        }
    };


    OnProfileListener onProfileListener = new OnProfileListener() {
        @Override
        public void onComplete(Profile profile) {
            progressBar.setVisibility(View.VISIBLE);
           List<User> user = User.find(User.class,"id_user_facebook = ?",profile.getId());
            if (user.isEmpty()){
                app.registerLogIn();
                app.registerSignInFacebook();
                app.registerUserData(profile);
            }
            Util.sendAndFinish(LoginActivity.this,ModosActivity.class);
        }

        @Override
        public void onException(Throwable throwable) {
            super.onException(throwable);
        }

        @Override
        public void onFail(String reason) {
            super.onFail(reason);
        }

        @Override
        public void onThinking() {
            super.onThinking();
        }
    };


    public void doSignInGoogle(View view){
        mGoogleApiClient.connect();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSimpleFacebook.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        /*
            // Result returned from launching the Intent from
            //   GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    GoogleSignInAccount acct = result.getSignInAccount();
                    // Get account information
                    user.setId(acct.getId());
                    user.setName(acct.getDisplayName());
                    user.setEmail(acct.getEmail());
                    // Register app login
                    app.registerLogIn();
                    app.registerSignInGoogle();
                    Util.sendAndFinish(LoginActivity.this, RegisterActivity.class);
                }
                else {
                    Util.showToastShort(this,"Error al obtener los datos");
                }
        }
        */
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }



    public void configureGoogleApi(){
        // Configure sign-in to request the user's ID, email address, and basic profile. ID and
        // basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
/*
    public void saveDataUser(){
        Gson userJson = new Gson();
        String userSerialize = userJson.toJson(user);
        app.saveValuePreferences(app.getAppKeyUserData(),userSerialize);
    }
    */
}
