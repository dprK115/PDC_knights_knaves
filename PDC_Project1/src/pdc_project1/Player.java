/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author lukea
 */
public class Player extends Character implements CanEquip {
    
    Inventory inventory = new Inventory();
    Item equippedArmor;
    Item equippedWeapon;
    
    
    public Player(String name) {
        super(name);
        this.maxHealth = 50;
        this.health = maxHealth;
        this.attack = 10;
        this.defense = 10; 
    }
       
    public void levelUp(){
        this.maxHealth += 100;
        this.attack += 10;
        this.defense +=10;
    }
    
    @Override
    public void attack(Character Enemy){
        Enemy.health -= this.attack;
    }
    @Override
    public void defend(){
        
        this.defense += (this.defense * 2);
    }
    @Override
    public void undefend(){
        this.defense -= (this.defense / 2); 
    }
    
    @Override
    public void equip(int index){
        if (inventory.items.get(index) instanceof Armor){
            equippedArmor = inventory.items.get(index);
        }else if (inventory.items.get(index) instanceof Weapon){
            equippedWeapon = inventory.items.get(index);
        }
    }
    
    
    
    
}