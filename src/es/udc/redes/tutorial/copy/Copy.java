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
        try (BufferedReader bufferRead = new BufferedReader(new FileReader(input));
             BufferedWriter bufferWrite = new BufferedWriter(new FileWriter(output))){

            int caracter = bufferRead.read();
            while (caracter != -1) {
                bufferWrite.write(caracter);
                caracter = bufferRead.read();
            }
            System.out.println("El archivo se copió y procesó correctamente.");
        }
        catch (FileNotFoundException e) {throw new RuntimeException(e);}
        catch (IOException e) {throw new RuntimeException(e);}
    }


    //////////////// MAIN ///////////////

    public static void main(String[] args) {
        try {
            Copy copy = new Copy(args);
            copy.doCopy();
        } catch (IllegalArgumentException e) {System.err.println(e.getMessage());}
    }

}