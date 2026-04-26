/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author lukea
 */
public abstract class Character {
    
    public String name = "";
    public int health;
    public int attack;
    public int defense;
    public int maxHealth;
    
    public Character(String name) {
        this.name = name;
    }
    
    public abstract void attack(Character Enemy);
    public abstract void defend();
    public abstract void undefend();
    
    
    
    
    
}
