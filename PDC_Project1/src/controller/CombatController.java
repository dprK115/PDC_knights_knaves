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
import pdc_project1.Enemy;

public class CombatController {

    private Player player;
    private Enemy enemy;

    public CombatController(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public void attack() {
        player.attack(enemy);
    }

    public void defend() {
        player.defend();
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
