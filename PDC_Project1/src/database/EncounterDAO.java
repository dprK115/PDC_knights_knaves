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

import pdc_project1.Combat;
import pdc_project1.Encounter;
import pdc_project1.EncounterFactory;
import pdc_project1.Item;
import pdc_project1.Player;

public class EncounterDAO implements Dao<Encounter> {

    private DBManager DBM;
    private Connection conn;
    private ItemDAO itemDAO;
    private Player player;

    public EncounterDAO(DBManager DBM, Player player) {
        this.DBM = DBM;
        this.player = player;

        try {
            this.conn = DBM.getConnection();
            this.itemDAO = new ItemDAO(DBM);
        } catch (SQLException e) {
            System.out.println("Error getting connection.");
            e.printStackTrace();
        }
    }

    @Override
    public void save(Encounter encounter) {
        /*
         * Because Encounter currently does not store an encounterID,
         * this will insert the encounter as new data.
         */
        insert(encounter);
    }

    @Override
    public void insert(Encounter encounter) {
        int encounterID = getNextEncounterID();

        EncounterSQLAdapter adapter = new EncounterSQLAdapter( encounter);

        String sql = "INSERT INTO ENCOUNTER "
                + "(ENCOUNTER_ID, ENCOUNTER_TYPE, STORY_TEXT, ENEMY_NAME, ENEMY_LEVEL, LOOT_ITEM_ID) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adapter.getEncounterID());
            ps.setString(2, adapter.getEncounterType());
            ps.setString(3, adapter.getStoryText());
            ps.setString(4, adapter.getEnemyName());
            ps.setInt(5, adapter.getEnemyLevel());
            ps.setInt(6, adapter.getLootItemID());

            ps.executeUpdate();

            System.out.println("Encounter inserted: " + adapter.getEncounterType());

        } catch (SQLException e) {
            System.out.println("Error inserting encounter.");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Encounter encounter) {
        /*
         * This cannot work properly unless Encounter has an ID field.
         * Use updateByID instead.
         */
        System.out.println("Cannot update Encounter without an encounter ID.");
    }

    public void updateByID(int encounterID, Encounter encounter) {
        EncounterSQLAdapter adapter = new EncounterSQLAdapter( encounter);

        String sql = "UPDATE ENCOUNTER SET "
                + "ENCOUNTER_TYPE = ?, "
                + "STORY_TEXT = ?, "
                + "ENEMY_NAME = ?, "
                + "ENEMY_LEVEL = ?, "
                + "LOOT_ITEM_ID = ? "
                + "WHERE ENCOUNTER_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, adapter.getEncounterType());
            ps.setString(2, adapter.getStoryText());
            ps.setString(3, adapter.getEnemyName());
            ps.setInt(4, adapter.getEnemyLevel());
            ps.setInt(5, adapter.getLootItemID());
            ps.setInt(6, adapter.getEncounterID());

            ps.executeUpdate();

            System.out.println("Encounter updated with ID: " + encounterID);

        } catch (SQLException e) {
            System.out.println("Error updating encounter.");
            e.printStackTrace();
        }
    }

    @Override
    public Encounter loadByID(int id) {
        String sql = "SELECT * FROM ENCOUNTER WHERE ENCOUNTER_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    String encounterType = rs.getString("ENCOUNTER_TYPE");
                    String storyText = rs.getString("STORY_TEXT");
                    String enemyName = rs.getString("ENEMY_NAME");
                    int enemyLevel = rs.getInt("ENEMY_LEVEL");
                    int lootItemID = rs.getInt("LOOT_ITEM_ID");

                    Item loot = null;

                    if (encounterType.equalsIgnoreCase("COMBAT")) {
                        loot = itemDAO.loadByID(lootItemID);
                    }

                    return EncounterFactory.createEncounter(
                            encounterType,
                            player,
                            storyText,
                            enemyName,
                            enemyLevel,
                            loot
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading encounter with ID: " + id);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Encounter> loadAll() {
        ArrayList<Encounter> encounters = new ArrayList<>();

        String sql = "SELECT * FROM ENCOUNTER ORDER BY ENCOUNTER_ID";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String encounterType = rs.getString("ENCOUNTER_TYPE");
                String storyText = rs.getString("STORY_TEXT");
                String enemyName = rs.getString("ENEMY_NAME");
                int enemyLevel = rs.getInt("ENEMY_LEVEL");
                int lootItemID = rs.getInt("LOOT_ITEM_ID");

                Item loot = null;

                if (encounterType.equalsIgnoreCase("COMBAT")) {
                    loot = itemDAO.loadByID(lootItemID);
                }

                Encounter encounter = EncounterFactory.createEncounter(
                        encounterType,
                        player,
                        storyText,
                        enemyName,
                        enemyLevel,
                        loot
                );

                encounters.add(encounter);
            }

        } catch (SQLException e) {
            System.out.println("Error loading all encounters.");
            e.printStackTrace();
        }

        return encounters;
    }

    @Override
    public boolean elementExists(Encounter encounter) {
        /*
         * Without encounterID inside Encounter, this cannot reliably check
         * if this exact encounter exists.
         */
        return false;
    }

    public boolean elementExistsByID(int encounterID) {
        String sql = "SELECT ENCOUNTER_ID FROM ENCOUNTER WHERE ENCOUNTER_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, encounterID);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("Error checking encounter existence.");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void delete(Encounter encounter) {
        /*
         * This cannot work properly unless Encounter has an ID field.
         * Use deleteByID instead.
         */
        System.out.println("Cannot delete Encounter without an encounter ID.");
    }

    public void deleteByID(int encounterID) {
        String sql = "DELETE FROM ENCOUNTER WHERE ENCOUNTER_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, encounterID);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Encounter deleted.");
            } else {
                System.out.println("No encounter found with ID: " + encounterID);
            }

        } catch (SQLException e) {
            System.out.println("Error deleting encounter.");
            e.printStackTrace();
        }
    }

    private int getNextEncounterID() {
        String sql = "SELECT MAX(ENCOUNTER_ID) FROM ENCOUNTER";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) + 1;
            }

        } catch (SQLException e) {
            System.out.println("Error getting next encounter ID.");
            e.printStackTrace();
        }

        return 1;
    }
}
