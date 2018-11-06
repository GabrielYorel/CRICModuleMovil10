package com.deinteti.gb.cricmodulemovil10.Enums;

import java.io.Serializable;

/**
 * Created by desarrollo on 12/04/2018.
 */

public class EstatusPartidaDocto implements Serializable{
    public static final int SIN_ENTREGAR = 1;
    public static final int ENTREGADO = 2;
    public static final int DEVUELTO = 3;
    public static final int ENTREGA_PARCIAL = 4;

    public static String GetDescripcion(int en){
        String txt="";
        switch (en)
        {
            case SIN_ENTREGAR:
                return "Sin entregar";
            case ENTREGADO:
                return  "Entregado";
            case DEVUELTO:
                return  "Devuleto";
            case ENTREGA_PARCIAL:
                return  "Entregado Parc.";
        }
        return  txt;
    }
}
