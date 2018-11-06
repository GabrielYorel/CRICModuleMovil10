package com.deinteti.gb.cricmodulemovil10.Entidades;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by desarrollo on 08/03/2018.
 */

public class Usuario implements Serializable {
    public Usuario()
    {
    }
    private int IdUsuario;
    private  String Nombre;
    @Nullable
    private  int Estatus;
    @Nullable
    private  int IdEmpleado;
    @Nullable
    private  int TipoAutenticacion;
    private  String CuentaAcceso;
    private  String ClaveAcceso;

    private  Empleado Empleado;

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    @Nullable
    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(@Nullable int estatus) {
        Estatus = estatus;
    }

    @Nullable
    public int getIdEmpleado() {
        return IdEmpleado;
    }

    public void setIdEmpleado(@Nullable int idEmpleado) {
        IdEmpleado = idEmpleado;
    }

    @Nullable
    public int getTipoAutenticacion() {
        return TipoAutenticacion;
    }

    public void setTipoAutenticacion(@Nullable int tipoAutenticacion) {
        TipoAutenticacion = tipoAutenticacion;
    }

    public String getCuentaAcceso() {
        return CuentaAcceso;
    }

    public void setCuentaAcceso(String cuentaAcceso) {
        CuentaAcceso = cuentaAcceso;
    }

    public String getClaveAcceso() {
        return ClaveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        ClaveAcceso = claveAcceso;
    }

    public Empleado getEmpleado() {
        return Empleado;
    }

    public void setEmpleado(Empleado empleado) {
        Empleado = empleado;
    }
}
