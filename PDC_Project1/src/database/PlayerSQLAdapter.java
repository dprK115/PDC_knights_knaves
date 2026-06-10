/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

/**
 *
 * @author lukea
 */
import model.Player;
import model.Item;
import model.Game;

import java.util.ArrayList;

public class PlayerSQLAdapter {

    private int playerID;
    private String name;
    private int level;
    private int health;
    private int equippedWeaponID;
    private int equippedArmorID;
    private int xp;
    private int difficulty;
    private int currentStoryIndex;

    public PlayerSQLAdapter(Player player) {
        this.playerID = player.getID();
        this.name = player.getName();
        this.level = player.getLevel();
        this.health = player.getHealth();
        this.xp = player.getXp();
        this.difficulty = player.getDifficultyModifier();
        this.currentStoryIndex = Game.manager.getIndex();

        Item equippedWeapon = player.getEquippedWeapon();
        Item equippedArmor = player.getEquippedArmor();

        if (equippedWeapon != null) {
            this.equippedWeaponID = equippedWeapon.getID();
        } else {
            this.equippedWeaponID = 0;
        }

        if (equippedArmor != null) {
            this.equippedArmorID = equippedArmor.getID();
        } else {
            this.equippedArmorID = 0;
        }
    }

    public int getPlayerID() {
       
        return playerID;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getEquippedWeaponID() {
        return equippedWeaponID;
    }

    public int getEquippedArmorID() {
        return equippedArmorID;
    }

    public int getXp() {
        return xp;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getCurrentStoryIndex() {
        System.out.println(currentStoryIndex);
        return currentStoryIndex;
    }

}
