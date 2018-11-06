package com.deinteti.gb.cricmodulemovil10.Entidades;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.deinteti.gb.cricmodulemovil10.AccesoDatos.ContratoTareas;
import com.deinteti.gb.cricmodulemovil10.GralUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by desarrollo on 08/03/2018.
 */

public class DoctosRutasTareas implements ContratoTareas.ColumnasDetalleTareas, Serializable {
    private int IdSucursal;
    public String Folio;
    public String IdDoctoTarea;
    @Nullable
    public int Orden;
    //@Nullable public int NoPartidas;
    @Nullable
    public Date DFechaEntrega;
    @Nullable
    public String FechaEntrega;
    @Nullable
    public int Estatus;
    @Nullable
    public Date DFechaCancelacion;
    @Nullable
    public String FechaCancelacion;
    public String MotivoCancelacion;
    @Nullable
    public double NoPzasEntregar;
    @Nullable
    public double LatitudInicial;
    @Nullable
    public double LongitudInicial;
    @Nullable
    public double LatitudFinal;
    @Nullable
    public double LongitudFinal;
    @Nullable
    public Date DHoraInicialEntrega;
    @Nullable
    public Date DHoraFinalEntrega;
    @Nullable
    public String HoraInicialEntrega;
    @Nullable
    public String HoraFinalEntrega;
    public String Observaciones;
    @Nullable
    public double SubTotal;
    @Nullable
    public double Iva;
    @Nullable
    public double Importe;
    @Nullable
    public String CveCliente;
    public String NombreCliente;
    public String Rfc;
    @Nullable
    public int CveDatosEnvio;
    @Nullable
    public Date DFechaDocumento;
    @Nullable
    public String FechaDocumento;
    public RutaTarea RutaTarea;
    @Nullable
    public ArrayList<DetalleDoctoRutaTarea> DetalleDoctoRutaTarea;
    @Nullable
    public INFENVIO INFENVIO;
    public String DireccionEntrega;
    public String TipoDocumento;
    public double CantPzas;
    public String Estatus_s;
    public String ObsEntrega;
    
    public boolean RequiereDetalle;
    public int TipoTarea;
    public boolean ReqEvidenciaFoto;
    public byte[] EvidenciaFoto;
    public boolean ReqEvidenciaArch;
    public boolean ReqCapturaInfo;
    public String CapturaInfo;
    public String EvidenciaFoto64;
    private String EvidenciaFotoRuta;

