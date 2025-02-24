package es.udc.redes.webserver;
import es.udc.redes.Utilities;
import es.udc.redes.webserver.Peticiones.ProcesarPeticion;

import java.io.IOException;
import java.net.*;

public class WebServer {

    /////////////// ATTRIBUTES ///////////////

    private static final String ERROR_ARGS = "ERROR EN LOS PARAMETROS <NUMERO DE PUERTO>";
    private static final int NUM_ARGS = 2;

    private String puerto;
    private int timeout = 300000;
    private String serverName;
    private Log log;
    private boolean allowDirectoryListing;
    private ProcesarPeticion processer;



    /////////////// CONSTRUCTOR ///////////////

    public WebServer(String args[], String serverName) {
        try {
            if (!Utilities.verifyArgs(args, NUM_ARGS)) {
                this.puerto = args[0];
                this.allowDirectoryListing = "1".equals(args[1]);
                this.serverName = serverName;
                processer = new ProcesarPeticion(serverName,allowDirectoryListing);
                log = new Log("p1-files/log.txt");
            } else {throw new IllegalArgumentException(ERROR_ARGS);}
        }
        catch (IOException e) {e.printStackTrace();}
    }



    //////////////// METHODS ///////////////

    private int parsePort() {
        try {
            int port = Integer.parseInt(puerto);
            return port;
        }
        catch (NumberFormatException e) {throw new IllegalArgumentException("[-] EL PUERTO ESPECIFICADO NO ES VALIDO");}
    }

    public void start() throws IOException {
        System.out.println("SERVER: INICIADO EN EL PUERTO " + parsePort());
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(parsePort());
            serverSocket.setSoTimeout(timeout);
            while (true) {
                Socket socketCliente = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socketCliente, log, processer);
                serverThread.start();
            }
        }
        catch (SocketTimeoutException e) {System.err.println("[-] NO SE HAN RECIBIDO PETICIONES EN " + timeout + " ms");}
        catch (Exception e) {
            System.err.println("[-] ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        finally {serverSocket.close();}
    }



    //////////////// MAIN ///////////////

    public static void main(String argv[]) {
        if (argv.length != 2) {
            System.err.println("Usage: java WebServer <port> <allow_directory_listing>");
            return;
        }
        try {
            WebServer server = new WebServer(argv, "TESTSERVER");
            server.start();
        }
        catch (IllegalArgumentException | IOException e) {System.err.println(e.getMessage());}
    }
}