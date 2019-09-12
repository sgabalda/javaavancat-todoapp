/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.todoapp.user.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import net.sergigabol.todoapp.user.User;

/**
 *  EJB que ja defineix directament la propia interfíce, REST en aquest cas.
 * @author gabalca
 */
@Stateless
@Path("/usuaris")
public class UserServiceEjb {

    private static final Logger LOG = Logger.getLogger(UserServiceEjb.class.getName());

    /**
     * Retorna un usuari. No estava a l'enunciat, però l'he afegit
     * @param userid
     * @return 
     */
    @GET
    @Path("{userid}")
    public User getUser(@PathParam("userid") long userid){
        LOG.log(Level.INFO, "Getting user {0}", userid);
        User result = new User();
        result.setAdmin(Boolean.TRUE);
        result.setUserid(userid);
        result.setUsername("Default");
        result.setPassword("defpass");
        return result;
    }
    
    /**
     * Retorna tots els usuaris
     * @return 
     */
    @GET
    public List<User> getAllUsers(){
        List<User> result = new ArrayList<>();
        for(int i=0; i<10; i++){
            result.add(this.getUser(i+1));
        }
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
        LOG.log(Level.INFO, "Should save user {0}", us);
    }
    
    @DELETE
    @Path("{userid}")
    public void removeUser(@PathParam("userid") long userid){
        LOG.log(Level.INFO, "Should delete user {0}", userid);
    }
}
