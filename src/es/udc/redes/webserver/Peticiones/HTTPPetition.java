package es.udc.redes.webserver.Peticiones;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.Date;

public class HTTPPetition {

    /////////////// ATTRIBUTES ///////////////

    private File input;
    private String header;

    /////////////// CONSTRUCTOR & SETTERS ///////////////

    public HTTPPetition(String arg) throws FileNotFoundException, AccessDeniedException {
        this.input = new File("p1-files" + File.separator + arg);
        if(!input.exists()) throw new FileNotFoundException(StatusCode.NotFound.toString());
        if (!input.canRead()) throw new AccessDeniedException(StatusCode.Forbidden.toString());
    }

    public HTTPPetition(){}


    //////////////// METHODS ///////////////

    private String getExtension(){
        String name = input.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) return "SIN EXTENSION";
        return name.substring(lastIndexOf+1);
    }

    private String getFileType() {
        if (input.isDirectory()) return "DIRECTORIO";
        String extension = getExtension();
        if (extension.equals("txt") || extension.equals("log") || extension.equals("doc") || extension.equals("docx") || extension.equals("c") || extension.equals("java") || extension.equals("html")) return "text/" + extension;
        if (extension.equals("jpg") || extension.equals("png") || extension.equals("gif") || extension.equals("bmp") || extension.equals("ico")) return "image/" + extension;
        if (extension.equals("mp3")) return "audio/" + extension;
        if (extension.equals("mp4") || extension.equals("avi") || extension.equals("mov")) return "video/" + extension;
        if (extension.equals("zip") || extension.equals("rar") || extension.equals("tar") || extension.equals("gz")) return "application/" + extension;
        if (extension.equals("bin") || extension.equals("exe")) return "application/" + extension;
        if (extension.equals("SIN EXTENSION")) return "text/plain";
        return "unknown";
    }

    private boolean listDirectory(OutputStream out) {
        try {
            out.write("<html><head><title>Indice del directorio</title></head><body>".getBytes());
            out.write(("<h1>Indice del directorio " + input.getAbsolutePath() + "</h1>").getBytes());
            out.write("<ul>".getBytes());
            for (String file : input.list()) {
                File f = new File(input, file);
                out.write(("<li><a href=\"" + f.getName() + "\">" + f.getName() + "</a></li>").getBytes());
            }
            out.write("</ul></body></html>".getBytes());
            return true;
        }
        catch (IOException e) {return false;}
    }

    private boolean transferFile(OutputStream out) {
        try(FileInputStream fStream = new FileInputStream(input)) {
            int c;
            while ((c = fStream.read()) != -1) out.write(c);
            return true;
        }
        catch (FileNotFoundException e) {return false;}
        catch (IOException e) {return false;}
    }


    public String getHeader(String serverName){
        Date date = new Date(input.lastModified());
        Date today = new Date();
        header = "Date: " + today + "\n" + "Server: " + serverName + "\n" + "Content-Type: " + getFileType() + "\n" + "Content-Length: " + input.length() + "\n" + "Last-Modified: " + date + "\n" + "\n";
        return header;
    }


    public boolean sendFile(OutputStream out) throws IOException {
        if (input.isDirectory()) {
            File indexFile = new File(input, "index.html");
            if (indexFile.exists()) {
                this.input = indexFile;
                return transferFile(out);
            }
            else return listDirectory(out);
        }
        else return transferFile(out);
    }


    public void sendStatus(OutputStream out, String statusCode) throws IOException {
        out.write(statusCode.getBytes());
    }

    public void sendPetition(OutputStream out) throws IOException {
        out.write(header.getBytes());
    }

    public boolean isModified(String date){
        String[] parts = date.split("/");
        String day = parts[0];
        String month = parts[1];
        String yearHourMinute = parts[2];
        String[] yearHourMinuteParts = yearHourMinute.split("T");
        String year = yearHourMinuteParts[0];
        String hourMinute = yearHourMinuteParts[1];
        String[] hourMinuteParts = hourMinute.split(":");
        String hour = hourMinuteParts[0];
        String minute = hourMinuteParts[1];

        Date d = new Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute));
        return (input.lastModified() <= d.getTime());
    }

}
