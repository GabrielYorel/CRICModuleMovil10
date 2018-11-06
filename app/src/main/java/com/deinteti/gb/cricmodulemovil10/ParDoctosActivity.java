package com.deinteti.gb.cricmodulemovil10;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.deinteti.gb.cricmodulemovil10.Entidades.Cliente;
import com.deinteti.gb.cricmodulemovil10.Entidades.DetalleDoctoRutaTarea;
import com.deinteti.gb.cricmodulemovil10.Entidades.DoctosRutasTareas;
import com.deinteti.gb.cricmodulemovil10.Entidades.OperationResultFault;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusDoctoTarea;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusPartidaDocto;
import com.deinteti.gb.cricmodulemovil10.Enums.TipoOperacionUsuario;
import com.deinteti.gb.cricmodulemovil10.Enums.TipoTarea;
import com.deinteti.gb.cricmodulemovil10.Interfaces.OnTaskCompleted;
import com.deinteti.gb.cricmodulemovil10.NetServices.NSTareaTask;
import com.deinteti.gb.cricmodulemovil10.NetServices.NetServicesUtils;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ParDoctosActivity extends AppCompatActivity {

    static final Integer LOCATION = 0x1;
    static final Integer CAMERA = 0x2;

    private LocationManager locationManager;
    double dLat;
    double dLon;
    boolean gpsActived;

    public static final String ARG_ITEM_FOLIO = "Folio";
    public static final String ARG_ITEM_DOCUMENTO = "Documento";
    public static final String ARG_TIPO_TAREA = "TipoTarea";
    private DoctosRutasTareas mItem;

    public TextView mNoFacturaD;
    public TextView mFechaFacturaD;
    public TextView mEstatusFacturaD;
    public TextView mFchEntregaD;
    public TextView mHora_inicio;
    public TextView mHora_fin;
    public TextView mClave_cliente;
    public TextView mNombre_cliente;
    public TextView mFecha_cancelacion;
    public TextView mMotivo_cancelacion;
    public TextView mTarea_docto_obs;
    public TextView mlblTarea_docto_obs;
    public TextView mlblLatLon;
    public TextView mlblTipoTarea;

    FloatingActionButton btnIndicarLlegada;
    FloatingActionButton btnTerminarEntrega;
    FloatingActionButton btnNoEntrega;
    android.support.design.widget.FloatingActionButton btnDireccionEnvio;

    private View mProgressView;
    private View mLoginFormView;
    int newEstatusDoc;
    EditText mEtObservaciones;
    Fragment currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_par_doctos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Detalle tarea");
        mLoginFormView = findViewById(R.id.form_par_view);
        mProgressView = findViewById(R.id.tarea_progress);

        //mlblLatLon = findViewById(R.id.lblLatLon);

        btnIndicarLlegada = (FloatingActionButton) findViewById(R.id.btnIndicarLlegada);
        btnTerminarEntrega = (FloatingActionButton) findViewById(R.id.btnTerminarEntrega);
        btnNoEntrega = (FloatingActionButton) findViewById(R.id.btnNoEntrega);
        btnDireccionEnvio = (android.support.design.widget.FloatingActionButton) findViewById(R.id.btnDireccionEnvio);

        btnIndicarLlegada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dLat == 0 && dLon == 0)
                    ActivityUtils.AlertOKMessage(ParDoctosActivity.this, "Error en ubicación",
                            "Error al obtener tu ubicación/nno es posoble continuar");
                else
                    IndicarLlegada("¿Confirmar llegada?");
            }
        });

        btnTerminarEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mItem.getTipoTarea() == TipoTarea.Entrega) {
                    int totalCount = ParDoctosFragment.mAdapter.getItemCount();
                    int selected = ParDoctosFragment.mAdapter.getSelectedItemCount();
                    if (selected == 0) {
                        ActivityUtils.AlertOKMessage(ParDoctosActivity.this, "Terminar tarea", "Debes seleccionar al menos un producto a entregar");
                    } else if (esEntregaParcial()) {
                        AprobarEntregaPar(view);
                    } else {
                        ConfirmarInfo("¿Realizar entrega?");
                    }
                } else if (mItem.getTipoTarea() == TipoTarea.Tarea) {
                    boolean continuar = true;
                    if (mItem.isReqEvidenciaFoto()) {
                        if (((DetalleTareaActividadFragment) currentFragment).fileUri == null) {
                            ActivityUtils.AlertOKMessage(ParDoctosActivity.this, "Info. incompleta", "Es necesario capturar una foto de evidencia");
                            continuar = false;
                        }
                    }
                    if (mItem.isReqCapturaInfo()) {
                        if (((DetalleTareaActividadFragment) currentFragment).txtInfoCaptura.getText().toString().equals("")) {
                            ActivityUtils.AlertOKMessage(ParDoctosActivity.this, "Info. incompleta", "Es necesario capturar información");
                            continuar = false;
                        }
                    }
                    if (continuar) {
                        ConfirmarInfo("¿Terminar tarea?");
                    }
                } else if (mItem.getTipoTarea() == TipoTarea.Cobranza) {
                }
            }
        });
        btnNoEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmarNoEntrega();
            }
        });

        btnDireccionEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItem.getINFENVIO() != null) {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + mItem.getINFENVIO().getDirBsqGoogle());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                    //ActivarGPS();
                }
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mlblTipoTarea = (TextView) findViewById(R.id.tvTipoTareaDisplayDetalle);
        mNoFacturaD = (TextView) findViewById(R.id.tvNoFacturaD);
        mFechaFacturaD = (TextView) findViewById(R.id.tvFechaFacturaD);
        mEstatusFacturaD = (TextView) findViewById(R.id.tvEstatusFacturaD);
        mFchEntregaD = (TextView) findViewById(R.id.tvFchEntregaD);
        mHora_inicio = (TextView) findViewById(R.id.tvHora_inicio);
        mHora_fin = (TextView) findViewById(R.id.tvHora_fin);
        mClave_cliente = (TextView) findViewById(R.id.tvClave_cliente);
        mNombre_cliente = (TextView) findViewById(R.id.tvNombre_cliente);
        mFecha_cancelacion = (TextView) findViewById(R.id.tvFecha_cancelacion);
        mMotivo_cancelacion = (TextView) findViewById(R.id.tvMotivo_cancelacion);

        mTarea_docto_obs = (TextView) findViewById(R.id.tvTarea_docto_observaciones);
        mlblTarea_docto_obs = (TextView) findViewById(R.id.lblTarea_doc_observaciones);
        String Folio = getIntent().getStringExtra(ParDoctosActivity.ARG_ITEM_FOLIO);
        String IdDocto = getIntent().getStringExtra(ParDoctosActivity.ARG_ITEM_DOCUMENTO);
        if (LoginActivity.dbDatos.ExisteTareaDetalleByFolio(Folio, IdDocto)) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            loadRutaTarea();
        }
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ParDoctosActivity.ARG_ITEM_FOLIO,
                    getIntent().getStringExtra(ParDoctosActivity.ARG_ITEM_FOLIO));
            arguments.putString(ParDoctosActivity.ARG_ITEM_DOCUMENTO,
                    getIntent().getStringExtra(ParDoctosActivity.ARG_ITEM_DOCUMENTO));
            int tipoTarea = getIntent().getIntExtra(ParDoctosActivity.ARG_TIPO_TAREA, TipoTarea.Tarea);
            arguments.putInt(ParDoctosActivity.ARG_TIPO_TAREA, tipoTarea);

            if (tipoTarea == TipoTarea.Tarea)
                currentFragment = new DetalleTareaActividadFragment();
            else
                currentFragment = new ParDoctosFragment();
            if (currentFragment != null) {
                currentFragment.setArguments(arguments);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.add(R.id.pardoc_detail_container, currentFragment);
                //transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();
            }
        }
    }

    public boolean esEntregaParcial() {
        boolean esEntregaParcial = false;
        int totalCount = ParDoctosFragment.mAdapter.getItemCount();
        int selected = ParDoctosFragment.mAdapter.getSelectedItemCount();
        if (selected < totalCount)
            return true;
        else {
            SparseArray selectedItems = ParDoctosFragment.mAdapter.getSelectedItems();
            if (newEstatusDoc != EstatusDoctoTarea.NO_ENTREGA) {
                for (int i = 0; i < selectedItems.size(); i++) {
                    int key = selectedItems.keyAt(i);
                    DetalleDoctoRutaTarea obj = (DetalleDoctoRutaTarea) selectedItems.get(key);
                    if (obj.isChecked()) {
                        if (obj.getCantRecibida() < obj.getCantSalida()) {
                            esEntregaParcial = true;
                            break;
                        }
                    }
                }
            }
        }
        return esEntregaParcial;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void AprobarEntregaPar(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_entrega_parc, null);
        mEtObservaciones = alertLayout.findViewById(R.id.txtobs_entrega);
        AppCompatTextView mAppCompatTextView = alertLayout.findViewById(R.id.lblEntregaTipo);
        mAppCompatTextView.setText("¿Deseas realizar una entrega parcial?");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Confirmar entrega");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Editable text = mEtObservaciones.getText();
                mItem.setObsEntrega(text == null ? "" : text.toString());
                newEstatusDoc = EstatusDoctoTarea.ENTREGA_PARCIAL;
                Save();
            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void Save() {
        Calendar cal = new GregorianCalendar();
        Date date = cal.getTime();
        mItem.setEstatus(newEstatusDoc);
        mItem.setLatitudFinal(dLat);
        mItem.setLongitudFinal(dLon);
        mItem.setHoraFinalEntrega(GralUtils.getDateFullTime(date));
        if (mItem.getTipoTarea() == TipoTarea.Entrega) {
            SparseArray selectedItems = ParDoctosFragment.mAdapter.getSelectedItems();
            if (newEstatusDoc != EstatusDoctoTarea.NO_ENTREGA) {
                mItem.setFechaEntrega(GralUtils.getDateFullTime(date));
                mItem.setDetalleDoctoRutaTarea(new ArrayList<DetalleDoctoRutaTarea>());
                for (int i = 0; i < selectedItems.size(); i++) {
                    int key = selectedItems.keyAt(i);
                    DetalleDoctoRutaTarea obj = (DetalleDoctoRutaTarea) selectedItems.get(key);
                    if (obj.isChecked()) {
                        if (obj.getCantRecibida() == obj.getCantSalida())
                            obj.setEstatus(EstatusPartidaDocto.ENTREGADO);
                        if (obj.getCantRecibida() < obj.getCantSalida())
                            obj.setEstatus(EstatusPartidaDocto.ENTREGA_PARCIAL);
                        mItem.getDetalleDoctoRutaTarea().add(obj);
                    }
                }
            }
            EjecutaProcesoTermino();
        } else if (mItem.getTipoTarea() == TipoTarea.Tarea) {
            if (newEstatusDoc != EstatusDoctoTarea.NO_ENTREGA) {
                mItem.setFechaEntrega(GralUtils.getDateFullTime(date));
                boolean continuar = true;
                if (mItem.isReqCapturaInfo()) {
                    mItem.setCapturaInfo(((DetalleTareaActividadFragment) currentFragment).getInfoCaptura());
                }
                if (mItem.isReqEvidenciaFoto()) {
                    byte[] oFoto = PictureTools.convertImageToByte(((DetalleTareaActividadFragment) currentFragment).fileUri, this);
                    if (oFoto != null) {
                        mItem.setEvidenciaFotoRuta(((DetalleTareaActividadFragment) currentFragment).fileUri.getPath());
                        Bitmap image = PictureTools.getImageStandarCricModule(((DetalleTareaActividadFragment) currentFragment).fileUri);
                        mItem.setEvidenciaFoto64(PictureTools.getBase64Image(image));
                    } else {
                        continuar = false;
                    }
                }
                if (continuar) {
                    EjecutaProcesoTermino();
                }
            }
        }
    }

    public void ConfirmarNoEntrega() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_entrega_parc, null);
        mEtObservaciones = alertLayout.findViewById(R.id.txtobs_entrega);
        AppCompatTextView mAppCompatTextView = alertLayout.findViewById(R.id.lblEntregaTipo);
        mAppCompatTextView.setText("¿Confirmar no entrega?");
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("No entrega");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Editable text = mEtObservaciones.getText();
                String txt = (text == null ? "" : text.toString().trim());
                if (!txt.equals("")) {
                    mItem.setObsEntrega(text == null ? "" : text.toString());
                    newEstatusDoc = EstatusDoctoTarea.NO_ENTREGA;
                    Save();
                } else {
                    ActivityUtils.AlertOKMessage(ParDoctosActivity.this, "Requerido", "Captura las observaciones");
                }
            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void ConfirmarInfo(String sMsg) {
        AlertDialog.Builder oADb = new AlertDialog.Builder(ParDoctosActivity.this);
        oADb.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                newEstatusDoc = EstatusDoctoTarea.ENTREGADO;
                Save();
            }
        });
        oADb.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog oAD = oADb.create();
        oAD.setTitle("Confirmar acción");
        oAD.setMessage(sMsg);
        oAD.show();
    }

    public void IndicarLlegada(String sMsg) {
        AlertDialog.Builder oADb = new AlertDialog.Builder(ParDoctosActivity.this);
        oADb.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Calendar cal = new GregorianCalendar();
                Date date = cal.getTime();
                mItem.setEstatus(EstatusDoctoTarea.INICIADO);
                mItem.setLatitudInicial(dLat);
                mItem.setLongitudInicial(dLon);
                mItem.setHoraInicialEntrega(GralUtils.getDateFullTime(date));
                EjecutaProcesoLlegada();
            }
        });
        oADb.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //notigng to do
                /*setResult(-1);
                finish();*/
            }
        });
        AlertDialog oAD = oADb.create();
        oAD.setTitle("Confirmar llegada");
        oAD.setMessage(sMsg);
        oAD.show();
    }

    NSTareaTask mNSTareaTask = null;

    public void EjecutaProcesoLlegada() {
        //showProgress(true);
        mNSTareaTask = new NSTareaTask(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object feed) {
                mNSTareaTask = null;
                showProgress(false);
                OperationResultFault correct = (OperationResultFault) feed;
                if (correct.getResult() == 1) {//OK
                    ActivityUtils.AlertMessageToast(getApplicationContext(), "Actualización correcta");
                    setResult(-1);
                    finish();
                } else {
                    ActivityUtils.AlertOKMessage(ParDoctosActivity.this, "Advertencia", correct.getErrorMessage());
                }
            }

            @Override
            public void onTaskError(Object feed) {
                ActivityUtils.AlertOKMessage(ParDoctosActivity.this, "Actualiza tarea", "Error Act. tareas " + feed.toString());
                mNSTareaTask = null;
                showProgress(false);
            }

            @Override
            public void onCancelled() {
                mNSTareaTask = null;
                showProgress(false);
            }
        }, this, MainActivity.loginResult.getVarMultiUso(), LoginActivity.dbDatos);
        mNSTareaTask.execute(TipoOperacionUsuario.ActualizaTarea, mItem);
    }

    public void EjecutaProcesoTermino() {
        //showProgress(true);
        mNSTareaTask = new NSTareaTask(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object feed) {
                mNSTareaTask = null;
                showProgress(false);
                OperationResultFault correct = (OperationResultFault) feed;
                if (correct.getResult() == 1) {//OK
                    ActivityUtils.AlertMessageToast(getApplicationContext(), "Actualización correcta");
                    setResult(-1);
                    finish();
                } else {
                    ActivityUtils.AlertOKMessage(ParDoctosActivity.this, "Advertencia", correct.getErrorMessage());
                }
            }

            @Override
            public void onTaskError(Object feed) {
                mNSTareaTask = null;
                showProgress(false);
                ActivityUtils.AlertOKMessage(ParDoctosActivity.this, "Actualiza tarea", "Error Act. tareas " + feed.toString());
            }

            @Override
            public void onCancelled() {
                mNSTareaTask = null;
                showProgress(false);
            }
        }, this, MainActivity.loginResult.getVarMultiUso(), LoginActivity.dbDatos);
        mNSTareaTask.execute(TipoOperacionUsuario.ActualizaTarea, mItem);
    }

    private void loadRutaTarea() {
        new GetLawyerByIdTask().execute();
    }

    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return LoginActivity.dbDatos.GetTareaDetalleByFolio(getIntent().getStringExtra(ParDoctosActivity.ARG_ITEM_FOLIO),
                    getIntent().getStringExtra(ParDoctosActivity.ARG_ITEM_DOCUMENTO));
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showLawyer(new DoctosRutasTareas(cursor));
            } else {
                /*showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();*/
            }
        }
    }

    private void showLawyer(DoctosRutasTareas DoctoTarea) {
        this.mItem = DoctoTarea;
        String sTipoTarea = TipoTarea.GetDescripcion(mItem.getTipoTarea());
        mlblTipoTarea.setText(sTipoTarea);
        mItem.setINFENVIO(LoginActivity.dbDatos.getInfEnvioByCve(mItem.getCveDatosEnvio()));
        mNoFacturaD.setText((mItem.getTipoTarea() != TipoTarea.Tarea ? "Docto.: " : "Id Tarea:") + DoctoTarea.getIdDoctoTarea());
        if (mItem.getTipoTarea() != TipoTarea.Tarea)
            mFechaFacturaD.setText("Fch.: " + DoctoTarea.getFechaDocumento());
        else
            mFechaFacturaD.setVisibility(View.GONE);

        mEstatusFacturaD.setText("Estatus: " + EstatusDoctoTarea.GetDescripcion(DoctoTarea.getEstatus(), mItem.getTipoTarea()));
        if (DoctoTarea.getFechaEntrega() != null)
            mFchEntregaD.setText("Fch. Entrega: " + GralUtils.getDateFullTimeView(DoctoTarea.getDFechaEntrega()));
        else
            mFchEntregaD.setVisibility(View.GONE);
        if (DoctoTarea.getHoraInicialEntrega() != null)
            mHora_inicio.setText("Inicio: " + GralUtils.getDateFullTimeView(DoctoTarea.getDHoraInicialEntrega()));
        else
            mHora_inicio.setVisibility(View.GONE);
        if (DoctoTarea.getHoraFinalEntrega() != null)
            mHora_fin.setText("Fin: " + GralUtils.getDateFullTimeView(DoctoTarea.getDHoraFinalEntrega()));
        else
            mHora_fin.setVisibility(View.GONE);

        if (LoginActivity.dbDatos.ExisteCliente(DoctoTarea.getCveCliente())) {
            Cursor cursor = LoginActivity.dbDatos.GetCliente(DoctoTarea.getCveCliente());
            if (cursor != null && cursor.moveToLast()) {
                Cliente cte = new Cliente(cursor);
                mClave_cliente.setText(cte.getCveCliente());
                mNombre_cliente.setText(cte.getNombre());
            }
        }

        if (DoctoTarea.getEstatus() == EstatusDoctoTarea.CANCELADO) {
            mFecha_cancelacion.setText("Fch. Cancelación: " + GralUtils.getDateFullTimeView(DoctoTarea.getDFechaCancelacion()));
            mMotivo_cancelacion.setText("Motivo: " + DoctoTarea.getMotivoCancelacion());
        } else {
            mFecha_cancelacion.setVisibility(View.GONE);
            mMotivo_cancelacion.setVisibility(View.GONE);
        }
        if (DoctoTarea.getObservaciones().equals("")) {
            mTarea_docto_obs.setVisibility(View.GONE);
            mlblTarea_docto_obs.setVisibility(View.GONE);
        } else
            mTarea_docto_obs.setText(DoctoTarea.getObservaciones());

        if (DoctoTarea.getEstatus() == EstatusDoctoTarea.INICIADO) {
            btnIndicarLlegada.setVisibility(View.GONE);
            EvaluatePermission();
        } else if (DoctoTarea.getEstatus() == EstatusDoctoTarea.SIN_ENTREGAR) {
            btnTerminarEntrega.setVisibility(View.GONE);
            btnNoEntrega.setVisibility(View.GONE);
            EvaluatePermission();
        }
        if (DoctoTarea.getEstatus() != EstatusDoctoTarea.INICIADO
                && DoctoTarea.getEstatus() != EstatusDoctoTarea.SIN_ENTREGAR) {
            btnIndicarLlegada.setEnabled(false);
            btnTerminarEntrega.setEnabled(false);
            btnNoEntrega.setEnabled(false);
        }
        if (mItem.getTipoTarea() == TipoTarea.Tarea) {
            btnDireccionEnvio.setVisibility(View.GONE);
        }
        switch (mItem.getTipoTarea()) {
            case TipoTarea.Tarea:
                btnTerminarEntrega.setTitle("Terminar tarea");
                btnNoEntrega.setTitle("Terminar NO OK");
                break;
            case TipoTarea.Cobranza:
                btnTerminarEntrega.setTitle("Terminar cobranza");
                btnNoEntrega.setTitle("NO Cobranza");
                break;
            case TipoTarea.Entrega:
                btnTerminarEntrega.setTitle("Terminar entrega");
                btnNoEntrega.setTitle("NO Entrega");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (gpsActived)
            ApagarGPS();
        super.onDestroy();
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            newLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //Toast.makeText(getBaseContext(),"onStatusChanged",Toast.LENGTH_SHORT);
        }

        @Override
        public void onProviderEnabled(String provider) {
            //Toast.makeText(getBaseContext(),"onProviderEnabled",Toast.LENGTH_SHORT);
        }

        @Override
        public void onProviderDisabled(String provider) {
            //Toast.makeText(getBaseContext(),"onProviderDisabled",Toast.LENGTH_SHORT);
        }
    };

    private void newLocation(Location pLoc) {
        this.dLat = pLoc.getLatitude();
        this.dLon = pLoc.getLongitude();
        //mlblLatLon.setText("Lat.: " + dLat + " Lon.: " + dLon);
        /*Toast.makeText(getApplicationContext(), "Detectado (" + String.valueOf(this.dLat) + "," +
                String.valueOf(this.dLon) + ")", Toast.LENGTH_LONG).show();*/
        /*Log.w("[CHECK]", "Detectado (" + String.valueOf(this.dLat) + "," +
                String.valueOf(this.dLon) + ")");*/
    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    public void EvaluatePermission() {
        if (checkLocation())
            askForPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, LOCATION);
        else
            Toast.makeText(this, "Erorr al activar el GPS", Toast.LENGTH_SHORT);
        askForPermission(android.Manifest.permission.CAMERA, CAMERA);
        askForPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, CAMERA);
        //askForPermission(Manifest.permission.ACCESS_COARSE_LOCATION, LOCATION);
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void ActivarGPS() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 20 * 1000, 10, locationListener);
        Toast.makeText(getApplicationContext(), "Activando GPS...", Toast.LENGTH_SHORT).show();
        Criteria criteria = new Criteria();
        //Aseguramos de que el uno poseible sea GPS
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //Obtenermos el mejor proveedor
        String provider = locationManager.getBestProvider(criteria, true);
        //obtenermo sla localizacion actual
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            gpsActived = true;
            newLocation(location);
        } else {
            /*Toast.makeText(this, "Localización invalida, No es posible continuar", Toast.LENGTH_SHORT).show();*/
            btnIndicarLlegada.setEnabled(false);
            btnTerminarEntrega.setEnabled(false);
            btnNoEntrega.setEnabled(false);
        }
    }

    public void ApagarGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
            Toast.makeText(getApplicationContext(), "Desactivando GPS...", Toast.LENGTH_SHORT).show();
        }
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            }
        } else {
            //Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
            onRequestPermissionsResult(requestCode, new String[]{permission}, new int[]{0});
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                //Location
                case 1:
                    ActivarGPS();
                    break;
            }
            //Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permiso denegado, No es posible continuar", Toast.LENGTH_SHORT).show();
            btnIndicarLlegada.setVisibility(View.GONE);
            btnTerminarEntrega.setVisibility(View.GONE);
            btnNoEntrega.setVisibility(View.GONE);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Habilitar ubicación")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }
}
