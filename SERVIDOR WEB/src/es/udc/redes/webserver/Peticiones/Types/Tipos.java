package es.udc.redes.webserver.Peticiones.Types;

import java.io.IOException;

public enum Tipos {

    GET("GET") {
        @Override
        public Peticion getPeticion() throws IOException {
            return new GET();
        }
    },
    HEAD("HEAD") {
        @Override
        public Peticion getPeticion() throws IOException {
            return new HEAD();
        }
    };

    String name;

    Tipos(String nme) {name = nme;}

    public abstract Peticion getPeticion() throws IOException;
}
