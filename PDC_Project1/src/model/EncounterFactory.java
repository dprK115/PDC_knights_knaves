/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lukea
 */
public class EncounterFactory {

    public static Encounter createEncounter(
            int encounterID,
            String encounterType,
            Player player,
            String storyText,
            String enemyName,
            int enemyLevel,
            Item loot
    ) {

        if (encounterType.equalsIgnoreCase("STORY")) {
            Story story = new Story(storyText);
            story.setID(encounterID);
            return story;
        }

        if (encounterType.equalsIgnoreCase("COMBAT")) {
            Enemy enemy = new Enemy(enemyName, enemyLevel);
            Combat combat = new Combat(player, enemy, loot);
            combat.setID(encounterID);
            return combat;
        }

        throw new IllegalArgumentException("Invalid encounter type: " + encounterType);
    }
}
