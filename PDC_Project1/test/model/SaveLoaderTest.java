/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import database.DBInitialiser;
import database.DBManager;
import database.EncounterDAO;
import database.InventoryDAO;
import database.ItemDAO;
import database.PlayerDAO;

import java.util.ArrayList;
import model.Game;
import model.StoryManager;
import model.Encounter;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author lukea
 */


public class SaveLoaderTest {

    private DBManager dbm;
    private DBInitialiser dbInitialiser;

    private PlayerDAO playerDAO;
    private InventoryDAO inventoryDAO;
    private ItemDAO itemDAO;
    private EncounterDAO encounterDAO;

    private Player testPlayer;

    private Item testPotion;
    private Item testWeapon;

    private Encounter testStoryEncounter;
    private Encounter testCombatEncounter;

    private final int TEST_PLAYER_ID = 9501;
    private final int TEST_POTION_ID = 9502;
    private final int TEST_WEAPON_ID = 9503;
    private final int TEST_STORY_ENCOUNTER_ID = 9504;
    private final int TEST_COMBAT_ENCOUNTER_ID = 9505;

    @Before
    public void setUp() {
        dbm = new DBManager();

        dbInitialiser = new DBInitialiser(dbm);
        dbInitialiser.createTables();

        playerDAO = new PlayerDAO(dbm);
        inventoryDAO = new InventoryDAO(dbm, TEST_PLAYER_ID);
        itemDAO = new ItemDAO(dbm);

        testPlayer = new Player("JUnit Loader Player", Difficulty.EASY);
        Game.manager = new StoryManager(new ArrayList<Encounter>());
        testPlayer.setID(TEST_PLAYER_ID);
        testPlayer.setEncounterIndex(1);

        testPotion = ItemFactory.createItem(
                TEST_POTION_ID,
                "POTION",
                "JUnit Loader Potion",
                0,
                0,
                25
        );

        testWeapon = ItemFactory.createItem(
                TEST_WEAPON_ID,
                "WEAPON",
                "JUnit Loader Sword",
                15,
                0,
                0
        );

        /*
         * Clean old data first.
         * Delete inventory before player because INVENTORY references PLAYER.
         */
        inventoryDAO.deleteByPlayerID(TEST_PLAYER_ID);
        playerDAO.delete(testPlayer);

        itemDAO.delete(testPotion);
        itemDAO.delete(testWeapon);

        /*
         * Items must exist before inventory and combat encounters can reference them.
         */
        itemDAO.save(testPotion);
        itemDAO.save(testWeapon);

        testPlayer.inventory.addItem(testPotion);
        testPlayer.inventory.addItem(testWeapon);
        

        playerDAO.save(testPlayer);
        inventoryDAO.save(testPlayer.inventory);

        encounterDAO = new EncounterDAO(dbm, testPlayer);

        testStoryEncounter = EncounterFactory.createEncounter(
                TEST_STORY_ENCOUNTER_ID,
                "STORY",
                testPlayer,
                "JUnit loader story encounter.",
                null,
                0,
                null
        );

        testCombatEncounter = EncounterFactory.createEncounter(
                TEST_COMBAT_ENCOUNTER_ID,
                "COMBAT",
                testPlayer,
                null,
                "JUnit Loader Goblin",
                1,
                testPotion
        );

        /*
         * Save encounters so SaveLoader can load them into StoryManager.
         */
        encounterDAO.save(testStoryEncounter);
        encounterDAO.save(testCombatEncounter);
        dbm.closeConnection();
    }

    @After
    public void tearDown() {
        dbm.establishConnection();
        if (encounterDAO != null) {
            if (testStoryEncounter != null) {
                encounterDAO.delete(testStoryEncounter);
            }

            if (testCombatEncounter != null) {
                encounterDAO.delete(testCombatEncounter);
            }
        }

        if (inventoryDAO != null) {
            inventoryDAO.deleteByPlayerID(TEST_PLAYER_ID);
        }

        if (playerDAO != null && testPlayer != null) {
            playerDAO.delete(testPlayer);
        }

        if (itemDAO != null) {
            if (testPotion != null) {
                itemDAO.delete(testPotion);
            }

            if (testWeapon != null) {
                itemDAO.delete(testWeapon);
            }
        }

        if (dbm != null) {
            dbm.closeConnection();
        }
    }

    @Test
    public void loadSave_existingPlayer_returnsGameState() {
        // Arrange
        SaveLoader loader = new SaveLoader();

        // Act
        GameState loadedState = loader.loadSave(TEST_PLAYER_ID);

        // Assert
        assertNotNull(loadedState);
        assertNotNull(loadedState.getPlayer());
        assertNotNull(loadedState.getStoryManager());
    }

    @Test
    public void loadSave_existingPlayer_loadsCorrectPlayer() {
        // Arrange
        SaveLoader loader = new SaveLoader();

        // Act
        GameState loadedState = loader.loadSave(TEST_PLAYER_ID);
        Player loadedPlayer = loadedState.getPlayer();

        // Assert
        assertNotNull(loadedPlayer);
        assertEquals(TEST_PLAYER_ID, loadedPlayer.getID());
        assertEquals("JUnit Loader Player", loadedPlayer.name);
    }

    @Test
    public void loadSave_existingPlayer_loadsInventory() {
        // Arrange
        SaveLoader loader = new SaveLoader();

        // Act
        GameState loadedState = loader.loadSave(TEST_PLAYER_ID);
        Player loadedPlayer = loadedState.getPlayer();

        // Assert
        assertNotNull(loadedPlayer.inventory);
        assertEquals(2, loadedPlayer.inventory.items.size());

        assertEquals(TEST_POTION_ID, loadedPlayer.inventory.items.get(0).getID());
        assertEquals(TEST_WEAPON_ID, loadedPlayer.inventory.items.get(1).getID());
    }

    @Test
    public void loadSave_existingPlayer_loadsStoryManager() {
        // Arrange
        SaveLoader loader = new SaveLoader();

        // Act
        GameState loadedState = loader.loadSave(TEST_PLAYER_ID);
        StoryManager loadedManager = loadedState.getStoryManager();

        // Assert
        assertNotNull(loadedManager);
        assertNotNull(loadedManager.getEncounters());
        assertTrue(loadedManager.getEncounters().size() >= 2);
    }

    @Test
    public void loadSave_setsStoryManagerIndexFromPlayerProgressMinusOne() {
        // Arrange
        SaveLoader loader = new SaveLoader();

        // Act
        GameState loadedState = loader.loadSave(TEST_PLAYER_ID);
        StoryManager loadedManager = loadedState.getStoryManager();

        // Assert
        /*
         * Your SaveLoader currently does:
         * sm.index = player.currentStoryIndex - 1;
         *
         * Since testPlayer currentStoryIndex is 1,
         * the loaded StoryManager index should be 0.
         */
        assertEquals(0, loadedManager.getIndex());
    }

    @Test
    public void getSaveList_returnsSavedPlayers() {
        // Arrange
        SaveLoader loader = new SaveLoader();

        // Act
        List<Player> saveList = loader.getSaveList();

        // Assert
        assertNotNull(saveList);
        assertTrue(saveList.size() >= 1);

        boolean foundTestPlayer = false;

        for (Player player : saveList) {
            if (player.getID() == TEST_PLAYER_ID) {
                foundTestPlayer = true;
                assertEquals("JUnit Loader Player", player.name);
            }
        }

        assertTrue(foundTestPlayer);
    }
}