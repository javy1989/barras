/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.Usuario;
import servicio.UsuarioFacade;

/**
 *
 * @author USUARIO
 */
@Path("/usuario")
public class UsuarioApi {

    @EJB
    private UsuarioFacade ejbUsuario;

    @GET
    @Asynchronous
    @Path("/login/{usuario}/{pwd}")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@Suspended final AsyncResponse asyncResponse, @PathParam(value = "usuario") String usuario, @PathParam(value = "pwd") String pwd) {
        try {
            if (usuario.isEmpty() || pwd.isEmpty()) {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
                return null;
            }
            Usuario user = ejbUsuario.login(usuario, pwd);
            if (user == null) {
                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build());
                return null;
            }
            GenericEntity<Usuario> respuesta = new GenericEntity<>(user, Usuario.class);
            asyncResponse.resume(Response.ok().entity(respuesta).build());
            return null;
        } catch (Exception ex) {
            asyncResponse.resume(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
            Logger.getLogger(UsuarioApi.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