    public DoctosRutasTareas(Cursor cursor) {
        Folio = cursor.getString(cursor.getColumnIndex(DoctosRutasTareas.FOLIO));
        IdDoctoTarea = cursor.getString(cursor.getColumnIndex(DoctosRutasTareas.IDDOCTOTAREA));
        FechaDocumento = cursor.getString(cursor.getColumnIndex(DoctosRutasTareas.FECHA_DOCUMENTO));
        Orden = cursor.getInt(cursor.getColumnIndex(DoctosRutasTareas.ORDEN));
        //NoPartidas = cursor.getInt(cursor.getColumnIndex(DoctosRutasTareas.NP));
        FechaEntrega = cursor.getString(cursor.getColumnIndex(DoctosRutasTareas.FECHA_ENTREGA));
        Estatus = cursor.getInt((cursor.getColumnIndex(DoctosRutasTareas.ESTATUS)));
        FechaCancelacion = cursor.getString(cursor.getColumnIndex(DoctosRutasTareas.FECHA_CANCELACION));
        MotivoCancelacion = cursor.getString(cursor.getColumnIndex(DoctosRutasTareas.MOTIVOCANCELACION));
        HoraInicialEntrega = cursor.getString(cursor.getColumnIndex(DoctosRutasTareas.HORA_INICIAL_ENTREGA));
        HoraFinalEntrega = cursor.getString(cursor.getColumnIndex(DoctosRutasTareas.HORA_FINAL_ENTREGA));

        CantPzas = cursor.getDouble(cursor.getColumnIndex(DoctosRutasTareas.CANT_PZAS));
        NoPzasEntregar = cursor.getDouble(cursor.getColumnIndex(DoctosRutasTareas.NO_PZAS_ENTREGAR));
        LatitudInicial = cursor.getDouble(cursor.getColumnIndex(DoctosRutasTareas.LATITUD_INICIAL));
        LongitudInicial = cursor.getDouble(cursor.getColumnIndex(DoctosRutasTareas.LONGITUD_INICIAL));
        LatitudFinal = cursor.getDouble(cursor.getColumnIndex(DoctosRutasTareas.LATITUD_FINAL));
        LongitudFinal = cursor.getDouble(cursor.getColumnIndex(DoctosRutasTareas.LONGITUD_FINAL));
        CveCliente = cursor.getString(cursor.getColumnIndex(DoctosRutasTareas.CVE_CLIENTE));
        CveDatosEnvio = cursor.getInt(cursor.getColumnIndex(DoctosRutasTareas.CVE_DATOS_ENVIO));
        Observaciones = cursor.getString(cursor.getColumnIndex(DoctosRutasTareas.OBSERVACIONES));
        IdSucursal = cursor.getInt(cursor.getColumnIndex(DoctosRutasTareas.ID_SUCURSAL));
        ObsEntrega = cursor.getString(cursor.getColumnIndex(DoctosRutasTareas.OBSENTREGA));

        RequiereDetalle = cursor.getInt(cursor.getColumnIndex(DoctosRutasTareas.REQUIEREDETALLE)) > 0;
        TipoTarea = cursor.getInt(cursor.getColumnIndex(DoctosRutasTareas.TIPOTAREA));
        ReqEvidenciaFoto = cursor.getInt(cursor.getColumnIndex(DoctosRutasTareas.REQEVIDENCIAFOTO)) > 0;
        EvidenciaFotoRuta = cursor.getString(cursor.getColumnIndex(DoctosRutasTareas.EVIDENCIAFOTORUTA));
        //EvidenciaFoto = cursor.getBlob(cursor.getColumnIndex(DoctosRutasTareas.EVIDENCIAFOTO));
        ReqEvidenciaArch = cursor.getInt(cursor.getColumnIndex(DoctosRutasTareas.REQEVIDENCIAARCH)) > 0;
        ReqCapturaInfo = cursor.getInt(cursor.getColumnIndex(DoctosRutasTareas.REQCAPTURAINFO)) > 0;
        CapturaInfo = cursor.getString(cursor.getColumnIndex(DoctosRutasTareas.CAPTURAINFO));

    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String folio) {
        Folio = folio;
    }

    public String getIdDoctoTarea() {
        return IdDoctoTarea;
    }

    public void setIdDoctoTarea(String IdDoctoTarea) {
        IdDoctoTarea = IdDoctoTarea;
    }

    @Nullable
    public int getOrden() {
        return Orden;
    }

    public void setOrden(@Nullable int orden) {
        Orden = orden;
    }

   /* @Nullable
    public int getNoPartidas() {
        return NoPartidas;
    }

    public void setNoPartidas(@Nullable int noPartidas) {
        NoPartidas = noPartidas;
    }*/

    @Nullable
    public String getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(@Nullable String fechaEntrega) {
        FechaEntrega = fechaEntrega;
    }

    @Nullable
    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(@Nullable int estatus) {
        Estatus = estatus;
    }

    @Nullable
    public String getFechaCancelacion() {
        return FechaCancelacion;
    }

    public void setFechaCancelacion(@Nullable String fechaCancelacion) {
        FechaCancelacion = fechaCancelacion;
    }

    public String getMotivoCancelacion() {
        return MotivoCancelacion == null ? "" : MotivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        MotivoCancelacion = motivoCancelacion;
    }

    @Nullable
    public double getNoPzasEntregar() {
        return NoPzasEntregar;
    }

