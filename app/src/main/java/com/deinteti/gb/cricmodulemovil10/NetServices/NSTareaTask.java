package com.deinteti.gb.cricmodulemovil10.NetServices;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.deinteti.gb.cricmodulemovil10.AccesoDatos.OperacionesBaseDatos;
import com.deinteti.gb.cricmodulemovil10.Datos.CRICModuleParams;
import com.deinteti.gb.cricmodulemovil10.Entidades.BitacoraRepartosRpt;
import com.deinteti.gb.cricmodulemovil10.Entidades.Cliente;
import com.deinteti.gb.cricmodulemovil10.Entidades.DetalleDoctoRutaTarea;
import com.deinteti.gb.cricmodulemovil10.Entidades.DoctosRutasTareas;
import com.deinteti.gb.cricmodulemovil10.Entidades.OperationResultFault;
import com.deinteti.gb.cricmodulemovil10.Entidades.RutaTarea;
import com.deinteti.gb.cricmodulemovil10.Entidades.TareaResult;
import com.deinteti.gb.cricmodulemovil10.Entidades.TareasGenReporteResult;
import com.deinteti.gb.cricmodulemovil10.Entidades.Usuario;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusDoctoTarea;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusGenReporteResult;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusTarea;
import com.deinteti.gb.cricmodulemovil10.Enums.TipoOperacionUsuario;
import com.deinteti.gb.cricmodulemovil10.Enums.TipoTarea;
import com.deinteti.gb.cricmodulemovil10.GralUtils;
import com.deinteti.gb.cricmodulemovil10.Interfaces.OnTaskCompleted;
import com.deinteti.gb.cricmodulemovil10.Photo;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by desarrollo on 08/03/2018.
 */

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class NSTareaTask extends AsyncTask<Object, Void, Object> {
    private static String LOG_TAG = "NSTareaActivity";
    private final Usuario mUsuario;
    OnTaskCompleted onTaskCompleted;
    Activity oActRef;
    private Exception exception;
    CRICModuleParams CricParams;
    OperacionesBaseDatos dbDatos;

    public NSTareaTask(OnTaskCompleted onTaskCompleted, Activity oActRef, Usuario Usuario, OperacionesBaseDatos dbDatos) {
        exception = null;
        this.onTaskCompleted = onTaskCompleted;
        this.oActRef = oActRef;
        mUsuario = Usuario;
        this.dbDatos = dbDatos;
    }

    @Override
    protected Object doInBackground(Object... params) {
        // TODO: attempt authentication against a network service.
        Object Result = null;
        String responseString = "";
        InputStream is = null;
        if (params[0] == TipoOperacionUsuario.TareasCount) {
            ///region obtener numero de tareas desde servidor
            try {
                CricParams = new CRICModuleParams(oActRef);
                String URL = CricParams.getBaseURI(false) + "TareasCount";
                String GetString = "?IdRepartidor=" + mUsuario.getEmpleado().getIdEmpleado() + "&IdSucursal=" + mUsuario.getEmpleado().getSucursal().getIdSucursal();
                String BaseUrl = URL + GetString;
                //Log.d(LOG_TAG, "URL: " + BaseUrl);
                is = new NetServicesUtils(LOG_TAG).ByGetMethod(BaseUrl, false);
                if (is != null) {
                    responseString = new NetServicesUtils(LOG_TAG).ConvertStreamToString(is);
                    JSONObject jsonObject = new JSONObject(responseString);
                    //Log.d(LOG_TAG, "ResTareasCount: " + responseString);
                    try {
                        Result = jsonObject.getInt("TareasCountByUsuarioResult");
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
            ///endregion
        }
        if (params[0] == TipoOperacionUsuario.TareasGet) {
            ///region Obtener e insertar tareas
            try {
                CricParams = new CRICModuleParams(oActRef);
                String URL = CricParams.getBaseURI(false) + "Tareas";
                //Log.d(LOG_TAG, "URL: " + URL);
                String GetString = "?IdRepartidor=" + mUsuario.getEmpleado().getIdEmpleado() + "&IdSucursal=" + mUsuario.getEmpleado().getSucursal().getIdSucursal();
                String BaseUrl = URL + GetString;
                is = new NetServicesUtils(LOG_TAG).ByGetMethod(BaseUrl, false);
                if (is != null) {
                    responseString = new NetServicesUtils(LOG_TAG).ConvertStreamToString(is);
                    JSONObject jsonObject = new JSONObject(responseString);
                    //Log.d(LOG_TAG, "Res Tareas: " + responseString);
                    Gson gson = new Gson();
                    //Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCDateAdapter()).create();
                    try {
                        Result = gson.fromJson(jsonObject.get("TareasResult").toString(), TareaResult.class);
                        TareaResult ts = (TareaResult) Result;
                        if (ts.getTareas().size() > 0) {
                            for (int i = 0; i < ts.getTareas().size(); i++) {
                                try {
                                    dbDatos.getDb().beginTransaction();
                                    RutaTarea rt = ts.getTareas().get(i);
                                    boolean insertTarea = false;
                                    if (dbDatos.ExisteTareaByFolio(rt.getFolio())) {
                                        Log.i(LOG_TAG, "Es necesario actualizar la tarea");
                                        insertTarea = true;
                                    } else {
                                        insertTarea = dbDatos.InsertarTarea(rt);
                                    }
                                    if (insertTarea) {
                                        for (DoctosRutasTareas drt : rt.getDoctosRutasTareas()) {
                                            boolean insertCte = false;
                                            if (dbDatos.ExisteCliente(drt.getCveCliente()))
                                                insertCte = true;
                                            else
                                                insertCte = dbDatos.InsertarCliente(new Cliente(drt.getCveCliente(), drt.getNombreCliente()));
                                            if (insertCte) {
                                                boolean insertInfEnvio = false;
                                                if (dbDatos.ExisteInfEnvio(drt.getCveDatosEnvio()) || drt.getINFENVIO() == null)
                                                    insertInfEnvio = true;
                                                else
                                                    insertInfEnvio = dbDatos.InsertarInfEnvio(drt.getINFENVIO());

                                                if (insertInfEnvio) {
                                                    boolean insertDocto = false;
                                                    if (dbDatos.ExisteTareaDetalleByFolio(drt.getFolio(), drt.getIdDoctoTarea()))
                                                        insertDocto = true;
                                                    else
                                                        insertDocto = dbDatos.InsertarDetalleTarea(drt);
                                                    if (insertDocto && drt.getDetalleDoctoRutaTarea() != null) {
                                                        for (DetalleDoctoRutaTarea pdrt : drt.getDetalleDoctoRutaTarea()) {
                                                            boolean insertParDocto = false;
                                                            if (dbDatos.ExisteParTareaDetalleByFolio(pdrt))
                                                                insertParDocto = true;
                                                            else
                                                                insertParDocto = dbDatos.InsertarParDetalleTarea(pdrt);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        dbDatos.getDb().setTransactionSuccessful();
                                    }
                                } finally {
                                    dbDatos.getDb().endTransaction();
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Log.i(LOG_TAG, "Error Tareas import " + ex.getMessage());
                    }
                } else {
                    responseString = "Something went wrong";
                    throw new Exception("Something went wrong");
                }
            } catch (Exception ex) {
                exception = ex;
            }
            ///endregion
        }
        if (params[0] == TipoOperacionUsuario.TareasClean) {
            ///region eliminar todas las tareas de la base de datos
            try {
                CricParams = new CRICModuleParams(oActRef);
                try {
                    dbDatos.getDb().beginTransaction();
                    dbDatos.CleanBaseDatos();
                    dbDatos.getDb().setTransactionSuccessful();
                    Result = true;
                } finally {
                    dbDatos.getDb().endTransaction();
                }
            } catch (Exception ex) {
                exception = ex;
            }
            ///endregion
        }
        if (params[0] == TipoOperacionUsuario.ActualizaTarea) {
            ///region indicar llegada con el cliente,terminar tarea ok o no
            try {
                CricParams = new CRICModuleParams(oActRef);
                try {
                    dbDatos.getDb().beginTransaction();
                    DoctosRutasTareas Docto = (DoctosRutasTareas) params[1];
                    boolean commit = false;
                    if (dbDatos.ActualizarDoctoTarea(Docto.getEstatus(), Docto)) {
                        boolean continuar = true;
                        JSONArray ParDosctosRutaTareaJ = new JSONArray();
                        if (Docto.getEstatus() == EstatusDoctoTarea.ENTREGADO ||
                                Docto.getEstatus() == EstatusDoctoTarea.ENTREGA_PARCIAL) {
                            //recorreer las partidas a actualizar
                            if (Docto.getDetalleDoctoRutaTarea() != null) {
                                for (DetalleDoctoRutaTarea par : Docto.getDetalleDoctoRutaTarea()) {
                                    if (!dbDatos.ActualizarParDoctoTarea(par)) {
                                        continuar = false;
                                    } else {
                                        JSONObject Partida = new JSONObject();
                                        Partida.put(DetalleDoctoRutaTarea.FOLIO, par.getFolio());
                                        Partida.put(DetalleDoctoRutaTarea.DOCUMENTO, par.getDocumento());
                                        Partida.put(DetalleDoctoRutaTarea.CVE_ART, par.getCveArt());
                                        Partida.put(DetalleDoctoRutaTarea.NUM_PART, par.getNumPart());
                                        Partida.put(DetalleDoctoRutaTarea.CANT_RECIBIDA, par.getCantRecibida());
                                        Partida.put(DetalleDoctoRutaTarea.ESTATUS, par.getEstatus());
                                        ParDosctosRutaTareaJ.put(Partida);
                                    }
                                }
                            }
                        }
                        if (continuar) {
                            try {
                                String URL = CricParams.getBaseURI(false) + "ActDocRutaTarea";
                                Log.d(LOG_TAG, "URL: " + URL);
                                ///region test with LinkedHashMap
                /*LinkedHashMap<String, String> postDataParams = new LinkedHashMap<>();
                postDataParams.put("Usuario", mUsuario);
                postDataParams.put("Contrasenia", mContrasenia);
                postDataParams.put("IdDispositivo", params[1].toString());*/
                                ///endregion
                                JSONObject postData = new JSONObject();
                                try {
                                    postData.put("TipoActualizacion", Docto.getEstatus());
                                    JSONObject ActRutaTarea = new JSONObject();
                                    ActRutaTarea.put(DoctosRutasTareas.ID_SUCURSAL, Docto.getIdSucursal());
                                    ActRutaTarea.put(DoctosRutasTareas.FOLIO, Docto.getFolio());
                                    ActRutaTarea.put(DoctosRutasTareas.IDDOCTOTAREA, Docto.getIdDoctoTarea());
                                    ActRutaTarea.put(DoctosRutasTareas.CVE_CLIENTE, Docto.getCveCliente());
                                    if (Docto.getEstatus() == EstatusDoctoTarea.INICIADO) {
                                        //cuando sea indicar llegada
                                        ActRutaTarea.put(DoctosRutasTareas.LATITUD_INICIAL, Docto.getLatitudInicial());
                                        ActRutaTarea.put(DoctosRutasTareas.LONGITUD_INICIAL, Docto.getLongitudInicial());
                                        ActRutaTarea.put("FechaLlegada", Docto.getHoraInicialEntrega());
                                    } else if (Docto.getEstatus() == EstatusDoctoTarea.ENTREGADO ||
                                            Docto.getEstatus() == EstatusDoctoTarea.ENTREGA_PARCIAL
                                            || Docto.getEstatus() == EstatusDoctoTarea.NO_ENTREGA) {

                                        if (Docto.getEstatus() != EstatusDoctoTarea.NO_ENTREGA) {
                                            //Cuando sea entrega ya sea parcial o completa
                                            ActRutaTarea.put("DetalleDoctoRutaTarea", ParDosctosRutaTareaJ);
                                            ActRutaTarea.put("FechaTermino", Docto.getFechaEntrega());
                                        }
                                        //cuando sea entrega parcial o completa o bien no entrega
                                        ActRutaTarea.put(DoctosRutasTareas.OBSENTREGA, Docto.getObsEntrega());
                                        ActRutaTarea.put(DoctosRutasTareas.LATITUD_FINAL, Docto.getLatitudFinal());
                                        ActRutaTarea.put(DoctosRutasTareas.LONGITUD_FINAL, Docto.getLongitudFinal());
                                        ActRutaTarea.put("FechaSalida", Docto.getHoraFinalEntrega());
                                        if (Docto.getTipoTarea() == TipoTarea.Tarea) {
                                            if (Docto.isReqCapturaInfo())
                                                ActRutaTarea.put(DoctosRutasTareas.CAPTURAINFO, Docto.getCapturaInfo());
                                            if (Docto.isReqEvidenciaFoto())
                                                ActRutaTarea.put("EvidenciaFoto64", Docto.getEvidenciaFoto64());
                                        }
                                    }
                                    ActRutaTarea.put(DoctosRutasTareas.ESTATUS, Docto.getEstatus());

                                    postData.put("ActRutaTarea", ActRutaTarea);
                                    //GenLlamada(Docto);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                is = new NetServicesUtils(LOG_TAG).performPostCall(URL, postData);
                                if (is != null) {
                                    responseString = new NetServicesUtils(LOG_TAG).ConvertStreamToString(is);
                                    JSONObject jsonObject = new JSONObject(responseString);
                                    //Log.d(LOG_TAG, "Res Tareas: " + responseString);
                                    Gson gson = new Gson();
                                    //Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCDateAdapter()).create();
                                    Result = gson.fromJson(jsonObject.get("ActDocRutaTareaResult").toString(), OperationResultFault.class);
                                    OperationResultFault ts = (OperationResultFault) Result;
                                    if (ts != null)
                                        commit = ts.getResult() == 1;//OK
                                } else {
                                    responseString = "Something went wrong";
                                    Log.d(LOG_TAG, "ErrorActTarea: ");
                                    throw new Exception("Something went wrong");
                                }
                            } catch (Exception ex) {
                                exception = ex;
                            }
                        }

                    }
                    if (commit)
                        dbDatos.getDb().setTransactionSuccessful();
                    //Result = true;
                } finally {
                    dbDatos.getDb().endTransaction();
                }
            } catch (Exception ex) {
                exception = ex;
            }
            ///endregion
        }
        if (params[0] == TipoOperacionUsuario.TerminaTareaTemp) {
            ///region termina tarea
            try {
                CricParams = new CRICModuleParams(oActRef);
                try {
                    dbDatos.getDb().beginTransaction();
                    RutaTarea Tarea = (RutaTarea) params[1];
                    boolean commit = false;
                    if (dbDatos.ActualizarTarea(Tarea.getEstatus(), Tarea)) {
                        try {
                            String URL = CricParams.getBaseURI(false) + "ActTarea";
                            Log.d(LOG_TAG, "URL: " + URL);
                            JSONObject postData = new JSONObject();
                            try {
                                JSONObject ActTarea = new JSONObject();
                                ActTarea.put(RutaTarea.FOLIO, Tarea.getFolio());
                                ActTarea.put(RutaTarea.ID_EMPLEADO, Tarea.getIdEmpleado());
                                ActTarea.put(RutaTarea.ID_SUCURSAL, Tarea.getIdSucursal());
                                ActTarea.put(RutaTarea.ESTATUS, EstatusTarea.ENRETORNO);
                                postData.put("ActTarea", ActTarea);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            is = new NetServicesUtils(LOG_TAG).performPostCall(URL, postData);
                            if (is != null) {
                                responseString = new NetServicesUtils(LOG_TAG).ConvertStreamToString(is);
                                JSONObject jaObject = new JSONObject(responseString);
                                String ResJson = jaObject.getString("ActTareaResult");
                                Log.d(LOG_TAG, "Res: " + responseString);
                                Result = Boolean.valueOf(ResJson);
                                commit = Boolean.valueOf(ResJson);
                            } else {
                                responseString = "Something went wrong";
                                throw new Exception("Something went wrong");
                            }
                        } catch (Exception ex) {
                            exception = ex;
                        }
                        if (commit)
                            dbDatos.getDb().setTransactionSuccessful();
                        Result = true;
                    }
                } finally {
                    dbDatos.getDb().endTransaction();
                }
            } catch (Exception ex) {
                exception = ex;
            }
            ///endregion
        }
        if (params[0] == TipoOperacionUsuario.GeneraReporteDia) {
            ///region Genera reporte tareas
            try {
                CricParams = new CRICModuleParams(oActRef);
                try {
                    dbDatos.getDb().beginTransaction();
                    BitacoraRepartosRpt Bitacora = (BitacoraRepartosRpt) params[1];
                    boolean commit = false;
                    if (dbDatos.InsertarBitacoraTareas(Bitacora)) {
                        try {
                            String URL = CricParams.getBaseURI(false) + "TareasGenReporte";
                            String GetString = "?IdRepartidor=" + Bitacora.getIdEmpleado() + "&IdSucursal=" + Bitacora.getIdSucursal()
                                    + "&FechaReporte=" + GralUtils.getDateTimeRReverse(Bitacora.getFecha());
                            String BaseUrl = URL + GetString;
                            Log.d(LOG_TAG, "URL: " + BaseUrl);
                            is = new NetServicesUtils(LOG_TAG).ByGetMethod(BaseUrl, false);
                            if (is != null) {
                                Gson gson = new Gson();
                                responseString = new NetServicesUtils(LOG_TAG).ConvertStreamToString(is);
                                JSONObject jaObject = new JSONObject(responseString);
                                String ResJson = jaObject.getString("TareasGenReporteResult");
                                Log.d(LOG_TAG, "Res: " + responseString);
                                Result = gson.fromJson(ResJson, TareasGenReporteResult.class);
                                TareasGenReporteResult ts = (TareasGenReporteResult) Result;
                                commit = ts.getEstatus() == EstatusGenReporteResult.GENERADO_OK;
                            } else {
                                responseString = "Something went wrong";
                                throw new Exception("Something went wrong");
                            }
                        } catch (Exception ex) {
                            exception = ex;
                        }
                        if (commit)
                            dbDatos.getDb().setTransactionSuccessful();
                    }
                } finally {
                    dbDatos.getDb().endTransaction();
                }
            } catch (Exception ex) {
                exception = ex;
            }
            ///endregion
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

    public void GenLlamada(DoctosRutasTareas mItem) {
        Photo ph = new Photo();
        ph.setPhotoasBase64(mItem.getEvidenciaFoto64());
        ph.setPhotoName("Test1.jpeg");
        JSONObject postData = new JSONObject();
        InputStream is = null;
        String responseString = "";
        try {
            JSONObject postDataIm = new JSONObject();
            postDataIm.put("photoasBase64", ph.getPhotoasBase64());
            postDataIm.put("photoName", ph.getPhotoName());
            postData.put("photo", postDataIm);
            String url = "http://192.168.0.7:80/WSCRICModule/CRICModuleMbl.svc/RESTService/LoadPhoto";
            is = new NetServicesUtils("PhotoDemo").performPostCall(url, postData);
            if (is != null) {
                responseString = new NetServicesUtils("PhotoDemo").ConvertStreamToString(is);
                Log.i("PhotoDemo", responseString);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
