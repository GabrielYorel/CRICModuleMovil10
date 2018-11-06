package com.deinteti.gb.cricmodulemovil10.AccesoDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.deinteti.gb.cricmodulemovil10.Entidades.BitacoraRepartosRpt;
import com.deinteti.gb.cricmodulemovil10.Entidades.Cliente;
import com.deinteti.gb.cricmodulemovil10.Entidades.DetalleDoctoRutaTarea;
import com.deinteti.gb.cricmodulemovil10.Entidades.DoctosRutasTareas;
import com.deinteti.gb.cricmodulemovil10.Entidades.INFENVIO;
import com.deinteti.gb.cricmodulemovil10.Entidades.RutaTarea;
import com.deinteti.gb.cricmodulemovil10.Entidades.VehiculosUsuario;

/**
 * Created by desarrollo on 23/03/2018.
 */

/**
 * Clase que administra la conexión de la base de datos y su estructuración
 */
public class BaseDatosTareas extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE_DATOS = "CricModule.db";

    private static final int VERSION_ACTUAL = 1;

    private final Context contexto;

    interface Tablas {
        String tblTarea = "RutaTarea";
        String tblDetalleTarea = "DoctosRutasTareas";
        String tblDetalleDoctoTarea = "DetalleDoctoRutaTarea";
        String tblInfEnvio = "INFENVIO";
        String tblCliente = "Cliente";
        String tblBitacoraRepartosRpt = "BitacoraRepartosRpt";
        String tblVehiculosUsuario = "VehiculosUsuario";
    }

    interface Referencias {
        String ID_TAREA = String.format("REFERENCES %s(%s) ON DELETE CASCADE",
                Tablas.tblTarea, RutaTarea.FOLIO);
        String ID_DOCUMENTO = String.format("REFERENCES %s(%s)",
                Tablas.tblDetalleTarea, DoctosRutasTareas.IDDOCTOTAREA);
        String ID_INF_ENVIO_IDE = String.format("REFERENCES %s(%s)",
                Tablas.tblInfEnvio, INFENVIO.CVE_INFOE);
        String ID_INF_ENVIO_IDS = String.format("REFERENCES %s(%s)",
                Tablas.tblInfEnvio, INFENVIO.ID_SUCURSAL);
        String ID_CLIENTE = String.format("REFERENCES %s(%s)",
                Tablas.tblCliente, Cliente.CVE_CLIENTE);
    }

    BaseDatosTareas(Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("CricModuleBd", "onCreate");

        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL UNIQUE,%s TEXT NULL)",
                Tablas.tblCliente, BaseColumns._ID,
                Cliente.CVE_CLIENTE, Cliente.NOMBRE));


        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s INTEGER NOT NULL,%s TEXT NULL,%s TEXT NULL,%s TEXT NULL," +
                        "%s TEXT NULL,%s TEXT NULL,%s TEXT NULL,%s TEXT NULL," +
                        "%s TEXT NULL,%s TEXT NULL,%s TEXT NULL,%s TEXT NULL," +
                        "%s INTEGER NULL,%s TEXT NULL,%s TEXT NULL,%s DATETIME NULL," +
                        "%s TEXT NULL,%s TEXT NULL,%s DATETIME NULL,%s TEXT NULL," +
                        "%s TEXT NULL,%s TEXT NULL,%s TEXT NULL,%s TEXT NULL," +
                        "%s INTEGER NOT NULL, UNIQUE (%s,%s) )",
                Tablas.tblInfEnvio, BaseColumns._ID,
                INFENVIO.CVE_INFOE, INFENVIO.CVE_CONSE, INFENVIO.NOMBREE, INFENVIO.CALLEE,
                INFENVIO.NUMINTE, INFENVIO.NUMEXTE, INFENVIO.CRUZAMIENTOSE, INFENVIO.CRUZAMIENTOS2E,
                INFENVIO.POBE, INFENVIO.CURPE, INFENVIO.REFERDIRE, INFENVIO.CVE_ZONAE,
                INFENVIO.CVE_OBSE, INFENVIO.STRNOGUIAE, INFENVIO.STRMODOENVE, INFENVIO.FECHA_ENVE,
                INFENVIO.NOMBRE_RECEPE, INFENVIO.NO_RECEPE, INFENVIO.FECHA_RECEPE, INFENVIO.COLONIAE,
                INFENVIO.CODIGOE, INFENVIO.ESTADOE, INFENVIO.PAISE, INFENVIO.MUNICIPIOE,
                INFENVIO.ID_SUCURSAL, INFENVIO.ID_SUCURSAL, INFENVIO.CVE_INFOE
        ));

        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT UNIQUE NOT NULL,%s INTEGER NOT NULL,%s INTEGER NOT NULL," +
                        "%s DATETIME NULL,%s DATETIME NULL,%s DATETIME NULL," +
                        "%s DATETIME NULL,%s INTEGER NULL,%s DATETIME NULL," +
                        "%s TEXT NULL,%s TEXT NULL" +
                        ")",
                Tablas.tblTarea, BaseColumns._ID,
                RutaTarea.FOLIO, RutaTarea.ID_EMPLEADO, RutaTarea.ID_SUCURSAL,
                RutaTarea.FECHA_ASIG, RutaTarea.FECHA_PROMESA, RutaTarea.FECHA_INICIO,
                RutaTarea.FECHA_TERMINO, RutaTarea.ESTATUS, RutaTarea.FECHA_CANCELACION,
                RutaTarea.MOTIVO_CANCELACION, RutaTarea.OBSERVACIONES));

        String sqlCreate = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL %s,%s TEXT NOT NULL," +
                        "%s INTEGER NOT NULL,%s DATETIME NULL,%s INTEGER NOT NULL," +
                        "%s DATETIME NULL,%s TEXT NULL,%s REAL NULL," +
                        "%s REAL NULL,%s REAL NULL,%s REAL NULL," +
                        "%s REAL NULL,%s DATETIME NULL,%s DATETIME NULL," +
                        "%s TEXT NULL,%s REAL NULL,%s REAL NULL," +
                        "%s REAL NULL,%s TEXT NOT NULL %s," +
                        "%s TEXT NULL,%s INTEGER NULL," +
                        "%s DATETIME NULL,%s TEXT NULL,%s REAL NOT NULL,%s INTEGER NOT NULL,%s TEXT NULL," +
                        "%s INTEGER NULL,%s INTEGER NULL,%s INTEGER NULL," +
                        "%s BLOB NULL,%s INTEGER NULL,%s INTEGER NULL,%s TEXT NULL,%s TEXT," +
                        "UNIQUE (%s,%s,%s) ",
                Tablas.tblDetalleTarea, BaseColumns._ID,
                DoctosRutasTareas.FOLIO, Referencias.ID_TAREA, DoctosRutasTareas.IDDOCTOTAREA,
                DoctosRutasTareas.ORDEN, DoctosRutasTareas.FECHA_ENTREGA, DoctosRutasTareas.ESTATUS,
                DoctosRutasTareas.FECHA_CANCELACION, DoctosRutasTareas.MOTIVOCANCELACION, DoctosRutasTareas.NO_PZAS_ENTREGAR,
                DoctosRutasTareas.LATITUD_INICIAL, DoctosRutasTareas.LONGITUD_INICIAL, DoctosRutasTareas.LATITUD_FINAL,
                DoctosRutasTareas.LONGITUD_FINAL, DoctosRutasTareas.HORA_INICIAL_ENTREGA, DoctosRutasTareas.HORA_FINAL_ENTREGA,
                DoctosRutasTareas.OBSERVACIONES, DoctosRutasTareas.SUBTOTAL, DoctosRutasTareas.IVA,
                DoctosRutasTareas.IMPORTE, DoctosRutasTareas.CVE_CLIENTE, Referencias.ID_CLIENTE,
                DoctosRutasTareas.RFC, DoctosRutasTareas.CVE_DATOS_ENVIO,
                DoctosRutasTareas.FECHA_DOCUMENTO, DoctosRutasTareas.TIPO_DOCUMENTO, DoctosRutasTareas.CANT_PZAS, DoctosRutasTareas.ID_SUCURSAL, DoctosRutasTareas.OBSENTREGA,
                DoctosRutasTareas.REQUIEREDETALLE, DoctosRutasTareas.TIPOTAREA, DoctosRutasTareas.REQEVIDENCIAFOTO,
                DoctosRutasTareas.EVIDENCIAFOTO, DoctosRutasTareas.REQEVIDENCIAARCH, DoctosRutasTareas.REQCAPTURAINFO, DoctosRutasTareas.CAPTURAINFO, DoctosRutasTareas.EVIDENCIAFOTORUTA,
                DoctosRutasTareas.FOLIO, DoctosRutasTareas.IDDOCTOTAREA, DoctosRutasTareas.ID_SUCURSAL)
                + ",FOREIGN KEY(IdSucursal, CveDatosEnvio) REFERENCES INFENVIO(IdSucursal, CVE_INFO))";
        db.execSQL(sqlCreate);

        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL %s,%s TEXT NOT NULL," +
                        "%s TEXT NOT NULL,%s INTEGER NULL,%s TEXT NULL," +
                        "%s TEXT NULL,%s REAL NULL,%s REAL NULL," +
                        "%s REAL NULL,%s REAL NULL,%s INTEGER NOT NULL," +
                        "UNIQUE (%s,%s,%s,%s)  )",
                Tablas.tblDetalleDoctoTarea, BaseColumns._ID,
                DetalleDoctoRutaTarea.FOLIO, Referencias.ID_TAREA, DetalleDoctoRutaTarea.DOCUMENTO,
                DetalleDoctoRutaTarea.CVE_ART, DetalleDoctoRutaTarea.ESTATUS, DetalleDoctoRutaTarea.DESCRIPCION,
                DetalleDoctoRutaTarea.LINEA, DetalleDoctoRutaTarea.CANT_SAE, DetalleDoctoRutaTarea.CANT_ASIGNADA,
                DetalleDoctoRutaTarea.CANT_SALIDA, DetalleDoctoRutaTarea.CANT_RECIBIDA, DetalleDoctoRutaTarea.NUM_PART,
                DetalleDoctoRutaTarea.FOLIO, DetalleDoctoRutaTarea.DOCUMENTO, DetalleDoctoRutaTarea.CVE_ART, DetalleDoctoRutaTarea.NUM_PART
        ));

        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s INTEGER NULL,%s INTEGER NULL,%s DATETIME NULL," +
                        "UNIQUE (%s,%s,%s,%s) )",
                Tablas.tblBitacoraRepartosRpt, BaseColumns._ID,
                BitacoraRepartosRpt.IDSUCURSAL, BitacoraRepartosRpt.IDEMPLEADO, BitacoraRepartosRpt.FECHA,
                BaseColumns._ID, BitacoraRepartosRpt.IDSUCURSAL, BitacoraRepartosRpt.IDEMPLEADO, BitacoraRepartosRpt.FECHA
        ));

        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s INTEGER NOT NULL,%s TEXT NULL,%s TEXT NULL," +
                        "%s TEXT NULL,%s INTEGER NULL," +
                        "UNIQUE (%s) )",
                Tablas.tblVehiculosUsuario, BaseColumns._ID,
                VehiculosUsuario.IDVEHICULO, VehiculosUsuario.DESCRIPCION,VehiculosUsuario.PLACAS,
                VehiculosUsuario.TIPOVEHICULO,VehiculosUsuario.ESTATUS,
                VehiculosUsuario.IDVEHICULO
        ));

        Log.d("CricModuleBd", "Base de datos creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("CricModuleBd", "onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.tblTarea);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.tblDetalleTarea);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.tblDetalleDoctoTarea);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.tblInfEnvio);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.tblCliente);

        onCreate(db);
    }
}