    public void setNoPzasEntregar(@Nullable double noPzasEntregar) {
        NoPzasEntregar = noPzasEntregar;
    }

    @Nullable
    public double getLatitudInicial() {
        return LatitudInicial;
    }

    public void setLatitudInicial(@Nullable double latitudInicial) {
        LatitudInicial = latitudInicial;
    }

    @Nullable
    public double getLongitudInicial() {
        return LongitudInicial;
    }

    public void setLongitudInicial(@Nullable double longitudInicial) {
        LongitudInicial = longitudInicial;
    }

    @Nullable
    public double getLatitudFinal() {
        return LatitudFinal;
    }

    public void setLatitudFinal(@Nullable double latitudFinal) {
        LatitudFinal = latitudFinal;
    }

    @Nullable
    public double getLongitudFinal() {
        return LongitudFinal;
    }

    public void setLongitudFinal(@Nullable double longitudFinal) {
        LongitudFinal = longitudFinal;
    }

    @Nullable
    public String getHoraInicialEntrega() {
        return HoraInicialEntrega;
    }

    public void setHoraInicialEntrega(@Nullable String horaInicialEntrega) {
        HoraInicialEntrega = horaInicialEntrega;
    }

    @Nullable
    public String getHoraFinalEntrega() {
        return HoraFinalEntrega;
    }

    public void setHoraFinalEntrega(@Nullable String horaFinalEntrega) {
        HoraFinalEntrega = horaFinalEntrega;
    }

