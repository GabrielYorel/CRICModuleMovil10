package com.deinteti.gb.cricmodulemovil10;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import com.deinteti.gb.cricmodulemovil10.FragmentsUtils.CustomDatePickerFragment;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FiltroTareasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FiltroTareasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FiltroTareasFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText mFechaIni;
    EditText mFechaFin;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FiltroTareasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FiltroTareasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FiltroTareasFragment newInstance(String param1, String param2) {
        FiltroTareasFragment fragment = new FiltroTareasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_filtro_tareas, null);
        final CheckBox cboFiltroTerminados = view.findViewById(R.id.cbo_filtro_end);
        final CheckBox cboFiltroEnProces = view.findViewById(R.id.cbo_filtro_process);
        cboFiltroTerminados.setOnCheckedChangeListener(getListenerChk());
        cboFiltroEnProces.setOnCheckedChangeListener(getListenerChk());

        mFechaIni = (EditText) view.findViewById(R.id.fecha_inicial);
        mFechaFin = (EditText) view.findViewById(R.id.fecha_final);
        mFechaIni.setInputType(0);
        mFechaFin.setInputType(0);
        java.util.Calendar c = java.util.Calendar.getInstance();
        mFechaIni.setText(UIParams.DateFormat.format(c));
        mFechaFin.setText(UIParams.DateFormat.format(c));

        mFechaIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(mFechaIni);
            }
        });
        mFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(mFechaFin);
            }
        });
        // Get the layout inflater
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        // Add action buttons
        builder.setPositiveButton(R.string.action_accept_short, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //
            }
        });

        builder.setNegativeButton(R.string.action_cancel_short, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                FiltroTareasFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    CompoundButton.OnCheckedChangeListener getListenerChk(){
        return  new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // to encode password in dots
                    //etEmail.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // to display the password in normal text
                    //etEmail.setTransformationMethod(null);
                }
            }
        };
    }

    public void showDatePickerDialog(final EditText mFecha) {
        CustomDatePickerFragment newFragment = CustomDatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                /*try {
                    fecha = UIParams.DateFormat.parse(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }*/
                /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Date fecha = Date.from(Instant.parse(selectedDate));
                    mFecha.setText(UIParams.DateFormat.format(fecha));
                } else {

                    Calendar calendar = new Calendar();
                    calendar.set(year, month, day);
                    mFecha.setText(UIParams.DateFormat.format(calendar));
                }*/
                mFecha.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");

    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filtro_tareas, container, false);
    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
