/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsh.steam.resources;

import filter.SecurityFilter;
import de.hsh.steam.application.Genre;
import de.hsh.steam.application.Score;
import de.hsh.steam.application.Series;
import de.hsh.steam.application.Steamservices;
import de.hsh.steam.application.Streamingprovider;
import de.hsh.steam.persistence.SerializedSeriesRepository;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author JanBär
 */
@Path("series")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class SeriesResource {

    Steamservices steamservices = Steamservices.singleton();
    SecurityFilter securityFilter = SecurityFilter.singleton();
    
    @Context
    private UriInfo context;

    public SeriesResource(){
    
    }
    
    /**
     * 
     * @return ArrayList<Series>, eine Liste mit allen Serien in der Datenbank
     */
    @GET
    public ArrayList<Series> getAllSeries() {
        return steamservices.getAllSeries();
    }
    
    /**
     * Suche mit QueryParams, bei jedem leeren Parameter wird jeweils in der gesamten Liste gesucht
     * @param user Alle Serien von <name>
     * @param genre Alle Serien vom Genre <g>. <g> muss vom Enum Genre sein: Thriller, ScienceFiction, Drama, Action, Comedy, Documentary;
     * @param provider Alle Serien vom StreamingProvider <sp>.<sp> muss vom Enum StreamingProvider sein: Netflix, AmazonPrime, Skye;
     * @param score Alle Serien mit Score <sc>, des eingeloggten Users, ohne Login oder mit falschem Login → leere Liste. <sc> muss vom Enum Score sein: bad, mediocre, good, very_good;
     * @param crc wird automatisch übergeben.
     * @return ArrayList<Series> mit Serien auf die alle Suchkriterien zutreffen
     */
    @Path("search")
    @GET
    public ArrayList<Series> search(@QueryParam("user") String user, 
                                    @QueryParam("genre") Genre genre, 
                                    @QueryParam("provider") Streamingprovider provider,
                                    @QueryParam("score") Score score,
                                    @Context ContainerRequestContext crc){
        ArrayList<Series> list = new ArrayList<>();
        System.out.println(score + "; " + user);
        if (score != null){
            if (user != null) {
                list = steamservices.search(user, genre, provider, score); 
            }
        } else {
            list = steamservices.search(user, genre, provider, null);
        }
        return list;
    }
    
    @Path("{seriesName}")
    @GET
    public ArrayList<Series> getSeriesWithTitle(@PathParam("seriesName") String seriesName) {
        return steamservices.getAllSeriesWithTitle(seriesName);
    }
}
