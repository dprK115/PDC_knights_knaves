/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lukea
 */
public class GameStateTest {

    @Test
    public void constructor_setsPlayerAndStoryManager() {
        // Arrange
        Player player = new Player("JUnit Player", Difficulty.EASY);
        StoryManager storyManager = new StoryManager(new ArrayList<Encounter>());

        // Act
        GameState gameState = new GameState(player, storyManager);

        // Assert
        assertNotNull(gameState);
        assertEquals(player, gameState.getPlayer());
        assertEquals(storyManager, gameState.getStoryManager());
    }

    @Test
    public void getPlayer_returnsCorrectPlayer() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);
        StoryManager storyManager = new StoryManager(new ArrayList<Encounter>());
        GameState gameState = new GameState(player, storyManager);

        // Act
        Player returnedPlayer = gameState.getPlayer();

        // Assert
        assertNotNull(returnedPlayer);
        assertEquals(player, returnedPlayer);
        assertEquals("Arthur", returnedPlayer.name);
    }

    @Test
    public void getStoryManager_returnsCorrectStoryManager() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);

        StoryManager storyManager = new StoryManager();
        Encounter encounter = new Story("JUnit test story encounter.");
        storyManager.addEncounter(encounter);

        GameState gameState = new GameState(player, storyManager);

        // Act
        StoryManager returnedStoryManager = gameState.getStoryManager();

        // Assert
        assertNotNull(returnedStoryManager);
        assertEquals(storyManager, returnedStoryManager);
        assertEquals(1, returnedStoryManager.getEncounters().size());
        assertEquals(encounter, returnedStoryManager.getEncounters().get(0));
    }

    @Test
    public void gameState_canStoreNullPlayer() {
        // Arrange
        StoryManager storyManager = new StoryManager();

        // Act
        GameState gameState = new GameState(null, storyManager);

        // Assert
        assertNull(gameState.getPlayer());
        assertEquals(storyManager, gameState.getStoryManager());
    }

    @Test
    public void gameState_canStoreNullStoryManager() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);

        // Act
        GameState gameState = new GameState(player, null);

        // Assert
        assertEquals(player, gameState.getPlayer());
        assertNull(gameState.getStoryManager());
    }
}
