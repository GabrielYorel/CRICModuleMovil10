package com.deinteti.gb.cricmodulemovil10.Entidades;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.deinteti.gb.cricmodulemovil10.AccesoDatos.ContratoTareas;
import com.deinteti.gb.cricmodulemovil10.Enums.EstatusTarea;
import com.deinteti.gb.cricmodulemovil10.GralUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by desarrollo on 08/03/2018.
 */

public class RutaTarea implements ContratoTareas.ColumnasTareas, Serializable {
    private String Folio;
    private int IdEmpleado;
    private int IdSucursal;
    @Nullable
    private Date DFechaAsig;
    @Nullable
    private Date DFechaPromesa;
    @Nullable
    private Date DFechaInicio;
    @Nullable
    private Date DFechaTermino;
    @Nullable
    private String FechaAsig;
    @Nullable
    private String FechaPromesa;
    @Nullable
    private String FechaInicio;
    @Nullable
    private String FechaTermino;
    @Nullable
    private int Estatus;
    @Nullable
    private Date DFechaCancelacion;
    @Nullable
    private String FechaCancelacion;
    private String MotivoCancelacion;
    private String Observaciones;
    private ArrayList<DoctosRutasTareas> DoctosRutasTareas;
    private String Estatus_s;
    private String NombreSucursal;
    private String NombreEmpleado;

    public RutaTarea(Cursor cursor) {
        Folio = cursor.getString(cursor.getColumnIndex(RutaTarea.FOLIO));
        IdEmpleado = cursor.getInt(cursor.getColumnIndex(RutaTarea.ID_EMPLEADO));
        IdSucursal = cursor.getInt(cursor.getColumnIndex(RutaTarea.ID_SUCURSAL));
        FechaAsig = cursor.getString(cursor.getColumnIndex(RutaTarea.FECHA_ASIG));
        FechaPromesa = cursor.getString(cursor.getColumnIndex(RutaTarea.FECHA_PROMESA));
        FechaInicio = cursor.getString(cursor.getColumnIndex(RutaTarea.FECHA_INICIO));
        FechaTermino = cursor.getString(cursor.getColumnIndex(RutaTarea.FECHA_TERMINO));
        Estatus = cursor.getInt((cursor.getColumnIndex(RutaTarea.ESTATUS)));
        FechaCancelacion = cursor.getString(cursor.getColumnIndex(RutaTarea.FECHA_CANCELACION));
        MotivoCancelacion = cursor.getString(cursor.getColumnIndex(RutaTarea.MOTIVO_CANCELACION));
        Observaciones = cursor.getString(cursor.getColumnIndex(RutaTarea.OBSERVACIONES));
    }

    public RutaTarea(String Folio, String FechaAsig, int Estatus) {
        this.Folio = Folio;
        this.FechaAsig = FechaAsig;
        this.Estatus = Estatus;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String folio) {
        Folio = folio;
    }

    public int getIdEmpleado() {
        return IdEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        IdEmpleado = idEmpleado;
    }

    public int getIdSucursal() {
        return IdSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        IdSucursal = idSucursal;
    }

    @Nullable
    public String getFechaAsig() {
        return FechaAsig;
    }

    public void setFechaAsig(@Nullable String fechaAsig) {
        FechaAsig = fechaAsig;
    }

    @Nullable
    public String getFechaPromesa() {
        return FechaPromesa;
    }

    public void setFechaPromesa(@Nullable String fechaPromesa) {
        FechaPromesa = fechaPromesa;
    }

    @Nullable
    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(@Nullable String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    @Nullable
    public String getFechaTermino() {
        return FechaTermino;
    }

    public void setFechaTermino(@Nullable String fechaTermino) {
        FechaTermino = fechaTermino;
    }

    @Nullable
    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(@Nullable int estatus) {
        Estatus = estatus;
    }

    @Nullable
    public String getFechaCancelacion() {
        return FechaCancelacion;
    }

    public void setFechaCancelacion(@Nullable String fechaCancelacion) {
        FechaCancelacion = fechaCancelacion;
    }

    public String getMotivoCancelacion() {
        return MotivoCancelacion == null ? "" : MotivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        MotivoCancelacion = motivoCancelacion;
    }

    public String getObservaciones() {
        return Observaciones == null ? "" : Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    public ArrayList<com.deinteti.gb.cricmodulemovil10.Entidades.DoctosRutasTareas> getDoctosRutasTareas() {
        return DoctosRutasTareas;
    }

    public void setDoctosRutasTareas(ArrayList<com.deinteti.gb.cricmodulemovil10.Entidades.DoctosRutasTareas> doctosRutasTareas) {
        DoctosRutasTareas = doctosRutasTareas;
    }

    public String getEstatus_s() {
        return Estatus_s;
    }

    public void setEstatus_s(String estatus_s) {
        Estatus_s = estatus_s;
    }

    public String getNombreSucursal() {
        return NombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        NombreSucursal = nombreSucursal;
    }

    public String getNombreEmpleado() {
        return NombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        NombreEmpleado = nombreEmpleado;
    }

    @Nullable
    public Date getDFechaAsig() {
        return GralUtils.getDateFullTime(getFechaAsig());
    }

    public void setDFechaAsig(@Nullable Date DFechaAsig) {
        this.DFechaAsig = DFechaAsig;
    }

    @Nullable
    public Date getDFechaPromesa() {
        return GralUtils.getDateFullTime(getFechaPromesa());
    }

    public void setDFechaPromesa(@Nullable Date DFechaPromesa) {
        this.DFechaPromesa = DFechaPromesa;
    }

    @Nullable
    public Date getDFechaInicio() {
        return GralUtils.getDateFullTime(getFechaInicio());
    }

    public void setDFechaInicio(@Nullable Date DFechaInicio) {
        this.DFechaInicio = DFechaInicio;
    }

    @Nullable
    public Date getDFechaTermino() {
        return GralUtils.getDateFullTime(getFechaTermino());
    }

    public void setDFechaTermino(@Nullable Date DFechaTermino) {
        this.DFechaTermino = DFechaTermino;
    }

    @Nullable
    public Date getDFechaCancelacion() {
        return GralUtils.getDateFullTime(getFechaCancelacion());
    }

    public void setDFechaCancelacion(@Nullable Date DFechaCancelacion) {
        this.DFechaCancelacion = DFechaCancelacion;
    }

    /**
     * An array of sample (dummy) items.
     */
    public static final List<RutaTarea> ITEMS = new ArrayList<RutaTarea>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, RutaTarea> ITEM_MAP = new HashMap<String, RutaTarea>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(RutaTarea item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getFolio(), item);
    }

    private static RutaTarea createDummyItem(int position) {
        return new RutaTarea("Folio: " + String.valueOf(position), new Date(2018, 03, 02).toString(), EstatusTarea.EN_PROCESO);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

}
