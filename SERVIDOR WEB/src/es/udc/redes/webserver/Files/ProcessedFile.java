package es.udc.redes.webserver.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public abstract class ProcessedFile {

    //////////////// ATRIBUTOS //////////////

    private File input;

    private String extension;

    private String type;

    private Date lastModified;

    private long length;

    private String contentLines;



    //////////////// CONSTRUCTOR //////////////

    public ProcessedFile(File file) {this.input = file;}



    //////////////// GETTERS & SETTERS //////////////

    public File getInput() {return input;}

    public String getExtension() {return extension;}

    protected void setExtension(String extension) {this.extension = extension;}

    public String getType() {return type;}

    protected void setType(String type) {this.type = type;}

    public Date getLastModified() {return lastModified;}

    protected void setLastModified(Date lastModified) {this.lastModified = lastModified;}

    public long getLength() {return length;}

    protected void setLength(long length) {this.length = length;}

    protected void setContentLines(String contentLines) {this.contentLines = contentLines;}



    //////////////// METODOS ABSTRACTOS //////////////

    protected abstract void readContent() throws IOException;



    //////////////// METODOS PUBLICOS //////////////

    public static ProcessedFile processType(File file) throws IOException {
        if (!file.exists()) throw new FileNotFoundException();
        if(file.isDirectory()){
            File indexFile = new File(file, "index.html");
            if (indexFile.exists()) return new NormalFile(indexFile);
            return new DynamicHTML(file);
        }
        return new NormalFile(file);
    }

    public void sendFile(OutputStream out) throws IOException {
        out.write(contentLines.getBytes());
        out.flush();
    }

    @Override
    public String toString() {
        return "Content-Type: " + getType() + "/" + getExtension() + "\n" + "Content-Length: " + getLength() + "\n" + "Last-Modified: " + getLastModified() + "\n";
    }

}
