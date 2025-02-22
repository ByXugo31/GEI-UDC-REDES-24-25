package es.udc.redes.webserver.Errores;

import es.udc.redes.webserver.Files.NormalFile;
import es.udc.redes.webserver.Files.ProcessedFile;
import es.udc.redes.webserver.Peticiones.Header;
import es.udc.redes.webserver.Peticiones.StatusCode;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class ErrorConFichero extends Error{

    //////////////// ATRIBUTOS ///////////////

    ProcessedFile file;



    //////////////// CONSTRUCTOR ///////////////

    public ErrorConFichero(StatusCode code, String serverName,File file) throws IOException {
        super(code,serverName);
        this.file = new NormalFile(file);
        setHeader(new Header(code,this.file,serverName));
    }



    //////////////// METODOS ///////////////

    @Override
    public String sendError(OutputStream out) throws IOException {
        getHeader().sendHeader(out);
        file.sendFile(out);
        return getHeader().toString();
    }

}
