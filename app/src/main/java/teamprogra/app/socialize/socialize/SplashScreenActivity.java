package teamprogra.app.socialize.socialize;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import teamprogra.app.utils.Utils;

public class SplashScreenActivity extends AppCompatActivity {

    private SocializeApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hace la actividad FULLSCREEN
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        app = (SocializeApplication) getApplicationContext();

        Log.d("DEBUG", "DEBIGIGIEGIEGE");
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
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
       throwSplash();
    }

    public void throwSplash(){
        if(Utils.isOnline(getApplicationContext())){
            final TimerTask timerTask = new TimerTask(){
                public void run(){
                    if(app.isLoginStart()){
                        Utils.sendAndFinish(SplashScreenActivity.this, ModosActivity.class);
                    }
                    else{
                        Utils.sendAndFinish(SplashScreenActivity.this, LoginActivity.class);
                    }
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask,3000);
        }else {
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("Conexión a Internet fallida")
                    .setMessage("¿Quiere intentar de nuevo?")
                    .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           throwSplash();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


    }
}
