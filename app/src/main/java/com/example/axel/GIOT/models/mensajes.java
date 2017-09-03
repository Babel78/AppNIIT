package com.example.axel.GIOT.models;

/**
 * Created by Axel on 25/08/2017.
 */

public class mensajes {

    String mensaje;
    String date;

    public mensajes(String mensaje, String date) {
        this.mensaje = mensaje;
        this.date = date;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
