package com.deinteti.gb.cricmodulemovil10.Entidades;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.deinteti.gb.cricmodulemovil10.AccesoDatos.ContratoTareas;

import java.io.Serializable;

/**
 * Created by desarrollo on 08/03/2018.
 */

public  class DetalleDoctoRutaTarea implements ContratoTareas.ColumnasDetalleDoctoTarea, Serializable {
   
    private String Folio;
    private String Documento;
    private String CveArt;
    @Nullable private int Estatus;
    private String Descripcion;
    private String Linea;
    private int NumPart;
    @Nullable private double CantSAE;
    @Nullable private double CantAsignada;
    @Nullable private double CantSalida;
    @Nullable private double CantRecibida;
    private boolean Checked;

    public DetalleDoctoRutaTarea(Cursor cursor) {
        Folio = cursor.getString(cursor.getColumnIndex(DetalleDoctoRutaTarea.FOLIO));
        Documento = cursor.getString(cursor.getColumnIndex(DetalleDoctoRutaTarea.DOCUMENTO));
        CveArt = cursor.getString(cursor.getColumnIndex(DetalleDoctoRutaTarea.CVE_ART));
        Estatus = cursor.getInt((cursor.getColumnIndex(DetalleDoctoRutaTarea.ESTATUS)));
        Descripcion = cursor.getString(cursor.getColumnIndex(DetalleDoctoRutaTarea.DESCRIPCION));
        Linea = cursor.getString(cursor.getColumnIndex(DetalleDoctoRutaTarea.LINEA));
        NumPart = cursor.getInt(cursor.getColumnIndex(DetalleDoctoRutaTarea.NUM_PART));
        CantSAE = cursor.getDouble(cursor.getColumnIndex(DetalleDoctoRutaTarea.CANT_SAE));
        CantAsignada = cursor.getDouble(cursor.getColumnIndex(DetalleDoctoRutaTarea.CANT_ASIGNADA));
        CantSalida = cursor.getDouble(cursor.getColumnIndex(DetalleDoctoRutaTarea.CANT_SALIDA));
        CantRecibida = cursor.getDouble(cursor.getColumnIndex(DetalleDoctoRutaTarea.CANT_RECIBIDA));
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String folio) {
        Folio = folio;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }

    public String getCveArt() {
        return CveArt;
    }

    public void setCveArt(String cveArt) {
        CveArt = cveArt;
    }

    @Nullable
    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(@Nullable int estatus) {
        Estatus = estatus;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getLinea() {
        return Linea;
    }

    public void setLinea(String linea) {
        Linea = linea;
    }

    @Nullable
    public double getCantSAE() {
        return CantSAE;
    }

    public void setCantSAE(@Nullable double cantSAE) {
        CantSAE = cantSAE;
    }

    @Nullable
    public double getCantAsignada() {
        return CantAsignada;
    }

    public void setCantAsignada(@Nullable double cantAsignada) {
        CantAsignada = cantAsignada;
    }

    @Nullable
    public double getCantSalida() {
        return CantSalida;
    }

    public void setCantSalida(@Nullable double cantSalida) {
        CantSalida = cantSalida;
    }

    @Nullable
    public double getCantRecibida() {
        return CantRecibida;
    }

    public void setCantRecibida(@Nullable double cantRecibida) {
        CantRecibida = cantRecibida;
    }

    public int getNumPart() {
        return NumPart;
    }

    public void setNumPart(int numPart) {
        NumPart = numPart;
    }

    public boolean isChecked()
    {
        return Checked;
    }

    public void setChecked(boolean Checked)
    {
        this.Checked = Checked;
    }
}
