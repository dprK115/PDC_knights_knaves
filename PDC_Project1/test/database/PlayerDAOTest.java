/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package database;

import java.util.ArrayList;
import model.Game;
import model.StoryManager;
import model.Encounter;
import model.Difficulty;
import model.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author lukea
 */
public class PlayerDAOTest {

    private DBManager dbm;
    private DBInitialiser dbInitialiser;
    private PlayerDAO playerDAO;

    private Player testPlayer;

    private final int TEST_PLAYER_ID = 9301;

    @Before
    public void setUp() {
        dbm = new DBManager();

        dbInitialiser = new DBInitialiser(dbm);
        dbInitialiser.createTables();

        playerDAO = new PlayerDAO(dbm);

        testPlayer = new Player("JUnit Player", Difficulty.EASY);
        testPlayer.setID(TEST_PLAYER_ID);
        testPlayer.setEncounterIndex(0);

        Game.manager = new StoryManager(new ArrayList<Encounter>());

        // Clean old test data
        playerDAO.delete(testPlayer);
    }

    @After
    public void tearDown() {
        if (playerDAO != null && testPlayer != null) {
            playerDAO.delete(testPlayer);
        }

        if (dbm != null) {
            dbm.closeConnection();
        }
    }

    @Test
    public void insert_player_canBeLoadedByID() {
        // Act
        playerDAO.insert(testPlayer);

        Player loadedPlayer = playerDAO.loadByID(TEST_PLAYER_ID);

        // Assert
        assertNotNull(loadedPlayer);
        assertEquals(TEST_PLAYER_ID, loadedPlayer.getID());
        assertEquals("JUnit Player", loadedPlayer.name);
        assertEquals(testPlayer.health, loadedPlayer.health);
        assertEquals(testPlayer.xp, loadedPlayer.xp);
    }

    @Test
    public void save_newPlayer_insertsPlayer() {
        // Act
        playerDAO.save(testPlayer);

        // Assert
        assertTrue(playerDAO.elementExists(testPlayer));

        Player loadedPlayer = playerDAO.loadByID(TEST_PLAYER_ID);

        assertNotNull(loadedPlayer);
        assertEquals(TEST_PLAYER_ID, loadedPlayer.getID());
        assertEquals("JUnit Player", loadedPlayer.name);
    }

    @Test
    public void save_existingPlayer_updatesPlayer() {
        // Arrange
        playerDAO.save(testPlayer);

        testPlayer.setName("Updated JUnit Player");
        testPlayer.setHealth(25);
        testPlayer.setXp(50);
        testPlayer.setEncounterIndex(3);

        // Act
        playerDAO.save(testPlayer);

        Player loadedPlayer = playerDAO.loadByID(TEST_PLAYER_ID);

        // Assert
        assertNotNull(loadedPlayer);
        assertEquals(TEST_PLAYER_ID, loadedPlayer.getID());
        assertEquals("Updated JUnit Player", loadedPlayer.name);
        assertEquals(25, loadedPlayer.health);
        assertEquals(50, loadedPlayer.xp);
    }

    @Test
    public void update_existingPlayer_changesPlayerData() {
        // Arrange
        playerDAO.insert(testPlayer);

        testPlayer.setName("Updated Name");
        testPlayer.setHealth(10);
        testPlayer.setXp(100);

        // Act
        playerDAO.update(testPlayer);

        Player loadedPlayer = playerDAO.loadByID(TEST_PLAYER_ID);

        // Assert
        assertNotNull(loadedPlayer);
        assertEquals("Updated Name", loadedPlayer.name);
        assertEquals(10, loadedPlayer.health);
        assertEquals(100, loadedPlayer.xp);
    }

    @Test
    public void loadByID_whenPlayerDoesNotExist_returnsNull() {
        // Act
        Player loadedPlayer = playerDAO.loadByID(9999);

        // Assert
        assertNull(loadedPlayer);
    }

    @Test
    public void elementExists_whenPlayerExists_returnsTrue() {
        // Arrange
        playerDAO.insert(testPlayer);

        // Act
        boolean exists = playerDAO.elementExists(testPlayer);

        // Assert
        assertTrue(exists);
    }

    @Test
    public void elementExists_whenPlayerDoesNotExist_returnsFalse() {
        // Act
        boolean exists = playerDAO.elementExists(testPlayer);

        // Assert
        assertFalse(exists);
    }

    @Test
    public void delete_existingPlayer_removesPlayerFromDatabase() {
        // Arrange
        playerDAO.insert(testPlayer);

        assertTrue(playerDAO.elementExists(testPlayer));

        // Act
        playerDAO.delete(testPlayer);

        // Assert
        assertFalse(playerDAO.elementExists(testPlayer));
        assertNull(playerDAO.loadByID(TEST_PLAYER_ID));
    }

    @Test
    public void loadAll_returnsInsertedPlayer() {
        // Arrange
        playerDAO.save(testPlayer);

        // Act
        ArrayList<Player> players = playerDAO.loadAll();

        // Assert
        assertNotNull(players);
        assertTrue(players.size() >= 1);

        boolean foundTestPlayer = false;

        for (Player player : players) {
            if (player.getID() == TEST_PLAYER_ID) {
                foundTestPlayer = true;
                assertEquals("JUnit Player", player.name);
            }
        }

        assertTrue(foundTestPlayer);
    }

    @Test
    public void getNextPlayerID_returnsNumberGreaterThanExistingMaxID() {
        // Arrange
        playerDAO.save(testPlayer);

        // Act
        int nextID = playerDAO.getNextPlayerID();

        // Assert
        assertTrue(nextID > TEST_PLAYER_ID);
    }
}
