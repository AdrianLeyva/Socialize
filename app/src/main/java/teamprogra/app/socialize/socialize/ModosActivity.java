package teamprogra.app.socialize.socialize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import teamprogra.app.util.Util;

public class ModosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hace la actividad FULLSCREEN
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_modos);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    public void goAnfitrion(View view){
       Util.sendAndFinish(ModosActivity.this,MainActivity.class);
    }
}
