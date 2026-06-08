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
    static DifficultySet difficulty; // adding difficulty to game
    
    
    public Character(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDifficulty(int difficultyValue){
        difficulty = new DifficultySet(difficultyValue);
        
    }
    
    public abstract void attack(Character Enemy);
    public abstract void defend();
    public abstract void undefend();
    
    
    
    
    
}
