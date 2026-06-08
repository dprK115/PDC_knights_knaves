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
import java.sql.Types;
import java.util.ArrayList;

import pdc_project1.Player;
import pdc_project1.Item;
import pdc_project1.Weapon;
import pdc_project1.Armor;

public class PlayerDAO implements Dao<Player> {

    private DBManager DBM;
    private Connection conn;
    private ItemDAO itemDAO;

    public PlayerDAO(DBManager DBM) {
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
    public void save(Player player) {
        if (elementExists(player)) {
            update(player);
        } else {
            insert(player);
        }
    }

    @Override
    public void insert(Player player) {
        PlayerSQLAdapter adapter = new PlayerSQLAdapter(player);

        String sql = "INSERT INTO PLAYER "
                + "(PLAYER_ID, NAME, LEVEL, HEALTH, EQUIPPED_WEAPON_ID, EQUIPPED_ARMOR_ID, XP, DIFFICULTY) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adapter.getPlayerID());
            ps.setString(2, adapter.getName());
            ps.setInt(3, adapter.getLevel());
            ps.setInt(4, adapter.getHealth());

            if (adapter.getEquippedWeaponID() > 0) {
                ps.setInt(5, adapter.getEquippedWeaponID());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            if (adapter.getEquippedArmorID() > 0) {
                ps.setInt(6, adapter.getEquippedArmorID());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            ps.setInt(7, adapter.getXp());
            ps.setString(8, adapter.getDifficulty());

            ps.executeUpdate();

            System.out.println("Player inserted: " + adapter.getName());

        } catch (SQLException e) {
            System.out.println("Error inserting player.");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Player player) {
        PlayerSQLAdapter adapter = new PlayerSQLAdapter(player);

        String sql = "UPDATE PLAYER SET "
                + "NAME = ?, "
                + "LEVEL = ?, "
                + "HEALTH = ?, "
                + "EQUIPPED_WEAPON_ID = ?, "
                + "EQUIPPED_ARMOR_ID = ?, "
                + "XP = ?, "
                + "DIFFICULTY = ? "
                + "WHERE PLAYER_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, adapter.getName());
            ps.setInt(2, adapter.getLevel());
            ps.setInt(3, adapter.getHealth());

            if (adapter.getEquippedWeaponID() > 0) {
                ps.setInt(4, adapter.getEquippedWeaponID());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            if (adapter.getEquippedArmorID() > 0) {
                ps.setInt(5, adapter.getEquippedArmorID());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.setInt(6, adapter.getXp());
            ps.setString(7, adapter.getDifficulty());
            ps.setInt(8, adapter.getPlayerID());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Player updated: " + adapter.getName());
            } else {
                System.out.println("No player found with ID: " + adapter.getPlayerID());
            }

        } catch (SQLException e) {
            System.out.println("Error updating player.");
            e.printStackTrace();
        }
    }

    @Override
    public Player loadByID(int id) {
        String sql = "SELECT * FROM PLAYER WHERE PLAYER_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    int playerID = rs.getInt("PLAYER_ID");
                    String name = rs.getString("NAME");
                    int level = rs.getInt("LEVEL");
                    int health = rs.getInt("HEALTH");
                    int xp = rs.getInt("XP");
                    String difficulty = rs.getString("DIFFICULTY");

                    int weaponID = rs.getInt("EQUIPPED_WEAPON_ID");
                    boolean weaponWasNull = rs.wasNull();

                    int armorID = rs.getInt("EQUIPPED_ARMOR_ID");
                    boolean armorWasNull = rs.wasNull();

                    Item weapon = null;
                    Item armor = null;

                    if (!weaponWasNull) {
                        weapon = itemDAO.loadByID(weaponID);
                    }

                    if (!armorWasNull) {
                        armor = itemDAO.loadByID(armorID);
                    }

                    Player player = new Player();

                    player.setPlayerID(playerID);
                    player.setName(name);
                    player.setLevel(level);
                    player.setHealth(health);
                    player.setXp(xp);
                    player.setDifficulty(difficulty);

                    if (weapon != null) {
                        player.setEquippedWeapon((Weapon) weapon);
                    }

                    if (armor != null) {
                        player.setEquippedArmor((Armor) armor);
                    }

                    return player;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading player with ID: " + id);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Player> loadAll() {
        ArrayList<Player> players = new ArrayList<>();

        String sql = "SELECT * FROM PLAYER ORDER BY PLAYER_ID";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int playerID = rs.getInt("PLAYER_ID");

                Player player = loadByID(playerID);

                if (player != null) {
                    players.add(player);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error loading all players.");
            e.printStackTrace();
        }

        return players;
    }

    @Override
    public boolean elementExists(Player player) {
        PlayerSQLAdapter adapter = new PlayerSQLAdapter(player);

        String sql = "SELECT PLAYER_ID FROM PLAYER WHERE PLAYER_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adapter.getPlayerID());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("Error checking player existence.");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void delete(Player player) {
        PlayerSQLAdapter adapter = new PlayerSQLAdapter(player);

        String sql = "DELETE FROM PLAYER WHERE PLAYER_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adapter.getPlayerID());

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Player deleted: " + adapter.getName());
            } else {
                System.out.println("No player found with ID: " + adapter.getPlayerID());
            }

        } catch (SQLException e) {
            System.out.println("Error deleting player.");
            e.printStackTrace();
        }
    }
}
