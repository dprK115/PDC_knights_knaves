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

import pdc_project1.Encounter;
import pdc_project1.EncounterFactory;
import pdc_project1.Item;
import pdc_project1.Player;

public class EncounterDAO implements Dao<Encounter> {

    private DBManager DBM;
    private Connection conn;
    private ItemDAO itemDAO;
    private Player player;
    EncounterSQLAdapter adapter;

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
        if (elementExists(encounter)) {
            update(encounter);
        } else {
            insert(encounter);
        }
    }

    @Override
    public void insert(Encounter encounter) {
        adapter = new EncounterSQLAdapter(encounter);

        String sql = "INSERT INTO ENCOUNTER "
                + "(ENCOUNTER_ID, ENCOUNTER_TYPE, ENCOUNTER_TEXT, ENEMY_NAME, ENEMY_LEVEL, LOOT_ITEM_ID) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adapter.getEncounterID());
            ps.setString(2, adapter.getEncounterType());
            ps.setString(3, adapter.getStoryText());
            ps.setString(4, adapter.getEnemyName());
            ps.setInt(5, adapter.getEnemyLevel());
            if(adapter.getLootItemID() > 0){
                ps.setInt(6, adapter.getLootItemID());
            }else{
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            
            

            ps.executeUpdate();

            System.out.println("Encounter inserted: " + adapter.getEncounterType());

        } catch (SQLException e) {
            System.out.println("Error inserting encounter.");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Encounter encounter) {
        adapter = new EncounterSQLAdapter(encounter);
        int encounterID = adapter.getEncounterID();

        String sql = "UPDATE ENCOUNTER SET "
                + "ENCOUNTER_TYPE = ?, "
                + "ENCOUNTER_TEXT = ?, "
                + "ENEMY_NAME = ?, "
                + "ENEMY_LEVEL = ?, "
                + "LOOT_ITEM_ID = ? "
                + "WHERE ENCOUNTER_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, adapter.getEncounterType());
            ps.setString(2, adapter.getStoryText());
            ps.setString(3, adapter.getEnemyName());
            ps.setInt(4, adapter.getEnemyLevel());
            if(adapter.getLootItemID() > 0){
                ps.setInt(5, adapter.getLootItemID());
            }else{
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.setInt(6, adapter.getEncounterID());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Encounter updated with ID: " + encounterID);
            } else {
                System.out.println("No encounter found with ID: " + encounterID);
            }

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
                    String storyText = rs.getString("ENCOUNTER_TEXT");
                    String enemyName = rs.getString("ENEMY_NAME");
                    int enemyLevel = rs.getInt("ENEMY_LEVEL");
                    int lootItemID = rs.getInt("LOOT_ITEM_ID");
                    int encounterID = rs.getInt("ENCOUNTER_ID");
                    
                    Item loot = null;

                    if (encounterType.equalsIgnoreCase("COMBAT")) {
                        loot = itemDAO.loadByID(lootItemID);
                    }

                    return EncounterFactory.createEncounter(
                            encounterID,
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

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String encounterType = rs.getString("ENCOUNTER_TYPE");
                String storyText = rs.getString("ENCOUNTER_TEXT");
                String enemyName = rs.getString("ENEMY_NAME");
                int enemyLevel = rs.getInt("ENEMY_LEVEL");
                int lootItemID = rs.getInt("LOOT_ITEM_ID");
                int encounterID = rs.getInt("ENCOUNTER_ID");

                Item loot = null;

                if (encounterType.equalsIgnoreCase("COMBAT")) {
                    loot = itemDAO.loadByID(lootItemID);
                }

                Encounter encounter = EncounterFactory.createEncounter(
                        encounterID,
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
        adapter = new EncounterSQLAdapter(encounter);
        int encounterID = adapter.getEncounterID();
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
        adapter = new EncounterSQLAdapter(encounter);
        int encounterID = adapter.getEncounterID();
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
}
