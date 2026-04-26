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

    public static void main(String[] args) {

        Player player = new Player("Player");

        // starter item
        player.inventory.addItem(new Potion("Health Potion", 20));

        StoryManager manager = new StoryManager();

        manager.addEncounter(new Story("You wake up in a dark forest..."));
        manager.addEncounter(new Combat(player, new Enemy("Enemy", 30, 5, 5)));
        manager.addEncounter(new Story("You defeated the enemy and move forward..."));

        Encounter encounter;

        while ((encounter = manager.getNextEncounter()) != null) {
            encounter.start(player);
        }

        System.out.println("Game Over");
    }
}