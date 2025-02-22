package es.udc.redes.webserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Log {
    File log;

    Log(String file) throws IOException {
        this.log = new File(file);
        if (!log.exists()) log.createNewFile();
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
