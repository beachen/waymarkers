/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.waymark.hacks.waymarkers.model;

/**
 *
 * @author Anders Strand
 */
public class Stock {

    private final String name;
    private final String price;

    public Stock(String name, String price) {
    
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + ":" + price;
    }
    
    
    
    
    
}
