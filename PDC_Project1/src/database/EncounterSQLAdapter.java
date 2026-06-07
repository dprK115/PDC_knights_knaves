/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

/**
 *
 * @author lukea
 */
import pdc_project1.Combat;
import pdc_project1.Encounter;
import pdc_project1.Story;
import pdc_project1.Enemy;

public class EncounterSQLAdapter {

    private int encounterID;
    private String encounterType;
    private String storyText;
    private int enemyID;
    private int lootItemID;

    public EncounterSQLAdapter(int encounterID, Encounter encounter) {
        this.encounterID = encounterID;

        if (encounter instanceof Story) {
            Story story = (Story) encounter;

            this.encounterType = "STORY";
            this.storyText = story.text;
            this.enemyID = 0;
            this.lootItemID = 0;
        } else if (encounter instanceof Combat) {
            Combat combat = (Combat) encounter;

            this.encounterType = "COMBAT";
            this.storyText = null;
            Enemy enemy = combat.getEnemy();
            this.enemyID = enemy.getID
            this.lootItemID = combat.loot.getID();
        } else {
            throw new IllegalArgumentException("Unknown encounter subclass.");
        }
    }

    public int getEncounterID() {
        return encounterID;
    }

    public String getEncounterType() {
        return encounterType;
    }

    public String getStoryText() {
        return storyText;
    }

    public int getEnemyID() {
        return enemyID;
    }

    public int getLootItemID() {
        return lootItemID;
    }
}
