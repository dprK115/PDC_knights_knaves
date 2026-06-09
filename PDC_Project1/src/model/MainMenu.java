/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lukea
 */
import java.util.ArrayList;

public class MainMenu extends Game {

    public void mainMenuStart() {

        while (true) {
            System.out.println("-----Knights And Knaves-----");
            System.out.println("\n1. New Game");
            System.out.println("2. Load Game");
            System.out.println("3. Quit and Save Game");

            int userInput = input.nextInt();

            switch (userInput) {
                case 1:
                    CharacterCreation cc = new CharacterCreation();
                    player = cc.createMenu();
                    break;
                case 2:
                    System.out.println("loading previous save files");
                    ArrayList<Player> saveList = (ArrayList) sm.getSaveList();
                    System.out.println("Number of saves found: " + saveList.size());

                    if (saveList.isEmpty()) {
                        System.out.println("No save files found.");
                        break;
                    }
                    for (Player players : saveList) {
                        System.out.println("Character Name: " + players.name + " Save Number:" + players.id);
                    }
                    userInput = input.nextInt();

                    GameState loadedState = SaveManager.loadGame(userInput);
                    if (loadedState != null) {
                        player = loadedState.getPlayer();
                        manager = loadedState.getStoryManager();
                        System.out.println("Succesfully loaded Game");
                    }
                    loadedGameState = true;
                    break;
                case 3:
                    GameState gameState = new GameState(player, manager);
                    sm.saveGame(gameState);
                    quit();
                    break;
                default:
                    System.out.println("Error! Invalid Input\nPlease enter a number corresponding to a menu option");
            }
            
            break;

        }

    }

}
