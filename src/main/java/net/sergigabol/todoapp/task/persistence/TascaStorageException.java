/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.todoapp.task.persistence;

import net.sergigabol.todoapp.task.Tasca;

/**
 *
 * @author gabalca
 */
public class TascaStorageException extends RuntimeException {
    private Tasca t;
    private Long tascaId;
    private String operation;

    public TascaStorageException(Tasca t, Long tascaId, String operation, String message) {
        super(message);
        this.t = t;
        this.tascaId = tascaId;
        this.operation = operation;
    }

    public TascaStorageException(Tasca t, Long tascaId, String operation, String message, Throwable cause) {
        super(message, cause);
        this.t = t;
        this.tascaId = tascaId;
        this.operation = operation;
    }

    public Tasca getTasca() {
        return t;
    }

    public Long getTascaId() {
        return tascaId;
    }

    public String getOperation() {
        return operation;
    }
    
    
}
