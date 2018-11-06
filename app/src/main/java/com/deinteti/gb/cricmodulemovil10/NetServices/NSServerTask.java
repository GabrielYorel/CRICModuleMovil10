package com.deinteti.gb.cricmodulemovil10.NetServices;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.deinteti.gb.cricmodulemovil10.Datos.CRICModuleParams;
import com.deinteti.gb.cricmodulemovil10.Enums.TipoOperacionServer;
import com.deinteti.gb.cricmodulemovil10.Interfaces.OnTaskCompleted;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.ConnectException;

/**
 * Created by desarrollo on 08/03/2018.
 */

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class NSServerTask extends AsyncTask<TipoOperacionServer, Void, Boolean> {
    private static String LOG_TAG = "NSServerTaskActivity";
    OnTaskCompleted onTaskCompleted;
    Activity oActRef;
    private Exception exception;
    CRICModuleParams CricParams;

    public NSServerTask(OnTaskCompleted onTaskCompleted, Activity oActRef) {
        exception = null;
        this.onTaskCompleted = onTaskCompleted;
        this.oActRef = oActRef;
    }

    @Override
    protected Boolean doInBackground(TipoOperacionServer... params) {
        // TODO: attempt authentication against a network service.
        boolean Result = false;
        if (params[0] == TipoOperacionServer.Probarconexion) {
            try {
                CricParams = new CRICModuleParams(oActRef);
                InputStream is = null;
                String URL = CricParams.getBaseURI(false) + "TestConnection";
                Log.d(LOG_TAG, "URL: " + URL);
                String res = "";
                is = new NetServicesUtils(LOG_TAG).ByGetMethod(URL,false);
                if (is != null) {
                    res = new NetServicesUtils(LOG_TAG).ConvertStreamToString(is);
                    JSONObject jaObject = new JSONObject(res);
                    String ResJson = jaObject.getString("TestConnectionResult");
                    Result = Boolean.valueOf(ResJson);
                    Log.d(LOG_TAG, "Res: " + res+" "+Result);
                } else {
                    res = "Something went wrong";
                    throw  new Exception("Something went wrong");
                }
            } catch (ConnectException ce) {
                Log.e(LOG_TAG, "Error in GetData", ce);
                exception = ce;
            }catch (Exception ex) {
                exception = ex;
            }
        }
        return Result;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (exception == null) {
            onTaskCompleted.onTaskCompleted(success);
        } else {
            onTaskCompleted.onTaskError(exception.getMessage());
        }
    }

    @Override
    protected void onCancelled() {
        onTaskCompleted.onCancelled();
    }


}
