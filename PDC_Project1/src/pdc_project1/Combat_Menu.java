/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author vishw
 */

import java.util.Scanner;
// combat_menu extends Game now to use the same scanner that is opened in Game.java
public class Combat_Menu extends Game {
    
    Character enemy;
    Player player;
    // changed parameter for this method to allow the use of the player and enemy objects already stored in Combat object.
    public void startCombat(Combat combat){
    
        this.enemy = combat.enemy;
        this.player = combat.player;
        while(player.health > 0 && enemy.health > 0){
            
            System.out.println("\n--- COMBAT ---");
            System.out.println(player.name + " Health: " + player.health + "/" + player.maxHealth);
            System.out.println(enemy.name + " Health: " + enemy.health + "/" + enemy.maxHealth);
            
            System.out.println("\n1. Attack");
            System.out.println("2. Defend");
            System.out.println("3. Use Item");
            
            int choice = input.nextInt();
            
            if(choice == 1){
                player.attack(enemy);
                System.out.println("You attacked the enemy");
                
                if(enemy.health <= 0){
                    break;
                }
            }
            else if(choice == 2){
                player.defend();
                System.out.println("You are defending");
            }
            else if(choice == 3){
                player.inventory.printInventory();
                System.out.println("Choose item index:");
                
                int index = input.nextInt();
                
                if(index >= 0 && index < player.inventory.items.size()){
                    
                    Item item = player.inventory.items.get(index);
                    
                    if(item instanceof Potion){
                        player.use(index);
                    }
                    else{
                        System.out.println("Item cannot be used");
                    }
                }
                else{
                    System.out.println("Invalid index");
                }
            }
            else{
                System.out.println("Invalid choice");
            }
            
            if(enemy.health > 0){
                enemy.attack(player);
                System.out.println("Enemy attacks");
                
                if(player.health <= 0 || enemy.health <= 0){
                    break;
                }
            }
            if (choice == 2){
                player.undefend();
            }
            
        }
        player.xp += combat.gainedXp;
        System.out.println("you defeated " + enemy.name + " and move forward");
        player.levelUp();
        
        if(player.health < 0){
            System.out.println("You lost");
        }
        
    }
}
