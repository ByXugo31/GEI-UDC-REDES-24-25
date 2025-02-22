package es.udc.redes.webserver.Peticiones;

import es.udc.redes.webserver.Files.ProcessedFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class Header {

    //////////////// ATRIBUTOS //////////////

    ProcessedFile input;

    StatusCode code;

    String serverName;

    Date date = new Date();



    //////////////// CONSTRUCTOR //////////////

    public Header(StatusCode statusCode, String serverName) {
        this.code = statusCode;
        this.serverName = serverName;
    }

    public Header(StatusCode statusCode, ProcessedFile input, String serverName) {
        this.code = statusCode;
        this.input = input;
        this.serverName = serverName;
    }



    //////////////// METODOS //////////////

    public void sendHeader(OutputStream out) throws IOException {
        out.write(toString().getBytes());
        out.flush();
    }

    @Override
    public String toString() {
        String head = code.toString();
        head += "Server: " + serverName + "\n" + "Date: " + date + "\n";
        if (input != null) head += input.toString();
        return head + "\n";
    }
}
