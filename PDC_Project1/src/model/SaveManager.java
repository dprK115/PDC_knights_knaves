/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lukea
 */
import java.util.List;

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
    public List getSaveList(){
        SaveLoader sl = new SaveLoader();
        return sl.getSaveList();
    }
}
