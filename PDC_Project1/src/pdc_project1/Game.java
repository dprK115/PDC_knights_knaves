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
import database.DBManager;
import database.DBInitialiser;



public class Game {

    static Player player;
    public static StoryManager manager;
    static SaveManager sm = new SaveManager();
    static MainMenu mainMenu = new MainMenu();
    static GameMenu gameMenu = new GameMenu();
    static boolean loadedGameState = false;
    Encounter encounter;
    Scanner input = new Scanner(System.in);
    static DifficultySet difficultySet;

    public static void main(String[] args) {
        DBManager dbm = new DBManager();
        //dbm.clearAllTables();
        DBInitialiser dbi = new DBInitialiser(dbm);
        //dbi.createTables();
        
        player = new Player("default");

        GameInit gi = new GameInit(dbm, player);
        gi.initialiseAndSave();
        
        manager = new StoryManager(gi.getGameEncounters());
        
        difficultySet = new DifficultySet(Difficulty.EASY);
        mainMenu.mainMenuStart();
        
        gameMenu.gameMenuStart();

    }

    public void quit() {
        System.out.println("Closing game, Thanks for Plaing");
        input.close();
        System.exit(0);
    }

}
