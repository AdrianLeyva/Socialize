package teamprogra.app.socialize.socialize;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;

import java.util.List;

import teamprogra.app.domain.User;

import teamprogra.app.utils.Utils;

public class LoginActivity extends AppCompatActivity{

    private ProgressBar progressBar;
    private SocializeApplication app;
    private User user;
    private SimpleFacebook mSimpleFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hace la actividad FULLSCREEN
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
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
        if(Utils.isOnline(getApplicationContext())){
            Thread threadAccess = new Thread(){
                public void run(){
                    mSimpleFacebook.login(onLoginListener);
                }
            };
            threadAccess.start();
        }else {
            Utils.showToastLong(this,"Verifique su conexión a Internet");
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
            Utils.showToastLong(LoginActivity.this,"Se ha reportado un error, intente nuevamente");
        }

        @Override
        public void onFail(String reason) {
            Utils.showToastShort(LoginActivity.this,"Error en la conexión, intente de nuevo");
        }
    };


    OnProfileListener onProfileListener = new OnProfileListener() {
        @Override
        public void onComplete(Profile profile) {
            progressBar.setVisibility(View.VISIBLE);
           List<User> user = User.find(User.class,"id_user_facebook = ?",profile.getId());
            if (user.isEmpty()){
                app.registerUserData(profile);
            }
            app.registerLogIn();
            app.registerSignInFacebook();
            Utils.sendAndFinish(LoginActivity.this,ModosActivity.class);
        }

        @Override
        public void onException(Throwable throwable) {
            super.onException(throwable);
        }

        @Override
        public void onFail(String reason) {
            super.onFail(reason);
            Utils.showToastLong(LoginActivity.this,"Ocurrió un error en el registro, intente nuevamente");
        }

        @Override
        public void onThinking() {
            super.onThinking();
        }
    };

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSimpleFacebook.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
