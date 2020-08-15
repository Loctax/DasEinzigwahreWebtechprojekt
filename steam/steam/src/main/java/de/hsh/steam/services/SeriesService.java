/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsh.steam.services;

import de.hsh.steam.application.Genre;
import static de.hsh.steam.application.Genre.Action;
import de.hsh.steam.application.Series;
import de.hsh.steam.application.Streamingprovider;
import static de.hsh.steam.application.Streamingprovider.AmazonPrime;
import static de.hsh.steam.application.Streamingprovider.Netflix;
import de.hsh.steam.persistence.DataBaseClass;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class SeriesService {
    private static Map<Long, Series> seriesMap = DataBaseClass.getSeries();

    public SeriesService(){
        Series s1 = new Series(1, "Game of Thrones", 5, Action, AmazonPrime);
        Series s2 = new Series(2, "The Flash", 4, Action, Netflix);
        seriesMap.put(1L, s1);
        seriesMap.put(2L, s2);
    }
    public List<Series> getAllSeries(){
        return new ArrayList<Series>(seriesMap.values());
    }
    public Series getSeries(long id){
        return seriesMap.get(id);
    }
    public Series addSeries(Series series){
        series.setId(seriesMap.size() + 1);
        seriesMap.put(series.getId(), series);
        return series;
    }
    public Series deleteSeries(long id){
        return seriesMap.remove(id);
    }
            
}
