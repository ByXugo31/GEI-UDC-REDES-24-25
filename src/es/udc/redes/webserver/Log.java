package es.udc.redes.webserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Log {
    File log;

    Log(String file) {
        this.log = new File(file);
        if (!log.exists()) {
            try {log.createNewFile();}
            catch (IOException e) {System.err.println("Error creando el fichero de log: " + e.getMessage());}
        }
    }

    public void storeLog(String message) throws FileNotFoundException,IOException {
        try (FileOutputStream out = new FileOutputStream(log, true)) {
            String head = "------------------------------------\n";
            out.write(head.getBytes());
            out.write(message.getBytes());
        }
        catch (FileNotFoundException e) {throw e;}
        catch (IOException e) {throw e;}
    }
}
