/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.todoapp.task.ejb;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import net.sergigabol.todoapp.task.Tasca;
import net.sergigabol.todoapp.task.persistence.TascaDao;
import net.sergigabol.todoapp.user.User;
import net.sergigabol.todoapp.user.persistence.UserDao;

/**
 *
 * Implementació de la interfície local, que alhora és REST TaskService amb un Stateless Session Bean
 * 
 * @author gabalca
 */
@DeclareRoles({"User","Admin"})
@RolesAllowed("User")
@Stateless
public class TaskServiceEjb implements TaskService{
    
    @Resource
    private SessionContext sessionCtx;
    
    @Inject
    private TascaDao tascaDao;
    @Inject
    private UserDao userDao;

    private static final Logger LOG = Logger.getLogger(TaskServiceEjb.class.getName());
    
    @Override
    public Tasca getTasca(long tascaid, long userid) {
        Tasca t = tascaDao.getTasca(tascaid);
        //TODO comprovar que es de l'usuari
        return t;
    }

    @Override
    public List<Tasca> getTasques(long userid) {
        List<Tasca> result = tascaDao.getTasquesUsuari(userid);
        return result;
    }

    @Override
    public Tasca novaTasca(Tasca t) {
        LOG.log(Level.INFO, "Going to save a Tasca {0}", t);
        //obtenim el id de l'usuari per forçar a que sigui el de la tasca
        Principal p = sessionCtx.getCallerPrincipal();
        User us = userDao.getUserByUsername(p.getName());
        t.setUserid(us.getUserid());
        
        
        tascaDao.creaTasca(t);
        return t;
    }

    @Override
    public void eliminaTasca(long tascaid) {
        //comprovar que l'usuari és el propietari
        
        Principal p = sessionCtx.getCallerPrincipal();
        Tasca t = tascaDao.getTasca(tascaid);
        String userName = p.getName();
        LOG.log(Level.INFO, "The user {0} is attempting to delete tasca {1}", 
                new Object[]{userName,t});
        User us = userDao.getUserByUsername(userName);
        if(t!=null && us!=null 
                && Objects.equals(t.getUserid(), us.getUserid())){
            LOG.log(Level.INFO, "Going to delete a Tasca {0}", tascaid);
            tascaDao.eliminaTasca(tascaid);
        }else{
            throw new SecurityException(userName+
                    " is not allowed to delete tasca "+tascaid);
        }
        
    }
    
    
    
}
