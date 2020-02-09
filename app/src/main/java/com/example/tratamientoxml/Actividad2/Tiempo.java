package com.example.tratamientoxml.Actividad2;

public class Tiempo {
    private String provincia;
    private String dia;
    private String estadoCielo;
    private String temperatura_min;
    private String temperatura_max;





    public String getDia() {
        return dia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setEstadoCielo(String estadoCielo) {
        this.estadoCielo = estadoCielo;
    }

    public void setTemperatura_min(String temperatura_min) {
        this.temperatura_min = temperatura_min;
    }

    public String getEstadoCielo() {
        return estadoCielo;
    }

    public String getTemperatura_min() {
        return temperatura_min;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getTemperatura_max() {
        return temperatura_max;
    }

    public void setTemperatura_max(String temperatura_max) {
        this.temperatura_max = temperatura_max;
    }
}
