/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package database;

import java.util.ArrayList;
import model.Inventory;
import model.Item;
import model.ItemFactory;
import model.Player;
import model.Difficulty;
import java.util.ArrayList;
import model.Game;
import model.StoryManager;
import model.Encounter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author lukea
 */
public class InventoryDAOTest {

    private DBManager dbm;
    private DBInitialiser dbInitialiser;

    private ItemDAO itemDAO;
    private InventoryDAO inventoryDAO;

    private int testPlayerID;
    private PlayerDAO playerDAO;
    private Player testPlayer;

    private Item testPotion;
    private Item testWeapon;
    private Item testArmor;

    @Before
    public void setUp() {
        dbm = new DBManager();

        dbInitialiser = new DBInitialiser(dbm);
        dbInitialiser.createTables();

        testPlayerID = 9001;

        itemDAO = new ItemDAO(dbm);
        playerDAO = new PlayerDAO(dbm);
        inventoryDAO = new InventoryDAO(dbm, testPlayerID);

        inventoryDAO.deleteByPlayerID(testPlayerID);

        testPotion = ItemFactory.createItem(9101, "POTION", "JUnit Potion", 0, 0, 25);
        testWeapon = ItemFactory.createItem(9102, "WEAPON", "JUnit Sword", 15, 0, 0);
        testArmor = ItemFactory.createItem(9103, "ARMOR", "JUnit Armor", 0, 10, 0);

        itemDAO.save(testPotion);
        itemDAO.save(testWeapon);
        itemDAO.save(testArmor);

        testPlayer = new Player("JUnit Player", Difficulty.EASY);
        testPlayer.setID(testPlayerID);

        
        Game.manager = new StoryManager(new ArrayList<Encounter>());

        playerDAO.save(testPlayer);
    }

    @After
    public void tearDown() {
        inventoryDAO.deleteByPlayerID(testPlayerID);

        playerDAO.delete(testPlayer);

        itemDAO.delete(testPotion);
        itemDAO.delete(testWeapon);
        itemDAO.delete(testArmor);

        dbm.closeConnection();
    }

    @Test
    public void insert_inventoryWithItems_canBeLoadedByPlayerID() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.addItem(testPotion);
        inventory.addItem(testWeapon);

        // Act
        inventoryDAO.insert(inventory);
        Inventory loadedInventory = inventoryDAO.loadByID(testPlayerID);

        // Assert
        assertNotNull(loadedInventory);
        assertEquals(2, loadedInventory.items.size());

        assertEquals(testPotion.getID(), loadedInventory.items.get(0).getID());
        assertEquals(testWeapon.getID(), loadedInventory.items.get(1).getID());
    }

    @Test
    public void save_newInventory_insertsInventory() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.addItem(testPotion);

        // Act
        inventoryDAO.save(inventory);

        // Assert
        assertTrue(inventoryDAO.elementExists(inventory));

        Inventory loadedInventory = inventoryDAO.loadByID(testPlayerID);
        assertEquals(1, loadedInventory.items.size());
        assertEquals(testPotion.getID(), loadedInventory.items.get(0).getID());
    }

    @Test
    public void save_existingInventory_updatesInventory() {
        // Arrange
        Inventory originalInventory = new Inventory();
        originalInventory.addItem(testPotion);

        Inventory updatedInventory = new Inventory();
        updatedInventory.addItem(testWeapon);
        updatedInventory.addItem(testArmor);

        // Act
        inventoryDAO.save(originalInventory);
        inventoryDAO.save(updatedInventory);

        Inventory loadedInventory = inventoryDAO.loadByID(testPlayerID);

        // Assert
        assertEquals(2, loadedInventory.items.size());
        assertEquals(testWeapon.getID(), loadedInventory.items.get(0).getID());
        assertEquals(testArmor.getID(), loadedInventory.items.get(1).getID());
    }

    @Test
    public void update_replacesOldInventoryRows() {
        // Arrange
        Inventory originalInventory = new Inventory();
        originalInventory.addItem(testPotion);
        originalInventory.addItem(testWeapon);

        Inventory updatedInventory = new Inventory();
        updatedInventory.addItem(testArmor);

        inventoryDAO.insert(originalInventory);

        // Act
        inventoryDAO.update(updatedInventory);

        // Assert
        Inventory loadedInventory = inventoryDAO.loadByID(testPlayerID);

        assertEquals(1, loadedInventory.items.size());
        assertEquals(testArmor.getID(), loadedInventory.items.get(0).getID());
    }

    @Test
    public void loadByID_whenNoInventoryExists_returnsEmptyInventory() {
        // Act
        Inventory loadedInventory = inventoryDAO.loadByID(testPlayerID);

        // Assert
        assertNotNull(loadedInventory);
        assertEquals(0, loadedInventory.items.size());
    }

    @Test
    public void elementExists_whenInventoryExists_returnsTrue() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.addItem(testPotion);

        inventoryDAO.insert(inventory);

        // Act
        boolean exists = inventoryDAO.elementExists(inventory);

        // Assert
        assertTrue(exists);
    }

    @Test
    public void elementExists_whenInventoryDoesNotExist_returnsFalse() {
        // Arrange
        Inventory inventory = new Inventory();

        // Act
        boolean exists = inventoryDAO.elementExists(inventory);

        // Assert
        assertFalse(exists);
    }

    @Test
    public void delete_existingInventory_removesInventoryRows() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.addItem(testPotion);
        inventory.addItem(testWeapon);

        inventoryDAO.insert(inventory);
        assertTrue(inventoryDAO.elementExists(inventory));

        // Act
        inventoryDAO.delete(inventory);

        // Assert
        assertFalse(inventoryDAO.elementExists(inventory));

        Inventory loadedInventory = inventoryDAO.loadByID(testPlayerID);
        assertEquals(0, loadedInventory.items.size());
    }

    @Test
    public void deleteByPlayerID_removesInventoryRowsForThatPlayer() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.addItem(testPotion);

        inventoryDAO.insert(inventory);

        // Act
        inventoryDAO.deleteByPlayerID(testPlayerID);

        // Assert
        Inventory loadedInventory = inventoryDAO.loadByID(testPlayerID);
        assertEquals(0, loadedInventory.items.size());
    }

    @Test
    public void loadAll_returnsInventoriesForPlayersWithInventoryRows() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.addItem(testPotion);

        inventoryDAO.save(inventory);

        // Act
        ArrayList<Inventory> inventories = inventoryDAO.loadAll();

        // Assert
        assertNotNull(inventories);
        assertTrue(inventories.size() >= 1);

        boolean foundTestInventory = false;

        for (Inventory inv : inventories) {
            for (Object obj : inv.getItems()) {
                Item item = (Item) obj;

                if (item.getID() == testPotion.getID()) {
                    foundTestInventory = true;
                }
            }
        }

        assertTrue(foundTestInventory);
    }
}
