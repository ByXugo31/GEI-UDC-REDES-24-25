package es.udc.redes.webserver;

import es.udc.redes.tutorial.tcp.server.TcpServer;

public class WebServer {
    
    public static void main(String[] args) {
        try {
            TcpServer server = new TcpServer(args);
            server.start();
        } catch (IllegalArgumentException e) {System.err.println(e.getMessage());}
    }
    
}
