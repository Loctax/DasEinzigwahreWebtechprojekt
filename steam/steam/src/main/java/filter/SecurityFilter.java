/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import de.hsh.steam.application.Steamservices;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.internal.util.Base64;

/**
 *
 * @author JanBär
 */

@Provider
public class SecurityFilter implements ContainerRequestFilter {
    
    private static SecurityFilter exemplar = null;
	public static SecurityFilter singleton() {
		if (exemplar == null) {
			exemplar = new SecurityFilter();
		}
		return exemplar; 
	}
    
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
    //private static final String SECURED_URL_PREFIX = "secured";
    private static final String SECURED_URL_PREFIX = "users";
    Steamservices steamservices = Steamservices.singleton();

    /**
     * Der SecurityFilter ueberprueft fuer alle URIs, die SECURED_URL_PREFIX enthalten die 
     * Authentizitaet, dafuer muss die Request die Login Daten, mit Basic Auth encodiert, enthalten
     * @param requestContext wird automatisch uebermittelt.
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
            List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER);
            if (authHeader != null && authHeader.size() > 0) {
                String authToken = authHeader.get(0);
                authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
                String decodedString = Base64.decodeAsString(authToken);
                StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
                String username = tokenizer.nextToken();
                String password = tokenizer.nextToken();
                
                if (steamservices.login(username, password)){
                    return;
                }

                /**if ("daisy".equals(username) && "123".equals(password)) {
                    return;
                }**/
            }
            Response unauthorizedStatus = Response
                                            .status(Response.Status.UNAUTHORIZED)
                                            .entity("User cannot access the resource.")
                                            .build();
            requestContext.abortWith(unauthorizedStatus);            
        }
    }
    
    /**
     * Methode um zu pruefen ob usernameFromParameter der eingeloggte User ist
     * @param requestContext wird automatisch uebermittelt.
     * @param usernameFromParameter der zu vergleichende Username
     * @return true : usernameFromParameter ist dereingeloggte User
     */
    public Boolean checkCredentials(ContainerRequestContext requestContext, String usernameFromParameter) {
        if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
            List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER);
            if (authHeader != null && authHeader.size() > 0) {
                String authToken = authHeader.get(0);
                authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
                String decodedString = Base64.decodeAsString(authToken);
                StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
                String username = tokenizer.nextToken();
                String password = tokenizer.nextToken();
                
                if (steamservices.login(username, password)){
                    if (username.equals(usernameFromParameter)){
                        return true;
                    }
                }
            }
        }        
        return false;
    }
    
    /**
     * Methode um den Username des eingeloggten Users zu bekommen
     * @param requestContext wird automatisch uebermittelt.
     * @return String : username des eingeloggte Users
     */
    public String getUsernameFromContext(ContainerRequestContext requestContext) {
        List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.size() > 0) {
            String authToken = authHeader.get(0);
            authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
            String decodedString = Base64.decodeAsString(authToken);
            StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
            String username = tokenizer.nextToken();
            return username;
        }
        else {
            return "no Auth found";
        }
    } 
}