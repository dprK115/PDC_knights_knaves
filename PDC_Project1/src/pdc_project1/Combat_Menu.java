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

public class Combat_Menu {
    
    Scanner scanner = new Scanner(System.in);
    
    public void startCombat(Player player, Enemy enemy){
    
        while(player.health > 0 && enemy.health > 0){
            
            System.out.println("\n--- COMBAT ---");
            System.out.println(player.name + " Health: " + player.health + "/" + player.maxHealth);
            System.out.println(enemy.name + " Health: " + enemy.health + "/" + enemy.maxHealth);
            
            System.out.println("\n1. Attack");
            System.out.println("2. Defend");
            System.out.println("3. Use Item");
            
            int choice = scanner.nextInt();
            
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
                
                int index = scanner.nextInt();
                
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
            
            player.undefend();
        }
        
        if(player.health < 0){
            System.out.println("You lost");
        }
        
    }
}
