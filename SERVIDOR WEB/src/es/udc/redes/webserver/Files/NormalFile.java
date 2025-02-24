package es.udc.redes.webserver.Files;

import java.io.*;
import java.util.Date;

public class NormalFile extends ProcessedFile {

    //////////////// ATRIBUTOS //////////////

    private String name;

    private String path;



    //////////////// CONSTRUCTOR //////////////

    public NormalFile(File file) throws IOException {
        super(file);
        setName(file.getName());
        setPath(file.getPath());
        setExtension(returnExtension());
        setType(getFileType());
        setLastModified(new Date(file.lastModified()));
        setLength(file.length());
        readContent();
    }



    //////////////// GETTERS & SETTERS //////////////

    public void setPath(String path) {this.path = path;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}



    //////////////// METODOS //////////////

    private String returnExtension(){
        String name = getInput().getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) return "plain";
        return name.substring(lastIndexOf+1);
    }

    private String getFileType() {
        String extension = getExtension();
        if (extension.equals("txt") || extension.equals("log") || extension.equals("js") || extension.equals("css") || extension.equals("c") || extension.equals("java") || extension.equals("html")) return "text";
        if (extension.equals("jpg") || extension.equals("png") || extension.equals("gif") || extension.equals("bmp") || extension.equals("ico")) return "image";
        if (extension.equals("mp3")) return "audio/" + extension;
        if (extension.equals("mp4") || extension.equals("avi") || extension.equals("mov")) return "video";
        if (extension.equals("zip") || extension.equals("rar") || extension.equals("tar") || extension.equals("gz")) return "application";
        if (extension.equals("bin") || extension.equals("exe")) return "application";
        if (extension.equals("plain")) return "text";
        return "unknown";
    }

    @Override
    protected void readContent() throws IOException {
        try (FileInputStream reader = new FileInputStream(getInput())) {
            byte[] bytes = reader.readAllBytes();
            setContentLines(bytes);
        }
    }
}