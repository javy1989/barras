/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Usuario;

/**
 *
 * @author Ricardo.Bravo
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "barras-PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public Usuario login(String usuario, String pwd) throws Exception {
        try {
            Query q = em.createQuery("select u from Usuario as u where u.usuario=:usuario and u.pwd=:pwd and u.activo=true");
            q.setParameter("usuario", usuario);
            q.setParameter("pwd", pwd);
            return (Usuario) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
