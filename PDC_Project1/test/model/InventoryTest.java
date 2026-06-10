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
public class InventoryTest {

    @Test
    public void newInventory_startsEmpty() {
        // Arrange + Act
        Inventory inventory = new Inventory();

        // Assert
        assertNotNull(inventory.items);
        assertEquals(0, inventory.items.size());
    }

    @Test
    public void addItem_addsItemToInventory() {
        // Arrange
        Inventory inventory = new Inventory();

        Item potion = ItemFactory.createItem(
                1,
                "POTION",
                "Health Potion",
                0,
                0,
                20
        );

        // Act
        inventory.addItem(potion);

        // Assert
        assertEquals(1, inventory.items.size());
        assertEquals(potion, inventory.items.get(0));
        assertEquals("Health Potion", inventory.items.get(0).name);
    }

    @Test
    public void removeItem_removesItemFromInventory() {
        // Arrange
        Inventory inventory = new Inventory();

        Item sword = ItemFactory.createItem(
                2,
                "WEAPON",
                "Iron Sword",
                10,
                0,
                0
        );

        inventory.addItem(sword);

        // Act
        inventory.removeItem(sword);

        // Assert
        assertEquals(0, inventory.items.size());
        assertFalse(inventory.items.contains(sword));
    }

    @Test
    public void constructorWithSavedInventory_copiesItemsIntoInventory() {
        // Arrange
        ArrayList<Item> savedItems = new ArrayList<>();

        Item armor = ItemFactory.createItem(
                3,
                "ARMOR",
                "Leather Armor",
                0,
                5,
                0
        );

        Item potion = ItemFactory.createItem(
                4,
                "POTION",
                "Small Potion",
                0,
                0,
                15
        );

        savedItems.add(armor);
        savedItems.add(potion);

        // Act
        Inventory inventory = new Inventory(savedItems);

        // Assert
        assertEquals(2, inventory.items.size());
        assertEquals(armor, inventory.items.get(0));
        assertEquals(potion, inventory.items.get(1));
    }

    @Test
    public void getItems_returnsInventoryItems() {
        // Arrange
        Inventory inventory = new Inventory();

        Item potion = ItemFactory.createItem(
                5,
                "POTION",
                "Large Potion",
                0,
                0,
                50
        );

        inventory.addItem(potion);

        // Act
        ArrayList<Item> items = inventory.getItems();

        // Assert
        assertEquals(1, items.size());
        assertEquals(potion, items.get(0));
    }
}
