package com.deinteti.gb.cricmodulemovil10.AccesoDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by desarrollo on 21/03/2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    //Constantes para la conexion a la base de datos
    private static final String TAG = "DBManager";
    private static final String DATABASE_NAME = "CRICModule.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "fugitivos";
    private static final String _ID = "id";
    private static final String CULUMN_NAME_NAME = "name";
    private static final String COLUMN_NAME_STATUS = "status";
    private static final String COLUMN_NAME_FOTO = "foto";
    private static final String COLUMN_NAME_LAT = "lat";
    private static final String COLUMN_NAME_LON = "lon";
    private static final String COLUMN_NAME_FECHA = "fecha";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w("CHECK", "BDHelper.onCreate....");
        db.execSQL("CREATE TABLE " + DBHelper.TABLE_NAME + " ("
                + DBHelper._ID + " INTEGER PRIMARY KEY,"
                + DBHelper.CULUMN_NAME_NAME + " TEXT,"
                + DBHelper.COLUMN_NAME_FOTO + " TEXT,"
                + DBHelper.COLUMN_NAME_LAT + " TEXT,"
                + DBHelper.COLUMN_NAME_LON + " TEXT,"
                + DBHelper.COLUMN_NAME_FECHA + " TEXT,"
                + DBHelper.COLUMN_NAME_STATUS + " INTEGER);");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Actualizacion de BDD de la version " + oldVersion + " a la " + newVersion + ", de la que destruira la version anterior");
        db.execSQL("DROP TABLE IF EXISTS " + DBHelper.TABLE_NAME);
        onCreate(db);
    }
}

