package es.udc.redes.webserver.Peticiones.Types;

import es.udc.redes.webserver.Files.DynamicHTML;
import es.udc.redes.webserver.Files.ProcessedFile;
import es.udc.redes.webserver.Peticiones.Header;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public abstract class Peticion {

    //////////////// ATRIBUTOS ///////////////

    Header header;

    ProcessedFile file;

    OutputStream out;

    Date isModifiedSince;

    String serverName;



    //////////////// GETTERS Y SETTERS ///////////////

    protected void setHeader(Header header) {this.header = header;}

    protected Header getHeader() {return header;}

    protected OutputStream getOut() {return out;}

    protected ProcessedFile getFile() {return file;}

    protected String getServerName(){return serverName;}

    protected Date getIsModifiedSince(){return isModifiedSince;}



    //////////////// METODOS DE CLASE ///////////////

    private Date parseDate(String date) {
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
        return d;
    }

    protected boolean isModifiedSince() {
        return file.getLastModified().after(isModifiedSince);
    }

    protected void buildCommonAtributes(String serverName, File file, OutputStream out, String ifModifiedSince) throws IOException {
        this.serverName = serverName;
        this.file =  ProcessedFile.processType(file);
        this.out = out;
        if(ifModifiedSince != null) this.isModifiedSince = parseDate(ifModifiedSince);
    }

    @Override
    public String toString(){
        if (header != null) return header.toString();
        else return "NO HEADER";
    }



    //////////////// METODOS ABSTRACTOS ///////////////

    public abstract void sendPeticion() throws IOException;

    public abstract void buildPetition(boolean allowDirectoryListing, String serverName, File file, String ifModifiedSince, OutputStream out) throws IOException;

}
