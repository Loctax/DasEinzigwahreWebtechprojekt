/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsh.steam;
import com.sun.net.httpserver.HttpServer;
import java.net.URI;
import javax.swing.JOptionPane;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.*;
/**
 *
 * @author Maxi
 */
public class RestServer {
    public static void main(String[] args){
    ResourceConfig rc = new ResourceConfig().packages( "de.hsh.steam" );
    HttpServer server = JdkHttpServerFactory.createHttpServer( 
    URI.create( "http://localhost:8080/api" ), rc );
    JOptionPane.showMessageDialog(null, "Server läuft");  
    server.stop(0);
    }
}
    
