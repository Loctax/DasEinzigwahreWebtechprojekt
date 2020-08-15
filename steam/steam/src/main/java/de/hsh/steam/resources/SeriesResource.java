
package de.hsh.steam.resources;

import de.hsh.steam.application.Series;
import de.hsh.steam.services.SeriesService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/series")
public class SeriesResource {
    
    SeriesService s = new SeriesService();
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Series> getAllSeries(){
        return s.getAllSeries();
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Series addSeries(Series series){
        return s.addSeries(series);
    }
    
    @GET
    @Path("/{seriesId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Series getSeries(@PathParam("seriesId") long seriesId){
        return s.getSeries(seriesId);
    }
    
    
    
    @DELETE
    @Path("/{seriesId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void deleteSeries(@PathParam("seriesId") long seriesId){
        s.deleteSeries(seriesId);
    }
    
}
