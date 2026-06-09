/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author lukea
 */
import database.InventoryDAO;
import database.Dao;
import database.PlayerDAO;
import database.DBManager;

public class GameSaver {

    Player player;
    Dao dao;
    DBManager dbm;

    public GameSaver(GameState currentGameState) {
        this.player = currentGameState.getPlayer();
        this.dbm = new DBManager();
    }

    public void SaveGame() {
        savePlayer(player);
        saveInventory();
        dbm.closeConnection();

    }

    private void savePlayer(Player player) {
        dao = new PlayerDAO(dbm);
        dao.save(player);
        System.out.println("Player saved");

    }

    private void saveInventory() {
        dao = new InventoryDAO(dbm, player.getID());
        dao.update(player.inventory);
        System.out.println("Inventory Saved");

    }

}
