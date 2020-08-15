
package de.hsh.steam.services;

import static de.hsh.steam.application.Genre.*;
import de.hsh.steam.application.Rating;
import de.hsh.steam.application.Score;
import de.hsh.steam.application.Series;
import static de.hsh.steam.application.Streamingprovider.*;
import de.hsh.steam.application.User;
import de.hsh.steam.persistence.DataBaseClass;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Userservice {
    private static Map<Long, User> userMap = DataBaseClass.getUser();
    SeriesService s = new SeriesService();
    
    public Userservice(){
        User u1 = new User(1, "Hans", "123abc");
        User u2 = new User(2, "Stephie", "123456789");
        List<Series> userSeries1 = new ArrayList<>();
        List<Series> userSeries2 = new ArrayList<>();
        u1.setSeries(userSeries1);
        u2.setSeries(userSeries2);
        Series s1 = new Series(1, "Game of Thrones", 5, Action, AmazonPrime);
        Series s2 = new Series(2, "The Flash", 4, Action, Netflix);
        userSeries1.add(s1);
        userSeries2.add(s2);
        u1.rate(s1, Score.very_good, "Best Series");
        u2.rate(s2, Score.mediocre, "Pretty Boring");
        userMap.put(1L, u1);
        userMap.put(2L, u2);


        
    }
    public List<User> getAllUsers(){
        return new ArrayList<>(userMap.values());
    }
    public User getUser(long id){
        return userMap.get(id);
    }
    public User addUser(User user){
        user.setId(userMap.size() + 1);       
        userMap.put(user.getId(), user);
        return user;
    }
    public User deleteUser(long id){
        return userMap.remove(id);
    }
    
    public List<Series> getUserSeries(long id){
        return getUser(id).getSeries();
    }
    
    public List<Series> addUserSeries(long userId, Series series){
        s.addSeries(series);
        getUser(userId).getSeries().add(series);
        return getUser(userId).getSeries();
    }
    
    public Rating getRating(long userId, long seriesId){
        return getUser(userId).ratingOf(s.getSeries(seriesId));
        
    }
}
