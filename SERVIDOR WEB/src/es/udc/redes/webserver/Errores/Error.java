package es.udc.redes.webserver.Errores;

import es.udc.redes.webserver.Peticiones.Header;
import es.udc.redes.webserver.Peticiones.StatusCode;

import java.io.IOException;
import java.io.OutputStream;

public abstract class Error {

    //////////////// ATRIBUTOS ///////////////

    private Header head;

    private StatusCode code;

    private String serverName;



    //////////////// CONSTRUCTOR ///////////////

    public Error(StatusCode code, String serverName) {
        this.code = code;
        this.serverName = serverName;
    }


    //////////////// SETTERS & GETTERS ///////////////

    protected void setHeader(Header head) {this.head = head;}
    Header getHeader() {return this.head;}



    //////////////// METODOS ///////////////

    public abstract String sendError(OutputStream out) throws IOException;
}
