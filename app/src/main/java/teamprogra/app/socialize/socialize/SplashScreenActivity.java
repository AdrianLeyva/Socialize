package teamprogra.app.socialize.socialize;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
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
        app = (SocializeApplication) getApplicationContext();
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
        if(Util.isOnline(getApplicationContext())){
            final Thread timerThreed = new Thread(){
                public void run(){
                    try{
                        sleep(3000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {
                        if(app.isLoginStart()){
                            Util.sendAndFinish(SplashScreenActivity.this, ModosActivity.class);
                        }
                        else{
                            Util.sendAndFinish(SplashScreenActivity.this, LoginActivity.class);
                        }
                    }
                }
            };
            timerThreed.start();
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
