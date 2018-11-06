package com.deinteti.gb.cricmodulemovil10.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deinteti.gb.cricmodulemovil10.Entidades.RutaTarea;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusTarea;
import com.deinteti.gb.cricmodulemovil10.Enums.TipoTarea;
import com.deinteti.gb.cricmodulemovil10.GralUtils;
import com.deinteti.gb.cricmodulemovil10.Interfaces.OnListFragmentInteractionListener;
import com.deinteti.gb.cricmodulemovil10.R;

/**
 * Created by desarrollo on 19/03/2018.
 */

/**
 * {@link RecyclerView.Adapter} that can display a {@link RutaTarea} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TareaRecyclerViewAdapter extends RecyclerView.Adapter<TareaRecyclerViewAdapter.TareaViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    private final OnListFragmentInteractionListener mListener;

    public TareaRecyclerViewAdapter(Context context, Cursor cursor, OnListFragmentInteractionListener listener) {
        this.mContext = context;
        this.mCursor = cursor;
        mListener = listener;
    }

    @Override
    public TareaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tarea_list_content_adtr, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TareaViewHolder holder, final int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String folio = mCursor.getString(mCursor.getColumnIndex(RutaTarea.FOLIO));
        String fecha = mCursor.getString(mCursor.getColumnIndex(RutaTarea.FECHA_ASIG));
        int estatus = mCursor.getInt((mCursor.getColumnIndex(RutaTarea.ESTATUS)));

        String tareaTitle = folio;
        holder.mTipoFolio.setText(tareaTitle);
        holder.mFechaAsig.setText("Fch. Asig.:"+GralUtils.getDateFullTimeView(GralUtils.getDateFullTime(fecha)));
        holder.mEstatus.setText("Estatus:"+EstatusTarea.GetDescripcion(estatus));
        holder.mTipoTarea.setImageResource(R.mipmap.ic_tarea_tipo_e);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(v, obtenerIdAlquiler(position));
                }
            }
        });
    }

    private String obtenerIdAlquiler(int posicion) {
        if (mCursor != null) {
            if (mCursor.moveToPosition(posicion)) {
                return mCursor.getString(mCursor.getColumnIndex(RutaTarea.FOLIO));
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

    public class TareaViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTipoFolio;
        public final TextView mFechaAsig;
        public final TextView mEstatus;
        public final ImageView mTipoTarea;
        //public RutaTarea mItem;

        public TareaViewHolder(View view) {
            super(view);
            mView = view;
            mTipoFolio = (TextView) view.findViewById(R.id.tvTipo);
            mFechaAsig = (TextView) view.findViewById(R.id.tvFecha_asig);
            mEstatus = (TextView) view.findViewById(R.id.tvEstatus);
            mTipoTarea = (ImageView) view.findViewById(R.id.ivTipo_tarea);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTipoFolio.getText() + "'";
        }
    }
}
