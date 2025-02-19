package es.udc.redes.webserver.Peticiones;

public enum StatusCode {
    BadRequest("HTTP/1.0 400 BAD REQUEST\n"),
    NotFound("HTTP/1.0 404 NOT FOUND\n"),
    OK("HTTP/1.0 200 OK\n"),
    Forbidden("HTTP/1.0 403 FORBIDDEN\n"),
    NotModified("HTTP/1.0 304 NOT MODIFIED\n"),
    InternalError("HTTP/1.0 500 INTERNAL SERVER ERROR\n");

    private final String message;

    StatusCode(String message) {this.message = message;}

    @Override
    public String toString(){return this.message;}
}
