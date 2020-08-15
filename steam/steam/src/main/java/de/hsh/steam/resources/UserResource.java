/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsh.steam.resources;

import de.hsh.steam.application.Rating;
import de.hsh.steam.application.Series;
import de.hsh.steam.application.User;
import de.hsh.steam.services.Userservice;
import de.hsh.steam.services.SeriesService;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/user")
public class UserResource {
    SeriesService s = new SeriesService();
    
    Userservice u = new Userservice();
    
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<User> getAllUsers(){
        return u.getAllUsers();
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User addUser(User user){
        return u.addUser(user);
    }
    
    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUser(@PathParam("userId") long userId){
        return u.getUser(userId);
    }
    
    @DELETE
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void deleteUser(@PathParam("userId") long userId){
            u.deleteUser(userId);
    }
    @GET
    @Path("/{userId}/series")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Series> getUserSeries(@PathParam("userId") long userId){
        return u.getUserSeries(userId);
    }
    
    @POST
    @Path("/{userId}/series")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Series> addUserSeries(@PathParam("userId") long userId, Series series){
        return u.addUserSeries(userId, series);
    }
    
    @GET
    @Path("/{userId}/series/{seriesId}/rating")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Rating getRating(@PathParam("userId") long userId, @PathParam("seriesId") long seriesId){
        return u.getRating(userId, seriesId);
    }
    
    
    @GET
    @Path ("/{userId}/series/{seriesId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Series getSeries(@PathParam("userId") long userId, @PathParam("seriesId") long seriesId){
            return s.getSeries(seriesId);
    }

}
        


