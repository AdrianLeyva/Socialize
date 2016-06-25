package teamprogra.app.util;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by leyva on 20/06/2016.
 */
public class Util {

    //Abre una nueva actividad y cierra la anterior
    public static void sendAndFinish(Activity activity, Class clase){
        Intent mainIntent = new Intent().setClass(activity,clase);
        activity.startActivity(mainIntent);
        activity.finish();
    }

    //Abre una nueva actividad
    public static void sendTo(Activity activity, Class clase){
        Intent mainIntent = new Intent().setClass(activity,clase);
        activity.startActivity(mainIntent);
    }
}
