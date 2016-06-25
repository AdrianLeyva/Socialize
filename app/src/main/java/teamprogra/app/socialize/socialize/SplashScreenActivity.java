package teamprogra.app.socialize.socialize;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import teamprogra.app.util.Util;

public class SplashScreenActivity extends AppCompatActivity {

    private SocializeApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hace la actividad FULLSCREEN
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        throwSplash();

    }

    @Override
    protected void onStop() {
        super.onStop();
        throwSplash();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
       throwSplash();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void throwSplash(){
        app = (SocializeApplication) getApplicationContext();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(app.isLoginStart()){
                    Util.sendAndFinish(SplashScreenActivity.this, ModosActivity.class);
                }
                else{
                    Util.sendAndFinish(SplashScreenActivity.this, LoginActivity.class);
                }
            }
        },3000);
    }
}
