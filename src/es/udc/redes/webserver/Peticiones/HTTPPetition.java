package es.udc.redes.webserver.Peticiones;

import es.udc.redes.Utilities;
import es.udc.redes.tutorial.info.Info;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.Date;

public class HTTPPetition {

    /////////////// ATTRIBUTES ///////////////

    private File input;
    private String header;


    /////////////// CONSTRUCTOR & SETTERS ///////////////

    public HTTPPetition(String arg) throws FileNotFoundException, AccessDeniedException {
        this.input = new File(arg);
        if(!input.exists()) throw new FileNotFoundException(StatusCode.NotFound.toString());
        if (!input.canRead()) throw new AccessDeniedException(StatusCode.Forbidden.toString());
    }


    //////////////// METHODS ///////////////

    private String getExtension(){
        String name = input.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) return "SIN EXTENSION";
        return name.substring(lastIndexOf);
    }

    private String getFileType() {
        if (input.isDirectory()) return "DIRECTORIO";
        String extension = getExtension();
        if (extension.equals(".txt") || extension.equals(".log") || extension.equals(".doc") || extension.equals(".docx") || extension.equals(".c") || extension.equals(".java") || extension.equals(".html")) return "TEXTO";
        if (extension.equals(".jpg") || extension.equals(".png") || extension.equals(".gif") || extension.equals(".bmp") || extension.equals(".ico")) return "IMAGEN";
        if (extension.equals(".mp3")) return "AUDIO";
        if (extension.equals(".mp4") || extension.equals(".avi") || extension.equals(".mov")) return "VIDEO";
        if (extension.equals(".zip") || extension.equals(".rar") || extension.equals(".tar") || extension.equals(".gz")) return "FICHERO COMPRIMIDO";
        if (extension.equals(".bin") || extension.equals(".exe")) return "BINARIO";
        if (extension.equals("SIN EXTENSION")) return "TEXTO";
        return "EXTENSION DESCONOCIDA O FICHERO SIN EXTENSION";
    }

    public String getHeader(){
        Date date = new Date(input.lastModified());
        return "    NOMBRE: " + input.getName() + "\n" + "    RUTA: " + input.getAbsolutePath() + "\n" + "    TAMANO: " + input.length() + " bytes" + "\n" +
                "    ULTIMA MODIFICACION: " + date + "\n" + "    EXTENSION: " + getExtension() + "\n" + "    TIPO: " + getFileType() + "\n";
    }

    public void sendFile(OutputStream out) throws IOException {
        try(FileInputStream fStream = new FileInputStream(input)) {
            int c;
            while ((c = fStream.read()) != -1) out.write(c);
        }
        catch (FileNotFoundException e) {throw new FileNotFoundException(StatusCode.NotFound.toString());}
        catch (IOException e) {throw new IOException(StatusCode.InternalError.toString());}
    }

}
