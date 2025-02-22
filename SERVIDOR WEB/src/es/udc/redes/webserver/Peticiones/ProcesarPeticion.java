package es.udc.redes.webserver.Peticiones;

import es.udc.redes.webserver.Errores.Error;
import es.udc.redes.webserver.Errores.ErrorConFichero;
import es.udc.redes.webserver.Errores.ErrorSoloCabecera;
import es.udc.redes.webserver.Peticiones.Types.Peticion;
import es.udc.redes.webserver.Peticiones.Types.Tipos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.NoSuchElementException;

public class ProcesarPeticion {

    //////////////// ATRIBUTOS //////////////

    private String serverName;

    private boolean allowDirectoryListing;

    private String nombre;

    private File file;

    private String ifModifiedSince;

    private Peticion peticion;

    private OutputStream out;

    private Error error400;

    private Error error404;

    private Error error304;



    //////////////// CONSTRUCTOR //////////////

    public ProcesarPeticion(String serverName, boolean allowDirectoryListing) throws IOException {
        this.serverName = serverName;
        this.allowDirectoryListing = allowDirectoryListing;
        error400 = new ErrorConFichero(StatusCode.BadRequest, serverName, new File("p1-files/error400.html"));
        error404 = new ErrorConFichero(StatusCode.NotFound, serverName, new File("p1-files/error404.html"));
        error304 = new ErrorSoloCabecera(StatusCode.NotModified, serverName);
    }



    /////////////// METODOS //////////////

    public String obtainResponse(String[] args, OutputStream out) throws IOException {
        String response;
        if (args.length == 0 || args.length == 4 || args.length == 2  || args.length > 5) return error400.sendError(out);
        this.nombre = args[0];
        this.file = new File("p1-files" + File.separator + args[1]);
        if (args.length == 5) {
            if (!args[3].equals("If-modified-since:")) return error400.sendError(out);
            this.ifModifiedSince = args[4];
        }
        this.out = out;
        response = procesarPeticion();
        return response;
    }


    private String procesarPeticion() throws IOException {
        try {
            this.peticion = buscarPeticion();
            this.peticion.buildPetition(allowDirectoryListing, serverName, file, ifModifiedSince, out);
            this.peticion.sendPeticion();
            return this.peticion.toString();
        }
        catch (FileNotFoundException e) {return error404.sendError(out);}
        catch (IllegalArgumentException e) {return error400.sendError(out);}
        catch (NoSuchElementException e) {return error304.sendError(out);}

    }

    private Peticion buscarPeticion() throws IOException {
        for (Tipos t : Tipos.values())
            if (t.name().equalsIgnoreCase(nombre)) return t.getPeticion();
        throw new IllegalArgumentException("No se encontró el tipo: " + nombre);
    }

}
