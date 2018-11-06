package com.deinteti.gb.cricmodulemovil10.Entidades;

import com.deinteti.gb.cricmodulemovil10.Enums.DispositivoUsuarioEnum;

import java.io.Serializable;
import java.util.List;

/**
 * Created by desarrollo on 08/03/2018.
 */

public class TareaResult implements Serializable{

    private int Result;
    private String Message;
    private List<RutaTarea> Tareas;

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<RutaTarea> getTareas() {
        return Tareas;
    }

    public void setTareas(List<RutaTarea> tareas) {
        Tareas = tareas;
    }
}
