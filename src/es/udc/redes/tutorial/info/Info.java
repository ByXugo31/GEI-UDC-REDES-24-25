
package es.udc.redes.tutorial.info;

import es.udc.redes.Utilities;

import java.io.*;
import java.util.Date;

public class Info {

    /////////////// ATTRIBUTES ///////////////

    private static final String ERROR_ARGS = "ERROR EN LOS PARAMETROS <FICHERO>";
    private static final int NUM_ARGS = 1;

    private File input;
    private String output;

    /////////////// CONSTRUCTOR ///////////////

    public Info(String[] args) {
        if(!Utilities.verifyArgs(args, NUM_ARGS)){
            this.input = new File(args[0]);
            if(!input.exists()) throw new IllegalArgumentException("NO SE HA ENCONTRADO EL FICHERO.");
        } else throw new IllegalArgumentException(ERROR_ARGS);
    }


    //////////////// METHODS ///////////////

    private String getExtension(){
        String name = input.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) return "NONE";
        return name.substring(lastIndexOf);
    }

    private String getFileType() {
        if (input.isDirectory()) return "DIRECTORY";
        if (input.isFile()) return "FILE";
        return "NONE";
    }


    //////////////// MAIN ///////////////
    
    public static void main(String[] args) {
        try {
            Info info = new Info(args);
            System.out.println(info.toString());
        } catch (IllegalArgumentException e) {System.err.println(e.getMessage());}
    }

    @Override
    public String toString() {
       String output;
       output = "FILE:\n " + "NAME: " + input.getName() + "\n" + "PATH: " + input.getAbsolutePath() + "\n" + "SIZE: " + input.length() + "\n" +
               "LAST MODIFIED: " + input.lastModified() + "\n" + "EXTENSION: " + getExtension() + "\n" + "TYPE: " + + "\n";
    }
}