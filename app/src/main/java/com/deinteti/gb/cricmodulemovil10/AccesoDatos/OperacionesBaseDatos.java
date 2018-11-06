package com.deinteti.gb.cricmodulemovil10.AccesoDatos;

/**
 * Created by desarrollo on 23/03/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.deinteti.gb.cricmodulemovil10.Entidades.BitacoraRepartosRpt;
import com.deinteti.gb.cricmodulemovil10.Entidades.Cliente;
import com.deinteti.gb.cricmodulemovil10.Entidades.DetalleDoctoRutaTarea;
import com.deinteti.gb.cricmodulemovil10.Entidades.DoctosRutasTareas;
import com.deinteti.gb.cricmodulemovil10.Entidades.INFENVIO;
import com.deinteti.gb.cricmodulemovil10.Entidades.RutaTarea;
import com.deinteti.gb.cricmodulemovil10.Entidades.VehiculosUsuario;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusDoctoTarea;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusTarea;
import com.deinteti.gb.cricmodulemovil10.Enums.TipoTarea;
import com.deinteti.gb.cricmodulemovil10.GralUtils;

/**
 * Clase auxiliar que implementa a {@link BaseDatosTareas} para llevar a cabo el CRUD
 * sobre las entidades existentes.
 */
public class OperacionesBaseDatos {
    private static BaseDatosTareas baseDatos;
    private Context ctx;
    //private static OperacionesBaseDatos instancia = new OperacionesBaseDatos();

    public OperacionesBaseDatos(Context contexto) {
        this.ctx = contexto;
        if (baseDatos == null) {
            baseDatos = new BaseDatosTareas(contexto);
        }
    }

    /*public obtenerInstancia(Context contexto) {

        //return instancia;
    }*/

    public SQLiteDatabase getDb() {
        try {
            return baseDatos.getWritableDatabase();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //region Operaciones con clientes
    public Cursor ObtenerClientes() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", BaseDatosTareas.Tablas.tblCliente);
        return db.rawQuery(sql, null);
    }

    public boolean ExisteCliente(String idCliente) {
        boolean exist = false;
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = Cliente.CVE_CLIENTE + " = ?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{idCliente};

        Cursor c = db.query(BaseDatosTareas.Tablas.tblCliente, null,
                selection, selectionArgs, null, null, null);
        exist = c.moveToFirst();
        return exist;
    }

    public Cursor GetCliente(String idCliente) {
        boolean exist = false;
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = Cliente.CVE_CLIENTE + " = ?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{idCliente};

        Cursor c = db.query(BaseDatosTareas.Tablas.tblCliente, null,
                selection, selectionArgs, null, null, null);
        return c;
    }

    public boolean InsertarCliente(Cliente cliente) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        // Generar Pk
        //String idCliente = Cliente.generarIdCliente();
        ContentValues valores = new ContentValues();
        valores.put(Cliente.CVE_CLIENTE, cliente.getCveCliente());
        valores.put(Cliente.NOMBRE, cliente.getNombre());

        return db.insertOrThrow(BaseDatosTareas.Tablas.tblCliente, null, valores) > 0;
    }

