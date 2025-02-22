package es.udc.redes.webserver.Errores;

import es.udc.redes.webserver.Peticiones.Header;
import es.udc.redes.webserver.Peticiones.StatusCode;
import java.io.IOException;
import java.io.OutputStream;

public class ErrorSoloCabecera extends Error {

    //////////////// CONSTRUCTOR ///////////////

    public ErrorSoloCabecera(StatusCode code, String serverName) {
        super(code, serverName);
        setHeader(new Header(code, serverName));
    }



    //////////////// METODOS ///////////////

    @Override
    public String sendError(OutputStream out) throws IOException {
        getHeader().sendHeader(out);
        return getHeader().toString();
    }
}