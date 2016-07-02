package teamprogra.app.socialize.socialize;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.orm.SugarApp;
import com.orm.SugarContext;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Profile;

import teamprogra.app.domain.User;


/**
 * Created by leyva on 20/06/2016.
 */
public class SocializeApplication extends SugarApp{

    private static final String APP_PREFERENCES = "APP_PREFERENCES";
    private SharedPreferences preferences;

    private static final String APP_KEY_IS_LOGIN_START = "APP_KEY_IS_LOGIN_START";
    private static final String APP_KEY_IS_SIGN_IN_WITH_GOOGLE = "APP_KEY_IS_LOGIN_WITH_GOOGLE";
    private static final String APP_KEY_IS_SIGN_IN_WITH_FACEBOOK = "APP_KEY_IS_LOGIN_WITH_FACEBOOK";

    public static final String APP_ID_USER = "APP_ID_USER";
    public static final String APP_VALUE_ID = "APP_VALUE_ID";
    public static final String APP_VALUE_NAME = "APP_VALUE_NAME";
    public static final String APP_VALUE_EMAIL = "APP_VALUE_EMAIL";
    public static final String APP_VALUE_PICTURE = "APP_VALUE_PICTURE";
    public static final String APP_VALUE_BIRTHDAY = "APP_VALUE_BIRTHDAY";
    public static final String APP_VALUE_GENDER = "APP_VALUE_GENDER";
    public static final String APP_VALUE_LOCALE = "APP_VALUE_LOCALE";
    public static final String APP_VALUE_PHONE = "APP_VALUE_PHONE";


    Permission[] permissions = new Permission[] {
            Permission.USER_BIRTHDAY,
            Permission.USER_PHOTOS,
            Permission.USER_WORK_HISTORY,
            Permission.EMAIL,
            Permission.USER_LOCATION
    };

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId("@strings/facebook_app_id")
                .setNamespace("Socialize")
                .setPermissions(permissions)
                .build();
        SimpleFacebook.setConfiguration(configuration);
        SugarContext.init(this);
    }

    public void registerLogIn(){
        saveValuePreferences(SocializeApplication.APP_KEY_IS_LOGIN_START,true);
    }

    public void registerLogOut(){
        saveValuePreferences(SocializeApplication.APP_KEY_IS_LOGIN_START,false);
    }

    public boolean isLoginStart(){
        return getBooleanRegisterValuePreferences(SocializeApplication.APP_KEY_IS_LOGIN_START);
    }

    public void registerSignInGoogle(){
        saveValuePreferences(SocializeApplication.APP_KEY_IS_SIGN_IN_WITH_GOOGLE,true);
    }

    public void registerLogOutGoogle(){
        saveValuePreferences(SocializeApplication.APP_KEY_IS_SIGN_IN_WITH_GOOGLE,false);
    }

    public void registerSignInFacebook(){
        saveValuePreferences(SocializeApplication.APP_KEY_IS_SIGN_IN_WITH_FACEBOOK, true);
    }

    public void registerLogOutFacebook(){
        saveValuePreferences(SocializeApplication.APP_KEY_IS_SIGN_IN_WITH_FACEBOOK,false);
    }

    public void registerUserData(Profile profile){
        saveValuePreferences(SocializeApplication.APP_VALUE_ID, profile.getId());
        saveValuePreferences(SocializeApplication.APP_VALUE_NAME, profile.getName());
        saveValuePreferences(SocializeApplication.APP_VALUE_EMAIL, profile.getEmail());
        saveValuePreferences(SocializeApplication.APP_VALUE_PICTURE, profile.getPicture());
        saveValuePreferences(SocializeApplication.APP_VALUE_BIRTHDAY, profile.getBirthday());
        saveValuePreferences(SocializeApplication.APP_VALUE_GENDER, profile.getGender());
        saveValuePreferences(SocializeApplication.APP_VALUE_LOCALE, profile.getLocation().getName());

        User user = new User();
        user.setIdUserFacebook(profile.getId());
        user.setName(profile.getName());
        user.setEmail(profile.getEmail());
        user.setPhoto(profile.getPicture());
        user.setBirthday(profile.getBirthday());
        user.setGender(profile.getGender());
        user.setLocale(profile.getLocation().getName());
        user.save();
    }


    public boolean isSignInGoogle(){
        return getBooleanRegisterValuePreferences(SocializeApplication.APP_KEY_IS_SIGN_IN_WITH_GOOGLE);
    }

    public boolean isSignInFacebook(){
        return getBooleanRegisterValuePreferences(SocializeApplication.APP_KEY_IS_SIGN_IN_WITH_FACEBOOK);
    }


    public void saveValuePreferences(String key,String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }


    public void saveValuePreferences(String key, boolean value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public String getStringRegisterValuePreferences(String key){
        return preferences.getString(key,null);
    }

    public boolean getBooleanRegisterValuePreferences(String key){
        return preferences.getBoolean(key,false);
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public static String getAppValueId() {
        return APP_VALUE_ID;
    }

    public static String getAppValueName() {
        return APP_VALUE_NAME;
    }

    public static String getAppValueEmail() {
        return APP_VALUE_EMAIL;
    }

    public static String getAppValuePicture() {
        return APP_VALUE_PICTURE;
    }

    public static String getAppValueBirthday() {
        return APP_VALUE_BIRTHDAY;
    }

    public static String getAppValueGender() {
        return APP_VALUE_GENDER;
    }

}
