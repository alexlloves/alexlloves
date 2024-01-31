/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package webscraping2.ej8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author node
 */
public class Main {
    ////*[@id="hoy"]/ul/li[1]/div/div/a
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        
         HttpClient httpClient = HttpClient.newBuilder()
                .proxy(ProxySelector.of(
                        new InetSocketAddress("192.168.0.11", 3128)))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://www.rtve.es/play/audios/boletines-rne/"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient
                .send(request, HttpResponse.BodyHandlers.ofString());
         String html = response.body();
        Document doc = Jsoup.parse(html);
        //PRIMER AUDIO INDIVIDUAL
        Elements audio = doc.selectXpath("//*[@id=\"hoy\"]/ul/li[1]/div/div/a");
        
    }
}
