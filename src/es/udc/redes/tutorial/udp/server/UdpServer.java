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
        // Create a server socket
        try (DatagramSocket serverSocket = new DatagramSocket(parsePort())) {
            // Set a timeout of 300 secs
            serverSocket.setSoTimeout(300000);
            while (true) {
                // Prepare datagram for reception
                byte array[] = new byte[1024];
                DatagramPacket dgramReceived = new DatagramPacket(array, array.length);
                // Receive the message
                serverSocket.receive(dgramReceived);
                System.out.println("SERVER: RECIBIDO " + new String(dgramReceived.getData()) + " DESDE " + dgramReceived.getAddress().toString() + ":" + dgramReceived.getPort());
                // Prepare datagram to send response
                DatagramPacket dgramSent = new DatagramPacket(dgramReceived.getData(), dgramReceived.getLength(), dgramReceived.getAddress(), dgramReceived.getPort());
                // Send response
                serverSocket.send(dgramSent);
            }
        }
        catch (SocketTimeoutException e) {System.err.println("NO SE HAN RECIBIDO PETICIONES EN 300S ");}
        catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
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
