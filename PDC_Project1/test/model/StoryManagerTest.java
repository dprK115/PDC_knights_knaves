/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import java.util.ArrayList;
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

public class StoryManagerTest {

    @Test
    public void defaultConstructor_createsEmptyEncounterList() {
        // Arrange + Act
        StoryManager manager = new StoryManager();

        // Assert
        assertNotNull(manager.getEncounters());
        assertEquals(0, manager.getEncounters().size());
        assertEquals(0, manager.getIndex());
    }

    @Test
    public void constructorWithEncounterList_storesEncounterList() {
        // Arrange
        ArrayList<Encounter> encounters = new ArrayList<>();
        Encounter story = new Story("Test story encounter.");
        encounters.add(story);

        // Act
        StoryManager manager = new StoryManager(encounters);

        // Assert
        assertEquals(1, manager.getEncounters().size());
        assertEquals(story, manager.getEncounters().get(0));
    }

    @Test
    public void addEncounter_addsEncounterToList() {
        // Arrange
        StoryManager manager = new StoryManager();
        Encounter story = new Story("You enter a cave.");

        // Act
        manager.addEncounter(story);

        // Assert
        assertEquals(1, manager.getEncounters().size());
        assertEquals(story, manager.getEncounters().get(0));
    }

    @Test
    public void getCurrentEncounter_returnsEncounterAtCurrentIndex() {
        // Arrange
        StoryManager manager = new StoryManager();

        Encounter first = new Story("First encounter.");
        Encounter second = new Story("Second encounter.");

        manager.addEncounter(first);
        manager.addEncounter(second);
        manager.setIndex(1);

        // Act
        Encounter current = manager.getCurrentEncounter();

        // Assert
        assertEquals(second, current);
        assertEquals(1, manager.getIndex());
    }

    @Test
    public void getNextEncounter_returnsFirstEncounterAndIncrementsIndex() {
        // Arrange
        StoryManager manager = new StoryManager();

        Encounter first = new Story("First encounter.");
        Encounter second = new Story("Second encounter.");

        manager.addEncounter(first);
        manager.addEncounter(second);

        // Act
        Encounter next = manager.getNextEncounter();

        // Assert
        assertEquals(first, next);
        assertEquals(1, manager.getIndex());
    }

    @Test
    public void getNextEncounter_returnsEncountersInOrder() {
        // Arrange
        StoryManager manager = new StoryManager();

        Encounter first = new Story("First encounter.");
        Encounter second = new Story("Second encounter.");
        Encounter third = new Story("Third encounter.");

        manager.addEncounter(first);
        manager.addEncounter(second);
        manager.addEncounter(third);

        // Act + Assert
        assertEquals(first, manager.getNextEncounter());
        assertEquals(second, manager.getNextEncounter());
        assertEquals(third, manager.getNextEncounter());
        assertEquals(3, manager.getIndex());
    }

    @Test
    public void getNextEncounter_whenNoEncountersLeft_returnsNull() {
        // Arrange
        StoryManager manager = new StoryManager();

        Encounter first = new Story("Only encounter.");
        manager.addEncounter(first);

        // Act
        Encounter firstResult = manager.getNextEncounter();
        Encounter secondResult = manager.getNextEncounter();

        // Assert
        assertEquals(first, firstResult);
        assertNull(secondResult);
        assertEquals(1, manager.getIndex());
    }

    @Test
    public void setEncounters_replacesEncounterList() {
        // Arrange
        StoryManager manager = new StoryManager();

        ArrayList<Encounter> newEncounters = new ArrayList<>();
        Encounter story = new Story("Replacement encounter.");
        newEncounters.add(story);

        // Act
        manager.setEncounters(newEncounters);

        // Assert
        assertEquals(1, manager.getEncounters().size());
        assertEquals(story, manager.getEncounters().get(0));
    }

    @Test
    public void setIndex_changesCurrentIndex() {
        // Arrange
        StoryManager manager = new StoryManager();

        // Act
        manager.setIndex(5);

        // Assert
        assertEquals(5, manager.getIndex());
    }
}
