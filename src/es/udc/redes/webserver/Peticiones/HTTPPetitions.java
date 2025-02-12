package es.udc.redes.webserver.Peticiones;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import es.udc.redes.tutorial.info.Info;

public enum HTTPPetitions {

    /////////////////// PETICIONES //////////////////////

    GET("GET") {
        @Override
        public String proccessPetition(String [] args, String serverName, OutputStream out) {
            //Validar argumentos
            try {
                HTTPPetition petition = new HTTPPetition(args[0]);
                //Verificar si es if-modified-since (no se como cojones hacerlo aun)
                //Si es una get a secas hace esto
                petition.sendFile(out);
                return petition.getHeader();
            }
            catch (FileNotFoundException e){return serverName + "\n" + e.getMessage() + "\n" + args;}
            catch (AccessDeniedException e){return serverName + "\n" + e.getMessage() + "\n" + args;}
            catch (IOException e) {return serverName + "\n" + e.getMessage() + "\n" + args;}
        }
    },


    HEAD("HEAD") {
        @Override
        public String proccessPetition(String [] args, String serverName, OutputStream out) {
            //Validar argumentos
            try {
                HTTPPetition petition = new HTTPPetition(args[0]);
                //Verificar si es if-modified-since (no se como cojones hacerlo aun)
                //Si es una get a secas hace esto
                return petition.getHeader();
            }
            catch (FileNotFoundException e){return serverName + "\n" + e.getMessage() + "\n" + args;}
            catch (AccessDeniedException e){return serverName + "\n" + e.getMessage() + "\n" + args;}
            catch (IOException e) {return serverName + "\n" + e.getMessage() + "\n" + args;}
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
        return "";
    }
}
