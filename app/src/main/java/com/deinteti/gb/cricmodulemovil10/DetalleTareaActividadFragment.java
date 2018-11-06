package com.deinteti.gb.cricmodulemovil10;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deinteti.gb.cricmodulemovil10.AccesoDatos.OperacionesBaseDatos;
import com.deinteti.gb.cricmodulemovil10.Entidades.DoctosRutasTareas;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusDoctoTarea;
import com.google.android.gms.plus.PlusOneButton;
import com.google.android.gms.vision.text.Line;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 *
 */
public class DetalleTareaActividadFragment extends Fragment {
    private DoctosRutasTareas mItem;
    // The request code must be 0 or greater.
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static String APP_PATH = "CricModule";
    private static final int MEDIA_TYPE_IMAGE = 1;

    private Button btnTomarFoto;
    private ImageView ivImgFoto;
    public TextView txtInfoCaptura;
    LinearLayout llInfo;
    LinearLayout llFoto;
    public Uri fileUri;

    String Folio;
    String IdFolioDocto;

    //private OnFragmentInteractionListener mListener;

    public DetalleTareaActividadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_tarea_actividad, container, false);
        llInfo = view.findViewById(R.id.llInfo);
        llFoto = view.findViewById((R.id.llFoto));

        txtInfoCaptura = (EditText) view.findViewById(R.id.txtInfoCaptura);
        ivImgFoto = (ImageView) view.findViewById(R.id.imgFoto);
        btnTomarFoto = (Button) view.findViewById(R.id.btnTomarFoto);
        Folio = getArguments().getString(ParDoctosActivity.ARG_ITEM_FOLIO);
        IdFolioDocto = getArguments().getString(ParDoctosActivity.ARG_ITEM_DOCUMENTO);
        if (LoginActivity.dbDatos.ExisteTareaDetalleByFolio(Folio, IdFolioDocto)) {
            loadRutaTarea();
        }
        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanzarCamara();
            }
        });
        ivImgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItem.getEstatus() == EstatusDoctoTarea.ENTREGADO
                        || mItem.getEstatus() == EstatusDoctoTarea.NO_ENTREGA) {
                    if (mItem.isReqEvidenciaFoto()) {
                        if (mItem.getEvidenciaFotoRuta() != null) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.parse(mItem.getEvidenciaFotoRuta()), "image/*");
                            startActivity(intent);
                        }
                    }
                }
            }
        });
        return view;
    }

    private void LanzarCamara() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = getOuputmediaFileUri(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE, mItem.IdDoctoTarea);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        } catch (Exception ex) {
            Log.e("DetalleTareaActividad", ex.getMessage(), ex);
        }
    }

    public static Uri getOuputmediaFileUri(int type, String pID) {
        return Uri.fromFile(getOuputMediaFile(type, pID));
    }

    public static File getOuputMediaFile(int type, String pID) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), APP_PATH);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdir()) {
                return null;
            }
        }
        //creamos el archivo
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + TareaUtils.GetNameTareaEvidenciaFile(pID));
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK && fileUri != null) {
                    Bitmap bit_map = PictureTools.getImageStandarCricModule(fileUri);
                    ivImgFoto.setImageBitmap(bit_map);
                } else {
                    //Error al realizat rla caspgura de pa imagen
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadRutaTarea() {
        new GetLawyerByIdTask().execute();
    }

    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return LoginActivity.dbDatos.GetTareaDetalleByFolio(Folio, IdFolioDocto);
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
        if (!mItem.isReqCapturaInfo()) {
            llInfo.setVisibility(View.GONE);
        }
        if (!mItem.isReqEvidenciaFoto()) {
            llFoto.setVisibility(View.GONE);
        }
        if (mItem.getEstatus() == EstatusDoctoTarea.SIN_ENTREGAR || mItem.getEstatus() == EstatusDoctoTarea.ENTREGADO
                || mItem.getEstatus() == EstatusDoctoTarea.NO_ENTREGA) {
            txtInfoCaptura.setEnabled(false);
            btnTomarFoto.setEnabled(false);
            if (mItem.getEstatus() == EstatusDoctoTarea.ENTREGADO
                    || mItem.getEstatus() == EstatusDoctoTarea.NO_ENTREGA) {
                if (mItem.isReqEvidenciaFoto()) {
                    Bitmap imgFoto = PictureTools.getImageStandarCricModule(mItem.getEvidenciaFotoRuta());
                    if (imgFoto != null) {
                        ivImgFoto.setImageBitmap(imgFoto);
                    }
                    btnTomarFoto.setVisibility(View.GONE);
                }
                if (mItem.isReqCapturaInfo()) {
                    txtInfoCaptura.setText(mItem.getCapturaInfo());
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

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
        //mListener = null;
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

    public String getInfoCaptura() {
        return txtInfoCaptura.getText().toString();
    }
}
