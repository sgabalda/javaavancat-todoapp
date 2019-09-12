/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.todoapp.task.persistence;

import java.util.List;
import net.sergigabol.todoapp.task.Tasca;

/**
 *
 * @author gabalca
 */
public interface TascaDao {
    public void creaTasca(Tasca t);
    public void eliminaTasca(long id);
    public List<Tasca> getTasquesUsuari(long userid);
    public Tasca getTasca(long id);
}
