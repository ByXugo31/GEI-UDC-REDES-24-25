package es.udc.redes.tutorial.tcp.server;

import es.udc.redes.Utilities;
import es.udc.redes.tutorial.udp.server.UdpServer;

import java.net.*;
import java.io.*;

/**
 * MonoThread TCP echo server.
 */
public class MonoThreadTcpServer {

    /////////////// ATTRIBUTES ///////////////

    private static final String ERROR_ARGS = "ERROR EN LOS PARAMETROS <NUMERO DE PUERTO>";
    private static final int NUM_ARGS = 1;

    private String puerto;
    private int timeout = 300000;


    /////////////// CONSTRUCTOR & SETTERS ///////////////

    public MonoThreadTcpServer(String args[]) {
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

    private void sendMessages(Socket socket, PrintWriter bufferSalida, BufferedReader bufferEntrada) throws IOException {
        String message = bufferEntrada.readLine();
        System.out.println("\033[0;32m[+] RECIBIDO " + message + " DESDE " + socket.getInetAddress() + ":" + socket.getPort() + "\033[0m");
        bufferSalida.println(message);
    }

    private void close(Socket socket, PrintWriter writer, BufferedReader reader) throws IOException {
        reader.close();
        writer.close();
        socket.close();
    }

    public void start() throws IOException {
        System.out.println("SERVER: INICIADO EN EL PUERTO " + parsePort());
        ServerSocket serverSocket = new ServerSocket(parsePort());
        try {
            serverSocket.setSoTimeout(timeout);
            while (true) {
                Socket socketCliente = serverSocket.accept();
                BufferedReader bufferEntrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                PrintWriter bufferSalida = new PrintWriter(socketCliente.getOutputStream(), true);
                sendMessages(socketCliente, bufferSalida, bufferEntrada);

                close(socketCliente, bufferSalida, bufferEntrada);
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
        try {
            MonoThreadTcpServer server = new MonoThreadTcpServer(argv);
            server.start();
        } catch (IllegalArgumentException | IOException e) {System.err.println(e.getMessage());}
    }
}
