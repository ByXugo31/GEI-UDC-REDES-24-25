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

    public void setInput(String input){this.input = input;}
    public void setOutput(String output){this.output = output;}


    //////////////// METHODS ///////////////


    public void doCopy(){
        try (FileInputStream fileInput = new FileInputStream(input);
             FileOutputStream fileOutput = new FileOutputStream(output)){

            byte[] buffer = new byte[1024];
            int nBytesLeidos = fileInput.read(buffer);
            while (nBytesLeidos != -1) {
                fileOutput.write(buffer, 0, nBytesLeidos);
                nBytesLeidos = fileInput.read(buffer);
            }
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