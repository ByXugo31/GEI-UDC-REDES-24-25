package es.udc.redes.tutorial.tcp.server;
import java.net.*;
import java.io.*;

/** Thread that processes an echo server connection. */

public class ServerThread extends Thread {

  /////////////// ATTRIBUTES ///////////////

  private Socket socket;
  private int timeout = 10;


  /////////////// CONSTRUCTOR & SETTERS ///////////////

  public ServerThread(Socket s) {socket = s;}

  public void setTimeout(int timeout) {this.timeout = timeout;}


  /////////////// METHODS ///////////////

  private void closeSocket() {
    try {socket.close();}
    catch (IOException e) {throw new RuntimeException(e);}
  }

  private void sendMessages(PrintWriter bufferSalida, BufferedReader bufferEntrada) throws IOException {
    String message = bufferEntrada.readLine();
    System.out.println("\033[0;32m[+] RECIBIDO " + message + " DESDE " + socket.getInetAddress() + ":" + socket.getPort() + "\033[0m");
    bufferSalida.println(message);
  }

  public void run() {
    try  (BufferedReader bufferEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          PrintWriter bufferSalida = new PrintWriter(socket.getOutputStream(), true)){
      sendMessages(bufferSalida, bufferEntrada);
    }
    catch (SocketTimeoutException e) {System.err.println("[-] NO SE HAN RECIBIDO PETICIONES EN 300S ");}
    catch (Exception e) {
      System.err.println("[-] ERROR: " + e.getMessage());
      e.printStackTrace();
    }
    finally {closeSocket();}
  }
}
