/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author lukea
 */
public class DBIntialiser {

    private DBManager dbManager;

    public DBIntialiser(DBManager dbManager) {
        this.dbManager = dbManager;
    }
    


    public void createTables() {
        createPlayerTable();
        createItemTable();
        createInventoryTable();
        createStoryProgressTable();
        createEncounterTable();
    }

    private void createPlayerTable() {
        String sql = "CREATE TABLE PLAYER ("
                + "PLAYER_ID INT PRIMARY KEY, "
                + "NAME VARCHAR(50), "
                + "CONSTITUTION INT, "
                + "HEALTH INT, "
                + "ATTACK INT, "
                + "DEFENSE INT"
                + ")";

        executeCreate(sql, "PLAYER");
    }

    private void createItemTable() {
        String sql = "CREATE TABLE ITEM ("
                + "ITEM_ID INT PRIMARY KEY, "
                + "NAME VARCHAR(50), "
                + "ITEM_TYPE VARCHAR(20), "
                + "ATTACK_MODIFIER INT, "
                + "DEFENSE_MODIFIER INT, "
                + "HEAL_AMOUNT INT"
                + ")";

        executeCreate(sql, "ITEM");
    }

    private void createInventoryTable() {
        String sql = "CREATE TABLE INVENTORY ("
                + "PLAYER_ID INT, "
                + "ITEM_ID INT, "
                + "QUANTITY INT, "
                + "PRIMARY KEY (PLAYER_ID, ITEM_ID)"
                + ")";

        executeCreate(sql, "INVENTORY");
    }

    private void createStoryProgressTable() {
        String sql = "CREATE TABLE STORY_PROGRESS ("
                + "PLAYER_ID INT PRIMARY KEY, "
                + "CURRENT_ENCOUNTER_INDEX INT"
                + ")";

        executeCreate(sql, "STORY_PROGRESS");
    }

    private void createEncounterTable() {
        String sql = "CREATE TABLE ENCOUNTER ("
                + "ENCOUNTER_ID INT PRIMARY KEY, "
                + "ENCOUNTER_TYPE VARCHAR(20), "
                + "TEXT VARCHAR(500), "
                + "ENEMY_NAME VARCHAR(50), "
                + "ENEMY_HEALTH INT, "
                + "ENEMY_ATTACK INT, "
                + "ENEMY_DEFENSE INT, "
                + "LOOT_ITEM_ID INT"
                + ")";

        executeCreate(sql, "ENCOUNTER");
    }

    private void executeCreate(String sql, String tableName) {
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println(tableName + " table created.");

        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {
                System.out.println(tableName + " table already exists.");
            } else {
                System.out.println("Error creating " + tableName + " table.");
                e.printStackTrace();
            }
        }
    }
}


