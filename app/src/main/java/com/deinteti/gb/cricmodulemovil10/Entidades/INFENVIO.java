package com.deinteti.gb.cricmodulemovil10.Entidades;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.deinteti.gb.cricmodulemovil10.AccesoDatos.ContratoTareas;

import java.io.Serializable;

/**
 * Created by desarrollo on 12/03/2018.
 */

public class INFENVIO implements ContratoTareas.ColumnasInfEnvio, Serializable {
    private int IdSucursal;
    private int CVE_INFO;
    private String CVE_CONS;
    private String NOMBRE;
    private String CALLE;
    private String NUMINT;
    private String NUMEXT;
    private String CRUZAMIENTOS;
    private String CRUZAMIENTOS2;
    private String POB;
    private String CURP;
    private String REFERDIR;
    private String CVE_ZONA;
    private @Nullable
    int CVE_OBS;
    private String STRNOGUIA;
    private String STRMODOENV;
    private String NOMBRE_RECEP;
    private String NO_RECEP;
    private String COLONIA;
    private String CODIGO;
    private String ESTADO;
    private String PAIS;
    private String MUNICIPIO;

    public int getCVE_INFO() {
        return CVE_INFO;
    }

    public void setCVE_INFO(int CVE_INFO) {
        this.CVE_INFO = CVE_INFO;
    }

    public String getCVE_CONS() {
        return CVE_CONS;
    }

    public void setCVE_CONS(String CVE_CONS) {
        this.CVE_CONS = CVE_CONS;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getCALLE() {
        return CALLE;
    }

    public void setCALLE(String CALLE) {
        this.CALLE = CALLE;
    }

    public String getNUMINT() {
        return NUMINT;
    }

    public void setNUMINT(String NUMINT) {
        this.NUMINT = NUMINT;
    }

    public String getNUMEXT() {
        return NUMEXT;
    }

    public void setNUMEXT(String NUMEXT) {
        this.NUMEXT = NUMEXT;
    }

    public String getCRUZAMIENTOS() {
        return CRUZAMIENTOS;
    }

    public void setCRUZAMIENTOS(String CRUZAMIENTOS) {
        this.CRUZAMIENTOS = CRUZAMIENTOS;
    }

    public String getCRUZAMIENTOS2() {
        return CRUZAMIENTOS2;
    }

    public void setCRUZAMIENTOS2(String CRUZAMIENTOS2) {
        this.CRUZAMIENTOS2 = CRUZAMIENTOS2;
    }

    public String getPOB() {
        return POB;
    }

    public void setPOB(String POB) {
        this.POB = POB;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getREFERDIR() {
        return REFERDIR;
    }

    public void setREFERDIR(String REFERDIR) {
        this.REFERDIR = REFERDIR;
    }

    public String getCVE_ZONA() {
        return CVE_ZONA;
    }

    public void setCVE_ZONA(String CVE_ZONA) {
        this.CVE_ZONA = CVE_ZONA;
    }


    public String getSTRNOGUIA() {
        return STRNOGUIA;
    }

    public void setSTRNOGUIA(String STRNOGUIA) {
        this.STRNOGUIA = STRNOGUIA;
    }

    public String getSTRMODOENV() {
        return STRMODOENV;
    }

    public void setSTRMODOENV(String STRMODOENV) {
        this.STRMODOENV = STRMODOENV;
    }


    public String getNOMBRE_RECEP() {
        return NOMBRE_RECEP;
    }

    public void setNOMBRE_RECEP(String NOMBRE_RECEP) {
        this.NOMBRE_RECEP = NOMBRE_RECEP;
    }

    public String getNO_RECEP() {
        return NO_RECEP;
    }

    public void setNO_RECEP(String NO_RECEP) {
        this.NO_RECEP = NO_RECEP;
    }

    public String getCOLONIA() {
        return COLONIA;
    }

    public void setCOLONIA(String COLONIA) {
        this.COLONIA = COLONIA;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
        this.ESTADO = ESTADO;
    }

    public String getPAIS() {
        return PAIS;
    }

    public void setPAIS(String PAIS) {
        this.PAIS = PAIS;
    }

    public String getMUNICIPIO() {
        return MUNICIPIO;
    }

    public void setMUNICIPIO(String MUNICIPIO) {
        this.MUNICIPIO = MUNICIPIO;
    }

    @Nullable
    public int getCVE_OBS() {
        return CVE_OBS;
    }

    public void setCVE_OBS(@Nullable int CVE_OBS) {
        this.CVE_OBS = CVE_OBS;
    }

    public int getIdSucursal() {
        return IdSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        IdSucursal = idSucursal;
    }

    public String getDirBsqGoogle() {
        String InfEnvio = "";
        InfEnvio = getCALLE() + " " + getNUMEXT() + ", " + getCOLONIA() + ", " + getMUNICIPIO() + ", " + getESTADO() + ", " + getPAIS();
        return InfEnvio;
    }

    public INFENVIO(Cursor cursor) {
        CVE_INFO = cursor.getInt(cursor.getColumnIndex(INFENVIO.CVE_INFOE));
        CALLE = cursor.getString(cursor.getColumnIndex(INFENVIO.CALLEE));
        NUMEXT = cursor.getString(cursor.getColumnIndex(INFENVIO.NUMEXTE));
        COLONIA = cursor.getString(cursor.getColumnIndex(INFENVIO.COLONIAE));
        MUNICIPIO = cursor.getString(cursor.getColumnIndex(INFENVIO.MUNICIPIOE));
        ESTADO = cursor.getString(cursor.getColumnIndex(INFENVIO.ESTADOE));
        PAIS = cursor.getString(cursor.getColumnIndex(INFENVIO.PAISE));
    }

}
