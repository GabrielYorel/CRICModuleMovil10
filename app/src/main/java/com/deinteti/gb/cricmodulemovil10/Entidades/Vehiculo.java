package com.deinteti.gb.cricmodulemovil10.Entidades;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by desarrollo on 08/03/2018.
 */

public class Vehiculo  implements Serializable {
    public Vehiculo()
    {
    }
    public int IdVehiculo;
    @Nullable
    public int IdTipo;
    public String Descripcion;
    public String Placas;
    @Nullable
    public int Estatus;

    public VehiculoTipos VehiculoTipos;

    public int getIdVehiculo() {
        return IdVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        IdVehiculo = idVehiculo;
    }

    @Nullable
    public int getIdTipo() {
        return IdTipo;
    }

    public void setIdTipo(@Nullable int idTipo) {
        IdTipo = idTipo;
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

    @Nullable
    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(@Nullable int estatus) {
        Estatus = estatus;
    }

    public com.deinteti.gb.cricmodulemovil10.Entidades.VehiculoTipos getVehiculoTipos() {
        return VehiculoTipos;
    }

    public void setVehiculoTipos(com.deinteti.gb.cricmodulemovil10.Entidades.VehiculoTipos vehiculoTipos) {
        VehiculoTipos = vehiculoTipos;
    }
}
