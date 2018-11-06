package com.deinteti.gb.cricmodulemovil10.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.deinteti.gb.cricmodulemovil10.Entidades.DetalleDoctoRutaTarea;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusDoctoTarea;
import com.deinteti.gb.cricmodulemovil10.Interfaces.OnListFragmentInteractionListenerParDocto;
import com.deinteti.gb.cricmodulemovil10.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by desarrollo on 19/03/2018.
 */

/**
 * {@link RecyclerView.Adapter} that can display a {@link DetalleDoctoRutaTarea} and makes a call to the
 * specified {@link OnListFragmentInteractionListenerParDocto}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ParTareaDoctosRecyclerViewAdapter extends
        RecyclerView.Adapter<ParTareaDoctosRecyclerViewAdapter.ParTareaDoctoViewHolder> implements View.OnClickListener {
    private Context mContext;
    private Cursor mCursor;
    private SparseArray seleccionados;
    private int EstatusDoc;

    private final OnListFragmentInteractionListenerParDocto mListener;

    public ParTareaDoctosRecyclerViewAdapter(Context context, Cursor cursor, OnListFragmentInteractionListenerParDocto listener,
                                             int EstatusDoc) {
        this.mContext = context;
        this.mCursor = cursor;
        mListener = listener;
        seleccionados = new SparseArray();
        this.EstatusDoc = EstatusDoc;
    }

    @Override
    public ParTareaDoctoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.pardoc_list_content_adtr, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ParTareaDoctoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ParTareaDoctoViewHolder holder, final int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        holder.mCve_producto.setText("Clave: " + mCursor.getString(mCursor.getColumnIndex(DetalleDoctoRutaTarea.CVE_ART)));
        holder.mDescr_producto.setText("Descr.: " + mCursor.getString(mCursor.getColumnIndex(DetalleDoctoRutaTarea.DESCRIPCION)));
        holder.mLinea.setText("Linea: " + mCursor.getString(mCursor.getColumnIndex(DetalleDoctoRutaTarea.LINEA)));
        holder.mCantidad.setText("Cant.: " + mCursor.getDouble(mCursor.getColumnIndex(DetalleDoctoRutaTarea.CANT_SALIDA)) + "");

        if (EstatusDoc == EstatusDoctoTarea.ENTREGADO || EstatusDoc == EstatusDoctoTarea.ENTREGA_PARCIAL
                || EstatusDoc == EstatusDoctoTarea.NO_ENTREGA) {
            if (holder.mCantidadEntregada.getVisibility() == View.GONE)
                holder.mCantidadEntregada.setVisibility(View.VISIBLE);
            holder.mCantidadEntregada.setText("Recibido: " + mCursor.getDouble(mCursor.getColumnIndex(DetalleDoctoRutaTarea.CANT_RECIBIDA)) + "");
        } else {
            holder.mCantidadEntregada.setVisibility(View.GONE);
        }
        final DetalleDoctoRutaTarea row = getIdParDocto(position);//desde el cursor
        DetalleDoctoRutaTarea ParTemp = getParByPosition(position);//desde la lista seleccionada
        holder.mView.setTag(row);
        holder.mSelected.setTag(position);
        holder.mSelected.setChecked(ParTemp != null ? ParTemp.isChecked() : row.isChecked());
        holder.mSelected.setOnClickListener(this);
        if (EstatusDoc == EstatusDoctoTarea.INICIADO) {
            holder.mSelected.setVisibility(View.VISIBLE);
            holder.mCantidadSelect.setVisibility(View.VISIBLE);
            if (ParTemp != null) {
                holder.mCantidadSelect.setText("Selec." + ParTemp.getCantRecibida());
            } else {
                holder.mCantidadSelect.setText("Selec." + row.getCantSalida());
            }
        } else {
            holder.mSelected.setVisibility(View.GONE);
            holder.mCantidadSelect.setVisibility(View.GONE);
        }
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (EstatusDoc == EstatusDoctoTarea.INICIADO) {
                    DetalleDoctoRutaTarea Par = (DetalleDoctoRutaTarea) v.getTag();
                    DetalleDoctoRutaTarea ParTemp = getParByPosition(position);
                    if (ParTemp != null)
                        if (Par.getCantSalida() > 1 && ParTemp.isChecked()) {
                            Par.setCantRecibida(ParTemp.getCantRecibida());
                            mListener.onListFragmentInteraction(v, Par, position);
                        }
                }
                return true;
            }
        });
    }

    public boolean isCheckedInList(int position) {
        boolean checked = false;
        Object parDosctosRutaTarea = seleccionados.get(position, null);
        if (parDosctosRutaTarea != null) {
            checked = ((DetalleDoctoRutaTarea) parDosctosRutaTarea).isChecked();
        }
        return checked;
    }

    public DetalleDoctoRutaTarea getParByPosition(int position) {
        DetalleDoctoRutaTarea Partida = null;
        Object parDosctosRutaTarea = seleccionados.get(position, null);
        if (parDosctosRutaTarea != null) {
            Partida = ((DetalleDoctoRutaTarea) parDosctosRutaTarea);
        }
        return Partida;
    }

    private List<String> obtenerIdParDocto(int posicion) {
        List<String> keys = new ArrayList<>();
        if (mCursor != null) {
            if (mCursor.moveToPosition(posicion)) {
                keys.add(mCursor.getString(mCursor.getColumnIndex(DetalleDoctoRutaTarea.FOLIO)));
                keys.add(mCursor.getString(mCursor.getColumnIndex(DetalleDoctoRutaTarea.DOCUMENTO)));
                keys.add(mCursor.getString(mCursor.getColumnIndex(DetalleDoctoRutaTarea.CVE_ART)));
                keys.add(mCursor.getString(mCursor.getColumnIndex(DetalleDoctoRutaTarea.NUM_PART)));
                return keys;
            }
        }
        return null;
    }

    private DetalleDoctoRutaTarea getIdParDocto(int posicion) {
        DetalleDoctoRutaTarea Par = null;
        if (mCursor != null) {
            if (mCursor.moveToPosition(posicion)) {
                Par = new DetalleDoctoRutaTarea(mCursor);
                return Par;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        CheckBox checkBox = (CheckBox) v;
        int position = (Integer) v.getTag();
        DetalleDoctoRutaTarea par = getIdParDocto(position);
        par.setChecked(checkBox.isChecked());
        //Toast.makeText(mContext, "clicked "+checkBox.isChecked(), Toast.LENGTH_SHORT).show();
        if (!v.isSelected()) {
            v.setSelected(true);
            par.setCantRecibida(par.getCantSalida());
            seleccionados.put(position, par);
        } else {
            v.setSelected(false);
            seleccionados.remove(position);
        }
    }

    public int getSelectedItemCount() {
        return seleccionados.size();
    }

    public SparseArray getSelectedItems() {
        return seleccionados;
    }

    public class ParTareaDoctoViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mCve_producto;
        public final TextView mDescr_producto;
        public final TextView mLinea;
        public final TextView mCantidad;
        public final TextView mCantidadEntregada;
        public final TextView mCantidadSelect;
        CheckBox mSelected;
        //public RutaTarea mItem;

        public ParTareaDoctoViewHolder(View view) {
            super(view);
            mView = view;
            mCve_producto = (TextView) view.findViewById(R.id.tvCve_producto);
            mDescr_producto = (TextView) view.findViewById(R.id.tvDescr_producto);
            mLinea = (TextView) view.findViewById(R.id.tvLinea);
            mCantidad = (TextView) view.findViewById(R.id.tvCantidadSalida);
            mCantidadEntregada = (TextView) view.findViewById(R.id.tvCantidadEntregada);
            mCantidadSelect = (TextView) view.findViewById(R.id.tvCantidadSelect);
            mSelected = (CheckBox) view.findViewById(R.id.chkSelecPar);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCve_producto.getText() + "'";
        }
    }
}
