package com.example.tratamientoxml.Actividad2;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;

import com.example.tratamientoxml.Actividad1.Noticia;

import org.xml.sax.Attributes;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RssParserSAXSimplificado {

    private URL rssUrl;
    private Tiempo tiempoActual;
    private String provinciaActual;

    public RssParserSAXSimplificado(String url) {
        try {
            this.rssUrl = new URL (url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Tiempo> parse() {
        final List<Tiempo> tiempos = new ArrayList<Tiempo>();
        RootElement root = new RootElement("root");
        Element origen = root.getChild("origen");

        Element provincia = origen.getChild("provincia");

        Element prediccion = root.getChild("prediccion");
        Element dia = prediccion.getChild("dia");
        Element temperatura = dia.getChild("temperatura");

        provincia.setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                provinciaActual = body;
            }
        });

        dia.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                tiempoActual = new Tiempo();
                tiempoActual.setDia(attributes.getValue("fecha"));
                tiempoActual.setProvincia(provinciaActual);
            }
        });
        dia.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                tiempos.add(tiempoActual);
            }
        });

        dia.getChild("estado_cielo ").setEndTextElementListener(
                new EndTextElementListener() {
                    @Override
                    public void end(String body) {
                        tiempoActual.setEstadoCielo(body);
                    }
                });

        temperatura.getChild("maxima").setEndTextElementListener(
                new EndTextElementListener() {
                    @Override
                    public void end(String body) {
                        tiempoActual.setTemperatura_max(body);
                    }
                });

        temperatura.getChild("minima").setEndTextElementListener(
                new EndTextElementListener() {
                    @Override
                    public void end(String body) {
                        tiempoActual.setTemperatura_min(body);
                    }
                });

        try {
            Xml.parse(this.getInputStream(),
                    Xml.Encoding.UTF_8,
                    root.getContentHandler());
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return tiempos;
    }

    private InputStream getInputStream() {
        try {
            return rssUrl.openConnection().getInputStream();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
