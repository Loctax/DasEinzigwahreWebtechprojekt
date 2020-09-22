package de.hsh.steam.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class User implements Serializable{

	private static final long serialVersionUID = -3545765962123273389L;
	
	// username ist eindeutig
	// username und pwd können nicht geändert werden
        private long userId;
	private String username;
	private String password;
	private ArrayList<Rating> myRatings = new ArrayList<Rating>();
        private List<Series> userSeries = new ArrayList<Series>();
	
        public User(){
            
        }
	public User(String name, String password) {
                this.username = name;
		this.password = password;
	}
	
	public Boolean login(String username, String password) {
		return this.username == username && this.password == password;
	}
	
	// setzt neues Rating oder modifiziert vorhandenes Rating
	public void rate(Series series, Score score, String remark) {
		for (Rating r: myRatings) {
			if ( r.getRatedSeries().equals(series) ) {
				r.setScore(score);
				r.setRemark(remark);
                                return;
			}
		}
		Rating r = new Rating(score, remark, this, series);
		myRatings.add(r);
	}
	
	
	public Rating ratingOf(Series s) {
		for (Rating r: myRatings) {
			if (r.getRatedSeries().equals(s))
				return r;
		}
		return null;
	}
        
        @XmlElement
        public long getId(){
            return userId;
        }
        
        public void setId(long id){
            this.userId = id;
        }
        
        @XmlElement
	public String getUsername() {
		return username;
	}
	
        @XmlElement
	public String getPassword() {
		return password;
	}

	public String toString() {
		return this.username + " " + this.password;
	}

        public List<Series> getSeries(){
            return userSeries;
        }
        
        public void setSeries(List<Series> userseries){
            this.userSeries = userseries;
        }
	
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		User u = (User) o;
		return this.username == u.username;
	}
}
