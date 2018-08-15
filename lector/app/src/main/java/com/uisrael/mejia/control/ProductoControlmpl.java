package com.uisrael.mejia.control;

import com.uisrael.mejia.model.Producto;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ProductoControlmpl implements ProductoControl {

    private static String NAMESPACE = "http://soap/";
    private static final String URL = "http://192.168.100.10:8080/barras/ProductoService?wsdl";

    @Override
    public boolean addProducto(Producto producto) {
        String METHOD_NAME = "crearProducto";
        String SOAP_ACTION = "crearProductoAction";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        SoapObject productoSoap = new SoapObject();
        productoSoap.addProperty("id", producto.getId()); //añade datos al servicio web
        productoSoap.addProperty("nombre", producto.getNombre()); //añade datos al servicio web
        productoSoap.addProperty("precio", producto.getPrecio()); //añade datos al servicio web
        productoSoap.addProperty("barras", producto.getBarras()); //añade datos al servicio web
        productoSoap.addProperty("foto", producto.getFoto()); //añade datos al servicio web
        productoSoap.addProperty("cantidad", producto.getCantidad());
        request.addProperty("producto", productoSoap);

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.setOutputSoapObject(request);
        try {
            HttpTransportSE transporte = new HttpTransportSE(URL);
            transporte.call(SOAP_ACTION, soapEnvelope);
            //Log.e("respuesta","data "+soapEnvelope.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Producto> listAll() throws Exception {
        List<Producto> productos;
        String METHOD_NAME = "lista";
        String SOAP_ACTION = "listaAction";
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.dotNet = false;
        soapEnvelope.setOutputSoapObject(request);

        try {
            HttpTransportSE transporte = new HttpTransportSE(URL);
            soapEnvelope.addMapping(NAMESPACE, "Producto", new Producto().getClass());
            transporte.call(SOAP_ACTION, soapEnvelope);
            Vector<SoapObject> listado_soap = null;
            Object obj = soapEnvelope.getResponse();
            listado_soap = (Vector<SoapObject>) soapEnvelope.getResponse();
            int tamaño = listado_soap.size();
            productos = new ArrayList<>();
            for (int i = 0; i < tamaño; i++) {
                SoapObject object = listado_soap.get(i);
                Producto producto = new Producto();
                producto.setId(object.getProperty("id").hashCode());
                producto.setNombre(object.getProperty("nombre").toString());
                producto.setCantidad(Integer.parseInt(object.getProperty("cantidad").toString()));
                producto.setPrecio(new BigDecimal(object.getProperty("precio").toString()));
                if (object.getProperty("barras").toString() != null) {
                    producto.setBarras(object.getProperty("barras").toString());
                }

                if (object.getProperty("foto").toString() != null) {
                    producto.setFoto(object.getProperty("foto").toString());
                }


                productos.add(producto);
            }
            return productos;

        } catch (Exception e) {
            throw new Exception(e);
        }

    }
}
