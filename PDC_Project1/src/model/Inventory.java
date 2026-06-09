/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lukea
 */

import java.util.ArrayList;
import java.io.Serializable;

public class Inventory implements Serializable{
    private static final long serialVersionUID = 1L;
    public ArrayList<Item> items = new ArrayList<>();
    
    public Inventory() {
        
    }
    
    public ArrayList getItems(){
        return items;
    }
    
    public Inventory(ArrayList<Item> savedInventory){
        items.addAll(savedInventory);
    }
    
    public void addItem(Item item){
        items.add(item);
        System.out.println("You picked up " + item.name);
    }
    
    public void removeItem(Item item){
        items.remove(item);
    }
    
    public void printInventory(){
        for(int i = 0; i < items.size(); i++){
            System.out.println(items.get(i).name + "(" + i + ")");
        }
    }
    
    
    
    
     
}
