package com.deinteti.gb.cricmodulemovil10.Entidades;

import java.io.Serializable;

/**
 * Created by desarrollo on 08/03/2018.
 */

public class Sucursal  implements Serializable {
    public Sucursal()
    {
    }
    public int IdSucursal;
    public String NoEmpresa;
    public String Nombre;
    public String CteMostrador;
    public Boolean Selec;


    public int getIdSucursal() {
        return IdSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        IdSucursal = idSucursal;
    }

    public String getNoEmpresa() {
        return NoEmpresa;
    }

    public void setNoEmpresa(String noEmpresa) {
        NoEmpresa = noEmpresa;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCteMostrador() {
        return CteMostrador;
    }

    public void setCteMostrador(String cteMostrador) {
        CteMostrador = cteMostrador;
    }

    public Boolean getSelec() {
        return Selec;
    }

    public void setSelec(Boolean selec) {
        Selec = selec;
    }
}
