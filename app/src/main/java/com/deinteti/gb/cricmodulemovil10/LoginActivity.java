package com.deinteti.gb.cricmodulemovil10;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.deinteti.gb.cricmodulemovil10.AccesoDatos.OperacionesBaseDatos;
import com.deinteti.gb.cricmodulemovil10.Entidades.Cliente;
import com.deinteti.gb.cricmodulemovil10.Entidades.LoginResult;
import com.deinteti.gb.cricmodulemovil10.Enums.DispositivoUsuarioEnum;
import com.deinteti.gb.cricmodulemovil10.Enums.ResultOperation;
import com.deinteti.gb.cricmodulemovil10.Enums.TipoOperacionServer;
import com.deinteti.gb.cricmodulemovil10.Enums.TipoOperacionUsuario;
import com.deinteti.gb.cricmodulemovil10.Interfaces.OnTaskCompleted;
import com.deinteti.gb.cricmodulemovil10.NetServices.NSServerTask;
import com.deinteti.gb.cricmodulemovil10.NetServices.NSUserTask;

import java.util.Calendar;

/**
 * A login screen that offers login via email/password.
 *///implements LoaderCallbacks<Cursor>
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "123:45678", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private NSUserTask mAuthTask = null;

    public static OperacionesBaseDatos dbDatos;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView txtMensaje;
    public static String UDID;

    public LoginActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UDID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        txtMensaje = (TextView) findViewById(R.id.textMensaje_Server);
        //populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(),"Val"+ActivityUtils.getPreferenceById("server_ip",LoginActivity.this),Toast.LENGTH_LONG).show();
                attemptLogin();
                //ProbarConexion();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        if (ValidateBD())
            ProbarConexion();
        else
            Toast.makeText(getApplicationContext(), "Error con la base de datos", Toast.LENGTH_SHORT).show();
    }

    public class RegistraDispositivoLocal extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            String fechaActual = Calendar.getInstance().getTime().toString();

            try {
                dbDatos.getDb().beginTransaction();
                // Inserción Clientes
                boolean cliente1 = dbDatos.InsertarCliente(new Cliente(Cliente.generarIdCliente(), "Veronica"));
                boolean cliente2 = dbDatos.InsertarCliente(new Cliente(Cliente.generarIdCliente(), "Carlos"));

                dbDatos.getDb().setTransactionSuccessful();
            } finally {
                dbDatos.getDb().endTransaction();
            }

            // [QUERIES]
            Log.d("Clientes", "Clientes");
            DatabaseUtils.dumpCursor(dbDatos.ObtenerClientes());
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // launch settings activity
            startActivity(new Intent(LoginActivity.this, SettingsServerActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }*/
    public boolean ValidateBD() {
        try {
            //if(getApplicationContext().deleteDatabase("CricModule.db")){
              //  dbDatos = new OperacionesBaseDatos(getApplicationContext());
            //}
            //getApplicationContext().deleteDatabase("CricModule.db");
            dbDatos = new OperacionesBaseDatos(getApplicationContext());
            //new RegistraDispositivoLocal().execute();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new NSUserTask(new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(Object feed) {
                    //MessageClose("Logeo con exito");
                    mAuthTask = null;
                    showProgress(false);
                    //Toast.makeText(getApplicationContext(), "Logueo correcto", Toast.LENGTH_LONG);

                    if (feed instanceof LoginResult) {
                        LoginResult loginResult = (LoginResult) feed;
                        if (loginResult.getLoginRes() == DispositivoUsuarioEnum.ACTIVE) {
                            mPasswordView.setText("");
                            Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                            intentMain.putExtra("loginResult", loginResult);
                            startActivity(intentMain);
                        } else {
                            setMensajeActivity(ResultOperation.WARNING_MESSAGE, loginResult.getMessage());
                        }
                    }
                }

                @Override
                public void onTaskError(Object feed) {
                    mAuthTask = null;
                    showProgress(false);
                    Toast.makeText(getApplicationContext(), "Ocurrio un error en la comunicacion con el WebServive", Toast.LENGTH_LONG).show();
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }

                @Override
                public void onCancelled() {
                    mAuthTask = null;
                    showProgress(false);
                }
            }, this, email, password,dbDatos);
            mAuthTask.execute(TipoOperacionUsuario.Login, LoginActivity.UDID);
        }
    }

    public void setMensajeActivity(ResultOperation resultOperation, String msj) {
        txtMensaje.setText(msj);
        txtMensaje.setTextColor(UIParams.GetColorMessage(resultOperation));
    }

    public void ProbarConexion() {
        showProgress(true);
        NSServerTask mAuthTask = new NSServerTask(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object feed) {
                setMensajeActivity(ResultOperation.OK_MESSAGE, "Conexion exitosa con el servidor ");
                showProgress(false);
                //Toast.makeText(getApplicationContext(), "Test success " + feed.toString(), Toast.LENGTH_LONG);

            }

            @Override
            public void onTaskError(Object feed) {
                setMensajeActivity(ResultOperation.ERROR_MESSAGE, "Error al establecer la conexión con el servidor\n"+feed.toString());
                showProgress(false);
                //Toast.makeText(getApplicationContext(), "Ocurrion un error en la comunicacion con el WebServive", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled() {
                showProgress(false);
            }
        }, this);
        mAuthTask.execute(TipoOperacionServer.Probarconexion);
    }

    public void MessageClose(String sMsg) {
        AlertDialog.Builder oADb = new AlertDialog.Builder(this);
        AlertDialog oAD = oADb.create();
        oAD.setTitle("Alerta");
        oAD.setMessage(sMsg);
        oAD.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //finish();
            }
        });
        oAD.show();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 2;
    }

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

