/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package database;

import java.util.ArrayList;

import model.Armor;
import model.Item;
import model.ItemFactory;
import model.Potion;
import model.Weapon;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author lukea
 */
public class ItemDAOTest {

    private DBManager dbm;
    private DBInitialiser dbInitialiser;
    private ItemDAO itemDAO;

    private Item testWeapon;
    private Item testArmor;
    private Item testPotion;

    @Before
    public void setUp() {
        dbm = new DBManager();

        dbInitialiser = new DBInitialiser(dbm);
        dbInitialiser.createTables();

        itemDAO = new ItemDAO(dbm);

        testWeapon = ItemFactory.createItem(
                9201,
                "WEAPON",
                "JUnit Sword",
                15,
                0,
                0
        );

        testArmor = ItemFactory.createItem(
                9202,
                "ARMOR",
                "JUnit Armor",
                0,
                10,
                0
        );

        testPotion = ItemFactory.createItem(
                9203,
                "POTION",
                "JUnit Potion",
                0,
                0,
                25
        );

        // Clean up old test data in case a previous test failed
        itemDAO.delete(testWeapon);
        itemDAO.delete(testArmor);
        itemDAO.delete(testPotion);
    }

    @After
    public void tearDown() {
        if (itemDAO != null) {
            itemDAO.delete(testWeapon);
            itemDAO.delete(testArmor);
            itemDAO.delete(testPotion);
        }

        if (dbm != null) {
            dbm.closeConnection();
        }
    }

    @Test
    public void insert_weapon_canBeLoadedByID() {
        // Act
        itemDAO.insert(testWeapon);

        Item loadedItem = itemDAO.loadByID(9201);

        // Assert
        assertNotNull(loadedItem);
        assertTrue(loadedItem instanceof Weapon);
        assertEquals(9201, loadedItem.getID());
        assertEquals("JUnit Sword", loadedItem.name);
    }

    @Test
    public void insert_armor_canBeLoadedByID() {
        // Act
        itemDAO.insert(testArmor);

        Item loadedItem = itemDAO.loadByID(9202);

        // Assert
        assertNotNull(loadedItem);
        assertTrue(loadedItem instanceof Armor);
        assertEquals(9202, loadedItem.getID());
        assertEquals("JUnit Armor", loadedItem.name);
    }

    @Test
    public void insert_potion_canBeLoadedByID() {
        // Act
        itemDAO.insert(testPotion);

        Item loadedItem = itemDAO.loadByID(9203);

        // Assert
        assertNotNull(loadedItem);
        assertTrue(loadedItem instanceof Potion);
        assertEquals(9203, loadedItem.getID());
        assertEquals("JUnit Potion", loadedItem.name);
    }

    @Test
    public void save_newItem_insertsItem() {
        // Act
        itemDAO.save(testWeapon);

        // Assert
        assertTrue(itemDAO.elementExists(testWeapon));

        Item loadedItem = itemDAO.loadByID(9201);
        assertNotNull(loadedItem);
        assertEquals("JUnit Sword", loadedItem.name);
    }

    @Test
    public void save_existingItem_updatesItem() {
        // Arrange
        itemDAO.save(testWeapon);

        Item updatedWeapon = ItemFactory.createItem(
                9201,
                "WEAPON",
                "Updated JUnit Sword",
                30,
                0,
                0
        );

        // Act
        itemDAO.save(updatedWeapon);

        Item loadedItem = itemDAO.loadByID(9201);

        // Assert
        assertNotNull(loadedItem);
        assertTrue(loadedItem instanceof Weapon);
        assertEquals(9201, loadedItem.getID());
        assertEquals("Updated JUnit Sword", loadedItem.name);
    }

    @Test
    public void update_existingItem_changesItemData() {
        // Arrange
        itemDAO.insert(testPotion);

        Item updatedPotion = ItemFactory.createItem(
                9203,
                "POTION",
                "Updated JUnit Potion",
                0,
                0,
                50
        );

        // Act
        itemDAO.update(updatedPotion);

        Item loadedItem = itemDAO.loadByID(9203);

        // Assert
        assertNotNull(loadedItem);
        assertTrue(loadedItem instanceof Potion);
        assertEquals("Updated JUnit Potion", loadedItem.name);
    }

    @Test
    public void loadByID_whenItemDoesNotExist_returnsNull() {
        // Act
        Item loadedItem = itemDAO.loadByID(9999);

        // Assert
        assertNull(loadedItem);
    }

    @Test
    public void loadAll_returnsInsertedItems() {
        // Arrange
        itemDAO.save(testWeapon);
        itemDAO.save(testArmor);
        itemDAO.save(testPotion);

        // Act
        ArrayList<Item> items = itemDAO.loadAll();

        // Assert
        assertNotNull(items);
        assertTrue(items.size() >= 3);

        boolean foundWeapon = false;
        boolean foundArmor = false;
        boolean foundPotion = false;

        for (Item item : items) {
            if (item.getID() == 9201 && item instanceof Weapon) {
                foundWeapon = true;
            }

            if (item.getID() == 9202 && item instanceof Armor) {
                foundArmor = true;
            }

            if (item.getID() == 9203 && item instanceof Potion) {
                foundPotion = true;
            }
        }

        assertTrue(foundWeapon);
        assertTrue(foundArmor);
        assertTrue(foundPotion);
    }

    @Test
    public void elementExists_whenItemExists_returnsTrue() {
        // Arrange
        itemDAO.insert(testArmor);

        // Act
        boolean exists = itemDAO.elementExists(testArmor);

        // Assert
        assertTrue(exists);
    }

    @Test
    public void elementExists_whenItemDoesNotExist_returnsFalse() {
        // Act
        boolean exists = itemDAO.elementExists(testWeapon);

        // Assert
        assertFalse(exists);
    }

    @Test
    public void delete_existingItem_removesItemFromDatabase() {
        // Arrange
        itemDAO.insert(testPotion);

        assertTrue(itemDAO.elementExists(testPotion));

        // Act
        itemDAO.delete(testPotion);

        // Assert
        assertFalse(itemDAO.elementExists(testPotion));
        assertNull(itemDAO.loadByID(9203));
    }
}
