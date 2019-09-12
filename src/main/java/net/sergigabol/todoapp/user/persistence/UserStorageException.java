/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.todoapp.user.persistence;

import net.sergigabol.todoapp.user.User;

/**
 *
 * @author gabalca
 */
public class UserStorageException extends RuntimeException{
    private final Long userid;
    private final User user;
    private final String action;

    public UserStorageException(User user, String action, String message, Throwable cause) {
        super(message, cause);
        this.action = action;
        this.user = user;
        if(user!=null){
            this.userid = user.getUserid();
        }else{
            this.userid=null;
        }
    }
    

    public UserStorageException(Long userid, String action, String message, Throwable cause) {
        super(message, cause);
        this.userid = userid;
        this.action = action;
        this.user = null;
    }

    
    
    public Long getUserid() {
        return userid;
    }

    public String getAction() {
        return action;
    }
    
    
}
