package com.example.tratamientoxml.Actividad1;

public class Noticia {

    private String titulo;
    private String descripcion;
    private String link;
    private String categoria;



    public String getTitulo() {
        return titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getLink() {
        return link;
    }
    public String getCategoria() {
        return categoria;
    }


    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public void setLink(String link) {
        this.link = link;
    }
}
