/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.waymark.hacks.waymarkers;

import se.waymark.hacks.waymarkers.model.Stock;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import static java.util.stream.Collectors.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 *
 * @author Anders Strand
 */
public class DownloadLargeCap 
{
    public static void main(String[] args) throws IOException 
    {
        // Download
        Document doc = Jsoup
                .connect("http://www.di.se/borssidor/large-cap/")
                .timeout(1000 * 10).get();
        
        // Get stocks
        List<Stock> stocks = getStocks(doc);
        
        // Log to file and console
        logStocks(stocks);
    }

    private static void logStocks(List<Stock> stocks) throws IOException {
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String stocksJson = gson.toJson(stocks);
        System.out.println(stocksJson);
        System.out.println("Number of stocks on LargeCap:" + stocks.size());
        
        // Write to file
        Files.write(Paths.get("./largeCap.json"), stocksJson.getBytes("UTF-8"));
    }

    private static List<Stock> getStocks(Document document) {

        // All even and odd rows (, or)
        Elements stocks = document.select("tbody tr.odd, tr.even");

        return stocks.stream()
                .map(s -> {
                    return new Stock(
                            s.select("td.fh-align-left span").get(0).text(),
                            s.select("td.nowrap").get(0).text());
                }).collect(toList());
    }
}