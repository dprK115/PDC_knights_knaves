/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author lukea
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    
    public void establishConnection(){
        try {
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException ex) {

        }
    }
    
}
