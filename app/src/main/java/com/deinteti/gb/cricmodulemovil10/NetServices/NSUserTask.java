package com.deinteti.gb.cricmodulemovil10.NetServices;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.deinteti.gb.cricmodulemovil10.AccesoDatos.OperacionesBaseDatos;
import com.deinteti.gb.cricmodulemovil10.Datos.CRICModuleParams;
import com.deinteti.gb.cricmodulemovil10.Entidades.LoginResult;
import com.deinteti.gb.cricmodulemovil10.Entidades.UsuarioVehiculosResult;
import com.deinteti.gb.cricmodulemovil10.Entidades.VehiculosUsuario;
import com.deinteti.gb.cricmodulemovil10.Enums.TipoOperacionUsuario;
import com.deinteti.gb.cricmodulemovil10.Interfaces.OnTaskCompleted;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by desarrollo on 08/03/2018.
 */

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class NSUserTask extends AsyncTask<Object, Void, Object> {
    private static String LOG_TAG = "NSUserLoginActivity";
    private final String mUsuario;
    private final String mContrasenia;
    OnTaskCompleted onTaskCompleted;
    Activity oActRef;
    private Exception exception;
    CRICModuleParams CricParams;
    OperacionesBaseDatos dbDatos;

    public NSUserTask(OnTaskCompleted onTaskCompleted, Activity oActRef, String usuario, String contrasenia, OperacionesBaseDatos dbDatos) {
        exception = null;
        this.onTaskCompleted = onTaskCompleted;
        this.oActRef = oActRef;
        mUsuario = usuario;
        mContrasenia = contrasenia;
        this.dbDatos = dbDatos;
    }

    @Override
    protected Object doInBackground(Object... params) {
        // TODO: attempt authentication against a network service.
        Object Result = null;
        String responseString = "";
        InputStream is = null;
        if (params[0] == TipoOperacionUsuario.Login) {
            try {
                CricParams = new CRICModuleParams(oActRef);
                String URL = CricParams.getBaseURI(false) + "Login";
                //Log.d(LOG_TAG, "URL: " + URL);
                ///region test with LinkedHashMap
                /*LinkedHashMap<String, String> postDataParams = new LinkedHashMap<>();
                postDataParams.put("Usuario", mUsuario);
                postDataParams.put("Contrasenia", mContrasenia);
                postDataParams.put("IdDispositivo", params[1].toString());*/
                ///endregion
                JSONObject postData = new JSONObject();
                try {
                    postData.put("Usuario", mUsuario);
                    postData.put("Contrasenia", mContrasenia);
                    postData.put("IdDispositivo", params[1].toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                is = new NetServicesUtils(LOG_TAG).performPostCall(URL, postData);
                if (is != null) {
                    responseString = new NetServicesUtils(LOG_TAG).ConvertStreamToString(is);
                    JSONObject jsonObject = new JSONObject(responseString);
                    //Log.d(LOG_TAG, "Res: " + responseString);
                    Gson gson = new Gson();
                    try {
                        Result = gson.fromJson(jsonObject.get("LoginResult").toString(), LoginResult.class);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    responseString = "Something went wrong";
                    throw new Exception("Something went wrong");
                }
            } catch (Exception ex) {
                exception = ex;
            }
        }
        if (params[0] == TipoOperacionUsuario.VehiculosGetAll) {
            try {
                CricParams = new CRICModuleParams(oActRef);
                int IdEmpleado = (int) params[1];
                String URL = CricParams.getBaseURI(false) + "VehiculosUsuario";
                String GetString = "?IdRepartidor=" + IdEmpleado;
                String BaseUrl = URL + GetString;
                Log.d(LOG_TAG, "URL: " + BaseUrl);
                is = new NetServicesUtils(LOG_TAG).ByGetMethod(BaseUrl, false);
                if (is != null) {
                    responseString = new NetServicesUtils(LOG_TAG).ConvertStreamToString(is);
                    JSONObject jsonObject = new JSONObject(responseString);
                    //Log.d(LOG_TAG, "Res: " + responseString);
                    Gson gson = new Gson();
                    try {
                        Result = gson.fromJson(jsonObject.get("VehiculosUsuarioResult").toString(), UsuarioVehiculosResult.class);
                        UsuarioVehiculosResult result = (UsuarioVehiculosResult) Result;
                        if (result.getVarMultiUso() != null)
                            if (result.getVarMultiUso().size() > 0) {
                                for (int i = 0; i < result.getVarMultiUso().size(); i++) {
                                    try {
                                        dbDatos.getDb().beginTransaction();
                                        VehiculosUsuario vu = result.getVarMultiUso().get(i);
                                        boolean insertTarea = false;
                                        if (dbDatos.ExisteVehiculo(vu.getIdVehiculo())) {
                                            insertTarea = dbDatos.ActualizarVehiculo(vu);
                                            ;
                                        } else {
                                            insertTarea = dbDatos.InsertarVehiculo(vu);
                                        }
                                        if (insertTarea) {
                                            dbDatos.getDb().setTransactionSuccessful();
                                        }
                                    } finally {
                                        dbDatos.getDb().endTransaction();
                                    }
                                }
                                result.setMessage("Vehiculos procesados con exito: " + result.getVarMultiUso().size());
                                Result = result;
                            }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw ex;
                    }
                } else {
                    responseString = "Something went wrong";
                    throw new Exception("Something went wrong");
                }
            } catch (Exception ex) {
                exception = ex;
            }
        }
        return Result;
    }

    @Override
    protected void onPostExecute(Object success) {
        if (exception == null) {
            onTaskCompleted.onTaskCompleted(success);
        } else {
            onTaskCompleted.onTaskError(exception.toString());
        }
    }

    @Override
    protected void onCancelled() {
        onTaskCompleted.onCancelled();
    }
}
