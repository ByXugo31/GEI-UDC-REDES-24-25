package es.udc.redes.tutorial.tcp.server;
import es.udc.redes.Utilities;
import java.net.*;

public class TcpServer {

  /////////////// ATTRIBUTES ///////////////

  private static final String ERROR_ARGS = "ERROR EN LOS PARAMETROS <NUMERO DE PUERTO>";
  private static final int NUM_ARGS = 1;

  private String puerto;
  private int timeout = 300000;


  /////////////// CONSTRUCTOR ///////////////

  public TcpServer(String args[]) {
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
    try (ServerSocket serverSocket = new ServerSocket(parsePort())) {
      serverSocket.setSoTimeout(timeout);
      while (true) {
        Socket socketCliente = serverSocket.accept();
        ServerThread serverThread = new ServerThread(socketCliente);
        serverThread.start();
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
      TcpServer server = new TcpServer(argv);
      server.start();
    } catch (IllegalArgumentException e) {System.err.println(e.getMessage());}
  }
}
