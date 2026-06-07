/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

/**
 *
 * @author lukea
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pdc_project1.Item;

public class ItemDAO {
    private DBManager DBM;

    public ItemDAO(DBManager DBM) {
        this.DBM = DBM;
    }
    
     public void saveItem(int itemId, Item item) {
        if (itemExists(itemId)) {
            updateItem(itemId, item);
        } else {
            insertItem(itemId, item);
        }
    }

    // INSERT item into database
    public void insertItem(int itemId, Item item) {
        ItemSQLAdapter adapter = new ItemSQLAdapter(item);

        String sql = "INSERT INTO ITEM "
                + "(ITEM_ID, NAME, ITEM_TYPE, ATTACK_MODIFIER, DEFENSE_MODIFIER, HEAL_AMOUNT) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBM.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, itemId);
            ps.setString(2, adapter.getName());
            ps.setString(3, adapter.getItemType());
            ps.setInt(4, adapter.getAttackModifier());
            ps.setInt(5, adapter.getDefenseModifier());
            ps.setInt(6, adapter.getHealAmount());

            ps.executeUpdate();

            System.out.println("Item inserted: " + adapter.getName());

        } catch (SQLException e) {
            System.out.println("Error inserting item.");
            e.printStackTrace();
        }
    }

    // UPDATE item in database
    public void updateItem(int itemId, Item item) {
        ItemSQLAdapter adapter = new ItemSQLAdapter(item);

        String sql = "UPDATE ITEM SET "
                + "NAME = ?, "
                + "ITEM_TYPE = ?, "
                + "ATTACK_MODIFIER = ?, "
                + "DEFENSE_MODIFIER = ?, "
                + "HEAL_AMOUNT = ? "
                + "WHERE ITEM_ID = ?";

        try (Connection conn = DBM.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, adapter.getName());
            ps.setString(2, adapter.getItemType());
            ps.setInt(3, adapter.getAttackModifier());
            ps.setInt(4, adapter.getDefenseModifier());
            ps.setInt(5, adapter.getHealAmount());
            ps.setInt(6, itemId);

            ps.executeUpdate();

            System.out.println("Item updated: " + adapter.getName());

        } catch (SQLException e) {
            System.out.println("Error updating item.");
            e.printStackTrace();
        }
    }

    // READ one item by ID
    public Item loadItemById(int itemId) {
        String sql = "SELECT * FROM ITEM WHERE ITEM_ID = ?";

        try (Connection conn = DBM.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, itemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("NAME");
                    String itemType = rs.getString("ITEM_TYPE");
                    int attackModifier = rs.getInt("ATTACK_MODIFIER");
                    int defenseModifier = rs.getInt("DEFENSE_MODIFIER");
                    int healAmount = rs.getInt("HEAL_AMOUNT");

                    //return ItemFactory.createItem(
                          //  itemType,
                          //  name,
                          //  attackModifier,
                          //  defenseModifier,
                           // healAmount
                    //);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading item with ID: " + itemId);
            e.printStackTrace();
        }

        return null;
    }

    // READ all items from database
    public ArrayList<Item> loadAllItems() {
        ArrayList<Item> items = new ArrayList<>();

        String sql = "SELECT * FROM ITEM ORDER BY ITEM_ID";

        try (Connection conn = DBM.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("NAME");
                String itemType = rs.getString("ITEM_TYPE");
                int attackModifier = rs.getInt("ATTACK_MODIFIER");
                int defenseModifier = rs.getInt("DEFENSE_MODIFIER");
                int healAmount = rs.getInt("HEAL_AMOUNT");

                //Item item = ItemFactory.createItem(
                       // itemType,
                        //name,
                       // attackModifier,
                       // defenseModifier,
                       // healAmount
               // );

               // items.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Error loading all items.");
            e.printStackTrace();
        }

        return items;
    }

    // Check whether an item already exists
    public boolean itemExists(int itemId) {
        String sql = "SELECT ITEM_ID FROM ITEM WHERE ITEM_ID = ?";

        try (Connection conn = DBM.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, itemId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("Error checking item existence.");
            e.printStackTrace();
        }

        return false;
    }

    // DELETE item from database
    public void deleteItem(int itemId) {
        String sql = "DELETE FROM ITEM WHERE ITEM_ID = ?";

        try (Connection conn = DBM.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, itemId);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Item deleted.");
            } else {
                System.out.println("No item found with ID: " + itemId);
            }

        } catch (SQLException e) {
            System.out.println("Error deleting item.");
            e.printStackTrace();
        }
    }
    
    
}
