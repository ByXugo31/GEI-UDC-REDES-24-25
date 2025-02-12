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
        public String proccessPetition(String [] args, String serverName, OutputStream out) {
            String message = "----------------------------------------\n" + LocalDateTime.now() + "\n" + serverName + "\n";
            if (args.length == 0) return message + StatusCode.BadRequest + Utilities.printArray(args) + "\n";
            try {
                HTTPPetition petition = new HTTPPetition(args[0]);
                if (args.length == 2 && args[1].equals("if-modified-since"))
                    if(petition.isModified(args[3])) return message + StatusCode.NotModified + "\n" + Utilities.printArray(args) + "\n";
                petition.sendFile(out);
                return message + StatusCode.OK + "\n" + petition.getHeader() + "\n" + "GET " + Utilities.printArray(args) + "\n";
            }
            catch (FileNotFoundException e){return message + e.getMessage() + "\n" + "GET " + Utilities.printArray(args) + "\n";}
            catch (AccessDeniedException e){return message + e.getMessage() + "\n" + "GET " + Utilities.printArray(args) + "\n";}
            catch (IOException e) {return message + e.getMessage() + "\n" + "GET " + Utilities.printArray(args) + "\n";}
        }
    },


    HEAD("HEAD") {
        @Override
        public String proccessPetition(String [] args, String serverName, OutputStream out) {
            String message = "----------------------------------------\n" + LocalDateTime.now() + "\n" + serverName + "\n";
            if (args.length == 0) return message + StatusCode.BadRequest + Utilities.printArray(args) + "\n";
            try {
                HTTPPetition petition = new HTTPPetition(args[0]);
                if (args.length == 2 && args[1].equals("if-modified-since"))
                    if(petition.isModified(args[3])) return message + StatusCode.NotModified + "\n" + "HEAD " + Utilities.printArray(args) + "\n";
                return message + StatusCode.OK + "\n" + petition.getHeader() + "\n" + "HEAD " + Utilities.printArray(args) + "\n";
            }
            catch (FileNotFoundException e){return message + e.getMessage() + "\n" + "HEAD " + Utilities.printArray(args) + "\n";}
            catch (AccessDeniedException e){return message + e.getMessage() + "\n" + "HEAD " + Utilities.printArray(args) + "\n";}
            catch (IOException e) {return message + e.getMessage() + "\n" + "HEAD " + Utilities.printArray(args) + "\n";}
        }
    };


    /////////////////// ATRIBUTOS & CONSTRUCTOR & GETTER //////////////////////

    private String name;

    HTTPPetitions(String nme) {name = nme;}


    /////////////////// METODOS //////////////////////ç

    public abstract String proccessPetition(String[] args, String serverName, OutputStream out);

    public static String executePetition(String [] args, String serverName, OutputStream out) {
        for (HTTPPetitions petition : HTTPPetitions.values()) {
            if (petition.name.equalsIgnoreCase(args[0])) {
                String[] argsPet = Arrays.copyOfRange(args, 1, args.length);
                return petition.proccessPetition(argsPet, serverName, out);
            }
        }
        return "----------------------------------------\n" + serverName + "\n" + StatusCode.BadRequest + "\n" + args + "\n";
    }
}