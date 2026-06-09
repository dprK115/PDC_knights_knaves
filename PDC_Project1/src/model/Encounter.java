/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author vishw
 */
import java.io.Serializable;

public abstract class Encounter implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    public abstract void start(Player player);

    public int getID() {
        return id;
    }
    
    public void setID(int id){
        this.id = id;
    }
    
}


