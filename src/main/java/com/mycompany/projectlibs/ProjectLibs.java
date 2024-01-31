/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.projectlibs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author node
 */
public class ProjectLibs {

    public final static String URL = "https://www.scrapethissite.com/pages/simple/";

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .proxy(ProxySelector.of(
                        new InetSocketAddress("192.168.0.11", 3128)))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://www.scrapethissite.com/pages/simple/"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient
                .send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println("Codigo de estado: "+response.statusCode());
        //System.out.println("Cuerpo de respuesta: "+response.body());
        String html = response.body();
        Document doc = Jsoup.parse(html);
        //PAIS INDIVIDUAL
        //Elements paises = doc.selectXpath("//*[@id=\"countries\"]");
//        ArrayList<Pais> paisesLista = new ArrayList();
//        for (Element p : paises) {
//            String nombre = p.getElementsByClass("country-name").get(0).text();
//            String capital = p.getElementsByClass("country-capital").get(1).text();
//            Double area = Double.parseDouble(p.getElementsByClass("country-area").get(2).text());
//            int poblacion = Integer.parseInt(p.getElementsByClass("country-population").get(3).text());
//            paisesLista.add(new Pais(nombre, capital, area, poblacion));
//        }
//
//        for (Pais p : paisesLista) {
//            System.out.println(p);
//        }

        //TODOS LOS PAISES
        List<Pais> paises = new ArrayList<>();
        String[] parser = response.body().split("<div class=\"col-md-4 country\">");

        for (int i = 1; i < parser.length; i++) {
            int inicio, fin;
            //Pais
            inicio = parser[i].indexOf("</i>") + 5;
            fin = parser[i].indexOf("</h3>");
            String pais = parser[i].substring(inicio, fin).trim();
            //Capital
            inicio = parser[i].indexOf("<span class=\"country-capital\">") + 30;
            fin = parser[i].indexOf("</span>");
            String capital = parser[i].substring(inicio, fin);
            //Poblacion
            inicio = parser[i].indexOf("<span class=\"country-population\">") + 33;
            fin = parser[i].indexOf("</span>", inicio);
            Integer poblacion = Integer.valueOf(parser[i].substring(inicio, fin));
            //Area
            inicio = parser[i].indexOf("<span class=\"country-area\">") + 27;
            fin = parser[i].indexOf("</span>", inicio);
            Double area = Double.valueOf(parser[i].substring(inicio, fin));
            paises.add(new Pais(pais, capital, area, poblacion));

        }
        
        for (Pais p : paises) {
            System.out.println(p);
        }

    }
}
