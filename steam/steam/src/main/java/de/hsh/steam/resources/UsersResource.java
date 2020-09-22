/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsh.steam.resources;
import filter.SecurityFilter;
import de.hsh.steam.application.Rating;
import de.hsh.steam.application.Series;
import de.hsh.steam.application.Steamservices;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author JanBär
 */
@Path("users")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {

    @Context
    private UriInfo context;
    
    SecurityFilter securityFilter = SecurityFilter.singleton();
    Steamservices steamservices = Steamservices.singleton();
    /**
     * Creates a new instance of UsersResource
     */
    public UsersResource() {
    }
       
    /**
     * userLogin() uerberprueft ob die per Basic Authentication uebergebenen Daten korrekt sind und gibt dann die Liste der Serien fuer den User aus.
     * @param crc wird automatisch übergeben.
     * @return ArrayList<Series> Liste mit Serien des erfolgreich engeloggten Users
     */
    @GET
    @Path("login")
    public ArrayList<Series> userLogin(@Context ContainerRequestContext crc) {
        String username = securityFilter.getUsernameFromContext(crc);
        return steamservices.getAllSeriesOfUser(username);
    }
    
    /**
     * Prueft ob der username in der URI dem des eingeloggten Users entspricht und returned eine Liste mit dessen Serien
     * @param username wird automatisch übergeben. (muss dem eingeloggten  user entsprechen)
     * @param crc wird automatisch übergeben.
     * @return ArrayList<Series> Liste mit Serien des Users
     */
    @Path("{username}/series")
    @GET
    public ArrayList<Series> getAllSeriesOfUser(@PathParam("username") String username, @Context ContainerRequestContext crc) {
        if (securityFilter.checkCredentials(crc, username)) {
            return steamservices.getAllSeriesOfUser(username);            
        } else {
            return null;
        }
    }
    
    /**
     * Fuegt fuer den eingeloggten User eine neue Serie hinzu
     * @param username wird automatisch übergeben. (muss dem eingeloggten  user entsprechen)
     * @param s : Series in Json-Format
     * @param crc wird automatisch übergeben.
     */
    @Path("{username}/series")
    @POST
    //public boolean addSeries(@PathParam("username") String username, String test, @Context ContainerRequestContext crc) {
    public boolean addSeries(@PathParam("username") String username, Series s, @Context ContainerRequestContext crc) {
//        steamservices.addOrModifySeries(s.getTitle(), s.getNumberOfSeasons(), s.getGenre(), s.getStreamedBy(), username, null, null);        
//        System.out.println("addSeries1");
//        return true;
        if (securityFilter.checkCredentials(crc, username)) {
            Rating rating = steamservices.getRating(s.getTitle(), username);
            if (rating != null) {
                steamservices.addOrModifySeries(s.getTitle(), s.getNumberOfSeasons(), s.getGenre(), s.getStreamedBy(), username, rating.getScore(), rating.getRemark());
                return true;
            } else {
                steamservices.addOrModifySeries(s.getTitle(), s.getNumberOfSeasons(), s.getGenre(), s.getStreamedBy(), username, null, null);
                return true;                
            }
        }
        return false;
    }
    
    /**
     * Erstellt fuer eine bestehende Serie ein Rating fuer den eingeloggten User
     * @param crc wird automatisch übergeben.
     * @param username wird automatisch übergeben. (muss dem eingeloggten  user entsprechen)
     * @param title wird automatisch übergeben. (muss einer existierenden Serie entsprechen)
     * @param r : Rating in Json-Format 
     * @return Rating mit dem User, der das Rating gegeben hat und der Serie
     */
    @Path("{username}/series/{title}")
    @PUT
    public Rating rateSeries(@Context ContainerRequestContext crc, @PathParam("username") String username, @PathParam("title") String title, Rating r) {
        if (securityFilter.checkCredentials(crc, username)) {
            Series s = null;
            ArrayList<Series> seriesList = steamservices.getAllSeries();
            for (Series series : seriesList) {
                if (title.equals(series.getTitle())){
                    s = series;
                }
            }
            if (s != null && r != null) {
                steamservices.addOrModifySeries(s.getTitle(), s.getNumberOfSeasons(), s.getGenre(), s.getStreamedBy(), username, r.getScore(), r.getRemark());
                return steamservices.getRating(s.getTitle(), username);
            }                       
        }
        return null;
    }
    
    @Path("{username}/series/{title}/rating")
    @GET
    public Rating getRating(@Context ContainerRequestContext crc, @PathParam("username") String username, @PathParam("title") String title) {
        if (securityFilter.checkCredentials(crc, username)) {
            return steamservices.getRating(title, username);
        }
        else {
            return null;
        }
    }
}
