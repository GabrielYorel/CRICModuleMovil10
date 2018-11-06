package com.deinteti.gb.cricmodulemovil10.Entidades;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by desarrollo on 08/03/2018.
 */

public class VehiculoTipos  implements Serializable {
    public VehiculoTipos()
    {
    }
    public int IdTipo;
    public String Descripcion;
    @Nullable
    public int Estatus;

    public int getIdTipo() {
        return IdTipo;
    }

    public void setIdTipo(int idTipo) {
        IdTipo = idTipo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    @Nullable
    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(@Nullable int estatus) {
        Estatus = estatus;
    }
}
