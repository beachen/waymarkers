/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.waymark.hacks.waymarkers;

/**
 *
 * @author Anders Strand
 */
class Stock {

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
