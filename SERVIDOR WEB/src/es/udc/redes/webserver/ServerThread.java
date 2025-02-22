package es.udc.redes.webserver;

import es.udc.redes.Utilities;
import es.udc.redes.webserver.Peticiones.ProcesarPeticion;

import java.net.*;
import java.io.*;


public class ServerThread extends Thread {

    private Socket socket;
    private String[] request;
    private String serverName;
    private boolean allowDirectoryListing;
    private Log log;
    private ProcesarPeticion processer;

    public ServerThread(Socket s, String serverName, boolean allowDirectoryListing, Log log, ProcesarPeticion processer) {
        // Store the socket s
        this.socket = s;
        this.serverName = serverName;
        this.allowDirectoryListing = allowDirectoryListing;
        this.log = log;
        this.processer = processer;
    }

    private void readRequest(BufferedReader in) throws IOException {
        String message = in.readLine();
        if (message == null) return;
        System.out.println("\033[0;32m[+] RECIBIDO " + message + " DESDE " + socket.getInetAddress() + ":" + socket.getPort() + "\033[0m");
        request = message.split(" ");
    }

    private void storeLog(String response){
        try {log.storeLog("CLIENT: " + socket.getInetAddress() + ":" + socket.getPort() + "\n\nREQUEST: \n" + Utilities.printArray(request) + "\n\n" + "RESPONSE: \n" + response + "\n");}
        catch (FileNotFoundException e) {System.err.println("[-] No se ha encontrado el fichero de log.");}
        catch (IOException e) {System.err.println("[-] Error inesperado al escribir en el fichero de log.");}
    }

    public void run() {
        String response;
        try (BufferedReader bufferEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            OutputStream bufferSalida = socket.getOutputStream();
            readRequest(bufferEntrada);
            response = processer.obtainResponse(request,bufferSalida);
            storeLog(response);
        }
        catch (IllegalArgumentException e) {System.err.println(e.getMessage());}
        catch (IOException e) {System.err.println("[-] " + e.getMessage());}
        catch (Exception e) {System.err.println("[-] " + e.getMessage());}
    }
}