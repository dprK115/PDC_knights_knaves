/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author vishw
 */
public class Weapon extends Item {
    
    int attackModifier;
    
    public Weapon( String name, int id, int attackModifier){
        super(name);
        this.id = id;
        this.attackModifier = attackModifier;
    }
    
    @Override
    public int getStat(){
        return this.attackModifier;
    }
}