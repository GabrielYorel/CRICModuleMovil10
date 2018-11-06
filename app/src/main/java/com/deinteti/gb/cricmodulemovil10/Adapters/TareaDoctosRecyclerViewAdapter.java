package com.deinteti.gb.cricmodulemovil10.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deinteti.gb.cricmodulemovil10.Entidades.Cliente;
import com.deinteti.gb.cricmodulemovil10.Entidades.DoctosRutasTareas;
import com.deinteti.gb.cricmodulemovil10.Entidades.RutaTarea;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusDoctoTarea;
import com.deinteti.gb.cricmodulemovil10.Enums.TipoTarea;
import com.deinteti.gb.cricmodulemovil10.GralUtils;
import com.deinteti.gb.cricmodulemovil10.Interfaces.OnListFragmentInteractionListenerDocto;
import com.deinteti.gb.cricmodulemovil10.LoginActivity;
import com.deinteti.gb.cricmodulemovil10.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by desarrollo on 19/03/2018.
 */

/**
 * {@link RecyclerView.Adapter} that can display a {@link DoctosRutasTareas} and makes a call to the
 * specified {@link OnListFragmentInteractionListenerDocto}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TareaDoctosRecyclerViewAdapter extends RecyclerView.Adapter<TareaDoctosRecyclerViewAdapter.TareaDoctoViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    private final OnListFragmentInteractionListenerDocto mListener;

    public TareaDoctosRecyclerViewAdapter(Context context, Cursor cursor, OnListFragmentInteractionListenerDocto listener) {
        this.mContext = context;
        this.mCursor = cursor;
        mListener = listener;
    }

    @Override
    public TareaDoctoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.docto_list_content_adtr, parent, false);
        return new TareaDoctoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TareaDoctoViewHolder holder, final int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String cveCliente = mCursor.getString(mCursor.getColumnIndex(DoctosRutasTareas.CVE_CLIENTE));
        final int tipoTarea = mCursor.getInt(mCursor.getColumnIndex(DoctosRutasTareas.TIPOTAREA));
        String sTipoTarea = TipoTarea.GetDescripcion(tipoTarea);
        holder.mNoFactura.setText((tipoTarea == TipoTarea.Tarea ? "Tarea: " : "Docto.: ") + mCursor.getString(mCursor.getColumnIndex(DoctosRutasTareas.IDDOCTOTAREA)));
        if (tipoTarea == TipoTarea.Tarea)
            holder.mFechaFactura.setVisibility(View.GONE);
        else if (holder.mFechaFactura.getVisibility() == View.GONE) {
            holder.mFechaFactura.setVisibility(View.VISIBLE);
        }
        if (tipoTarea != TipoTarea.Tarea)
            holder.mFechaFactura.setText("Fch. " + GralUtils.getDateTime(GralUtils.getDateTime(
                    mCursor.getString(mCursor.getColumnIndex(DoctosRutasTareas.FECHA_DOCUMENTO)))));
        int estatusTarea = mCursor.getInt(mCursor.getColumnIndex(DoctosRutasTareas.ESTATUS));
        holder.mEstatusFactura.setText("Estatus: " + EstatusDoctoTarea.GetDescripcion(estatusTarea, tipoTarea));
        String lblFchTerEntrega = TipoTarea.lblFechaFinalizado(tipoTarea);
        if (estatusTarea == EstatusDoctoTarea.ENTREGADO || estatusTarea == EstatusDoctoTarea.ENTREGA_PARCIAL) {
            if (holder.mFchEntrega.getVisibility() == View.GONE)
                holder.mFchEntrega.setVisibility(View.VISIBLE);
            holder.mFchEntrega.setText(lblFchTerEntrega + GralUtils.getDateFullTimeView(GralUtils.getDateFullTime(
                    mCursor.getString(mCursor.getColumnIndex(DoctosRutasTareas.FECHA_ENTREGA)))));
        } else {
            holder.mFchEntrega.setVisibility(View.GONE);
        }
        holder.mTipoTareaDisplay.setText(sTipoTarea);

        if (LoginActivity.dbDatos.ExisteCliente(cveCliente)) {
            Cursor cursor = LoginActivity.dbDatos.GetCliente(cveCliente);
            if (cursor != null && cursor.moveToLast()) {
                Cliente cte = new Cliente(cursor);
                holder.mClienteFactura.setText((tipoTarea == TipoTarea.Tarea ? "Tarea: " : "Cliente: ")
                        + cte.getCveCliente() + "\n" + cte.getNombre());
            }
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    List<String> keys = obtenerIdDocto(position);
                    mListener.onListFragmentInteraction(v, keys.get(0), keys.get(1), Integer.parseInt(keys.get(2)));
                }
            }
        });
    }

    private List<String> obtenerIdDocto(int posicion) {
        List<String> keys = new ArrayList<>();
        if (mCursor != null) {
            if (mCursor.moveToPosition(posicion)) {
                keys.add(mCursor.getString(mCursor.getColumnIndex(DoctosRutasTareas.FOLIO)));
                keys.add(mCursor.getString(mCursor.getColumnIndex(DoctosRutasTareas.IDDOCTOTAREA)));
                keys.add(mCursor.getString(mCursor.getColumnIndex(DoctosRutasTareas.TIPOTAREA)));
                return keys;
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

    public class TareaDoctoViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTipoTareaDisplay;
        public final TextView mNoFactura;
        public final TextView mFechaFactura;
        public final TextView mEstatusFactura;
        public final TextView mFchEntrega;
        public final TextView mClienteFactura;
        //public RutaTarea mItem;

        public TareaDoctoViewHolder(View view) {
            super(view);
            mView = view;
            mTipoTareaDisplay = (TextView) view.findViewById(R.id.tvTipoTareaDisplay);
            mNoFactura = (TextView) view.findViewById(R.id.tvNoFactura2);
            mFechaFactura = (TextView) view.findViewById(R.id.tvFechaFactura);
            mEstatusFactura = (TextView) view.findViewById(R.id.tvEstatusFactura);
            mFchEntrega = (TextView) view.findViewById(R.id.tvFchEntrega);
            mClienteFactura = (TextView) view.findViewById(R.id.tvClienteFactura);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNoFactura.getText() + "'";
        }
    }
}
