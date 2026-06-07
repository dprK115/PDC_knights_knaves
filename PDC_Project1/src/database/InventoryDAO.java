/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author lukea
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pdc_project1.Item;

public class InventoryDAO implements Dao<InventoryEntry> {

    private ArrayList<InventoryEntry> inventoryList;
    private DBManager DBM;
    private Connection conn;
    private ItemDAO itemDAO;

    public InventoryDAO(DBManager DBM) {
        this.DBM = DBM;

        try {
            this.conn = DBM.getConnection();
            this.itemDAO = new ItemDAO(DBM);
        } catch (SQLException e) {
            System.out.println("Error getting connection.");
            e.printStackTrace();
        }
    }

    @Override
    public void save(InventoryEntry entry) {
        if (elementExists(entry)) {
            update(entry);
        } else {
            insert(entry);
        }
    }

    @Override
    public void insert(InventoryEntry entry) {
        String sql = "INSERT INTO INVENTORY "
                + "(INVENTORY_ID, PLAYER_ID, ITEM_ID, QUANTITY) "
                + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, entry.getInventoryID());
            ps.setInt(2, entry.getPlayerID());
            ps.setInt(3, entry.getItem().getID());
            ps.setInt(4, entry.getQuantity());

            ps.executeUpdate();

            System.out.println("Inventory item inserted: " + entry.getItem().getName());

        } catch (SQLException e) {
            System.out.println("Error inserting inventory item.");
            e.printStackTrace();
        }
    }

    @Override
    public void update(InventoryEntry entry) {
        String sql = "UPDATE INVENTORY SET "
                + "PLAYER_ID = ?, "
                + "ITEM_ID = ?, "
                + "QUANTITY = ? "
                + "WHERE INVENTORY_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, entry.getPlayerID());
            ps.setInt(2, entry.getItem().getID());
            ps.setInt(3, entry.getQuantity());
            ps.setInt(4, entry.getInventoryID());

            ps.executeUpdate();

            System.out.println("Inventory item updated: " + entry.getItem().getName());

        } catch (SQLException e) {
            System.out.println("Error updating inventory item.");
            e.printStackTrace();
        }
    }

    @Override
    public InventoryEntry loadByID(int id) {
        String sql = "SELECT * FROM INVENTORY WHERE INVENTORY_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    int inventoryID = rs.getInt("INVENTORY_ID");
                    int playerID = rs.getInt("PLAYER_ID");
                    int itemID = rs.getInt("ITEM_ID");
                    int quantity = rs.getInt("QUANTITY");

                    Item item = itemDAO.loadByID(itemID);

                    return new InventoryEntry(inventoryID, playerID, item, quantity);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading inventory item with ID: " + id);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<InventoryEntry> loadAll() {
        inventoryList = new ArrayList<>();

        String sql = "SELECT * FROM INVENTORY ORDER BY INVENTORY_ID";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int inventoryID = rs.getInt("INVENTORY_ID");
                int playerID = rs.getInt("PLAYER_ID");
                int itemID = rs.getInt("ITEM_ID");
                int quantity = rs.getInt("QUANTITY");

                Item item = itemDAO.loadByID(itemID);

                InventoryEntry entry = new InventoryEntry(
                        inventoryID,
                        playerID,
                        item,
                        quantity
                );

                inventoryList.add(entry);
            }

        } catch (SQLException e) {
            System.out.println("Error loading all inventory items.");
            e.printStackTrace();
        }

        return inventoryList;
    }

    public ArrayList<InventoryEntry> loadByPlayerID(int playerID) {
        ArrayList<InventoryEntry> playerInventory = new ArrayList<>();

        String sql = "SELECT * FROM INVENTORY WHERE PLAYER_ID = ? ORDER BY INVENTORY_ID";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playerID);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int inventoryID = rs.getInt("INVENTORY_ID");
                    int itemID = rs.getInt("ITEM_ID");
                    int quantity = rs.getInt("QUANTITY");

                    Item item = itemDAO.loadByID(itemID);

                    InventoryEntry entry = new InventoryEntry(
                            inventoryID,
                            playerID,
                            item,
                            quantity
                    );

                    playerInventory.add(entry);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading inventory for player ID: " + playerID);
            e.printStackTrace();
        }

        return playerInventory;
    }

    @Override
    public boolean elementExists(InventoryEntry entry) {
        String sql = "SELECT INVENTORY_ID FROM INVENTORY WHERE INVENTORY_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, entry.getInventoryID());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("Error checking inventory item existence.");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void delete(InventoryEntry entry) {
        String sql = "DELETE FROM INVENTORY WHERE INVENTORY_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, entry.getInventoryID());

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Inventory item deleted.");
            } else {
                System.out.println("No inventory item found with ID: " + entry.getInventoryID());
            }

        } catch (SQLException e) {
            System.out.println("Error deleting inventory item.");
            e.printStackTrace();
        }
    }

    public void deleteByPlayerID(int playerID) {
        String sql = "DELETE FROM INVENTORY WHERE PLAYER_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playerID);
            ps.executeUpdate();

            System.out.println("Inventory deleted for player ID: " + playerID);

        } catch (SQLException e) {
            System.out.println("Error deleting inventory for player ID: " + playerID);
            e.printStackTrace();
        }
    }
}