/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author lukea
 */
import java.io.Serializable;

public abstract class Character implements Serializable {
    private static final long serialVersionUID = 1L;
    public String name = "";
    public int health;
    public int attack;
    public int defense;
    public int maxHealth;
    public int xp; // adding xp to all characters
    int level; // adding levels to all characters
    static DifficultySet difficulty = Game.difficultySet; // adding difficulty to game
    
    public Character(String name) {
        this.name = name;
    }
    
    public abstract void attack(Character Enemy);
    public abstract void defend();
    public abstract void undefend();
    
    
    
    
    
}
