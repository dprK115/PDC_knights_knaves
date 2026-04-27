/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author vishw
 */
public class Weapon extends Item {
    
    int attackModifier;
    
    public Weapon(String name, int attackModifier){
        super(name);
        this.attackModifier = attackModifier;
    }
    
    @Override
    public int getStat(){
        return this.attackModifier;
    }
}