package teamprogra.app.socialize.socialize;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.squareup.picasso.Picasso;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLogoutListener;

import teamprogra.app.domain.User;
import teamprogra.app.util.Util;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener{

    private SocializeApplication app;
    private User user;
    private SimpleFacebook mSimpleFacebook;
    private GoogleApiClient mGoogleApiClient;
    private TextView textViewName;
    private TextView textViewEmail;
    private ImageView imageViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hace la actividad FULLSCREEN
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        app = (SocializeApplication) getApplicationContext();
        user = new User();
        getUserData();

        View headerView = navigationView.getHeaderView(0);
        imageViewProfile = (ImageView) headerView.findViewById(R.id.imageView);
        textViewName = (TextView)headerView.findViewById(R.id.textView_userNameMA);
        textViewEmail = (TextView)headerView.findViewById(R.id.textView_emailUserMA);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
        textViewName.setText(user.getName());
        textViewEmail.setText(user.getEmail());
        Picasso.with(this).load(user.getPhoto()).into(imageViewProfile);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logOut) {
            if(app.isSignInGoogle()){
                app.registerLogOut();
                app.registerLogOutGoogle();

                Util.showToastShort(this,"google");
                Util.sendAndFinish(MainActivity.this,LoginActivity.class);
            }
            else{
                mSimpleFacebook.logout(onLogoutListener);
            }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getUserData(){
        user.setName(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_NAME));
        user.setEmail(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_EMAIL));
        user.setBirthday(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_BIRTHDAY));
        user.setId(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_ID));
        user.setGender(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_GENDER));
        user.setPhoto(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_PICTURE));

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    // logout listener
    OnLogoutListener onLogoutListener = new OnLogoutListener() {

        @Override
        public void onLogout() {
            app.registerLogOut();
            app.registerLogOutFacebook();
            Util.showToastShort(MainActivity.this,"facebook");
            Util.sendAndFinish(MainActivity.this,LoginActivity.class);
        }
    };

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
