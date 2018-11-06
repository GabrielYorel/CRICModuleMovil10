package com.deinteti.gb.cricmodulemovil10.Enums;

public class EstatusVehiculoEnum {
    public static final int ACTIVO = 1;
    public static final int BAJA = 2;
    public static final int BLOQUEADO = 3;
    public static final int EN_USO = 4;

    public static String GetDescripcion(int estatus) {
        String txt = "No especificado";
        switch (estatus) {
            case ACTIVO:
                return "Disponible";
            case BAJA:
                return "Baja";
            case BLOQUEADO:
                return "Bloqueado";
            case EN_USO:
                return "En uso";
        }
        return txt;
    }
}
