package com.deinteti.gb.cricmodulemovil10.Entidades;

import android.database.Cursor;

import com.deinteti.gb.cricmodulemovil10.AccesoDatos.ContratoTareas;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by desarrollo on 23/03/2018.
 */

public class Cliente implements ContratoTareas.ColumnasCliente, Serializable {
    private String CveCliente;
    private String Nombre;

    public Cliente(String cveCliente, String nombre) {
        CveCliente = cveCliente;
        Nombre = nombre;
    }
    public Cliente(Cursor cursor) {
        CveCliente = cursor.getString(cursor.getColumnIndex(Cliente.CVE_CLIENTE));
        Nombre = cursor.getString(cursor.getColumnIndex(Cliente.NOMBRE));
    }

    public String getCveCliente() {
        return CveCliente;
    }

    public void setCveCliente(String cveCliente) {
        CveCliente = cveCliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public static String generarIdCliente() {
        return "CLI-" + UUID.randomUUID().toString();
    }
}
