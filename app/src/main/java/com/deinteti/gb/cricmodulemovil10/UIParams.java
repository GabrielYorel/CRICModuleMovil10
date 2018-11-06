package com.deinteti.gb.cricmodulemovil10;


import android.graphics.Color;

import com.deinteti.gb.cricmodulemovil10.Enums.ResultOperation;

import java.text.SimpleDateFormat;

/**
 * Created by desarrollo on 11/03/2018.
 */

public class UIParams {
    public  static int ColorMessageNotify= Color.parseColor("#1976d2");
    public  static int ColorMessageError= Color.parseColor("#F44336");
    public  static int ColorMessageWarning= Color.parseColor("#FFA000");
    public  static int ColorMessageDefault= Color.parseColor("#455A64");
    static SimpleDateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy");
    static SimpleDateFormat DateFormatR = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm:ss");
    static  SimpleDateFormat DateFullFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    static  SimpleDateFormat DateFullFormatViewR=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
    static  SimpleDateFormat DateFullFormatView=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");

    public  static int GetColorMessage(ResultOperation resultOperation){
        switch (resultOperation){
            case OK_MESSAGE:
                return ColorMessageNotify;
            case WARNING_MESSAGE:
                return  ColorMessageWarning;
            case ERROR_MESSAGE:
                return ColorMessageError;
            default:
                return ColorMessageDefault;
        }
    }
}