    public String getObservaciones() {
        return Observaciones == null ? "" : Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    @Nullable
    public double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(@Nullable double subTotal) {
        SubTotal = subTotal;
    }

    @Nullable
    public double getIva() {
        return Iva;
    }

    public void setIva(@Nullable double iva) {
        Iva = iva;
    }

    @Nullable
    public double getImporte() {
        return Importe;
    }

    public void setImporte(@Nullable double importe) {
        Importe = importe;
    }

    @Nullable
    public String getCveCliente() {
        return CveCliente;
    }

    public void setCveCliente(@Nullable String cveCliente) {
        CveCliente = cveCliente;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        NombreCliente = nombreCliente;
    }

    public String getRfc() {
        return Rfc;
    }

    public void setRfc(String rfc) {
        Rfc = rfc;
    }

    @Nullable
    public int getCveDatosEnvio() {
        return CveDatosEnvio;
    }

    public void setCveDatosEnvio(@Nullable int cveDatosEnvio) {
        CveDatosEnvio = cveDatosEnvio;
    }

    @Nullable
    public String getFechaDocumento() {
        return FechaDocumento;
    }

    public void setFechaDocumento(@Nullable String fechaDocumento) {
        FechaDocumento = fechaDocumento;
    }

    public com.deinteti.gb.cricmodulemovil10.Entidades.RutaTarea getRutaTarea() {
        return RutaTarea;
    }

    public void setRutaTarea(com.deinteti.gb.cricmodulemovil10.Entidades.RutaTarea rutaTarea) {
        RutaTarea = rutaTarea;
    }

    public ArrayList<DetalleDoctoRutaTarea> getDetalleDoctoRutaTarea() {
        return DetalleDoctoRutaTarea;
    }

    public void setDetalleDoctoRutaTarea(ArrayList<DetalleDoctoRutaTarea> detalleDoctoRutaTarea) {
        DetalleDoctoRutaTarea = detalleDoctoRutaTarea;
    }

    public com.deinteti.gb.cricmodulemovil10.Entidades.INFENVIO getINFENVIO() {
        return INFENVIO;
    }

    public void setINFENVIO(com.deinteti.gb.cricmodulemovil10.Entidades.INFENVIO INFENVIO) {
        this.INFENVIO = INFENVIO;
    }

    public String getDireccionEntrega() {
        return DireccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        DireccionEntrega = direccionEntrega;
    }

    public String getTipoDocumento() {
        return TipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        TipoDocumento = tipoDocumento;
    }

    public double getCantPzas() {
        return CantPzas;
    }

    public void setCantPzas(double cantPzas) {
        CantPzas = cantPzas;
    }

    public String getEstatus_s() {
        return Estatus_s;
    }

    public void setEstatus_s(String estatus_s) {
        Estatus_s = estatus_s;
    }

    @Nullable
    public Date getDFechaEntrega() {
        return GralUtils.getDateFullTime(getFechaEntrega());
    }

    public void setDFechaEntrega(@Nullable Date DFechaEntrega) {
        this.DFechaEntrega = DFechaEntrega;
    }

    @Nullable
    public Date getDFechaCancelacion() {
        return DFechaCancelacion;
    }

    public void setDFechaCancelacion(@Nullable Date DFechaCancelacion) {
        this.DFechaCancelacion = DFechaCancelacion;
    }

    @Nullable
    public Date getDHoraInicialEntrega() {
        return GralUtils.getDateFullTime(getHoraInicialEntrega());
    }

    public void setDHoraInicialEntrega(@Nullable Date DHoraInicialEntrega) {
        this.DHoraInicialEntrega = DHoraInicialEntrega;
    }

    @Nullable
    public Date getDHoraFinalEntrega() {
        return GralUtils.getDateFullTime(getHoraFinalEntrega());
    }

    public void setDHoraFinalEntrega(@Nullable Date DHoraFinalEntrega) {
        this.DHoraFinalEntrega = DHoraFinalEntrega;
    }

    @Nullable
    public Date getDFechaDocumento() {
        return GralUtils.getDateTimeR(getFechaDocumento());
    }

    public void setDFechaDocumento(@Nullable Date DFechaDocumento) {
        this.DFechaDocumento = DFechaDocumento;
    }

    public int getIdSucursal() {
        return IdSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        IdSucursal = idSucursal;
    }

    public String getObsEntrega() {
        return ObsEntrega == null ? "" : ObsEntrega;
    }

    public void setObsEntrega(String obsEntrega) {
        ObsEntrega = obsEntrega;
    }

    public boolean isRequiereDetalle() {
        return RequiereDetalle;
    }

    public void setRequiereDetalle(boolean requiereDetalle) {
        RequiereDetalle = requiereDetalle;
    }

    public int getTipoTarea() {
        return TipoTarea;
    }

    public void setTipoTarea(int tipoTarea) {
        TipoTarea = tipoTarea;
    }

    public boolean isReqEvidenciaFoto() {
        return ReqEvidenciaFoto;
    }

    public void setReqEvidenciaFoto(boolean reqEvidenciaFoto) {
        ReqEvidenciaFoto = reqEvidenciaFoto;
    }

    public byte[] getEvidenciaFoto() {
        return EvidenciaFoto;
    }

    public void setEvidenciaFoto(byte[] evidenciaFoto) {
        EvidenciaFoto = evidenciaFoto;
    }

    public boolean isReqEvidenciaArch() {
        return ReqEvidenciaArch;
    }

    public void setReqEvidenciaArch(boolean reqEvidenciaArch) {
        ReqEvidenciaArch = reqEvidenciaArch;
    }

    public boolean isReqCapturaInfo() {
        return ReqCapturaInfo;
    }

    public void setReqCapturaInfo(boolean reqCapturaInfo) {
        ReqCapturaInfo = reqCapturaInfo;
    }

    public String getCapturaInfo() {
        return CapturaInfo;
    }

    public void setCapturaInfo(String capturaInfo) {
        CapturaInfo = capturaInfo;
    }

    public String getEvidenciaFoto64() {
        return EvidenciaFoto64;
    }

    public void setEvidenciaFoto64(String evidenciaFoto64) {
        EvidenciaFoto64 = evidenciaFoto64;
    }

    public String getEvidenciaFotoRuta() {
        return EvidenciaFotoRuta;
    }

    public void setEvidenciaFotoRuta(String evidenciaFotoRuta) {
        EvidenciaFotoRuta = evidenciaFotoRuta;
    }
}
