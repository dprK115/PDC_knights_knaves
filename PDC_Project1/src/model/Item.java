/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lukea
 */
import java.io.Serializable;

public abstract class Item implements Serializable{
    private static final long serialVersionUID = 1L;
    public String name;
    public int id;
    
    public Item(String name){
    this.name = name;
}
    public abstract int getStat();

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
    
   
}