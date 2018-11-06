package com.deinteti.gb.cricmodulemovil10;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deinteti.gb.cricmodulemovil10.Adapters.DividerItemDecoration;
import com.deinteti.gb.cricmodulemovil10.Adapters.TareaDoctosRecyclerViewAdapter;
import com.deinteti.gb.cricmodulemovil10.Entidades.RutaTarea;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusTarea;
import com.deinteti.gb.cricmodulemovil10.Enums.TipoOperacionUsuario;
import com.deinteti.gb.cricmodulemovil10.Enums.TipoTarea;
import com.deinteti.gb.cricmodulemovil10.Interfaces.OnTaskCompleted;
import com.deinteti.gb.cricmodulemovil10.NetServices.NSTareaTask;

/**
 * An activity representing a single Tarea detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link TareaListFragment}.
 */
public class TareaDetailActivity extends AppCompatActivity {
    public static final String ARG_ITEM_ID = "Folio";
    String Folio;
    TareaDoctosRecyclerViewAdapter mAdapter;
    RecyclerView recyclerView;
    private RutaTarea mItem;

    public TextView mTipoFolio;
    public TextView mFechaAsig;
    public TextView mEstatus;
    public ImageView mTipoTarea;
    public TextView mFechaPromesa;

    public TextView mFechaCanc;
    public TextView mMotivoCanc;

    public TextView mFechaInicio;
    public TextView mFechaFin;

    public TextView mTareaobs;
    public TextView mlblTareaobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Detalle de tarea");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RevisaTareasPendientes();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTipoFolio = (TextView) findViewById(R.id.tvTipo);
        mFechaAsig = (TextView) findViewById(R.id.tvFecha_asig);
        mEstatus = (TextView) findViewById(R.id.tvEstatus);
        //mTipoTarea = (ImageView) findViewById(R.id.ivTipo_tarea);
        mFechaPromesa = (TextView) findViewById(R.id.tvFecha_Promesa);

        mFechaInicio = (TextView) findViewById(R.id.tvFechaInicio);
        mFechaFin = (TextView) findViewById(R.id.tvFechaFin);

        mFechaCanc = (TextView) findViewById(R.id.tvFecha_cancelacion);
        mMotivoCanc = (TextView) findViewById(R.id.tvMotivo_cancelacion);

