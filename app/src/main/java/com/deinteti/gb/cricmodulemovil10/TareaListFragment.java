package com.deinteti.gb.cricmodulemovil10;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deinteti.gb.cricmodulemovil10.Adapters.DividerItemDecoration;
import com.deinteti.gb.cricmodulemovil10.Adapters.TareaRecyclerViewAdapter;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusTarea;

import static android.app.Activity.RESULT_OK;

/**
 * An activity representing a list of Tareas. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TareaDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TareaListFragment extends Fragment {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    TextView textViewLeyenda;
    RecyclerView recyclerView;
    TareaRecyclerViewAdapter mAdapter;
    int TipoTareasLoad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_tarea_list);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarea_list, container, false);

        TipoTareasLoad = getArguments().getInt(MainActivity.ARG_TTareaLoad);
        textViewLeyenda = view.findViewById(R.id.txtLeyendaTareas);
        recyclerView = view.findViewById(R.id.tarea_list);
        Context context = view.getContext();
        setupRecyclerView(recyclerView, context);
        return view;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tarea_main_filtro, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                return true;
            case R.id.action_filtro_tarea:
                DialogFragment newFragment = new FiltroTareasFragment();
                newFragment.show(getFragmentManager(), "Filtro");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static int TAREAS_REQUEST_UPDATE = 1;

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, Context context) {
        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            //recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
            mAdapter = new TareaRecyclerViewAdapter(getActivity(), LoginActivity.dbDatos.obtenerTareasEncabezado(TipoTareasLoad), new com.deinteti.gb.cricmodulemovil10.Interfaces.OnListFragmentInteractionListener() {
                @Override
                public void onListFragmentInteraction(View v, String item) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, TareaDetailActivity.class);
                    intent.putExtra(TareaDetailActivity.ARG_ITEM_ID, item);
                    //context.startActivity(intent);
                    startActivityForResult(intent, TAREAS_REQUEST_UPDATE);

                }
            });
            recyclerView.setAdapter(mAdapter);
            recyclerView.addItemDecoration(
                    new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            if (mAdapter.getItemCount() == 0) {
                textViewLeyenda.setVisibility(View.VISIBLE);
                if (TipoTareasLoad == EstatusTarea.HISTORICO)
                    textViewLeyenda.setText(R.string.tareas_empty_historico);
                else
                    textViewLeyenda.setText(R.string.tareas_empty_proceso);
                recyclerView.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  Handle activity result here
        if (requestCode == TAREAS_REQUEST_UPDATE) {
            if (resultCode == RESULT_OK) {
                try {
                    mAdapter.swapCursor(LoginActivity.dbDatos.obtenerTareasEncabezado(TipoTareasLoad));
                } catch (Exception ex) {
                }
            }
        }
    }
}
