/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lukea
 */
import database.InventoryDAO;
import database.Dao;
import database.PlayerDAO;
import database.EncounterDAO;
import database.DBManager;
import java.util.ArrayList;
import java.util.List;

public class SaveLoader {

    Player player;
    Dao dao;
    DBManager dbm;
    Inventory inventory;
    StoryManager sm;

    public SaveLoader() {
        dbm = new DBManager();
        inventory = new Inventory();
    }

    public GameState loadSave(int playerID) {
        this.player = loadPlayer(playerID);
        this.inventory = loadInventory(playerID);
        this.player.inventory = this.inventory;
        
        dao = new EncounterDAO(dbm, player);
        var encounters = (ArrayList) dao.loadAll();
        sm = new StoryManager(encounters);
        sm.index = player.currentStoryIndex -1;
        
        GameState loadedGS = new GameState(player, sm);
        return loadedGS;

    }

    private Player loadPlayer(int playerID) {
        dao = new PlayerDAO(dbm);
        return (Player) dao.loadByID(playerID);
    }

    private Inventory loadInventory(int playerID) {
        dao = new InventoryDAO(dbm, playerID);
        return (Inventory) dao.loadByID(playerID);

    }

    public List<Player> getSaveList() {
        dao = new PlayerDAO(dbm);
        List playerList = dao.loadAll();
        return playerList;

    }

}
