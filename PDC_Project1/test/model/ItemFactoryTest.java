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



public class ItemFactoryTest {

    @Test
    public void createItem_whenTypeIsWeapon_returnsWeapon() {
        // Arrange
        int itemID = 1;
        String itemType = "WEAPON";
        String name = "Iron Sword";
        int attackModifier = 10;
        int defenseModifier = 0;
        int healAmount = 0;

        // Act
        Item item = ItemFactory.createItem(
                itemID,
                itemType,
                name,
                attackModifier,
                defenseModifier,
                healAmount
        );

        // Assert
        assertNotNull(item);
        assertTrue(item instanceof Weapon);
        assertEquals(itemID, item.getID());
        assertEquals(name, item.name);
    }

    @Test
    public void createItem_whenTypeIsArmor_returnsArmor() {
        // Arrange
        int itemID = 2;
        String itemType = "ARMOR";
        String name = "Leather Armor";
        int attackModifier = 0;
        int defenseModifier = 5;
        int healAmount = 0;

        // Act
        Item item = ItemFactory.createItem(
                itemID,
                itemType,
                name,
                attackModifier,
                defenseModifier,
                healAmount
        );

        // Assert
        assertNotNull(item);
        assertTrue(item instanceof Armor);
        assertEquals(itemID, item.getID());
        assertEquals(name, item.name);
    }

    @Test
    public void createItem_whenTypeIsPotion_returnsPotion() {
        // Arrange
        int itemID = 3;
        String itemType = "POTION";
        String name = "Health Potion";
        int attackModifier = 0;
        int defenseModifier = 0;
        int healAmount = 25;

        // Act
        Item item = ItemFactory.createItem(
                itemID,
                itemType,
                name,
                attackModifier,
                defenseModifier,
                healAmount
        );

        // Assert
        assertNotNull(item);
        assertTrue(item instanceof Potion);
        assertEquals(itemID, item.getID());
        assertEquals(name, item.name);
    }

    @Test
    public void createItem_whenTypeIsLowercaseWeapon_stillReturnsWeapon() {
        // Arrange
        int itemID = 4;

        // Act
        Item item = ItemFactory.createItem(
                itemID,
                "weapon",
                "Rusty Sword",
                3,
                0,
                0
        );

        // Assert
        assertNotNull(item);
        assertTrue(item instanceof Weapon);
        assertEquals(itemID, item.getID());
    }

    @Test
    public void createItem_whenTypeIsInvalid_throwsIllegalArgumentException() {
        // Act + Assert
        try {
            ItemFactory.createItem(
                    99,
                    "RING",
                    "Magic Ring",
                    0,
                    0,
                    0
            );

            fail("Expected IllegalArgumentException to be thrown.");

        } catch (IllegalArgumentException e) {
            assertEquals("Unknown item type: RING", e.getMessage());
        }
    }
}