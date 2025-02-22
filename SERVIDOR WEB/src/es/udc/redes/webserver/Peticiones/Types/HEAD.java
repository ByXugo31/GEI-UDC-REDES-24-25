package es.udc.redes.webserver.Peticiones.Types;

import es.udc.redes.webserver.Peticiones.Header;
import es.udc.redes.webserver.Peticiones.StatusCode;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class HEAD extends Peticion{

    /////////////////// CONSTRUCTOR //////////////////////

    public HEAD() throws IOException {}



    /////////////////// METODOS //////////////////////

    @Override
    public void sendPeticion() throws IOException {
        setHeader(new Header(StatusCode.OK, getFile(), getServerName()));
        getHeader().sendHeader(getOut());
    }

    @Override
    public void buildPetition(boolean allowDirectoryListing, String serverName, File file, String ifModifiedSince, OutputStream out) throws IOException {
        super.buildCommonAtributes(serverName, file, out, ifModifiedSince);
    }
}
