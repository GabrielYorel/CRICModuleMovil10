package com.deinteti.gb.cricmodulemovil10;

import java.util.Date;

/**
 * Created by desarrollo on 23/03/2018.
 */

public class GralUtils {
    public static String getDateTime(Date date) {
        if (date == null)
            return "";
        else
            return UIParams.DateFormat.format(date);
    }

    public static String getTime(Date date) {
        return UIParams.TimeFormat.format(date);
    }

    public static Date getDateTime(String sDate) {
        try {
            return UIParams.DateFormat.parse(sDate);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Date getDateTimeR(String sDate) {
        try {
            return UIParams.DateFormatR.parse(sDate);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getDateTimeRReverse(Date date) {
        try {
            return UIParams.DateFormatR.format(date);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Date getDateFullTime(String sDate) {
        Date date = null;
        try {
            date = UIParams.DateFullFormat.parse(sDate);
        } catch (Exception ex) {
            //ex.printStackTrace();
            return null;
        }
        return date;
    }

    public static String getDateFullTime(Date date) {
        if (date == null)
            return "";
        else
            try {
                return UIParams.DateFullFormat.format(date);
            } catch (Exception ex) {
                //ex.printStackTrace();
                return null;
            }
    }

    public static String getDateFullTimeView(Date date) {
        if (date == null)
            return "";
        else
            try {
                return UIParams.DateFullFormatView.format(date);
            } catch (Exception ex) {
                //ex.printStackTrace();
                return null;
            }
    }

    public static int getIntFromBoolean(boolean valor) {
        return valor ? 1 : 0;
    }
}
