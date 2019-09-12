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
import javax.ejb.Local;
import javax.ejb.Stateless;
import net.sergigabol.todoapp.task.Tasca;

/**
 *
 * Implementació de la interfície local, que alhora és REST TaskService amb un Stateless Session Bean
 * 
 * @author gabalca
 */
@Stateless
public class TaskServiceEjb implements TaskService{

    private static final Logger LOG = Logger.getLogger(TaskServiceEjb.class.getName());
    
    @Override
    public Tasca getTasca(long tascaid, long userid) {
        Tasca t = new Tasca();
        t.setTascaid(tascaid);;
        t.setAcabada(Boolean.FALSE);
        t.setUserid(userid);
        t.setDescripcio("Una tasca qualsevol");
        return t;
    }

    @Override
    public List<Tasca> getTasca(long userid) {
        List<Tasca> result = new ArrayList<>();
        for(int i=0; i<10; i++){
            Tasca t = new Tasca();
            t.setTascaid((long)i);;
            t.setAcabada(Boolean.FALSE);
            t.setUserid(userid);
            t.setDescripcio("Una tasca qualsevol "+i);
            result.add(t);
        }
        
        return result;
    }

    @Override
    public void novaTasca(Tasca t) {
        LOG.log(Level.INFO, "Should save a Tasca {0}", t);
    }

    @Override
    public void eliminaTasca(long tascaid) {
        LOG.log(Level.INFO, "Should delete a Tasca {0}", tascaid);
    }
    
    
    
}
