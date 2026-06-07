/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lukea
 */
package database;

import java.util.ArrayList;
import pdc_project1.Inventory;
import pdc_project1.Item;

public class InventorySQLAdapter {

    private int playerID;
    private Inventory inventory;

    public InventorySQLAdapter(int playerID, Inventory inventory) {
        this.playerID = playerID;
        this.inventory = inventory;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getInventoryIndex(int index) {
        return index;
    }

    public int getItemID(int index) {
        return inventory.items.get(index).id;
    }

    public Item getItem(int index) {
        return inventory.items.get(index);
    }

    public int getInventorySize() {
        return inventory.items.size();
    }

    public ArrayList<Item> getItems() {
        return inventory.getItems();
    }
}
