package com.deinteti.gb.cricmodulemovil10;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.deinteti.gb.cricmodulemovil10.Entidades.BitacoraRepartosRpt;
import com.deinteti.gb.cricmodulemovil10.Entidades.TareaResult;
import com.deinteti.gb.cricmodulemovil10.Entidades.TareasGenReporteResult;
import com.deinteti.gb.cricmodulemovil10.Entidades.UsuarioVehiculosResult;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusGenReporteResult;
import com.deinteti.gb.cricmodulemovil10.Enums.ResultOperation;
import com.deinteti.gb.cricmodulemovil10.Enums.TipoOperacionUsuario;
import com.deinteti.gb.cricmodulemovil10.Enums.VehiculosUsuarioEnum;
import com.deinteti.gb.cricmodulemovil10.Interfaces.OnTaskCompleted;
import com.deinteti.gb.cricmodulemovil10.NetServices.NSTareaTask;
import com.deinteti.gb.cricmodulemovil10.NetServices.NSUserTask;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SincFragment# //newInstance} factory method to
 * create an instance of this fragment.
 */
public class SincFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View mProgressView;
    private View mLoginFormView;
    private TextView txtMensaje;
    Calendar myCalendar = Calendar.getInstance();
    EditText txtFechaRpt;
    //private OnFragmentInteractionListener mListener;

    //public SincFragment() {
    // Required empty public constructor
    //}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * //* @param param1 Parameter 1.
     * //* @param param2 Parameter 2.
     *
     * @return A new instance of fragment SincFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*public static SincFragment newInstance(String param1, String param2) {
        SincFragment fragment = new SincFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/
    @Override
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
        View view = inflater.inflate(R.layout.fragment_sinc, container, false);
        Button mBtnSincDown = (Button) view.findViewById(R.id.btnSincDown);
        mBtnSincDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(),"Val"+ActivityUtils.getPreferenceById("server_ip",LoginActivity.this),Toast.LENGTH_LONG).show();
                EjecutaProcesoSinc();
            }
        });
        Button mBtnSincClean = (Button) view.findViewById(R.id.btnSincClean);
        mBtnSincClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(),"Val"+ActivityUtils.getPreferenceById("server_ip",LoginActivity.this),Toast.LENGTH_LONG).show();
                EjecutaProcesoSincClean();
            }
        });
        Button mBtnGenRpt = (Button) view.findViewById(R.id.btnGenReporte);
        mBtnGenRpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(),"Val"+ActivityUtils.getPreferenceById("server_ip",LoginActivity.this),Toast.LENGTH_LONG).show();
                if (txtFechaRpt.getText().toString().trim().equals(""))
                    ActivityUtils.AlertOKMessage(getActivity(), "Campo requerido",
                            "Indica la fecha del reporte a generar");
                else
                    EjecutaProcesoGenReporte();
            }
        });

        Button mBtnSicVehiculos = (Button) view.findViewById(R.id.btnSincVehiculos);
        mBtnSicVehiculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(),"Val"+ActivityUtils.getPreferenceById("server_ip",LoginActivity.this),Toast.LENGTH_LONG).show();
                EjecutaProcesoDescargaVehiculos();
            }
        });

        txtFechaRpt = (EditText) view.findViewById(R.id.txtFechaRpt);

        txtFechaRpt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), dateListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mLoginFormView = view.findViewById(R.id.sinc_form);
        mProgressView = view.findViewById(R.id.sinc_progress);

        txtMensaje = (TextView) view.findViewById(R.id.textMensaje_Server);
        return view;
    }

    private void updateLabelFecha() {
        txtFechaRpt.setText(UIParams.DateFormat.format(myCalendar.getTime()));
    }

    NSTareaTask mNSTareaTask = null;
    NSUserTask mNSUserTask = null;

    public void EjecutaProcesoSinc() {
        showProgress(true);
        mNSTareaTask = new NSTareaTask(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object feed) {
                mNSTareaTask = null;
                showProgress(false);
                int tareasCount = (int) feed;
                if (tareasCount > 0) {
                    setMensajeActivity(ResultOperation.OK_MESSAGE, "Se encontraron " + tareasCount + " Tareas");
                    EjecutaProcesoSincTareas();
                } else {
                    setMensajeActivity(ResultOperation.WARNING_MESSAGE, "No se encontraron Tareas");
                }
            }

            @Override
            public void onTaskError(Object feed) {
                setMensajeActivity(ResultOperation.ERROR_MESSAGE, "Error al obtener las tareas del usuario");
                mNSTareaTask = null;
                showProgress(false);
                //Toast.makeText(getActivity(), "Ocurrio un error en la comunicacion con el WebServive", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled() {
                mNSTareaTask = null;
                showProgress(false);
            }
        }, getActivity(), MainActivity.loginResult.getVarMultiUso(), LoginActivity.dbDatos);
        mNSTareaTask.execute(TipoOperacionUsuario.TareasCount);
    }

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelFecha();
        }
    };

    public void EjecutaProcesoSincTareas() {
        showProgress(true);
        mNSTareaTask = new NSTareaTask(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object feed) {
                mNSTareaTask = null;
                showProgress(false);
                TareaResult tareasResult = (TareaResult) feed;
                if (tareasResult.getTareas().size() > 0) {
                    //actualziar tareas
                    setMensajeActivity(ResultOperation.WARNING_MESSAGE, "Tareas actualizadas " + tareasResult.getTareas().size());
                } else {
                    setMensajeActivity(ResultOperation.WARNING_MESSAGE, "No se encontraron Tareas");
                }
            }

            @Override
            public void onTaskError(Object feed) {
                setMensajeActivity(ResultOperation.ERROR_MESSAGE, "Error al obtener las tareas del usuario");
                mNSTareaTask = null;
                showProgress(false);
                //Toast.makeText(getActivity(), "Ocurrio un error en la comunicacion con el WebServive", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled() {
                mNSTareaTask = null;
                showProgress(false);
            }
        }, getActivity(), MainActivity.loginResult.getVarMultiUso(), LoginActivity.dbDatos);
        mNSTareaTask.execute(TipoOperacionUsuario.TareasGet);
    }

    public void EjecutaProcesoSincClean() {
        showProgress(true);
        mNSTareaTask = new NSTareaTask(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object feed) {
                mNSTareaTask = null;
                showProgress(false);
                boolean correct = (boolean) feed;
                if (correct) {
                    setMensajeActivity(ResultOperation.WARNING_MESSAGE, "Limpieza correcta");
                } else {
                    setMensajeActivity(ResultOperation.WARNING_MESSAGE, "Error al limpiar base de datos");
                }
            }

            @Override
            public void onTaskError(Object feed) {
                setMensajeActivity(ResultOperation.ERROR_MESSAGE, "Error al obtener las tareas del usuario");
                mNSTareaTask = null;
                showProgress(false);
                //Toast.makeText(getActivity(), "Ocurrio un error en la comunicacion con el WebServive", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled() {
                mNSTareaTask = null;
                showProgress(false);
            }
        }, getActivity(), MainActivity.loginResult.getVarMultiUso(), LoginActivity.dbDatos);
        mNSTareaTask.execute(TipoOperacionUsuario.TareasClean);
    }

    public void EjecutaProcesoGenReporte() {
        showProgress(true);
        BitacoraRepartosRpt bitacoraRepartosRpt =
                new BitacoraRepartosRpt();
        bitacoraRepartosRpt.setIdEmpleado(MainActivity.loginResult.getVarMultiUso().getEmpleado().getIdEmpleado());
        bitacoraRepartosRpt.setIdSucursal(MainActivity.loginResult.getVarMultiUso().getEmpleado().getSucursal().getIdSucursal());
        bitacoraRepartosRpt.setFecha(myCalendar.getTime());

        //bitacoraRepartosRpt.setFecha();
        mNSTareaTask = new NSTareaTask(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object feed) {
                mNSTareaTask = null;
                showProgress(false);
                TareasGenReporteResult tareasResult = (TareasGenReporteResult) feed;
                if (tareasResult.getEstatus() == EstatusGenReporteResult.GENERADO_OK) {
                    setMensajeActivity(ResultOperation.OK_MESSAGE, tareasResult.getMessage());
                } else {
                    setMensajeActivity(ResultOperation.ERROR_MESSAGE, tareasResult.getMessage());
                }
            }

            @Override
            public void onTaskError(Object feed) {
                setMensajeActivity(ResultOperation.ERROR_MESSAGE, "Error al Generar reporte");
                mNSTareaTask = null;
                showProgress(false);
                //Toast.makeText(getActivity(), "Ocurrio un error en la comunicacion con el WebServive", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled() {
                mNSTareaTask = null;
                showProgress(false);
            }
        }, getActivity(), MainActivity.loginResult.getVarMultiUso(), LoginActivity.dbDatos);
        mNSTareaTask.execute(TipoOperacionUsuario.GeneraReporteDia, bitacoraRepartosRpt);
    }

    public void EjecutaProcesoDescargaVehiculos() {
        showProgress(true);
        mNSUserTask = new NSUserTask(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object feed) {
                mNSUserTask = null;
                showProgress(false);
                if (feed instanceof UsuarioVehiculosResult) {
                    UsuarioVehiculosResult loginResult = (UsuarioVehiculosResult) feed;
                    if (loginResult.getResultado() == VehiculosUsuarioEnum.OK) {
                        setMensajeActivity(ResultOperation.OK_MESSAGE, loginResult.getMessage());
                    } else {
                        setMensajeActivity(ResultOperation.WARNING_MESSAGE, loginResult.getMessage());
                    }
                }
            }

            @Override
            public void onTaskError(Object feed) {
                mNSUserTask = null;
                showProgress(false);
                setMensajeActivity(ResultOperation.ERROR_MESSAGE, "Error al obtener los vehiculos del empleado");
            }

            @Override
            public void onCancelled() {
                mNSUserTask = null;
                showProgress(false);
            }
        }, getActivity(), "", "", LoginActivity.dbDatos);
        mNSUserTask.execute(TipoOperacionUsuario.VehiculosGetAll, MainActivity.loginResult.getVarMultiUso().getEmpleado().getIdEmpleado());
    }

    public void setMensajeActivity(ResultOperation resultOperation, String msj) {
        txtMensaje.setText(msj);
        txtMensaje.setTextColor(UIParams.GetColorMessage(resultOperation));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
      /*  if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //   mListener = null;
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
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
