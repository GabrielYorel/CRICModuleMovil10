package com.deinteti.gb.cricmodulemovil10.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

import com.deinteti.gb.cricmodulemovil10.Enums.DispositivoUsuarioEnum;

import java.io.Serializable;

/**
 * Created by desarrollo on 08/03/2018.
 */

public class LoginResult implements Serializable{

    private DispositivoUsuarioEnum LoginRes;
    private String Message;
    private Usuario VarMultiUso;


    public DispositivoUsuarioEnum getLoginRes() {
        return LoginRes;
    }

    public void setLoginRes(DispositivoUsuarioEnum loginRes) {
        LoginRes = loginRes;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Usuario getVarMultiUso() {
        return VarMultiUso;
    }

    public void setVarMultiUso(Usuario varMultiUso) {
        VarMultiUso = varMultiUso;
    }


}
