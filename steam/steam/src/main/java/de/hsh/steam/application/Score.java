package de.hsh.steam.application;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum Score implements Serializable {
	
	bad, mediocre, good, very_good;
	
}
