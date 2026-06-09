/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author vishw
 */
import pdc_project1.Player;

public class InventoryController {

    private Player player;

    public InventoryController(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void equipItem(int index) {
        player.equip(index);
    }

    public void useItem(int index) {
        player.use(index);
    }
}