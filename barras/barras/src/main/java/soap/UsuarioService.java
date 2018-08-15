/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import modelo.Usuario;
import servicio.UsuarioFacade;

/**
 *
 * @author USUARIO
 */
@WebService(serviceName = "UsuarioService")
public class UsuarioService {

    @EJB
    private UsuarioFacade ejbRef;

    @WebMethod(operationName = "login",action = "loginAction")
    public Usuario login(@WebParam(name = "usuario") String usuario, @WebParam(name = "pwd") String pwd) throws Exception {
        return ejbRef.login(usuario, pwd);
    }
    
}
