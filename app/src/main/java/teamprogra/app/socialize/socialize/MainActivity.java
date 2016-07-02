package teamprogra.app.socialize.socialize;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import teamprogra.app.fragment.FragmentDataUser;
import teamprogra.app.util.CircleTransform;
import teamprogra.app.util.Util;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener,FragmentDataUser.OnFragmentInteractionListener{

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

        mSimpleFacebook = SimpleFacebook.getInstance(this);
        app = (SocializeApplication) getApplicationContext();
        user = new User();

        View headerView = navigationView.getHeaderView(0);
        imageViewProfile = (ImageView) headerView.findViewById(R.id.imageView);
        textViewName = (TextView)headerView.findViewById(R.id.textView_userNameMA);
        textViewEmail = (TextView)headerView.findViewById(R.id.textView_emailUserMA);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserData();
        textViewName.setText(user.getName());
        textViewEmail.setText(user.getEmail());
        Picasso.with(getApplicationContext()).load(user.getPhoto()).error(R.drawable.login_user).transform(new CircleTransform()).into(imageViewProfile);
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home){


        }
        else if(id == R.id.nav_userData){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentDataUser fragment = FragmentDataUser.newInstance();
            fragmentTransaction.add(R.id.container, fragment);
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_logOut){
            if(app.isSignInGoogle()){
                app.registerLogOut();
                app.registerLogOutGoogle();

                Util.showToastShort(this,"google");
                Util.sendAndFinish(MainActivity.this,LoginActivity.class);
            }
            else{
                mSimpleFacebook.logout(onLogoutListener);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getUserData(){
        user.setName(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_NAME));
        user.setEmail(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_EMAIL));
        user.setBirthday(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_BIRTHDAY));
        user.setIdUserFacebook(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_ID));
        user.setGender(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_GENDER));
        user.setPhoto(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_PICTURE));
        user.setLocale(app.getStringRegisterValuePreferences(SocializeApplication.APP_VALUE_LOCALE));
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        Util.showToastShort(MainActivity.this,"Revise su conexión a Internet");
    }

    // logout listener
    OnLogoutListener onLogoutListener = new OnLogoutListener() {

        @Override
        public void onLogout() {
            app.registerLogOut();
            app.registerLogOutFacebook();
            Util.sendAndFinish(MainActivity.this,LoginActivity.class);
        }
    };

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
