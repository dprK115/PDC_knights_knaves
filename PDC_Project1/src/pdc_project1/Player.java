/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author lukea
 */

public class Player extends Character implements CanEquip, CanUse {
    
    Inventory inventory = new Inventory();
    Item equippedArmor;
    Item equippedWeapon;
    
    public Player(String name) {
        super(name);
        this.maxHealth = 50;
        this.health = maxHealth;
        this.attack = 10;
        this.defense = 5; 
    }
       
    public void levelUp(){
        this.maxHealth += 100;
        this.attack += 10;
        this.defense +=10;
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
    
    @Override
    public void equip(int index){
        if (inventory.items.get(index) instanceof Armor){
            equippedArmor = inventory.items.get(index);
        } else if (inventory.items.get(index) instanceof Weapon){
            equippedWeapon = inventory.items.get(index);
        }
    }
    
    @Override
    public void use(int index){
        
        Item item = inventory.items.get(index);
        
        if(item instanceof Potion){
            Potion potion = (Potion)item;
            
            this.health += potion.healAmount;
            
            if(this.health > this.maxHealth){
                this.health = this.maxHealth;
            }
            
            System.out.println("You used " + potion.name + " and healed " + potion.healAmount);
            
            inventory.removeItem(item);
        }
        else{
            System.out.println("Item cannot be used");
        }
    }
}