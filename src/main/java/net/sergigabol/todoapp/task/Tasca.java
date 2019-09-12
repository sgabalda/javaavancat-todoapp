/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.todoapp.task;

/**
 *
 * @author gabalca
 */
public class Tasca {
    public Long tascaid;
    public Long userid;
    public String descripcio;
    public Boolean acabada;

    public Long getTascaid() {
        return tascaid;
    }

    public void setTascaid(Long tascaid) {
        this.tascaid = tascaid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Boolean getAcabada() {
        return acabada;
    }

    public void setAcabada(Boolean acabada) {
        this.acabada = acabada;
    }

    @Override
    public String toString() {
        return "Tasca{" + "tascaid=" + tascaid + ", userid=" + userid + ", descripcio=" + descripcio + ", acabada=" + acabada + '}';
    }
    
    

}
