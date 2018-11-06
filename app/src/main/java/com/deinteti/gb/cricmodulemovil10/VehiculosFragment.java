package com.deinteti.gb.cricmodulemovil10;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deinteti.gb.cricmodulemovil10.Adapters.DividerItemDecoration;
import com.deinteti.gb.cricmodulemovil10.Adapters.VehiculoUsuarioRecyclerViewAdapter;
import com.deinteti.gb.cricmodulemovil10.Entidades.VehiculosUsuario;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class VehiculosFragment  extends DialogFragment {

    OnDialogDismissListener mCallback;
    private int newValSelected;
    RecyclerView recyclerView;
    public static VehiculoUsuarioRecyclerViewAdapter mAdapter;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VehiculosFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static VehiculosFragment newInstance(int columnCount) {
        VehiculosFragment fragment = new VehiculosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehiculousuario_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;
            setupRecyclerView(recyclerView, getActivity());
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setTitle("Vehiculos asignados");
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimaryLight);
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, final Context context) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new VehiculoUsuarioRecyclerViewAdapter(getActivity(), LoginActivity.dbDatos.ObtenerVehiculos(), new OnFragmentInteractionListener() {
            @Override
            public void onVehiculoSelected(int IdVehiculo) {
                mCallback.onDialogDismissListener(IdVehiculo);
                dismiss();
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnDialogDismissListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDialogDismissListener");
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onVehiculoSelected(int IdVehiculo);
    }
    // Container Activity must implement this interface
    public interface OnDialogDismissListener {
        public void onDialogDismissListener(int IdVehiculo);
    }

}
