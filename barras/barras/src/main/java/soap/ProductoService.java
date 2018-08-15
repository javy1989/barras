/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap;

import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import modelo.Producto;
import servicio.ProductoFacade;

/**
 *
 * @author USUARIO
 */
@WebService(serviceName = "ProductoService")
public class ProductoService {

    @EJB
    private ProductoFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "productoById", action = "productoByIdAction")
    public Producto getEntityManager(@WebParam(name = "id") Integer id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "lista", action = "listaAction")
    public List<Producto> getLista() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "crearProducto", action = "crearProductoAction")
    public boolean crear(@WebParam(name = "producto") Producto producto) {
        try {
            ejbRef.create(producto);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }
}
