package teamprogra.app.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adrianleyva on 25/06/16.
 */
public class SocializeSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATA_BASE_SOCIALIZE = "socialize_app";
    private static final int DATA_BASE_VERSION = 1;

    public SocializeSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table User(name text, email text, age integer, occupation text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists User");
        sqLiteDatabase.execSQL("create table User(name text, email text, age integer, occupation text)");
    }

    public static String getDataBaseSocialize() {
        return DATA_BASE_SOCIALIZE;
    }

    public static int getDataBaseVersion() {
        return DATA_BASE_VERSION;
    }
}
