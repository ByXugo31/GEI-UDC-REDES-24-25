
package es.udc.redes.tutorial.info;

import es.udc.redes.Utilities;

import java.io.*;

public class Info {

    /////////////// ATTRIBUTES ///////////////

    private static final String ERROR_ARGS = "ERROR EN LOS PARAMETROS <FICHERO>";
    private static final int NUM_ARGS = 1;

    private File input;

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
        if (input.isDirectory()) return "DIRECTORIO";
        String extension = getExtension();
        if (extension.equals(".txt")) return "TEXTO";
        if (extension.equals(".jpg") || extension.equals(".png") || extension.equals(".gif") || extension.equals(".bmp") || extension.equals(".ico")) return "IMAGEN";
        if (extension.equals(".mp3")) return "AUDIO";
        if (extension.equals(".mp4") || extension.equals(".avi") || extension.equals(".mov")) return "VIDEO";
        if (extension.equals(".zip") || extension.equals(".rar") || extension.equals(".tar") || extension.equals(".gz")) return "FICHERO COMPRIMIDO";
        if (extension.equals(".bin") || extension.equals(".exe")) return "BINARIO";
        return "EXTENSION DESCONOCIDA";
    }


    //////////////// MAIN ///////////////
    
    public static void main(String[] args) {
        try {
            Info info = new Info(args);
            System.out.println(info);
        } catch (IllegalArgumentException e) {System.err.println(e.getMessage());}
    }

    @Override
    public String toString() {
       return "INFORMACION:\n " + "NOMBRE: " + input.getName() + "\n" + "RUTA: " + input.getAbsolutePath() + "\n" + "TAMANO: " + input.length() + "\n" +
               "ULTIMA MODIFICACION: " + input.lastModified() + "\n" + "EXTENSION: " + getExtension() + "\n" + "TIPO: " + getFileType() + "\n";
    }
}