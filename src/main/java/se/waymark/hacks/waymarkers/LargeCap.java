/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.waymark.hacks.waymarkers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 *
 * @author Anders Strand
 */
public class LargeCap 
{
    public static void main(String[] args) throws IOException 
    {
        // Download
        Document res = Jsoup
                .connect("http://www.di.se/borssidor/large-cap/")
                .timeout(1000 * 10).get();
        
        List<Stock> stocks = getStocks(res);
        stocks.stream().forEach(e -> System.out.println(e.toString()));
        System.out.println("Stocks:" + stocks.size());
        
        // Log to file and console
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
       
        Files.write(Paths.get("./largeCap.json"), gson.toJson(stocks).getBytes("UTF-8"));
        
    }

    private static List<Stock> getStocks(Document res) {
        // <div> with profiles
        Elements stocks = res.select("tbody tr.even,odd");
        System.out.println(stocks.size());
        List<Stock> st =
                stocks.stream().map( s ->
                {
                    return  new Stock(s.select("td.fh-align-left span").get(0).text(), s.select("td.nowrap").get(0).text());
                }).collect(
                        Collectors.toList());
        return st;
    }

    private static Profile createProfile(Element e) {
        Profile profile = new Profile();
        profile.setName(e.select("img").attr("alt"));
        
        Elements info = e.select("p");
        profile.setSummary(info.get(0).text());
        profile.setYearOfBirth(parse(info.get(1).text()));
        profile.setEducation(parse(info.get(2).text()));
        profile.setSpeciality(parse(info.get(3).text()));
   
        return profile;
    }

    private static String parse(String text) 
    {
        String[] values = text.split(":");
        return values[1].trim();
    }
}