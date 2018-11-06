package com.deinteti.gb.cricmodulemovil10.Datos;

import android.content.Context;

import com.deinteti.gb.cricmodulemovil10.ActivityUtils;

/**
 * Created by desarrollo on 08/03/2018.
 */

public class CRICModuleParams {
    private String server_ip;
    private String server_puerto;
    public final String ServiceName = "/WSCRICModule/CRICModuleMbl.svc/RESTService/";

    public CRICModuleParams(Context Context) {
        server_ip = ActivityUtils.getPreferenceById("server_ip", Context);
        server_puerto = ActivityUtils.getPreferenceById("server_puerto", Context);
        if (server_ip.equals("") || server_puerto.equals(""))
            throw new RuntimeException("Por favor configure los datos del servidor");
    }

    public String getBaseURI(boolean use_https) {
        String baseUri = (use_https ? "https://" : "http://") + getServer_ip() + ":" + getServer_puerto() + ServiceName;
        return baseUri;
    }

    public String getServer_ip() {
        return server_ip.equals(null) ? "192.168.0.6" : server_ip;
    }

    public void setServer_ip(String server_ip) {
        this.server_ip = server_ip;
    }

    public String getServer_puerto() {
        return server_puerto.equals(null) ? "80" : server_puerto;
    }

    public void setServer_puerto(String server_puerto) {
        this.server_puerto = server_puerto;
    }
}
