package com.deinteti.gb.cricmodulemovil10.Enums;

import java.io.Serializable;

/**
 * Created by desarrollo on 19/03/2018.
 */

public class EstatusTarea implements Serializable{
    public static final int EN_PROCESO = 2;
    public static final int PAUSADO = 3;
    public static final int CANCELADO = 4;
    public static final int TERMINADO = 5;
    public static final int HISTORICO = 10;
    public static final int ENRETORNO = 100;

    public static String GetDescripcion(int en){
        String txt="";
        switch (en)
        {
            case EN_PROCESO:
                return "En proceso";
            case PAUSADO:
                return  "En pausa";
            case CANCELADO:
                return  "Cancelado";
            case TERMINADO:
                return  "Terminado";
            case ENRETORNO:
                return  "Retorno a sucursal";
        }
        return  txt;
    }

}
