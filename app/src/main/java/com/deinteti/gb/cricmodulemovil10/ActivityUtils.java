package com.deinteti.gb.cricmodulemovil10;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by desarrollo on 08/03/2018.
 */

public class ActivityUtils {

    public static String getPreferenceById(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static void AlertOKMessage(Context context, String title, String sMsg) {
        AlertDialog.Builder oADb = new AlertDialog.Builder(context);
        oADb.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog oAD = oADb.create();
        oAD.setTitle(title);
        oAD.setMessage(sMsg);
        oAD.show();
    }

    public static void AlertMessageToast(Context context, String Msj) {
        Toast.makeText(context, Msj, Toast.LENGTH_SHORT).show();
    }


}