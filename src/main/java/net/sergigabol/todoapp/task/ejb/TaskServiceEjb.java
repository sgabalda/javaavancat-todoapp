/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.todoapp.task.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import net.sergigabol.todoapp.task.Tasca;
import net.sergigabol.todoapp.task.persistence.TascaDao;

/**
 *
 * Implementació de la interfície local, que alhora és REST TaskService amb un Stateless Session Bean
 * 
 * @author gabalca
 */
@Stateless
public class TaskServiceEjb implements TaskService{
    
    @Inject
    private TascaDao tascaDao;

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
    public void novaTasca(Tasca t) {
        LOG.log(Level.INFO, "Going to save a Tasca {0}", t);
        tascaDao.creaTasca(t);
    }

    @Override
    public void eliminaTasca(long tascaid) {
        LOG.log(Level.INFO, "Going to delete a Tasca {0}", tascaid);
        tascaDao.eliminaTasca(tascaid);
    }
    
    
    
}
