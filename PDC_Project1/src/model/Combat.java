/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author vishw
 */
public class Combat extends Encounter {
    
    Player player;
    Enemy enemy;
    public Item loot;
    int gainedXp;
    
    public Combat(Player player, Enemy enemy, Item loot){
        this.player = player;
        this.enemy = enemy;
        this.loot = loot;
        this.gainedXp = enemy.xp;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Item getLoot() {
        return loot;
    }
    
    
    
    @Override
    public void start(Player player){
        Combat_Menu menu = new Combat_Menu();
        menu.startCombat(this);// changed parameter requiremens for method
        player.inventory.addItem(loot);
        
        
    }
}
