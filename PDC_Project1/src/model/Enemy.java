package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lukea
 */
public class Enemy extends Character{
    
    public Enemy(String name, int level){
        super(name);
        this.maxHealth = level * difficulty.enemyModifier / 2;
        this.health = this.maxHealth;
        this.attack = level * 5;
        this.defense = level * 2;
        this.level = level;
        this.xp = level * difficulty.xpModifier;
    }

    public int getLevel() {
        return level;
    }
    
    
    @Override
    public void attack(Character Enemy){
        int damage = this.attack - Enemy.defense;
        
        if(damage < 1){
            damage = 1;
        }
        
        Enemy.health -= damage;
    }
    
    @Override
    public void defend(){
        this.defense += 5;
    }
    
    @Override
    public void undefend(){
        this.defense -= 5; 
    }
    
    
    
}
