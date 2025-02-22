package es.udc.redes.webserver.Peticiones.Types;

import es.udc.redes.webserver.Errores.*;
import es.udc.redes.webserver.Errores.Error;
import es.udc.redes.webserver.Files.*;
import es.udc.redes.webserver.Peticiones.Header;
import es.udc.redes.webserver.Peticiones.StatusCode;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class HEAD extends Peticion{

    /////////////////// ATRIBUTOS //////////////////////

    private Error error304 = new ErrorSoloCabecera(StatusCode.NotModified, getServerName());



    /////////////////// CONSTRUCTOR //////////////////////

    public HEAD() throws IOException {}



    /////////////////// METODOS //////////////////////

    @Override
    public void sendPeticion() throws IOException {
        if (getIsModifiedSince()!=null && !isModifiedSince()){
            error304.sendError(getOut());
            return;
        }
        setHeader(new Header(StatusCode.OK, getFile(), getServerName()));
        getHeader().sendHeader(getOut());
    }

    @Override
    public void buildPetition(boolean allowDirectoryListing, String serverName, File file, String ifModifiedSince, OutputStream out) throws IOException {
        super.buildCommonAtributes(serverName, file, out, ifModifiedSince);
    }
}
