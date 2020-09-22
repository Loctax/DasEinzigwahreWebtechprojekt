/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsh.steam.resources;

import de.hsh.steam.application.Steamservices;
import de.hsh.steam.application.User;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author JanBär
 */
@Path("register")
@RequestScoped
public class RegisterResource {

    @Context
    private UriInfo context;
    
    Steamservices steamservices = Steamservices.singleton();

    /**
     * Creates a new instance of RegisterResource
     */
    public RegisterResource() {
    }

    /**
     * Methode um einen neuen User anzulegen
     * @param u : User im JSON-Format
     * @return true, wenn registrieren erfolgreich war
     * @return false, wenn registrieren nicht erfolgreich war
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean registerUser(User u) {
        return steamservices.newUser(u.getUsername(), u.getPassword());
    }
}
