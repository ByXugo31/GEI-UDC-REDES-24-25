package es.udc.redes.webserver.Peticiones.Types;

import es.udc.redes.webserver.Errores.*;
import es.udc.redes.webserver.Errores.Error;
import es.udc.redes.webserver.Files.*;
import es.udc.redes.webserver.Peticiones.Header;
import es.udc.redes.webserver.Peticiones.StatusCode;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class GET extends Peticion{

    /////////////////// ATRIBUTOS //////////////////////

    private boolean allowDirectoryListing;

    private Error error403 = new ErrorConFichero(StatusCode.Forbidden, getServerName(), new File("p1-files/error403.html"));

    private Error error304 = new ErrorSoloCabecera(StatusCode.NotModified, getServerName());



    /////////////////// CONSTRUCTOR //////////////////////

    public GET() throws IOException {}



    /////////////////// METODOS //////////////////////

    private boolean listable(){
        return allowDirectoryListing && getFile() instanceof DynamicHTML;
    }

    @Override
    public void sendPeticion() throws IOException {
        if (getFile() instanceof DynamicHTML && !listable()){
            error403.sendError(getOut());
            return;
        }
        if (getIsModifiedSince()!=null && !isModifiedSince()){
            error304.sendError(getOut());
            return;
        }
        setHeader(new Header(StatusCode.OK, getFile(), getServerName()));
        getHeader().sendHeader(getOut());
        getFile().sendFile(getOut());
    }

    @Override
    public void buildPetition(boolean allowDirectoryListing, String serverName, File file, String ifModifiedSince, OutputStream out) throws IOException {
        this.allowDirectoryListing = allowDirectoryListing;
        super.buildCommonAtributes(serverName, file, out, ifModifiedSince);
    }
}
