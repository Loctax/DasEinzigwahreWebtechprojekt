/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsh.steam.persistence;

import de.hsh.steam.application.Series;
import de.hsh.steam.application.User;
import java.util.HashMap;
import java.util.Map;



public class DataBaseClass {
    
    private static Map<Long, Series> series = new HashMap<>();
    private static Map<Long, User> user = new HashMap<>();
    
    public static Map<Long, Series> getSeries(){
        return series;
    }
    public static Map<Long, User> getUser(){
        return user;
    }

}
