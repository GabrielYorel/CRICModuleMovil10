package com.deinteti.gb.cricmodulemovil10.AccesoDatos;

/**
 * Created by desarrollo on 22/03/2018.
 */

/**
 * Clase que establece los nombres a usar en la base de datos
 */
public class ContratoTareas {

    public ContratoTareas() {

    }

    public interface ColumnasTareas {
        String FOLIO = "Folio";
        String ID_EMPLEADO = "IdEmpleado";
        String ID_SUCURSAL = "IdSucursal";
        String FECHA_ASIG = "FechaAsig";
        String FECHA_PROMESA = "FechaPromesa";
        String FECHA_INICIO = "FechaInicio";
        String FECHA_TERMINO = "FechaTermino";
        String ESTATUS = "Estatus";
        String FECHA_CANCELACION = "FechaCancelacion";
        String MOTIVO_CANCELACION = "MotivoCancelacion";
        String OBSERVACIONES = "Observaciones";
    }

    public interface ColumnasInfEnvio {

        String CVE_INFOE = "CVE_INFO";
        String CVE_CONSE = "CVE_CONS";
        String NOMBREE = "NOMBRE";
        String CALLEE = "CALLE";
        String NUMINTE = "NUMINT";
        String NUMEXTE = "NUMEXT";
        String CRUZAMIENTOSE = "CRUZAMIENTOS";
        String CRUZAMIENTOS2E = "CRUZAMIENTOS2";
        String POBE = "POB";
        String CURPE = "CURP";
        String REFERDIRE = "REFERDIR";
        String CVE_ZONAE = "CVE_ZONAE";
        String CVE_OBSE = "CVE_OBSE";
        String STRNOGUIAE = "STRNOGUIA";
        String STRMODOENVE = "STRMODOENV";
        String FECHA_ENVE = "FECHA_ENV";
        String NOMBRE_RECEPE = "NOMBRE_RECEP";
        String NO_RECEPE = "NO_RECEP";
        String FECHA_RECEPE = "FECHA_RECEP";
        String COLONIAE = "COLONIA";
        String CODIGOE = "CODIGO";
        String ESTADOE = "ESTADO";
        String PAISE = "PAIS";
        String MUNICIPIOE = "MUNICIPIO";
        String ID_SUCURSAL = "IdSucursal";
    }

    public interface ColumnasDetalleTareas {
        String FOLIO = "Folio";
        String IDDOCTOTAREA = "IdDoctoTarea";
        String ORDEN = "Orden";
        String FECHA_ENTREGA = "FechaEntrega";
        String ESTATUS = "Estatus";
        String FECHA_CANCELACION = "FechaCancelacion";
        String MOTIVOCANCELACION = "MotivoCancelacion";
        String NO_PZAS_ENTREGAR = "NoPzasEntregar";
        String LATITUD_INICIAL = "LatitudInicial";
        String LONGITUD_INICIAL = "LongitudInicial";
        String LATITUD_FINAL = "LatitudFinal";
        String LONGITUD_FINAL = "LongitudFinal";
        String HORA_INICIAL_ENTREGA = "HoraInicialEntrega";
        String HORA_FINAL_ENTREGA = "HoraFinalEntrega";
        String OBSERVACIONES = "Observaciones";
        String SUBTOTAL = "SubTotal";
        String IVA = "Iva";
        String IMPORTE = "Importe";
        String CVE_CLIENTE = "CveCliente";
        String RFC = "Rfc";
        String CVE_DATOS_ENVIO = "CveDatosEnvio";
        String FECHA_DOCUMENTO = "FechaDocumento";
        String TIPO_DOCUMENTO = "TipoDocumento";
        String CANT_PZAS = "CantPzas";
        String ID_SUCURSAL = "IdSucursal";
        String OBSENTREGA = "ObsEntrega";
        String REQUIEREDETALLE = "RequiereDetalle";
        String TIPOTAREA = "TipoTarea";
        String REQEVIDENCIAFOTO = "ReqEvidenciaFoto";
        String EVIDENCIAFOTO = "EvidenciaFoto";
        String REQEVIDENCIAARCH = "ReqEvidenciaArch";
        String REQCAPTURAINFO = "ReqCapturaInfo";
        String CAPTURAINFO = "CapturaInfo";
        String EVIDENCIAFOTORUTA = "CapturaInfoRuta";
    }

    public interface ColumnasDetalleDoctoTarea {
        String FOLIO = "Folio";
        String DOCUMENTO = "Documento";
        String CVE_ART = "CveArt";
        String ESTATUS = "Estatus";
        String DESCRIPCION = "Descripcion";
        String LINEA = "Linea";
        String CANT_SAE = "CantSAE";
        String CANT_ASIGNADA = "CantAsignada";
        String CANT_SALIDA = "CantSalida";
        String CANT_RECIBIDA = "CantRecibida";
        String NUM_PART = "NumPart";
    }

    public interface ColumnasCliente {
        String CVE_CLIENTE = "CveCliente";
        String NOMBRE = "Nombre";

    }

    public interface BitacoraRepartosRpt {
        String IDFOLIO = "IdRow";
        String IDSUCURSAL = "IdSucursal";
        String IDEMPLEADO = "IdEmpleado";
        String FECHA = "Fecha";
    }

    public interface VehiculosUsuario {
        String IDVEHICULO = "IdVehiculo";
        String DESCRIPCION = "Descripcion";
        String PLACAS = "Placas";
        String ESTATUS = "Estatus";
        String TIPOVEHICULO = "TipoVehiculo";
    }
}
