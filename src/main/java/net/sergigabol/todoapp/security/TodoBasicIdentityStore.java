/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.todoapp.security;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import net.sergigabol.todoapp.user.User;
import net.sergigabol.todoapp.user.persistence.UserDao;

/**
 *
 * @author gabalca
 */
@ApplicationScoped
public class TodoBasicIdentityStore implements IdentityStore{

    private static final Logger LOG = Logger.getLogger(TodoBasicIdentityStore.class.getName());

    @Inject
    private UserDao userDao;

    /**
     * Valida les credencials.
     * La implementació concreta que es posi de Credential farà que apliqui 
     * aquest Identity store o un altre 
     * @param credential
     * @return 
     */
    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        LOG.log(Level.INFO, "Validating user {0}", credential.getCaller());
        User us = userDao.getUserByUsername(credential.getCaller());
        
        if(us!=null){
            if(us.getPassword().equals(credential.getPasswordAsString())){
                System.out.println("User validated!");
                
                Set<String> groups=new HashSet<>();
                groups.add("User");
                if(us.getAdmin()!=null && us.getAdmin()){
                    groups.add("Admin");
                }
                return new CredentialValidationResult(credential.getCaller(), groups);
            }else{
                return CredentialValidationResult.INVALID_RESULT;
            }
        }else{
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }
    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return IdentityStore.super.getCallerGroups(validationResult); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int priority() {
        return IdentityStore.super.priority(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return IdentityStore.super.validationTypes(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
