package com.deinteti.gb.cricmodulemovil10.Entidades;

import android.support.annotation.Nullable;

import com.deinteti.gb.cricmodulemovil10.AccesoDatos.ContratoTareas;

import java.io.Serializable;
import java.util.Date;

public class BitacoraRepartosRpt implements ContratoTareas.BitacoraRepartosRpt, Serializable {

    private int IdRow;
    @Nullable
    private int IdSucursal;
    @Nullable
    private int IdEmpleado;
    @Nullable
    private Date Fecha;

    public int getIdRow() {
        return IdRow;
    }

    public void setIdRow(int idRow) {
        IdRow = idRow;
    }

    @Nullable
    public int getIdSucursal() {
        return IdSucursal;
    }

    public void setIdSucursal(@Nullable int idSucursal) {
        IdSucursal = idSucursal;
    }

    @Nullable
    public int getIdEmpleado() {
        return IdEmpleado;
    }

    public void setIdEmpleado(@Nullable int idEmpleado) {
        IdEmpleado = idEmpleado;
    }

    @Nullable
    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(@Nullable Date fecha) {
        Fecha = fecha;
    }
}
