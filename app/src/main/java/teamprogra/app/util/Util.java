package teamprogra.app.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * Created by leyva on 20/06/2016.
 */
public class Util {

    //Abre una nueva actividad y cierra la anterior
    public static void sendAndFinish(Activity activity, Class clase) {
        Intent i = new Intent(activity, clase);
        activity.startActivity(i);
        activity.finish();
    }

    //Abre una nueva actividad
    public static void sendTo(Activity activity, Class clase) {
        Intent mainIntent = new Intent().setClass(activity, clase);
        activity.startActivity(mainIntent);
    }

    //Muestra un toast de corta duración
    public static void showToastShort(Activity activity, String text) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }

    //Muestra un toast de larga duración
    public static void showToastLong(Activity activity, String text) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
    }

    /**
     * Creación del objeto desde el jSon
     */
    public static Object createObjectFromJson(String userJson, Class c) {
        Gson objectJson = new Gson();
        return objectJson.fromJson(userJson, c.getClass());
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
