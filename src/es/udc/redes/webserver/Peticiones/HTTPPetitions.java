package es.udc.redes.webserver.Peticiones;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Arrays;
import es.udc.redes.Utilities;

public enum HTTPPetitions {

    /////////////////// PETICIONES //////////////////////

    GET("GET") {

        @Override
        public String proccessPetition(String [] args, String serverName, OutputStream out) throws IOException {
            if (args.length == 0 || args.length > 4) return HTTPPetitions.sendBadRequest(out);
            try {
                HTTPPetition petition = new HTTPPetition(args[0]);
                if (args.length == 4 && args[2].equals("If-modified-since:"))
                    if(petition.isModified(args[3])) return HTTPPetitions.sendNotModified(out);
                return sendResponse(petition,out,petition.getHeader(serverName));
            }
            catch (FileNotFoundException e){return sendNotFound(out);}
            catch (AccessDeniedException e){return sendForbidden(out);}
            catch (IOException e) {return sendInternalError(out);}
        }

        @Override
        public String sendResponse(HTTPPetition petition, OutputStream out, String message) {
            try {
                petition.sendStatus(out,StatusCode.OK.toString());
                petition.sendPetition(out);
                if(petition.sendFile(out)) return StatusCode.OK + message + "\n";
                else return HTTPPetitions.sendInternalError(out);
            } catch (IOException e) {return StatusCode.InternalError.toString();}
        }
    },

    HEAD("HEAD") {

        @Override
        public String proccessPetition(String [] args, String serverName, OutputStream out) throws IOException {
            if (args.length == 0 || args.length > 4) return HTTPPetitions.sendBadRequest(out);
            try {
                HTTPPetition petition = new HTTPPetition(args[0]);
                if (args.length == 4 && args[2].equals("If-modified-since:"))
                    if(petition.isModified(args[3])) return HTTPPetitions.sendNotModified(out);
                return sendResponse(petition,out,petition.getHeader(serverName));
            }
            catch (FileNotFoundException e){return sendNotFound(out);}
            catch (AccessDeniedException e){return sendForbidden(out);}
            catch (IOException e) {return sendInternalError(out);}
        }

        @Override
        public String sendResponse(HTTPPetition petition, OutputStream out, String message) {
            try {
                petition.sendStatus(out,StatusCode.OK.toString());
                petition.sendPetition(out);
                return StatusCode.OK + message + "\n";
            } catch (IOException e) {return StatusCode.InternalError.toString();}
        }
    };


    /////////////////// ATRIBUTOS & CONSTRUCTOR & GETTER //////////////////////

    private String name;

    HTTPPetitions(String nme) {name = nme;}


    /////////////////// METODOS //////////////////////ç

    private static String sendNotFound(OutputStream out) throws IOException {
        HTTPPetition petition = new HTTPPetition("error404.html");
        petition.sendStatus(out,StatusCode.NotFound.toString());
        if (!petition.sendFile(out)) return StatusCode.InternalError.toString();
        return StatusCode.NotFound.toString();
    }

    private static String sendForbidden(OutputStream out) throws IOException {
        HTTPPetition petition = new HTTPPetition("error403.html");
        petition.sendStatus(out,StatusCode.NotFound.toString());
        if (!petition.sendFile(out)) return StatusCode.InternalError.toString();
        return StatusCode.Forbidden.toString();
    }

    private static String sendBadRequest(OutputStream out) throws IOException {
        HTTPPetition petition = new HTTPPetition("error400.html");
        petition.sendStatus(out,StatusCode.BadRequest.toString());
        if (!petition.sendFile(out)) return StatusCode.InternalError.toString();
        return StatusCode.BadRequest.toString();
    }

    private static String sendInternalError(OutputStream out) throws IOException {
        HTTPPetition petition = new HTTPPetition();
        petition.sendStatus(out,StatusCode.InternalError.toString());
        return StatusCode.InternalError.toString();
    }

    private static String sendNotModified(OutputStream out) throws IOException {
        HTTPPetition petition = new HTTPPetition();
        petition.sendStatus(out,StatusCode.NotModified.toString());
        return StatusCode.NotModified.toString();
    }

    public abstract String sendResponse(HTTPPetition petition, OutputStream out, String message);

    public abstract String proccessPetition(String[] args, String serverName, OutputStream out) throws IOException;

    public static String executePetition(String [] args, String serverName, OutputStream out) throws IOException {
        for (HTTPPetitions petition : HTTPPetitions.values()) {
            if (petition.name.equalsIgnoreCase(args[0])) {
                String[] argsPet = Arrays.copyOfRange(args, 1, args.length);
                return petition.proccessPetition(argsPet, serverName, out);
            }
        }
        return sendBadRequest(out);
    }
}