package es.udc.redes.webserver.Peticiones;

public enum StatusCode {
    BadRequest("\u001B[31m[-] HTTP/1.0 400 BAD REQUEST \u001B[0m"),
    NotFound("\u001B[31m[-] HTTP/1.0 404 NOT FOUND \u001B[0m"),
    OK("\u001B[32m[+] HTTP/1.0 200 OK \u001B[0m"),
    Forbidden("\u001B[31m[-] HTTP/1.0 403 FORBIDDEN \u001B[0m"),
    NotModified("\u001B[33m[-] HTTP/1.0 304 NOT MODIFIED \u001B[0m"),
    InternalError("\u001B[31m[-] HTTP/1.0 500 INTERNAL SERVER ERROR \u001B[0m");

    private final String message;

    StatusCode(String message) {this.message = message;}

    @Override
    public String toString(){return this.message;}
}
