/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.todoapp;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * Config class to annotate that all REST services fall under the path /rest
 * @author gabalca
 */
@ApplicationPath("/rest")
@ApplicationScoped
public class ApplicationRestConfig extends Application{

    
}
