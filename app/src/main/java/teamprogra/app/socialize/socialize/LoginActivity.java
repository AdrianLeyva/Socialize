package teamprogra.app.socialize.socialize;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import teamprogra.app.domain.User;
import teamprogra.app.persistence.SocializeSQLiteOpenHelper;
import teamprogra.app.util.Util;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private SocializeApplication app;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 1000;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hace la actividad FULLSCREEN
        //....
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        configureGoogleApi();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    public void doSignInGoogle(View view){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from
        //   GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                // Get account information
                user = new User(acct.getDisplayName(),acct.getEmail());
                app = (SocializeApplication) getApplicationContext();
                app.registerLogIn();
                app.registerSignInGoogle();
                Util.showToastShort(this,user.getName());
                Util.sendAndFinish(this,ModosActivity.class);
            }
            else {
                Util.showToastShort(this,"Error al obtener los datos");
            }
        }
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

    public void saveUserData(User user){
        SocializeSQLiteOpenHelper admin = new SocializeSQLiteOpenHelper(this,
                                            SocializeSQLiteOpenHelper.getDataBaseSocialize(),null,
                                            SocializeSQLiteOpenHelper.getDataBaseVersion());
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues registry = new ContentValues();
        registry.put("name",user.getName());
        registry.put("email",user.getEmail());

        bd.insert("User",null,registry);
        bd.close();
    }
}