        mTareaobs = (TextView) findViewById(R.id.tvTarea_observaciones);
        mlblTareaobs = (TextView) findViewById(R.id.lblTarea_observaciones);
        recyclerView = findViewById(R.id.rvPartidas);
        Folio = getIntent().getStringExtra(TareaDetailActivity.ARG_ITEM_ID);
        if (Folio != null)
            if (LoginActivity.dbDatos.ExisteTareaByFolio(Folio)) {
                loadRutaTarea();
                setupRecyclerView(recyclerView, this);
            }
    }

    private void loadRutaTarea() {
        new GetLawyerByIdTask().execute();
    }

    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return LoginActivity.dbDatos.obtenerTareasEncabezadoByFolio(Folio);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showLawyer(new RutaTarea(cursor));
            } else {
                /*showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();*/
            }
        }
    }

    private void showLawyer(RutaTarea Tarea) {
        this.mItem = Tarea;
        String tareaTitle = Tarea.getFolio();
        mTipoFolio.setText(tareaTitle);
        mFechaAsig.setText("Fch. Asignación: " + GralUtils.getDateFullTimeView(Tarea.getDFechaAsig()));
        mEstatus.setText("Estatus: " + EstatusTarea.GetDescripcion(Tarea.getEstatus()));
        mFechaPromesa.setText("Fch. Promesa: " + GralUtils.getDateFullTimeView(Tarea.getDFechaPromesa()));

        mFechaInicio.setText("Fch. y Hr ini.: " + GralUtils.getDateFullTimeView(Tarea.getDFechaInicio()));
        if (Tarea.getFechaTermino() != null)
            mFechaFin.setText("Fch. y Hr fin.:" + GralUtils.getDateFullTimeView(Tarea.getDFechaTermino()));
        else
            mFechaFin.setVisibility(View.GONE);

        if (Tarea.getEstatus() == EstatusTarea.CANCELADO) {
            mFechaCanc.setText("Fch. Cancelación: " + GralUtils.getDateFullTimeView(Tarea.getDFechaCancelacion()));
            mMotivoCanc.setText("Motivo: " + Tarea.getMotivoCancelacion());
        } else {
            mFechaCanc.setVisibility(View.GONE);
            mMotivoCanc.setVisibility(View.GONE);
        }
        if (Tarea.getObservaciones().trim().equals("")) {
            mTareaobs.setVisibility(View.GONE);
            mlblTareaobs.setVisibility(View.GONE);
        } else
            mTareaobs.setText(Tarea.getObservaciones());

        //assert recyclerView != null;
        //setupRecyclerView((RecyclerView) recyclerView);
    }

    static final int TAREA_REQUEST_UPDATE = 0;

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, final Context context) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
        mAdapter = new TareaDoctosRecyclerViewAdapter(this, LoginActivity.dbDatos.GetTareaDetalleByFolio(Folio), new com.deinteti.gb.cricmodulemovil10.Interfaces.OnListFragmentInteractionListenerDocto() {
            @Override
            public void onListFragmentInteraction(View v, String Folio, String Documento, int TipoTarea) {
                //Toast.makeText(context, "Mostrar partidas docto", Toast.LENGTH_SHORT).show();

                Context context = v.getContext();
                Intent intent = new Intent(context, ParDoctosActivity.class);
                intent.putExtra(ParDoctosActivity.ARG_ITEM_FOLIO, Folio);
                intent.putExtra(ParDoctosActivity.ARG_ITEM_DOCUMENTO, Documento);
                intent.putExtra(ParDoctosActivity.ARG_TIPO_TAREA, TipoTarea);
                //context.startActivity(intent);
                startActivityForResult(intent, TAREA_REQUEST_UPDATE);

            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == TAREA_REQUEST_UPDATE) {
            if (resultCode == RESULT_OK) {
                if (Folio != null) {
                    mAdapter.swapCursor(LoginActivity.dbDatos.GetTareaDetalleByFolio(Folio));
                    RevisaTareasPendientes();
                }
            }
        }
    }

    public void RevisaTareasPendientes() {
        boolean pendientes = LoginActivity.dbDatos.ExisteTareaPendientes(mItem.getFolio());
        if (!pendientes) {
            //notificar cambio de estatus de tarea
            mItem.setEstatus(EstatusTarea.TERMINADO);
            EjecutaProcesoTerminoT();
        }
    }

    NSTareaTask mNSTareaTask = null;
    public void EjecutaProcesoTerminoT() {
        //showProgress(true);
        mNSTareaTask = new NSTareaTask(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object feed) {
                mNSTareaTask = null;
                //showProgress(false);
                boolean correct = (boolean) feed;
                if (correct) {
                    ActivityUtils.AlertMessageToast(getApplicationContext(), "Tarea concluida");
                } else {
                    ActivityUtils.AlertMessageToast(getApplicationContext(), "No se actualizo tarea");
                }
                setResult(-1);
                finish();
            }

            @Override
            public void onTaskError(Object feed) {
                ActivityUtils.AlertOKMessage(TareaDetailActivity.this, "Actualiza tarea", "Error Act. tarea " + feed.toString());
                mNSTareaTask = null;
                //showProgress(false);
            }

            @Override
            public void onCancelled() {
                mNSTareaTask = null;
                //showProgress(false);
            }
        }, this, MainActivity.loginResult.getVarMultiUso(), LoginActivity.dbDatos);
        mNSTareaTask.execute(TipoOperacionUsuario.TerminaTareaTemp, mItem);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, TareaListFragment.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
