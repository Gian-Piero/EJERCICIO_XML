package com.example.tratamientoxml.Actividad1;

import com.example.tratamientoxml.Actividad1.Noticia;
import com.example.tratamientoxml.Actividad1.RssHandlerNoticia;
import com.example.tratamientoxml.Actividad2.Tiempo;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RssParserSAX {

    private URL rssUrl;

    public RssParserSAX(String url) {
        try {
            this.rssUrl = new URL (url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Noticia> parse() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            RssHandlerNoticia handler = new RssHandlerNoticia();
            parser.parse(this.getInputStream(), handler);
            return handler.getNoticias();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private InputStream getInputStream (){
        try {
            return rssUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
