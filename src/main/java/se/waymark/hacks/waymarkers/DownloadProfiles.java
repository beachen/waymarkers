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
import static java.util.stream.Collectors.toList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import se.waymark.hacks.waymarkers.model.Profile;


/**
 *
 * @author Anders Strand
 */
public class DownloadProfiles 
{
    public static void main(String[] args) throws IOException 
    {
        // Download
        Document res = Jsoup
                .connect("http://www.waymark.se")
                .timeout(1000 * 10).get();
        
        // <div> with profiles
        List<Profile> profiles = res.select("div.consultant-container")
                .stream()
                .map(n -> {return createProfile(n);})
                .collect(toList());
        
        
        // Log to file and console
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(profiles));
        
        List<Profile> javaPros = profiles.stream()
                .filter(p -> p.getSpeciality().toLowerCase().contains("krav"))
                .collect(toList());
        
        Files.write(Paths.get("./profiles.json"), gson.toJson(profiles).getBytes("UTF-8"));
        
        System.out.println("Java pros:" + javaPros.size());
        javaPros.stream().forEach(p -> System.out.println(p.getName()));
    }

    private static Profile createProfile(Element e) {
        Profile profile = new Profile();
        profile.setName(e.select("img").attr("alt"));
        profile.setImageUrl("www.waymark.se/" + e.select("img").attr("src"));
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
