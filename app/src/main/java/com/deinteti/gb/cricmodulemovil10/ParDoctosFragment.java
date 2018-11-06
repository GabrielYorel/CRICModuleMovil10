package com.deinteti.gb.cricmodulemovil10;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deinteti.gb.cricmodulemovil10.Adapters.DividerItemDecoration;
import com.deinteti.gb.cricmodulemovil10.Adapters.ParTareaDoctosRecyclerViewAdapter;
import com.deinteti.gb.cricmodulemovil10.Entidades.DetalleDoctoRutaTarea;
import com.deinteti.gb.cricmodulemovil10.Entidades.DoctosRutasTareas;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link ParDoctosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParDoctosFragment extends Fragment implements NumParSelectorFragment.OnFragmentInteractionListener {

    //private OnFragmentInteractionListener mListener;
    private DoctosRutasTareas mItem;
    public static ParTareaDoctosRecyclerViewAdapter mAdapter;
    RecyclerView recyclerView;
    public static String NUMMAXPAR = "MaxNumPar";
    public static String NUMPARSELECTED = "NumParSelected";
    public static int PARSELECTEDREQ = 9;
    private int currentPosition = -1;

    public ParDoctosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ParDoctosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParDoctosFragment newInstance(String param1, String param2) {
        ParDoctosFragment fragment = new ParDoctosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_par_doctos, container, false);
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.rvPartidasDoc);
        if (LoginActivity.dbDatos.ExisteTareaDetalleByFolio(getArguments().getString(ParDoctosActivity.ARG_ITEM_FOLIO),
                getArguments().getString(ParDoctosActivity.ARG_ITEM_DOCUMENTO))) {
            loadRutaTarea();
        }
        return view;
    }

    private void loadRutaTarea() {
        new GetLawyerByIdTask().execute();
    }

    @Override
    public void onCantParSelected(int Cantidad) {
        if (currentPosition != -1) {
            ((DetalleDoctoRutaTarea) mAdapter.getSelectedItems().get(currentPosition)).setCantRecibida(Cantidad);
            mAdapter.notifyItemChanged(currentPosition);
        }
    }

    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return LoginActivity.dbDatos.GetTareaDetalleByFolio(getArguments().getString(ParDoctosActivity.ARG_ITEM_FOLIO),
                    getArguments().getString(ParDoctosActivity.ARG_ITEM_DOCUMENTO));
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
        mItem = DoctoTarea;
        //mItem.setINFENVIO(LoginActivity.dbDatos.getInfEnvioByCve(mItem.getCveDatosEnvio()));
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
        mAdapter = new ParTareaDoctosRecyclerViewAdapter(getActivity(), LoginActivity.dbDatos.GetParTareaDetalleByFolio(mItem.getFolio(), mItem.getIdDoctoTarea()),
                new com.deinteti.gb.cricmodulemovil10.Interfaces.OnListFragmentInteractionListenerParDocto() {
                    @Override
                    public void onListFragmentInteraction(View v, DetalleDoctoRutaTarea Partida, int position) {
                        currentPosition = position;
                        DialogFragment newFragment = new NumParSelectorFragment();
                        Bundle arguments = new Bundle();
                        arguments.putInt(ParDoctosFragment.NUMMAXPAR, (int) Partida.getCantSalida());
                        arguments.putInt(ParDoctosFragment.NUMPARSELECTED, (int) Partida.getCantRecibida());
                        newFragment.setArguments(arguments);
                        newFragment.setTargetFragment(ParDoctosFragment.this, PARSELECTEDREQ);
                        newFragment.show(getFragmentManager(), "SelecPar");
                    }
                }, mItem.getEstatus());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
