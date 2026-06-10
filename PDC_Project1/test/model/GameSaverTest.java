/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import database.DBInitialiser;
import database.DBManager;
import database.InventoryDAO;
import database.ItemDAO;
import database.PlayerDAO;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author lukea
 */


public class GameSaverTest {

    private DBManager dbm;
    private DBInitialiser dbInitialiser;

    private PlayerDAO playerDAO;
    private InventoryDAO inventoryDAO;
    private ItemDAO itemDAO;

    private Player testPlayer;
    private StoryManager testStoryManager;
    private GameState testGameState;

    private Item testPotion;
    private Item testWeapon;

    private final int TEST_PLAYER_ID = 9401;
    private final int TEST_POTION_ID = 9402;
    private final int TEST_WEAPON_ID = 9403;

    @Before
    public void setUp() {
        dbm = new DBManager();

        dbInitialiser = new DBInitialiser(dbm);
        dbInitialiser.createTables();

        playerDAO = new PlayerDAO(dbm);
        inventoryDAO = new InventoryDAO(dbm, TEST_PLAYER_ID);
        itemDAO = new ItemDAO(dbm);

        
        Game.manager = new StoryManager(new ArrayList<Encounter>());

        testPotion = ItemFactory.createItem(
                TEST_POTION_ID,
                "POTION",
                "JUnit Health Potion",
                0,
                0,
                25
        );

        testWeapon = ItemFactory.createItem(
                TEST_WEAPON_ID,
                "WEAPON",
                "JUnit Sword",
                15,
                0,
                0
        );

        /*
         * Clean old test data first.
         * Inventory must be deleted before player because INVENTORY references PLAYER.
         */
        inventoryDAO.deleteByPlayerID(TEST_PLAYER_ID);

        testPlayer = new Player("JUnit Saver Player", Difficulty.EASY);
        testPlayer.setID(TEST_PLAYER_ID);
        testPlayer.setEncounterIndex(0);

        playerDAO.delete(testPlayer);

        itemDAO.delete(testPotion);
        itemDAO.delete(testWeapon);

        /*
         * Items must exist in ITEM table before they can be saved in INVENTORY.
         */
        itemDAO.save(testPotion);
        itemDAO.save(testWeapon);

        testPlayer.inventory.addItem(testPotion);
        testPlayer.inventory.addItem(testWeapon);

        testStoryManager = new StoryManager(new ArrayList<Encounter>());
        testGameState = new GameState(testPlayer, testStoryManager);
    }

    @After
    public void tearDown() {
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
    public void SaveGame_savesPlayerToDatabase() {
        // Arrange
        GameSaver gameSaver = new GameSaver(testGameState);

        // Act
        gameSaver.SaveGame();

        // Assert
        Player loadedPlayer = playerDAO.loadByID(TEST_PLAYER_ID);

        assertNotNull(loadedPlayer);
        assertEquals(TEST_PLAYER_ID, loadedPlayer.getID());
        assertEquals("JUnit Saver Player", loadedPlayer.name);
    }

    @Test
    public void SaveGame_savesInventoryToDatabase() {
        // Arrange
        GameSaver gameSaver = new GameSaver(testGameState);

        // Act
        gameSaver.SaveGame();

        // Assert
        Inventory loadedInventory = inventoryDAO.loadByID(TEST_PLAYER_ID);

        assertNotNull(loadedInventory);
        assertEquals(2, loadedInventory.items.size());

        assertEquals(TEST_POTION_ID, loadedInventory.items.get(0).getID());
        assertEquals(TEST_WEAPON_ID, loadedInventory.items.get(1).getID());
    }

    @Test
    public void SaveGame_updatesExistingPlayerSave() {
        // Arrange
        GameSaver firstSave = new GameSaver(testGameState);
        firstSave.SaveGame();

        testPlayer.setName("Updated Saver Player");
        testPlayer.setHealth(20);
        testPlayer.setXp(50);

        GameState updatedState = new GameState(testPlayer, testStoryManager);
        GameSaver secondSave = new GameSaver(updatedState);

        // Act
        secondSave.SaveGame();

        // Assert
        Player loadedPlayer = playerDAO.loadByID(TEST_PLAYER_ID);

        assertNotNull(loadedPlayer);
        assertEquals("Updated Saver Player", loadedPlayer.name);
        assertEquals(20, loadedPlayer.health);
        assertEquals(50, loadedPlayer.xp);
    }

    @Test
    public void SaveGame_updatesExistingInventorySave() {
        // Arrange
        GameSaver firstSave = new GameSaver(testGameState);
        firstSave.SaveGame();

        testPlayer.inventory = new Inventory();
        testPlayer.inventory.addItem(testWeapon);

        GameState updatedState = new GameState(testPlayer, testStoryManager);
        GameSaver secondSave = new GameSaver(updatedState);

        // Act
        secondSave.SaveGame();

        // Assert
        Inventory loadedInventory = inventoryDAO.loadByID(TEST_PLAYER_ID);

        assertNotNull(loadedInventory);
        assertEquals(1, loadedInventory.items.size());
        assertEquals(TEST_WEAPON_ID, loadedInventory.items.get(0).getID());
    }
}