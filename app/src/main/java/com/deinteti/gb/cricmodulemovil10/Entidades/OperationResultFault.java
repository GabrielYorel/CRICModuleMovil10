package com.deinteti.gb.cricmodulemovil10.Entidades;

import java.io.Serializable;

/**
 * Created by Yorel on 27/04/2018.
 */

public class OperationResultFault implements Serializable{

    public int Result;
    private String ErrorMessage;

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        this.Result = result;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.ErrorMessage = errorMessage;
    }
}
