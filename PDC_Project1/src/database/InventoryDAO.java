package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pdc_project1.Inventory;
import pdc_project1.Item;

public class InventoryDAO implements Dao<Inventory> {

    private DBManager DBM;
    private Connection conn;
    private ItemDAO itemDAO;

    // This is the player whose inventory is being saved/loaded
    private int playerID;

    public InventoryDAO(DBManager DBM, int playerID) {
        this.DBM = DBM;
        this.playerID = playerID;

        try {
            this.conn = DBM.getConnection();
            this.itemDAO = new ItemDAO(DBM);
        } catch (SQLException e) {
            System.out.println("Error getting connection.");
            e.printStackTrace();
        }
    }

    @Override
    public void save(Inventory inventory) {
        if (elementExists(inventory)) {
            update(inventory);
        } else {
            insert(inventory);
        }
    }

    @Override
    public void insert(Inventory inventory) {
        InventorySQLAdapter adapter = new InventorySQLAdapter(playerID, inventory);

        String sql = "INSERT INTO INVENTORY "
                + "(PLAYER_ID, INVENTORY_INDEX, ITEM_ID) "
                + "VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < adapter.getInventorySize(); i++) {
                ps.setInt(1, adapter.getPlayerID());
                ps.setInt(2, adapter.getInventoryIndex(i));
                ps.setInt(3, adapter.getItemID(i));

                ps.executeUpdate();
            }

            System.out.println("Inventory inserted for player ID: " + adapter.getPlayerID());

        } catch (SQLException e) {
            System.out.println("Error inserting inventory.");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Inventory inventory) {
        /*
         * Simplest and safest update method:
         * 1. Delete old inventory rows for the player
         * 2. Re-insert the current ArrayList<Item>
         */
        delete(inventory);
        insert(inventory);

        System.out.println("Inventory updated for player ID: " + playerID);
    }

    @Override
    public Inventory loadByID(int id) {
        /*
         * Here, id means PLAYER_ID.
         * It loads all inventory rows for that player and rebuilds
         * the Inventory ArrayList<Item>.
         */

        Inventory inventory = new Inventory();

        String sql = "SELECT ITEM_ID FROM INVENTORY "
                + "WHERE PLAYER_ID = ? "
                + "ORDER BY INVENTORY_INDEX";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int itemID = rs.getInt("ITEM_ID");

                    Item item = itemDAO.loadByID(itemID);

                    if (item != null) {
                        inventory.addItem(item);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading inventory for player ID: " + id);
            e.printStackTrace();
        }

        return inventory;
    }

    @Override
    public ArrayList<Inventory> loadAll() {
        /*
         * This loads one Inventory object for each distinct player ID.
         * Usually your game only has one player, so loadByID(playerID)
         * is what you will use most often.
         */

        ArrayList<Inventory> inventories = new ArrayList<>();

        String sql = "SELECT DISTINCT PLAYER_ID FROM INVENTORY ORDER BY PLAYER_ID";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int foundPlayerID = rs.getInt("PLAYER_ID");
                Inventory inventory = loadByID(foundPlayerID);
                inventories.add(inventory);
            }

        } catch (SQLException e) {
            System.out.println("Error loading all inventories.");
            e.printStackTrace();
        }

        return inventories;
    }

    @Override
    public boolean elementExists(Inventory inventory) {
        String sql = "SELECT PLAYER_ID FROM INVENTORY WHERE PLAYER_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playerID);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("Error checking inventory existence.");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void delete(Inventory inventory) {
        /*
         * Since the INVENTORY table stores rows by PLAYER_ID,
         * deleting an inventory means deleting all rows for that player.
         */

        String sql = "DELETE FROM INVENTORY WHERE PLAYER_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playerID);

            int rowsDeleted = ps.executeUpdate();

            System.out.println(rowsDeleted + " inventory entries deleted for player ID: " + playerID);

        } catch (SQLException e) {
            System.out.println("Error deleting inventory.");
            e.printStackTrace();
        }
    }

    public void deleteByPlayerID(int playerID) {
        String sql = "DELETE FROM INVENTORY WHERE PLAYER_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playerID);

            int rowsDeleted = ps.executeUpdate();

            System.out.println(rowsDeleted + " inventory entries deleted for player ID: " + playerID);

        } catch (SQLException e) {
            System.out.println("Error deleting inventory for player ID: " + playerID);
            e.printStackTrace();
        }
    }
}
