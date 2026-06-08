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
public class DBInitialiser {

    private DBManager dbManager;

    public DBInitialiser(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void createTables() {
        createItemTable();
        createPlayerTable();
        createInventoryTable();
        createStoryProgressTable();
        createEncounterTable();
    }

    private void createPlayerTable() {
        String sql = "CREATE TABLE PLAYER ("
                + "PLAYER_ID INT PRIMARY KEY, "
                + "NAME VARCHAR(50), "
                + "LEVEL INT, "
                + "HEALTH INT, "
                + "EQUIPPED_WEAPON_ID INT, "
                + "EQUIPPED_ARMOR_ID INT,"
                + "XP INT,"
                + "DIFFICULTY INT,"
                + "FOREIGN KEY (EQUIPPED_WEAPON_ID) REFERENCES ITEM(ITEM_ID),"
                + "FOREIGN KEY (EQUIPPED_ARMOR_ID) REFERENCES ITEM(ITEM_ID)"
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
                + "INVENTORY_INDEX INT, "
                + "PRIMARY KEY (INVENTORY_INDEX),"
                + "FOREIGN KEY (PLAYER_ID) REFERENCES PLAYER(PLAYER_ID), "
                + "FOREIGN KEY (ITEM_ID) REFERENCES ITEM(ITEM_ID)"
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
                + "ENCOUNTER_TEXT VARCHAR(500), "
                + "ENEMY_NAME VARCHAR(50), "
                + "LOOT_ITEM_ID INT,"
                + "ENEMY_LEVEL INT,"
                + "FOREIGN KEY (LOOT_ITEM_ID) REFERENCES ITEM(ITEM_ID)"
                + ")";

        executeCreate(sql, "ENCOUNTER");
    }

    private void executeCreate(String sql, String tableName) {
        try (Connection conn = dbManager.getConnection(); Statement stmt = conn.createStatement()) {

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
