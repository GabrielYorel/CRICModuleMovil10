package com.deinteti.gb.cricmodulemovil10.Entidades;

import java.io.Serializable;

public class TareasGenReporteResult implements Serializable {

    private int Estatus;

    private String Message;

    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(int estatus) {
        Estatus = estatus;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
