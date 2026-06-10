/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package database;

import java.util.ArrayList;
import model.Combat;
import model.Difficulty;
import model.Encounter;
import model.EncounterFactory;
import model.Item;
import model.ItemFactory;
import model.Player;
import model.Story;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author lukea
 */
public class EncounterDAOTest {

    private DBManager dbm;
    private DBInitialiser dbInitialiser;
    private EncounterDAO encounterDAO;
    private ItemDAO itemDAO;
    private Player testPlayer;

    private Item testLoot;

    @Before
    public void setUp() {
        dbm = new DBManager();

        // Make sure your tables exist before testing.
        dbInitialiser = new DBInitialiser(dbm);
        dbInitialiser.createTables();

        testPlayer = new Player("JUnitTester", Difficulty.EASY);

        itemDAO = new ItemDAO(dbm);
        encounterDAO = new EncounterDAO(dbm, testPlayer);

        // Combat encounters need loot that already exists in ITEM table.
        testLoot = ItemFactory.createItem(
                9001,
                "POTION",
                "JUnit Potion",
                0,
                0,
                25
        );

        itemDAO.save(testLoot);
    }

    @After
    public void tearDown() {
        // Delete test encounters first because they may reference ITEM through LOOT_ITEM_ID.
        Encounter story = EncounterFactory.createEncounter(
                9001,
                "STORY",
                testPlayer,
                "JUnit story encounter.",
                null,
                0,
                null
        );

        Encounter combat = EncounterFactory.createEncounter(
                9002,
                "COMBAT",
                testPlayer,
                null,
                "JUnit Goblin",
                1,
                testLoot
        );

        encounterDAO.delete(story);
        encounterDAO.delete(combat);

        itemDAO.delete(testLoot);

        dbm.closeConnection();
    }

    @Test
    public void insert_storyEncounter_canBeLoadedByID() {
        // Arrange
        Encounter story = EncounterFactory.createEncounter(
                9001,
                "STORY",
                testPlayer,
                "JUnit story encounter.",
                null,
                0,
                null
        );

        // Act
        encounterDAO.insert(story);
        Encounter loaded = encounterDAO.loadByID(9001);

        // Assert
        assertNotNull(loaded);
        assertTrue(loaded instanceof Story);
        assertEquals(9001, loaded.getID());
    }

    @Test
    public void insert_combatEncounter_canBeLoadedByID() {
        // Arrange
        Encounter combat = EncounterFactory.createEncounter(
                9002,
                "COMBAT",
                testPlayer,
                null,
                "JUnit Goblin",
                1,
                testLoot
        );

        // Act
        encounterDAO.insert(combat);
        Encounter loaded = encounterDAO.loadByID(9002);

        // Assert
        assertNotNull(loaded);
        assertTrue(loaded instanceof Combat);
        assertEquals(9002, loaded.getID());
    }

    @Test
    public void save_newEncounter_insertsEncounter() {
        // Arrange
        Encounter story = EncounterFactory.createEncounter(
                9001,
                "STORY",
                testPlayer,
                "JUnit save insert story.",
                null,
                0,
                null
        );

        // Act
        encounterDAO.save(story);

        // Assert
        assertTrue(encounterDAO.elementExists(story));

        Encounter loaded = encounterDAO.loadByID(9001);
        assertNotNull(loaded);
        assertTrue(loaded instanceof Story);
    }

    @Test
    public void save_existingEncounter_updatesEncounter() {
        // Arrange
        Encounter original = EncounterFactory.createEncounter(
                9001,
                "STORY",
                testPlayer,
                "Original story text.",
                null,
                0,
                null
        );

        Encounter updated = EncounterFactory.createEncounter(
                9001,
                "STORY",
                testPlayer,
                "Updated story text.",
                null,
                0,
                null
        );

        // Act
        encounterDAO.save(original);
        encounterDAO.save(updated);

        Encounter loaded = encounterDAO.loadByID(9001);

        // Assert
        assertNotNull(loaded);
        assertTrue(loaded instanceof Story);
        assertEquals(9001, loaded.getID());
    }

    @Test
    public void elementExists_whenEncounterExists_returnsTrue() {
        // Arrange
        Encounter story = EncounterFactory.createEncounter(
                9001,
                "STORY",
                testPlayer,
                "JUnit exists story.",
                null,
                0,
                null
        );

        encounterDAO.insert(story);

        // Act
        boolean exists = encounterDAO.elementExists(story);

        // Assert
        assertTrue(exists);
    }

    @Test
    public void elementExists_whenEncounterDoesNotExist_returnsFalse() {
        // Arrange
        Encounter story = EncounterFactory.createEncounter(
                9999,
                "STORY",
                testPlayer,
                "This should not exist.",
                null,
                0,
                null
        );

        // Act
        boolean exists = encounterDAO.elementExists(story);

        // Assert
        assertFalse(exists);
    }

    @Test
    public void delete_existingEncounter_removesEncounterFromDatabase() {
        // Arrange
        Encounter story = EncounterFactory.createEncounter(
                9001,
                "STORY",
                testPlayer,
                "JUnit delete story.",
                null,
                0,
                null
        );

        encounterDAO.insert(story);
        assertTrue(encounterDAO.elementExists(story));

        // Act
        encounterDAO.delete(story);

        // Assert
        assertFalse(encounterDAO.elementExists(story));
        assertNull(encounterDAO.loadByID(9001));
    }

    @Test
    public void loadAll_returnsListOfEncounters() {
        // Arrange
        Encounter story = EncounterFactory.createEncounter(
                9001,
                "STORY",
                testPlayer,
                "JUnit load all story.",
                null,
                0,
                null
        );

        Encounter combat = EncounterFactory.createEncounter(
                9002,
                "COMBAT",
                testPlayer,
                null,
                "JUnit Goblin",
                1,
                testLoot
        );

        encounterDAO.save(story);
        encounterDAO.save(combat);

        // Act
        ArrayList<Encounter> encounters = encounterDAO.loadAll();

        // Assert
        assertNotNull(encounters);
        assertTrue(encounters.size() >= 2);

        boolean foundStory = false;
        boolean foundCombat = false;

        for (Encounter e : encounters) {
            if (e.getID() == 9001 && e instanceof Story) {
                foundStory = true;
            }

            if (e.getID() == 9002 && e instanceof Combat) {
                foundCombat = true;
            }
        }

        assertTrue(foundStory);
        assertTrue(foundCombat);
    }
}
