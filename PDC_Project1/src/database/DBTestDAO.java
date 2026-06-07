package database;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author lukea
 */


import database.DBManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBTestDAO {
    
    

    public static void main(String[] args) {
        DBManager db = new DBManager();
        DBInitialiser dbInit = new DBInitialiser(db);
        db.DropAllTables();
        try (Connection conn = db.getConnection()) {
            System.out.println("Connected to KnightsKnavesDB successfully.");
        } catch (SQLException e) {
            System.out.println("Could not connect to database.");
            e.printStackTrace();
        }
        
        dbInit.createTables();
        
        
    }
}
