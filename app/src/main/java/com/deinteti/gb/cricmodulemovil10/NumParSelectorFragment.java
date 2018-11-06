package com.deinteti.gb.cricmodulemovil10;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link NumParSelectorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumParSelectorFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match

    private OnFragmentInteractionListener mListener;
    private int newValSelected;

    public NumParSelectorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NumParSelectorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NumParSelectorFragment newInstance(String param1, String param2) {
        NumParSelectorFragment fragment = new NumParSelectorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_num_par_selector, container, false);
        // Inflate the layout for this fragment
        getDialog().setTitle("CRICModule");
        NumberPicker mNP = (NumberPicker) view.findViewById(R.id.npSelectPar);
        mNP.setMinValue(1);
        mNP.setMaxValue(getArguments().getInt(ParDoctosFragment.NUMMAXPAR));
        //mNP.setMaxValue(19);
        mNP.setWrapSelectorWheel(true);
        int selected = getArguments().getInt(ParDoctosFragment.NUMPARSELECTED);
        mNP.setValue(selected == 0 ? 1 : selected);
        mNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newValSelected = newVal;
            }
        });
        Button btnAceptar = view.findViewById(R.id.btnAceptar_select_par);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBackResult();
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onCantParSelected(int Cantidad);
    }

    public  void sendBackResult(){
        mListener = (OnFragmentInteractionListener) getTargetFragment();
        mListener.onCantParSelected(newValSelected);
        dismiss();
    }
}
