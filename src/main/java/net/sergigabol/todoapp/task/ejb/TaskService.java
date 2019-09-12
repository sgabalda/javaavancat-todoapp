/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.todoapp.task.ejb;

import java.util.List;
import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import net.sergigabol.todoapp.task.Tasca;

/**
 *  REST Interface d'un EJB, en aquest cas definida a part del Bean. 
 * Més mantenible i separades les responsabilitats que en l'exemple de l'UserService
 * @author gabalca
 */
@Local
@Path("/tasques/{userid}")
public interface TaskService {
    
    @GET
    public List<Tasca> getTasques(@PathParam("userid") long userid);
    
    @GET
    @Path("/{tascaid}")
    public Tasca getTasca(@PathParam("tascaid") long tascaid,
            @PathParam("userid") long userid);
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void novaTasca(Tasca t);
    
    @DELETE
    @Path("/{tascaid}")
    public void eliminaTasca(@PathParam("tascaid") long tascaid);
    
}
