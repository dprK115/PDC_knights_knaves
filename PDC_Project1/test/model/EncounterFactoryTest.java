/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lukea
 */
import org.junit.Test;


public class EncounterFactoryTest {

    @Test
    public void createEncounter_whenTypeIsStory_returnsStoryEncounter() {
        // Arrange
        int encounterID = 1;
        String encounterType = "STORY";
        Player player = new Player("TestPlayer", Difficulty.EASY);
        String storyText = "You enter a dark forest.";

        // Act
        Encounter encounter = EncounterFactory.createEncounter(
                encounterID,
                encounterType,
                player,
                storyText,
                null,
                0,
                null
        );

        // Assert
        assertNotNull(encounter);
        assertTrue(encounter instanceof Story);
        assertEquals(encounterID, encounter.getID());
    }

    @Test
    public void createEncounter_whenTypeIsCombat_returnsCombatEncounter() {
        // Arrange
        int encounterID = 2;
        String encounterType = "COMBAT";
        Player player = new Player("TestPlayer", Difficulty.EASY);

        Item loot = ItemFactory.createItem(
                1,
                "POTION",
                "Health Potion",
                0,
                0,
                20
        );

        // Act
        Encounter encounter = EncounterFactory.createEncounter(
                encounterID,
                encounterType,
                player,
                null,
                "Goblin",
                1,
                loot
        );

        // Assert
        assertNotNull(encounter);
        assertTrue(encounter instanceof Combat);
        assertEquals(encounterID, encounter.getID());
    }

    @Test
    public void createEncounter_whenTypeIsLowercaseStory_stillReturnsStoryEncounter() {
        // Arrange
        int encounterID = 3;
        Player player = new Player("TestPlayer", Difficulty.EASY);

        // Act
        Encounter encounter = EncounterFactory.createEncounter(
                encounterID,
                "story",
                player,
                "Lowercase story test.",
                null,
                0,
                null
        );

        // Assert
        assertNotNull(encounter);
        assertTrue(encounter instanceof Story);
        assertEquals(encounterID, encounter.getID());
    }

    @Test
    public void createEncounter_whenTypeIsInvalid_throwsIllegalArgumentException() {
        // Arrange
        Player player = new Player("TestPlayer", Difficulty.EASY);

        // Act + Assert
        try {
            EncounterFactory.createEncounter(
                    99,
                    "PUZZLE",
                    player,
                    null,
                    null,
                    0,
                    null
            );

            fail("Expected IllegalArgumentException to be thrown.");

        } catch (IllegalArgumentException e) {
            assertEquals("Invalid encounter type: PUZZLE", e.getMessage());
        }
    }
}
