package com.deinteti.gb.cricmodulemovil10.Enums;

import com.google.gson.annotations.SerializedName;

/**
 * Created by desarrollo on 19/03/2018.
 */

public class TipoTarea {
    public static final int Entrega = 1;
    public static final int Cobranza = 2;
    public static final int Tarea = 3;

    public static String lblFechaFinalizado(int tipoTarea) {
        String txt = "";
        switch (tipoTarea) {
            case Entrega:
                return "Fch. Entrega: ";
            case Tarea:
                return "Fch. término: ";
            case Cobranza:
                return "Fch. cobranza: ";
        }
        return txt;
    }

    public static String GetDescripcion(int en){
        String txt="";
        switch (en)
        {
            case Cobranza:
                return "Cobranza";
            case Entrega:
                return  "Entrega";
            case Tarea:
                return "Actividad";
        }
        return  txt;
    }
}
