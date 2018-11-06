package com.deinteti.gb.cricmodulemovil10.Entidades;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by desarrollo on 08/03/2018.
 */

public class Empleado  implements Serializable {
    public Empleado()
    {
    }
    public int IdEmpleado;
    public String Nombre;
    @Nullable
    public int IdPuesto;
    @Nullable
    public int IdDepto;
    @Nullable
    public int IdVehiculo;
    @Nullable
    public int Estatus;
    public String Apellidos;
    public int IdSucursal;
    public byte[] Foto;

    public Vehiculo Vehiculo;
    public Sucursal Sucursal;
    public ArrayList<RutaTarea> RutaTarea;

    public int getIdEmpleado() {
        return IdEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        IdEmpleado = idEmpleado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    @Nullable
    public int getIdPuesto() {
        return IdPuesto;
    }

    public void setIdPuesto(@Nullable int idPuesto) {
        IdPuesto = idPuesto;
    }

    @Nullable
    public int getIdDepto() {
        return IdDepto;
    }

    public void setIdDepto(@Nullable int idDepto) {
        IdDepto = idDepto;
    }

    @Nullable
    public int getIdVehiculo() {
        return IdVehiculo;
    }

    public void setIdVehiculo(@Nullable int idVehiculo) {
        IdVehiculo = idVehiculo;
    }

    @Nullable
    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(@Nullable int estatus) {
        Estatus = estatus;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public int getIdSucursal() {
        return IdSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        IdSucursal = idSucursal;
    }

    public byte[] getFoto() {
        return Foto;
    }

    public void setFoto(byte[] foto) {
        Foto = foto;
    }

    public com.deinteti.gb.cricmodulemovil10.Entidades.Vehiculo getVehiculo() {
        return Vehiculo;
    }

    public void setVehiculo(com.deinteti.gb.cricmodulemovil10.Entidades.Vehiculo vehiculo) {
        Vehiculo = vehiculo;
    }

    public com.deinteti.gb.cricmodulemovil10.Entidades.Sucursal getSucursal() {
        return Sucursal;
    }

    public void setSucursal(com.deinteti.gb.cricmodulemovil10.Entidades.Sucursal sucursal) {
        Sucursal = sucursal;
    }

    public ArrayList<com.deinteti.gb.cricmodulemovil10.Entidades.RutaTarea> getRutaTarea() {
        return RutaTarea;
    }

    public void setRutaTarea(ArrayList<com.deinteti.gb.cricmodulemovil10.Entidades.RutaTarea> rutaTarea) {
        RutaTarea = rutaTarea;
    }
}
