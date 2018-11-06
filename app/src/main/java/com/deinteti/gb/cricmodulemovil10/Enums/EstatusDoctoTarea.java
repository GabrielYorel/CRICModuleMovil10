package com.deinteti.gb.cricmodulemovil10.Enums;

import java.io.Serializable;

/**
 * Created by desarrollo on 19/03/2018.
 */

public class EstatusDoctoTarea implements Serializable {
    public static final int SIN_ENTREGAR = 1;
    public static final int ENTREGADO = 2;
    public static final int CANCELADO = 3;
    public static final int EN_PROCESO = 4;

    public static final int ENTREGA_PARCIAL = 100;
    public static final int INICIADO = 200;
    public static final int NO_ENTREGA = 300;

    public static String GetDescripcion(int en, int tipoTarea) {
        String txt = "No especificado";
        switch (en) {
            case SIN_ENTREGAR:
                if (tipoTarea == TipoTarea.Tarea)
                    return "No terminada";
                else
                    return "Sin entregar";
            case ENTREGADO:
                if (tipoTarea == TipoTarea.Tarea)
                    return "Terminada";
                else
                    return "Entregado";
            case CANCELADO:
                return "Cancelado";
            case EN_PROCESO:
                return "En proceso";
            case ENTREGA_PARCIAL:
                return "Entregado Parc.";
            case INICIADO:
                if (tipoTarea == TipoTarea.Tarea)
                    return "Iniciada";
                else
                    return "Con cliente";
            case NO_ENTREGA:
                return "No entrega";
        }
        return txt;
    }

}
