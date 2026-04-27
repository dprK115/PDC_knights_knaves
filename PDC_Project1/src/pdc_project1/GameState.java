/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author lukea
 */
import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Player player;
    private StoryManager storyManager;
    

    public GameState(Player player, StoryManager storyManager) {
        this.player = player;
        this.storyManager = storyManager;
       
    }

    public Player getPlayer() {
        return player;
    }

    public StoryManager getStoryManager() {
        return storyManager;
    }

    
    
}
