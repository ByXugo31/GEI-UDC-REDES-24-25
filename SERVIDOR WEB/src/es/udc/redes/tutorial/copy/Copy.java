package es.udc.redes.tutorial.copy;

import es.udc.redes.Utilities;
import java.io.*;

public class Copy {

    /////////////// ATTRIBUTES ///////////////

    private static final String ERROR_ARGS = "ERROR EN LOS PARAMETROS <FICHERO ORIGEN> <FICHERO DESTINO>";
    private static final int NUM_ARGS = 2;

    private String input;
    private String output;


    /////////////// CONSTRUCTOR ///////////////

    public Copy(String[] args) {
        if(!Utilities.verifyArgs(args, NUM_ARGS)){
            this.input = args[0];
            this.output = args[1];
        } else throw new IllegalArgumentException(ERROR_ARGS);
    }

    //////////////// METHODS ///////////////

    public void doCopy(){
        try (FileInputStream fileInput = new FileInputStream(input);
             FileOutputStream fileOutput = new FileOutputStream(output)){
            byte[] bytes = fileInput.readAllBytes();
            fileOutput.write(bytes);
            System.out.println("El archivo se copio  y proces  correctamente.");
        }
        catch (FileNotFoundException e) {System.err.println(e.getMessage());}
        catch (IOException e) {System.err.println(e.getMessage());}
    }

    //////////////// MAIN ///////////////

    public static void main(String[] args) {
        try {
            Copy copy = new Copy(args);
            copy.doCopy();
        } catch (IllegalArgumentException e) {System.err.println(e.getMessage());}
    }

}