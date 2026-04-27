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
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {

            out.writeObject(gameState);
            System.out.println("Game saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving game.");
            e.printStackTrace();
        }
    }
    
    public static GameState loadGame() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(SAVE_FILE))) {

            System.out.println("Game loaded successfully.");
            return (GameState) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No save file found or save file is invalid.");
            return null;
        }
    }
}
