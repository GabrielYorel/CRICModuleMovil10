package com.deinteti.gb.cricmodulemovil10.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deinteti.gb.cricmodulemovil10.ActivityUtils;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusVehiculoEnum;
import com.deinteti.gb.cricmodulemovil10.Enums.VehiculosUsuarioEnum;
import com.deinteti.gb.cricmodulemovil10.R;
import com.deinteti.gb.cricmodulemovil10.VehiculosFragment.OnFragmentInteractionListener;
import com.deinteti.gb.cricmodulemovil10.Entidades.*;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link VehiculosUsuario} and makes a call to the
 * specified {@link OnFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class VehiculoUsuarioRecyclerViewAdapter extends RecyclerView.Adapter<VehiculoUsuarioRecyclerViewAdapter.VehiculoViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private final OnFragmentInteractionListener mListener;

    public VehiculoUsuarioRecyclerViewAdapter(Context context, Cursor cursor, OnFragmentInteractionListener listener) {
        this.mContext=context;
        this.mCursor=cursor;
        mListener = listener;
    }

    @Override
    public VehiculoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.fragment_vehiculousuario, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        /*View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_vehiculousuario, parent, false);*/
        return new VehiculoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VehiculoViewHolder holder, final int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        holder.mTxtDescr.setText(mCursor.getString(mCursor.getColumnIndex(VehiculosUsuario.DESCRIPCION)));
        holder.mTxtPlacas.setText("Placas: "+mCursor.getString(mCursor.getColumnIndex(VehiculosUsuario.PLACAS)));
        final int estatusV = mCursor.getInt(mCursor.getColumnIndex(VehiculosUsuario.ESTATUS));
        holder.mTxtEstatus.setText("Estatus: " + EstatusVehiculoEnum.GetDescripcion(estatusV));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    if(estatusV== EstatusVehiculoEnum.ACTIVO){
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        List<String> keys = obtenerIdVehiculo(position);
                        mListener.onVehiculoSelected(Integer.parseInt(keys.get(0)));
                    }else{
                        ActivityUtils.AlertOKMessage(mContext, "Validación", EstatusVehiculoEnum.GetDescripcion(estatusV));
                    }
                }
            }
        });
    }

    private List<String> obtenerIdVehiculo(int posicion) {
        List<String> keys = new ArrayList<>();
        if (mCursor != null) {
            if (mCursor.moveToPosition(posicion)) {
                keys.add(mCursor.getString(mCursor.getColumnIndex(VehiculosUsuario.IDVEHICULO)));
                return keys;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class VehiculoViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTxtDescr;
        public final TextView mTxtPlacas;
        public final TextView mTxtEstatus;

        public VehiculoViewHolder(View view) {
            super(view);
            mView = view;
            mTxtDescr = (TextView) view.findViewById(R.id.txtDescripcion);
            mTxtPlacas = (TextView) view.findViewById(R.id.txtPlacas);
            mTxtEstatus = (TextView) view.findViewById(R.id.txtEstatusV);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTxtDescr.getText() + "'";
        }
    }
}