    public boolean ActualizarCliente(Cliente cliente) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Cliente.NOMBRE, cliente.getNombre());

        String whereClause = String.format("%s=?", Cliente.CVE_CLIENTE);
        final String[] whereArgs = {cliente.getCveCliente()};
        int resultado = db.update(BaseDatosTareas.Tablas.tblCliente, valores, whereClause, whereArgs);
        return resultado > 0;
    }

    public boolean EliminarCliente(String idCliente) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s=?", Cliente.CVE_CLIENTE);
        final String[] whereArgs = {idCliente};

        int resultado = db.delete(BaseDatosTareas.Tablas.tblCliente, whereClause, whereArgs);
        return resultado > 0;
    }
    //endregion

    //region Operaciones con información de envio
    public boolean ExisteInfEnvio(int InfEnvio) {
        boolean exist = false;
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = INFENVIO.CVE_INFOE + " = ?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{InfEnvio + ""};

        Cursor c = db.query(BaseDatosTareas.Tablas.tblInfEnvio, null,
                selection, selectionArgs, null, null, null);
        exist = c.moveToFirst();
        return exist;
    }

    public boolean InsertarInfEnvio(INFENVIO infEnvio) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(INFENVIO.CVE_INFOE, infEnvio.getCVE_INFO());
        valores.put(INFENVIO.CVE_CONSE, infEnvio.getCVE_CONS());
        valores.put(INFENVIO.NOMBREE, infEnvio.getNOMBRE());
        valores.put(INFENVIO.CALLEE, infEnvio.getCALLE());
        valores.put(INFENVIO.NUMINTE, infEnvio.getNUMINT());
        valores.put(INFENVIO.NUMEXTE, infEnvio.getNUMEXT());
        valores.put(INFENVIO.CRUZAMIENTOSE, infEnvio.getCRUZAMIENTOS());
        valores.put(INFENVIO.CRUZAMIENTOS2E, infEnvio.getCRUZAMIENTOS2());
        valores.put(INFENVIO.POBE, infEnvio.getPOB());
        valores.put(INFENVIO.CURPE, infEnvio.getCURP());
        valores.put(INFENVIO.REFERDIRE, infEnvio.getREFERDIR());
        valores.put(INFENVIO.CVE_ZONAE, infEnvio.getCVE_ZONA());
        valores.put(INFENVIO.CVE_OBSE, infEnvio.getCVE_OBS());
        valores.put(INFENVIO.STRNOGUIAE, infEnvio.getSTRNOGUIA());
        valores.put(INFENVIO.STRMODOENVE, infEnvio.getSTRMODOENV());

        valores.put(INFENVIO.NOMBRE_RECEPE, infEnvio.getNOMBRE_RECEP());
        valores.put(INFENVIO.NO_RECEPE, infEnvio.getNO_RECEP());

        valores.put(INFENVIO.COLONIAE, infEnvio.getCOLONIA());
        valores.put(INFENVIO.CODIGOE, infEnvio.getCODIGO());
        valores.put(INFENVIO.ESTADOE, infEnvio.getESTADO());
        valores.put(INFENVIO.PAISE, infEnvio.getPAIS());
        valores.put(INFENVIO.MUNICIPIOE, infEnvio.getMUNICIPIO());
        valores.put(INFENVIO.ID_SUCURSAL, infEnvio.getIdSucursal());

        return db.insertOrThrow(BaseDatosTareas.Tablas.tblInfEnvio, null, valores) > 0;
    }

    public INFENVIO getInfEnvioByCve(int CveInf) {
        INFENVIO infEnvio = null;
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = INFENVIO.CVE_INFOE + " = ?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{CveInf + ""};

        Cursor cursor = db.query(BaseDatosTareas.Tablas.tblInfEnvio, null,
                selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToLast()) {
            infEnvio = new INFENVIO(cursor);
        }
        return infEnvio;
    }

    //endregion

    //region Operaciones Tareas
    private static final String CABECERA_PEDIDO_JOIN_CLIENTE_Y_FORMA_PAGO = "cabecera_pedido " +
            "INNER JOIN cliente " +
            "ON cabecera_pedido.id_cliente = cliente.id " +
            "INNER JOIN forma_pago " +
            "ON cabecera_pedido.id_forma_pago = forma_pago.id";

    /*public Cursor obtenerTarasFull() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(CABECERA_PEDIDO_JOIN_CLIENTE_Y_FORMA_PAGO);
        return builder.query(db, null, null, null, null, null, null);
    }*/

    public Cursor obtenerTareasEncabezado(int Tipo) {
        /*
        HISTORICO =  terminados, cancelados
        * */
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = "";

        String selectionArgs[];
        if (Tipo == EstatusTarea.HISTORICO) {
            selection = " Where (" + RutaTarea.ESTATUS + " =? or " + RutaTarea.ESTATUS + "=?)"; // WHERE id LIKE ?
            selectionArgs = new String[]{EstatusTarea.CANCELADO + "", EstatusTarea.TERMINADO + ""};
        } else {
            selection = " Where " + RutaTarea.ESTATUS + " =?"; // WHERE id LIKE ?
            selectionArgs = new String[]{EstatusTarea.EN_PROCESO + ""};
        }

        String sql = String.format("SELECT * FROM %s", BaseDatosTareas.Tablas.tblTarea) + selection;
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor obtenerTareasEncabezadoByFolio(String FolioTarea) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = RutaTarea.FOLIO + " = ?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{FolioTarea};

        return db.query(BaseDatosTareas.Tablas.tblTarea, null,
                selection, selectionArgs, null, null, null);
    }

    public boolean ExisteTareaByFolio(String FolioTarea) {
        boolean exist = false;
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = RutaTarea.FOLIO + " = ?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{FolioTarea};

        Cursor c = db.query(BaseDatosTareas.Tablas.tblTarea, null,
                selection, selectionArgs, null, null, null);
        exist = c.moveToFirst();
        return exist;
    }

    public boolean InsertarTarea(RutaTarea RutaTarea) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(RutaTarea.FOLIO, RutaTarea.getFolio());
        valores.put(RutaTarea.ID_EMPLEADO, RutaTarea.getIdEmpleado());
        valores.put(RutaTarea.ID_SUCURSAL, RutaTarea.getIdSucursal());
        valores.put(RutaTarea.FECHA_ASIG, RutaTarea.getDFechaAsig() != null ? GralUtils.getDateFullTime(RutaTarea.getDFechaAsig()) : null);
        valores.put(RutaTarea.FECHA_PROMESA, RutaTarea.getDFechaPromesa() != null ? GralUtils.getDateFullTime(RutaTarea.getDFechaPromesa()) : null);
        valores.put(RutaTarea.FECHA_INICIO, RutaTarea.getDFechaInicio() != null ? GralUtils.getDateFullTime(RutaTarea.getDFechaInicio()) : null);
        valores.put(RutaTarea.FECHA_TERMINO, RutaTarea.getDFechaTermino() != null ? GralUtils.getDateFullTime(RutaTarea.getDFechaTermino()) : null);
        valores.put(RutaTarea.ESTATUS, RutaTarea.getEstatus());
        valores.put(RutaTarea.FECHA_CANCELACION, RutaTarea.getDFechaCancelacion() != null ? GralUtils.getDateFullTime(RutaTarea.getDFechaCancelacion()) : null);
        valores.put(RutaTarea.MOTIVO_CANCELACION, RutaTarea.getMotivoCancelacion());
        valores.put(RutaTarea.OBSERVACIONES, RutaTarea.getObservaciones());

        return db.insertOrThrow(BaseDatosTareas.Tablas.tblTarea, null, valores) > 0;
    }

    public boolean ExisteTareaPendientes(String FolioTarea) {
        boolean exist = false;
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = DoctosRutasTareas.FOLIO + " = ? AND (" + DoctosRutasTareas.ESTATUS + "=? or " + DoctosRutasTareas.ESTATUS + "=?)";
        String selectionArgs[] = new String[]{FolioTarea, EstatusDoctoTarea.INICIADO + "", EstatusDoctoTarea.SIN_ENTREGAR + ""};

        Cursor c = db.query(BaseDatosTareas.Tablas.tblDetalleTarea, null,
                selection, selectionArgs, null, null, null);
        exist = c.moveToFirst();
        return exist;
    }

    public boolean ActualizarTarea(int EstatusTarea, RutaTarea Tarea) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(RutaTarea.ESTATUS, Tarea.getEstatus());

        String whereClause = String.format("%s=? AND %s=? AND %s=?", RutaTarea.FOLIO, RutaTarea.ID_EMPLEADO, RutaTarea.ID_SUCURSAL);

        final String[] whereArgs = {Tarea.getFolio(), Tarea.getIdEmpleado() + "", Tarea.getIdSucursal() + ""};
        int resultado = db.update(BaseDatosTareas.Tablas.tblTarea, valores, whereClause, whereArgs);
        return resultado > 0;
    }
    //endregion

    //region Operaciones detalle de Tareas
    public boolean ExisteTareaDetalleByFolio(String FolioTarea, String Documento) {
        boolean exist = false;
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = DoctosRutasTareas.FOLIO + " =? AND " + DoctosRutasTareas.IDDOCTOTAREA + " =?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{FolioTarea, Documento};
        String columns[] = new String[]{DoctosRutasTareas.FOLIO, DoctosRutasTareas.IDDOCTOTAREA};
        Cursor c = db.query(BaseDatosTareas.Tablas.tblDetalleTarea, columns,
                selection, selectionArgs, null, null, null);
        exist = c.moveToFirst();
        return exist;
    }

    public Cursor GetTareaDetalleByFolio(String FolioTarea, String Documento) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = DoctosRutasTareas.FOLIO + " =? AND " + DoctosRutasTareas.IDDOCTOTAREA + " =?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{FolioTarea, Documento};
        String columns[] = new String[]{DoctosRutasTareas.FOLIO, DoctosRutasTareas.IDDOCTOTAREA, DoctosRutasTareas.CVE_CLIENTE, DoctosRutasTareas.TIPOTAREA,
                DoctosRutasTareas.FECHA_DOCUMENTO, DoctosRutasTareas.ESTATUS, DoctosRutasTareas.FECHA_ENTREGA, DoctosRutasTareas.CVE_DATOS_ENVIO
                , DoctosRutasTareas.HORA_INICIAL_ENTREGA, DoctosRutasTareas.HORA_FINAL_ENTREGA, DoctosRutasTareas.MOTIVOCANCELACION,
                DoctosRutasTareas.REQCAPTURAINFO, DoctosRutasTareas.FECHA_CANCELACION, DoctosRutasTareas.IMPORTE, DoctosRutasTareas.OBSERVACIONES, DoctosRutasTareas.ORDEN,
                DoctosRutasTareas.CANT_PZAS, DoctosRutasTareas.NO_PZAS_ENTREGAR, DoctosRutasTareas.LATITUD_INICIAL, DoctosRutasTareas.LONGITUD_INICIAL,
                DoctosRutasTareas.LATITUD_FINAL, DoctosRutasTareas.LONGITUD_FINAL, DoctosRutasTareas.ID_SUCURSAL, DoctosRutasTareas.OBSENTREGA,
                DoctosRutasTareas.REQUIEREDETALLE, DoctosRutasTareas.REQEVIDENCIAFOTO, DoctosRutasTareas.REQEVIDENCIAARCH, DoctosRutasTareas.REQCAPTURAINFO,
                DoctosRutasTareas.CAPTURAINFO, DoctosRutasTareas.EVIDENCIAFOTORUTA};

        Cursor c = db.query(BaseDatosTareas.Tablas.tblDetalleTarea, columns,
                selection, selectionArgs, null, null, null);
        return c;
    }

    /*public byte[] GetImageTareaDetalleByFolio(String FolioTarea, String Documento) {
        byte[] imgByte = null;
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        db.setPageSize(10000000);
        String selection = " Where " + DoctosRutasTareas.FOLIO + " =? AND " + DoctosRutasTareas.IDDOCTOTAREA + " =?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{FolioTarea, Documento};
        String sql = String.format("SELECT " + DoctosRutasTareas.EVIDENCIAFOTO + " FROM %s", BaseDatosTareas.Tablas.tblDetalleTarea) + selection;
        Cursor c = db.rawQuery(sql, selectionArgs);
        if (c.moveToFirst()){
            imgByte = c.getBlob(0);
            c.close();
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }

        return imgByte;
    }*/

    public Cursor GetTareaDetalleByFolio(String FolioTarea) {
        boolean exist = false;
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = " Where " + DoctosRutasTareas.FOLIO + " =? "; // WHERE id LIKE ?
        String order = " order by " + DoctosRutasTareas.ORDEN; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{FolioTarea};
        String columns = DoctosRutasTareas.CVE_CLIENTE + "," + DoctosRutasTareas.TIPOTAREA + "," + DoctosRutasTareas.IDDOCTOTAREA + "," + DoctosRutasTareas.FECHA_DOCUMENTO
                + "," + DoctosRutasTareas.ESTATUS + "," + DoctosRutasTareas.FECHA_ENTREGA + "," + DoctosRutasTareas.FOLIO;
        String sql = String.format("SELECT " + columns + " FROM %s", BaseDatosTareas.Tablas.tblDetalleTarea) + selection + order;
        Cursor c = db.rawQuery(sql, selectionArgs);
        /*Cursor c = db.query(BaseDatosTareas.Tablas.tblDetalleTarea, null,
                selection, selectionArgs, null, null, null);*/
        return c;
    }

    public boolean InsertarDetalleTarea(DoctosRutasTareas DetalleRutaTarea) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(DoctosRutasTareas.FOLIO, DetalleRutaTarea.getFolio());
        valores.put(DoctosRutasTareas.IDDOCTOTAREA, DetalleRutaTarea.getIdDoctoTarea());
        valores.put(DoctosRutasTareas.ORDEN, DetalleRutaTarea.getOrden());
        valores.put(DoctosRutasTareas.FECHA_ENTREGA, DetalleRutaTarea.getDFechaEntrega() != null ? GralUtils.getDateFullTime(DetalleRutaTarea.getDFechaEntrega()) : null);
        valores.put(DoctosRutasTareas.ESTATUS, DetalleRutaTarea.getEstatus());
        valores.put(DoctosRutasTareas.FECHA_CANCELACION, DetalleRutaTarea.getDFechaCancelacion() != null ? GralUtils.getDateFullTime(DetalleRutaTarea.getDFechaCancelacion()) : null);
        valores.put(DoctosRutasTareas.MOTIVOCANCELACION, DetalleRutaTarea.getMotivoCancelacion());
        valores.put(DoctosRutasTareas.NO_PZAS_ENTREGAR, DetalleRutaTarea.getNoPzasEntregar());
        valores.put(DoctosRutasTareas.LATITUD_INICIAL, DetalleRutaTarea.getLatitudInicial());
        valores.put(DoctosRutasTareas.LONGITUD_INICIAL, DetalleRutaTarea.getLongitudInicial());
        valores.put(DoctosRutasTareas.LATITUD_FINAL, DetalleRutaTarea.getLatitudFinal());
        valores.put(DoctosRutasTareas.LONGITUD_FINAL, DetalleRutaTarea.getLongitudFinal());
        valores.put(DoctosRutasTareas.HORA_INICIAL_ENTREGA, DetalleRutaTarea.getDHoraInicialEntrega() != null ? GralUtils.getDateFullTime(DetalleRutaTarea.getDHoraInicialEntrega()) : null);
        valores.put(DoctosRutasTareas.HORA_FINAL_ENTREGA, DetalleRutaTarea.getDHoraFinalEntrega() != null ? GralUtils.getDateFullTime(DetalleRutaTarea.getDHoraFinalEntrega()) : null);
        valores.put(DoctosRutasTareas.OBSERVACIONES, DetalleRutaTarea.getObservaciones());
        valores.put(DoctosRutasTareas.SUBTOTAL, DetalleRutaTarea.getSubTotal());
        valores.put(DoctosRutasTareas.IVA, DetalleRutaTarea.getIva());
        valores.put(DoctosRutasTareas.IMPORTE, DetalleRutaTarea.getImporte());
        valores.put(DoctosRutasTareas.CVE_CLIENTE, DetalleRutaTarea.getCveCliente());
        valores.put(DoctosRutasTareas.RFC, DetalleRutaTarea.getRfc());
        if (DetalleRutaTarea.getTipoTarea() == TipoTarea.Tarea)
            valores.putNull(DoctosRutasTareas.CVE_DATOS_ENVIO);
        else
            valores.put(DoctosRutasTareas.CVE_DATOS_ENVIO, DetalleRutaTarea.getCveDatosEnvio());
        valores.put(DoctosRutasTareas.FECHA_DOCUMENTO, DetalleRutaTarea.getDFechaDocumento() != null ? GralUtils.getDateTime(DetalleRutaTarea.getDFechaDocumento()) : null);
        valores.put(DoctosRutasTareas.TIPO_DOCUMENTO, DetalleRutaTarea.getTipoDocumento());
        valores.put(DoctosRutasTareas.CANT_PZAS, DetalleRutaTarea.getCantPzas());
        valores.put(DoctosRutasTareas.REQUIEREDETALLE, GralUtils.getIntFromBoolean(DetalleRutaTarea.isRequiereDetalle()));
        valores.put(DoctosRutasTareas.TIPOTAREA, DetalleRutaTarea.getTipoTarea());
        valores.put(DoctosRutasTareas.REQEVIDENCIAFOTO, GralUtils.getIntFromBoolean(DetalleRutaTarea.isReqEvidenciaFoto()));
        valores.put(DoctosRutasTareas.REQEVIDENCIAARCH, GralUtils.getIntFromBoolean(DetalleRutaTarea.isReqEvidenciaArch()));
        valores.put(DoctosRutasTareas.REQCAPTURAINFO, GralUtils.getIntFromBoolean(DetalleRutaTarea.isReqCapturaInfo()));

        valores.put(DoctosRutasTareas.ID_SUCURSAL, DetalleRutaTarea.getIdSucursal());
        return db.insertOrThrow(BaseDatosTareas.Tablas.tblDetalleTarea, null, valores) > 0;
    }

    public boolean ActualizarDoctoTarea(int estatusDoctoTarea, DoctosRutasTareas DocRuta) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(DoctosRutasTareas.ESTATUS, DocRuta.getEstatus());
        if (estatusDoctoTarea == EstatusDoctoTarea.INICIADO) {
            //se actualiza la ubicacion inicial de llegada y la fecha y hora
            valores.put(DoctosRutasTareas.LATITUD_INICIAL, DocRuta.getLatitudInicial());
            valores.put(DoctosRutasTareas.LONGITUD_INICIAL, DocRuta.getLongitudInicial());
            valores.put(DoctosRutasTareas.HORA_INICIAL_ENTREGA, GralUtils.getDateFullTime(DocRuta.getDHoraInicialEntrega()));

        } else if (estatusDoctoTarea == EstatusDoctoTarea.ENTREGADO ||
                estatusDoctoTarea == EstatusDoctoTarea.ENTREGA_PARCIAL) {
            //se manda localizacion fginal y fecha de entrega
            valores.put(DoctosRutasTareas.LATITUD_FINAL, DocRuta.getLatitudFinal());
            valores.put(DoctosRutasTareas.LONGITUD_FINAL, DocRuta.getLongitudFinal());
            valores.put(DoctosRutasTareas.HORA_FINAL_ENTREGA, GralUtils.getDateFullTime(DocRuta.getDHoraFinalEntrega()));
            valores.put(DoctosRutasTareas.FECHA_ENTREGA, GralUtils.getDateFullTime(DocRuta.getDFechaEntrega()));
            valores.put(DoctosRutasTareas.OBSENTREGA, DocRuta.getObsEntrega());
            if (DocRuta.getTipoTarea() == TipoTarea.Tarea) {
                if (DocRuta.isReqCapturaInfo()) {
                    valores.put(DoctosRutasTareas.CAPTURAINFO, DocRuta.getCapturaInfo());
                }
                if (DocRuta.isReqEvidenciaFoto()) {
                    valores.put(DoctosRutasTareas.EVIDENCIAFOTORUTA, DocRuta.getEvidenciaFotoRuta());
                }
            }
        } else if (estatusDoctoTarea == EstatusDoctoTarea.NO_ENTREGA) {
            //se manda la localizacion final y fecha de no entrega
            valores.put(DoctosRutasTareas.LATITUD_FINAL, DocRuta.getLatitudFinal());
            valores.put(DoctosRutasTareas.LONGITUD_FINAL, DocRuta.getLongitudFinal());
            valores.put(DoctosRutasTareas.HORA_FINAL_ENTREGA, GralUtils.getDateFullTime(DocRuta.getDHoraFinalEntrega()));
            valores.put(DoctosRutasTareas.OBSENTREGA, DocRuta.getObsEntrega());
        }

        String whereClause = String.format("%s=? AND %s=? AND %s=?", DoctosRutasTareas.ID_SUCURSAL, DoctosRutasTareas.FOLIO, DoctosRutasTareas.IDDOCTOTAREA);

        final String[] whereArgs = {DocRuta.getIdSucursal() + "", DocRuta.getFolio(), DocRuta.getIdDoctoTarea()};
        int resultado = db.update(BaseDatosTareas.Tablas.tblDetalleTarea, valores, whereClause, whereArgs);
        return resultado > 0;
    }
    //endregion


    //region Operaciones partida detalle de Tareas

    public Cursor GetParTareaDetalleByFolio(String FolioTarea, String Documento) {
        boolean exist = false;
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = " Where " + DetalleDoctoRutaTarea.FOLIO + "=? AND " + DetalleDoctoRutaTarea.DOCUMENTO + "=? "; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{FolioTarea, Documento};
        String sql = String.format("SELECT * FROM %s", BaseDatosTareas.Tablas.tblDetalleDoctoTarea) + selection;
        Cursor c = db.rawQuery(sql, selectionArgs);
        /*Cursor c = db.query(BaseDatosTareas.Tablas.tblDetalleTarea, null,
                selection, selectionArgs, null, null, null);*/
        return c;
    }

    public boolean ExisteParTareaDetalleByFolio(DetalleDoctoRutaTarea ParTareaDetalle) {
        boolean exist = false;
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = DetalleDoctoRutaTarea.FOLIO + " =? AND " + DetalleDoctoRutaTarea.DOCUMENTO + " =?" +
                " AND " + DetalleDoctoRutaTarea.CVE_ART + " =? AND " + DetalleDoctoRutaTarea.NUM_PART + "=?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{ParTareaDetalle.getFolio(), ParTareaDetalle.getDocumento()
                , ParTareaDetalle.getCveArt(), ParTareaDetalle.getNumPart() + ""};

        Cursor c = db.query(BaseDatosTareas.Tablas.tblDetalleDoctoTarea, null,
                selection, selectionArgs, null, null, null);
        exist = c.moveToFirst();
        return exist;
    }

    public boolean InsertarParDetalleTarea(DetalleDoctoRutaTarea ParDetalleRutaTarea) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        //CantSalida,CantAsignada,Estatus,CveArt,CantSAE,Folio,Linea,NumPart,CantRecibida,Documento,Descripcion
        ContentValues valores = new ContentValues();
        valores.put(DetalleDoctoRutaTarea.FOLIO, ParDetalleRutaTarea.getFolio());
        valores.put(DetalleDoctoRutaTarea.DOCUMENTO, ParDetalleRutaTarea.getDocumento());
        valores.put(DetalleDoctoRutaTarea.CVE_ART, ParDetalleRutaTarea.getCveArt());
        valores.put(DetalleDoctoRutaTarea.ESTATUS, ParDetalleRutaTarea.getEstatus());
        valores.put(DetalleDoctoRutaTarea.DESCRIPCION, ParDetalleRutaTarea.getDescripcion());
        valores.put(DetalleDoctoRutaTarea.LINEA, ParDetalleRutaTarea.getLinea());
        valores.put(DetalleDoctoRutaTarea.CANT_SAE, ParDetalleRutaTarea.getCantSAE());
        valores.put(DetalleDoctoRutaTarea.CANT_ASIGNADA, ParDetalleRutaTarea.getCantAsignada());
        valores.put(DetalleDoctoRutaTarea.CANT_SALIDA, ParDetalleRutaTarea.getCantSalida());
        valores.put(DetalleDoctoRutaTarea.CANT_RECIBIDA, ParDetalleRutaTarea.getCantRecibida());
        valores.put(DetalleDoctoRutaTarea.NUM_PART, ParDetalleRutaTarea.getNumPart());

        return db.insertOrThrow(BaseDatosTareas.Tablas.tblDetalleDoctoTarea, null, valores) > 0;
    }

    public boolean ActualizarParDoctoTarea(DetalleDoctoRutaTarea ParDocRuta) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        //se manda la localizacion final y fecha de no entrega
        valores.put(DetalleDoctoRutaTarea.ESTATUS, ParDocRuta.getEstatus());
        valores.put(DetalleDoctoRutaTarea.CANT_RECIBIDA, ParDocRuta.getCantRecibida());

        String whereClause = String.format("%s=? AND %s=? AND %s=? AND %s=?",
                DetalleDoctoRutaTarea.FOLIO, DetalleDoctoRutaTarea.DOCUMENTO, DetalleDoctoRutaTarea.CVE_ART, DetalleDoctoRutaTarea.NUM_PART);

        final String[] whereArgs = {ParDocRuta.getFolio(), ParDocRuta.getDocumento(), ParDocRuta.getCveArt(), ParDocRuta.getNumPart() + ""};
        int resultado = db.update(BaseDatosTareas.Tablas.tblDetalleDoctoTarea, valores, whereClause, whereArgs);
        return resultado > 0;
    }
    //endregion

    //region Bitacora tareas
    public boolean InsertarBitacoraTareas(BitacoraRepartosRpt BitacoraRepartosRpt) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();
        //
        ContentValues valores = new ContentValues();
        valores.put(BitacoraRepartosRpt.IDSUCURSAL, BitacoraRepartosRpt.getIdSucursal());
        valores.put(BitacoraRepartosRpt.IDEMPLEADO, BitacoraRepartosRpt.getIdEmpleado());
        valores.put(BitacoraRepartosRpt.FECHA, BitacoraRepartosRpt.getFecha() != null ? GralUtils.getDateTime(BitacoraRepartosRpt.getFecha()) : null);

        return db.insertOrThrow(BaseDatosTareas.Tablas.tblBitacoraRepartosRpt, null, valores) > 0;
    }

    public boolean ExisteBitacoraTareas(BitacoraRepartosRpt BitacoraRepartosRpt) {
        boolean exist = false;
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = BitacoraRepartosRpt.IDSUCURSAL + " =? AND " + BitacoraRepartosRpt.IDEMPLEADO + " =? AND " + BitacoraRepartosRpt.FECHA + "=?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{BitacoraRepartosRpt.getIdSucursal() + "",
                BitacoraRepartosRpt.getIdEmpleado() + "", GralUtils.getDateTimeRReverse(BitacoraRepartosRpt.getFecha())};
        String columns[] = new String[]{DoctosRutasTareas.FOLIO, DoctosRutasTareas.IDDOCTOTAREA};
        Cursor c = db.query(BaseDatosTareas.Tablas.tblDetalleTarea, columns,
                selection, selectionArgs, null, null, null);
        exist = c.moveToFirst();
        return exist;
    }

    //endregion Bitacora tareas

    //region Operaciones generales
    public boolean CleanBaseDatos() {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        int resultado1 = db.delete(BaseDatosTareas.Tablas.tblDetalleDoctoTarea, null, null);
        int resultado2 = db.delete(BaseDatosTareas.Tablas.tblDetalleTarea, null, null);
        int resultado3 = db.delete(BaseDatosTareas.Tablas.tblInfEnvio, null, null);
        int resultado4 = db.delete(BaseDatosTareas.Tablas.tblTarea, null, null);
        int resultado5 = db.delete(BaseDatosTareas.Tablas.tblCliente, null, null);
        int resultado6 = db.delete(BaseDatosTareas.Tablas.tblBitacoraRepartosRpt, null, null);
        return resultado1 > 0 && resultado2 > 0 && resultado3 > 0 && resultado4 > 0 && resultado5 > 0 && resultado6 > 0;
    }
    //endregion


    //region Operaciones con VEhiculoss
    public Cursor ObtenerVehiculos() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", BaseDatosTareas.Tablas.tblVehiculosUsuario);
        return db.rawQuery(sql, null);
    }

    public boolean ExisteVehiculo(int IdVehiculo) {
        boolean exist = false;
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String selection = VehiculosUsuario.IDVEHICULO + " = ?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{IdVehiculo + ""};

        Cursor c = db.query(BaseDatosTareas.Tablas.tblVehiculosUsuario, null,
                selection, selectionArgs, null, null, null);
        exist = c.moveToFirst();
        return exist;
    }

    public Cursor GetVehiculo(int IdVehiculo) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();
        String selection = " Where " + VehiculosUsuario.IDVEHICULO + "=?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{IdVehiculo + ""};
        String sql = String.format("SELECT * FROM %s", BaseDatosTareas.Tablas.tblVehiculosUsuario) + selection;
        Cursor c = db.rawQuery(sql, selectionArgs);
        /*Cursor c = db.query(BaseDatosTareas.Tablas.tblDetalleTarea, null,
                selection, selectionArgs, null, null, null);*/
        return c;
    }

    public boolean InsertarVehiculo(VehiculosUsuario Vehiculo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(VehiculosUsuario.IDVEHICULO, Vehiculo.getIdVehiculo());
        valores.put(VehiculosUsuario.DESCRIPCION, Vehiculo.getDescripcion());
        valores.put(VehiculosUsuario.PLACAS, Vehiculo.getPlacas());
        valores.put(VehiculosUsuario.TIPOVEHICULO, Vehiculo.getTipoVehiculo());
        valores.put(VehiculosUsuario.ESTATUS, Vehiculo.getEstatus());

        return db.insertOrThrow(BaseDatosTareas.Tablas.tblVehiculosUsuario, null, valores) > 0;
    }

    public boolean ActualizarVehiculo(VehiculosUsuario Vehiculo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(VehiculosUsuario.DESCRIPCION, Vehiculo.getDescripcion());
        valores.put(VehiculosUsuario.PLACAS, Vehiculo.getPlacas());
        valores.put(VehiculosUsuario.TIPOVEHICULO, Vehiculo.getTipoVehiculo());
        valores.put(VehiculosUsuario.ESTATUS, Vehiculo.getEstatus());

        String whereClause = String.format("%s=?", VehiculosUsuario.IDVEHICULO);
        final String[] whereArgs = {Vehiculo.getIdVehiculo() + ""};
        int resultado = db.update(BaseDatosTareas.Tablas.tblVehiculosUsuario, valores, whereClause, whereArgs);
        return resultado > 0;
    }

    /*public boolean EliminarCliente(String idCliente) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s=?", Cliente.CVE_CLIENTE);
        final String[] whereArgs = {idCliente};

        int resultado = db.delete(BaseDatosTareas.Tablas.tblCliente, whereClause, whereArgs);
        return resultado > 0;
    }*/
    //endregion
}
