/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author vishw
 */
public class Potion extends Item {
    
    int healAmount;
    
    public Potion(String name, int id, int healAmount){
        super(name);
        this.id = id;
        this.healAmount = healAmount;
    }
    
    @Override
    public int getStat(){
        return healAmount;
    }
}