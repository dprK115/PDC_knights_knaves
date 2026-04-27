/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author vishw
 */
public class Game {
    
    static Player player;
    static StoryManager manager = new StoryManager();
    static SaveManager sm = new SaveManager();
    static MainMenu mainMenu = new MainMenu();
    boolean loadedGameState = false;

    public static void main(String[] args) {
        
        mainMenu.mainMenuStart();
        
        

        // starter item
        player.inventory.addItem(new Potion("Health Potion", 20));

        

        manager.addEncounter(new Story("You wake up in a dark forest..."));
        manager.addEncounter(new Combat(player, new Enemy("Enemy", 30, 5, 5), new Armor("Armor of Sir Jean Paul Gautier", 25 )));
        manager.addEncounter(new Story("You defeated the enemy and move forward..."));

        Encounter encounter;

        while ((encounter = manager.getNextEncounter()) != null) {
            encounter.start(player);
        }

        System.out.println("Game Over");
    }
    
    public void quit(){
        System.out.println("Closing game, Thanks for Plaing");
        System.exit(0);
    }
    
 
}