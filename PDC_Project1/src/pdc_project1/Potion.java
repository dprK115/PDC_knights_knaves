/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author vishw
 */
public class Potion extends Item {
    
    int healAmount;
    
    public Potion(String name, int healAmount){
        super(name);
        this.healAmount = healAmount;
    }
}