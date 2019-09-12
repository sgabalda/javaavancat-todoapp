/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.todoapp.user.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.sergigabol.todoapp.task.Tasca;
import net.sergigabol.todoapp.task.persistence.TascaDao;
import net.sergigabol.todoapp.user.User;
import net.sergigabol.todoapp.user.persistence.UserDao;

/**
 *  EJB que ja defineix directament la propia interfíce, REST en aquest cas.
 * @author gabalca
 */
@Stateless
@Path("/usuaris")
public class UserServiceEjb {

    private static final Logger LOG = Logger.getLogger(UserServiceEjb.class.getName());
    
    /**
     * Dao to persist and retrieve Users
     */
    @Inject
    private UserDao userDao;
    @Inject
    private TascaDao tascaDao;

    /**
     * Retorna un usuari. No estava a l'enunciat, però l'he afegit de bonus
     * Per al segon exercici, canvio el tipus de retorn. No és obligatori, però
     * per a ser més estrictes amb REST, podem tornar codis i així ho heu vist 
     * per quan fem JAX-RS
     * @param userid
     * @return 
     */
    @GET
    @Path("{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userid") long userid){
        LOG.log(Level.INFO, "Getting user {0}", userid);
        User result = userDao.getUserById(userid);
        if(result!=null){
            LOG.log(Level.INFO, "The user found is {0}", result);
            return Response.ok(result).build();
        }else{
            LOG.log(Level.INFO, "No user found with id {0}", userid);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    /**
     * Retorna tots els usuaris
     * @return 
     */
    @GET
    public List<User> getAllUsers(){
        List<User> result = userDao.getAllUsers();
        return result;
    }
    
    /**
     * Afegeix un nou usuari
     * @param us 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //encara no hem vist a fons JAX-RS, però podem posar com a paràmetre 
            //d'entrada un POJO, i si s'envia un JSON amb camps correctes, 
            //ens ho converteix automàticament.
    public void createUser(User us){
        LOG.log(Level.INFO, "Going to save user {0}", us);
        userDao.createUser(us);
    }
    
    @DELETE
    @Path("{userid}")
    public void removeUser(@PathParam("userid") long userid){
        LOG.log(Level.INFO, "Going to delete user {0}", userid);
        //delete all tasks for this user
        
        //si comenteu això, veureu que si l'usuari té una tasca, no l'elimina 
        //ja que anula tota la transacció
        List<Tasca> tasques= tascaDao.getTasquesUsuari(userid);
        if(tasques!=null){
            tasques.forEach(task -> tascaDao.eliminaTasca(task.getTascaid()));
        }
        userDao.deleteUser(userid);
    }
}
