package com.uisrael.mejia.control;

import com.uisrael.mejia.model.Usuario;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LoginControlImpl implements LoginControl{

    private static String NAMESPACE = "http://soap/";
    private static  final String URL="http://192.168.100.10:8080/barras/UsuarioService?wsdl";

    @Override
    public boolean login(String usuario,String pwd) throws  Exception {
        if (usuario==null){
            return false;
        }
        String METHOD_NAME = "login";
        String SOAP_ACTION = "loginAction";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("usuario", usuario);
        request.addProperty("pwd", pwd);
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.setOutputSoapObject(request);
        try {
            HttpTransportSE transporte = new HttpTransportSE(URL);
            transporte.call(SOAP_ACTION, soapEnvelope);
            SoapObject object = (SoapObject) soapEnvelope.getResponse();
            return object!=null;
        }catch (Exception ex){
            throw new Exception("Usuario o contraseña incorrecta");
        }

    }
}
