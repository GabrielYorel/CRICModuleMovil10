package com.deinteti.gb.cricmodulemovil10.Entidades;

import android.database.Cursor;

import com.deinteti.gb.cricmodulemovil10.AccesoDatos.ContratoTareas;

import java.io.Serializable;

public class VehiculosUsuario implements ContratoTareas.VehiculosUsuario, Serializable {

    private int IdVehiculo;
    private String Descripcion;
    private String Placas;
    private String TipoVehiculo;
    private int Estatus;

    public VehiculosUsuario() {
    }

    public VehiculosUsuario(Cursor cursor) {
        IdVehiculo = cursor.getInt(cursor.getColumnIndex(VehiculosUsuario.IDVEHICULO));
        Descripcion = cursor.getString(cursor.getColumnIndex(VehiculosUsuario.DESCRIPCION));
        Placas = cursor.getString(cursor.getColumnIndex(VehiculosUsuario.PLACAS));
        Estatus = cursor.getInt((cursor.getColumnIndex(VehiculosUsuario.ESTATUS)));
    }

    public int getIdVehiculo() {
        return IdVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        IdVehiculo = idVehiculo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getPlacas() {
        return Placas;
    }

    public void setPlacas(String placas) {
        Placas = placas;
    }

    public String getTipoVehiculo() {
        return TipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        TipoVehiculo = tipoVehiculo;
    }

    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(int estatus) {
        Estatus = estatus;
    }
}
