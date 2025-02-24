package es.udc.redes.webserver.Files;

import java.io.*;
import java.util.Date;

public class DynamicHTML extends ProcessedFile {

    //////////////// CONSTRUCTOR //////////////

    public DynamicHTML(File input) {
        super(input);
        setExtension("html");
        setType("text");
        setLastModified(new Date());
        readContent();
    }



    //////////////// METODOS //////////////

    @Override
    protected void readContent() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html lang=\"es\">\n<head>\n<meta charset=\"UTF-8\">\n")
                .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
                .append("<title>Listado del directorio ").append(getInput().getName()).append("</title>\n")
                .append("<style>\nbody { font-family: Arial, sans-serif; margin: 0; padding: 20px; }\n")
                .append("h1 { color: #333; }\nul { list-style-type: none; padding: 0; }\n")
                .append("li { margin-bottom: 10px; }\na { color: #0066cc; text-decoration: none; }\n")
                .append("a:hover { text-decoration: underline; }\n</style>\n</head>\n<body>\n")
                .append("<h1>Listado del directorio: ").append(getInput().getName()).append("</h1>\n<ul>\n");

        for (File file : getInput().listFiles()) {
            html.append("<li><a href=\"").append(getInput().getName()).append("/").append(file.getName())
                    .append("\">").append(file.getName()).append(file.isDirectory() ? "/" : "").append("</a></li>\n");
        }

        html.append("</ul>\n</body>\n</html>");
        setLength(html.length());
        setContentLines(html.toString().getBytes());
    }
}