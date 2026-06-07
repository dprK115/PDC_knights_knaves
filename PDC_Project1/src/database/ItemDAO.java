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
    private Connection conn;

    public ItemDAO(DBManager DBM) {
        this.DBM = DBM;
        try {
            this.conn = DBM.getConnection();
        } catch (SQLException e) {
            System.out.println("Error getting connection.");
            e.printStackTrace();
        }

    }

    public void saveItem(Item item) {
        if (itemExists(item)) {
            updateItem(item);
        } else {
            insertItem(item);
        }
    }

    // INSERT item into database
    public void insertItem(Item item) {

        ItemSQLAdapter adapter = new ItemSQLAdapter(item);

        String sql = "INSERT INTO ITEM "
                + "(ITEM_ID, NAME, ITEM_TYPE, ATTACK_MODIFIER, DEFENSE_MODIFIER, HEAL_AMOUNT) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adapter.getID());
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
    public void updateItem(Item item) {
        ItemSQLAdapter adapter = new ItemSQLAdapter(item);

        String sql = "UPDATE ITEM SET "
                + "NAME = ?, "
                + "ITEM_TYPE = ?, "
                + "ATTACK_MODIFIER = ?, "
                + "DEFENSE_MODIFIER = ?, "
                + "HEAL_AMOUNT = ? "
                + "WHERE ITEM_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, adapter.getName());
            ps.setString(2, adapter.getItemType());
            ps.setInt(3, adapter.getAttackModifier());
            ps.setInt(4, adapter.getDefenseModifier());
            ps.setInt(5, adapter.getHealAmount());
            ps.setInt(6, adapter.getID());

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

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

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

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

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
    public boolean itemExists(Item item) {
        ItemSQLAdapter adapter = new ItemSQLAdapter(item);
        String sql = "SELECT ITEM_ID FROM ITEM WHERE ITEM_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adapter.getID());

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
    public void deleteItem(Item item) {
        String sql = "DELETE FROM ITEM WHERE ITEM_ID = ?";
        ItemSQLAdapter adapter = new ItemSQLAdapter(item);
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adapter.getID());

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Item deleted.");
            } else {
                System.out.println("No item found with ID: " + adapter.getID());
            }

        } catch (SQLException e) {
            System.out.println("Error deleting item.");
            e.printStackTrace();
        }
    }

}
