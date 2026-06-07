/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

/**
 *
 * @author lukea
 */
import pdc_project1.Item;
import pdc_project1.Weapon;
import pdc_project1.Armor;
import pdc_project1.Potion;


public class ItemSQLAdapter {
    
    private Item item;

    public ItemSQLAdapter(Item item) {
        this.item = item;
    }

    public ItemSQLAdapter() {
    }
    
    
    
    public void setItem(Item item){
        this.item = item;
    }
    
    public String getName() {
        return item.name;
    }
    
    public int getID(){
        return item.id;
    }

    public String getItemType() {
        if (item instanceof Weapon) {
            return "WEAPON";
        }

        if (item instanceof Armor) {
            return "ARMOR";
        }

        if (item instanceof Potion) {
            return "POTION";
        }

        throw new IllegalArgumentException("Unknown item type: " + item.getClass().getName());
    }

    public int getAttackModifier() {
        if (item instanceof Weapon weapon ) {
            return weapon.getStat();
        }

        return 0;
    }

    public int getDefenseModifier() {
        if (item instanceof Armor armor) {
            return armor.getStat();
        }

        return 0;
    }

    public int getHealAmount() {
        if (item instanceof Potion potion) {
            return potion.getStat();
        }

        return 0;
    }

}
    
    

