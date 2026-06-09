/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

/**
 *
 * @author lukea
 */
import model.Combat;
import model.Encounter;
import model.Story;
import model.Enemy;

public class EncounterSQLAdapter {

    private int encounterID;
    private String encounterType;
    private String storyText;
    private String enemyName;
    private int enemyLevel;
    private int lootItemID;

    public EncounterSQLAdapter( Encounter encounter) {
        this.encounterID = encounter.getID();

        if (encounter instanceof Story) {
            Story story = (Story) encounter;

            this.encounterType = "STORY";
            this.storyText = story.getText();
            this.enemyName = null;
            this.lootItemID = 0;
        } else if (encounter instanceof Combat) {
            Combat combat = (Combat) encounter;

            this.encounterType = "COMBAT";
            this.storyText = null;
            Enemy enemy = combat.getEnemy();
            this.enemyName = enemy.name;
            this.lootItemID = combat.loot.id;
            this.enemyLevel = enemy.getLevel();
        } else {
            throw new IllegalArgumentException("Unknown encounter subclass.");
        }
    }

    public int getEncounterID() {
        return encounterID;
    }

    public int getEnemyLevel() {
        return enemyLevel;
    }
    
    public String getEncounterType() {
        return encounterType;
    }

    public String getStoryText() {
        return storyText;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public int getLootItemID() {
        return lootItemID;
        
    }
}
