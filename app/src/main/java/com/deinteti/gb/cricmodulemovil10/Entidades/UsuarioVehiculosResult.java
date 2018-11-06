package com.deinteti.gb.cricmodulemovil10.Entidades;

import java.io.Serializable;
import java.util.ArrayList;

public class UsuarioVehiculosResult implements Serializable {
    private int Resultado;
    private String Message;
    private ArrayList<VehiculosUsuario> VarMultiUso;

    public int getResultado() {
        return Resultado;
    }

    public void setResultado(int resultado) {
        Resultado = resultado;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<VehiculosUsuario> getVarMultiUso() {
        return VarMultiUso;
    }

    public void setVarMultiUso(ArrayList<VehiculosUsuario> varMultiUso) {
        VarMultiUso = varMultiUso;
    }
}
