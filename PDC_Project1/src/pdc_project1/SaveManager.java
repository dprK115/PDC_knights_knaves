/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author lukea
 */
import java.io.*;

public class SaveManager {
    private static final String SAVE_FILE = "save.dat";
    
    public static void saveGame(GameState gameState){
        GameSaver gs = new GameSaver(gameState);
        gs.SaveGame();
    }
    
    public static GameState loadGame(int playerID) {
        SaveLoader sl = new SaveLoader();
        GameState gs = sl.loadSave(playerID);
        return gs;
        
    }
}
