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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    private static final String URL = "jdbc:derby:Knights_KnavesDB;create=true"; //DBURL
    private static final String USER_NAME = "pdc"; // DB username
    private static final String PASSWORD = "pdc"; // DB password
    Connection conn;

    public DBManager() {
        establishConnection();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }

    public final void establishConnection() {
        try {
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void updateDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ResultSet queryDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }

    public void clearAllTables() {
        String[] tables = {
            "INVENTORY",
            "PLAYER",
            "ENCOUNTER",
            "ITEM"
        };

        try (Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(false);

            for (String table : tables) {
                try {
                    stmt.executeUpdate("DELETE FROM " + table);
                    System.out.println(table + " table cleared.");
                } catch (SQLException e) {
                    System.out.println("Could not clear table: " + table);

                    // X0X05 = table does not exist in Derby
                    if ("X0X05".equals(e.getSQLState())) {
                        System.out.println(table + " does not exist yet.");
                    } else {
                        throw e;
                    }
                }
            }

            conn.commit();
            System.out.println("All database tables cleared.");

        } catch (SQLException e) {
            System.out.println("Error clearing database tables.");
            e.printStackTrace();
        }
    }

    public void DropAllTables() {
        String[] tables = {
            "INVENTORY",
            "PLAYER",
            "ENCOUNTER",
            "ITEM"
        };

        try (Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(false);

            for (String table : tables) {
                try {
                    stmt.executeUpdate("DROP TABLE " + table);
                    System.out.println(table + " table cleared.");
                } catch (SQLException e) {
                    System.out.println("Could not clear table: " + table);

                    // X0X05 = table does not exist in Derby
                    if ("42Y55".equals(e.getSQLState())) {
                        System.out.println(table + " does not exist yet.");
                    } else {
                        throw e;
                    }
                }
            }

            conn.commit();
            System.out.println("All database tables dropped.");

        } catch (SQLException e) {
            System.out.println("Error clearing database tables.");
            e.printStackTrace();
        }
    }

}
