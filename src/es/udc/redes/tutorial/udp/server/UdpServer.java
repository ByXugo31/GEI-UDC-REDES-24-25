package es.udc.redes.tutorial.udp.server;

import es.udc.redes.Utilities;
import java.net.*;

/**
 * Implements a UDP echo server.
 */
public class UdpServer {

    /////////////// ATTRIBUTES ///////////////

    private static final String ERROR_ARGS = "ERROR EN LOS PARAMETROS <NUMERO DE PUERTO>";
    private static final int NUM_ARGS = 1;

    private String puerto;
    private int timeout = 300000;
    private int tamBuffer = 1024;

    /////////////// CONSTRUCTOR & SETTERS ///////////////

    public UdpServer(String args[]) {
        if (!Utilities.verifyArgs(args, NUM_ARGS) ) {
            this.puerto = args[0];
        } else throw new IllegalArgumentException(ERROR_ARGS);
    }


    //////////////// METHODS ///////////////

    private int parsePort() {
        try {
            int port = Integer.parseInt(puerto);
            return port;
        }
        catch (NumberFormatException e) {throw new IllegalArgumentException("EL PUERTO ESPECIFICADO NO ES VALIDO");}
    }


    public void start() {
        System.out.println("SERVER: INICIADO EN EL PUERTO " + parsePort());
        try (DatagramSocket serverSocket = new DatagramSocket(parsePort())) {
            serverSocket.setSoTimeout(timeout);
            while (true) {
                byte array[] = new byte[tamBuffer];
                DatagramPacket dgramReceived = new DatagramPacket(array, array.length);
                serverSocket.receive(dgramReceived);

                String message = new String(dgramReceived.getData(), 0, dgramReceived.getLength());
                System.out.println("\033[0;32m[+] RECIBIDO " + message + " DESDE " + dgramReceived.getAddress() + ":" + dgramReceived.getPort() + "\033[0m");

                DatagramPacket dgramSent = new DatagramPacket(dgramReceived.getData(), dgramReceived.getLength(), dgramReceived.getAddress(), dgramReceived.getPort());
                serverSocket.send(dgramSent);
            }
        }
        catch (SocketTimeoutException e) {System.err.println("[-] NO SE HAN RECIBIDO PETICIONES EN " + timeout + " ms");}
        catch (Exception e) {
            System.err.println("[-] ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //////////////// MAIN ///////////////

    public static void main(String argv[]) {
        try {
            UdpServer server = new UdpServer(argv);
            server.start();
        } catch (IllegalArgumentException e) {System.err.println(e.getMessage());}
    }
}
