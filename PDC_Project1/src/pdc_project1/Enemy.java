package pdc_project1;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lukea
 */
public class Enemy extends Character{
    
    public Enemy(String name, int health, int attack, int defense){
        super(name);
        this.maxHealth = health;
        this.health = this.maxHealth;
        this.attack = attack;
        this.defense = defense;
    }
    
    @Override
    public void attack(Character Enemy){
        int damage = this.attack - Enemy.defense;
        
        if(damage < 0){
            damage = 0;
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
