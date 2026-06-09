/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lukea
 */
import database.DBManager;
import database.ItemDAO;
import database.EncounterDAO;

import java.util.ArrayList;

public class GameInit {

    private ArrayList<Encounter> gameEncounters;
    private ArrayList<Item> gameItems;

    private DBManager dbm;
    private ItemDAO itemDAO;
    private EncounterDAO encounterDAO;

    private Player player;

    public GameInit(DBManager dbManager, Player player) {
        this.dbm = dbManager;
        this.player = player;

        this.gameEncounters = new ArrayList<>();
        this.gameItems = new ArrayList<>();

        this.itemDAO = new ItemDAO(dbManager);
        this.encounterDAO = new EncounterDAO(dbManager, player);
    }

    public void initialiseGameData() {
        createItems();
        createEncounters();
    }

    private void createItems() {
        addItem(ItemFactory.createItem(
                1,
                "ARMOR",
                "Iron Mail",
                0,//atk
                25,//def
                0//healamount
        ));

        addItem(ItemFactory.createItem(
                2,
                "WEAPON",
                "Iron Sword",
                10,
                0,
                0
        ));

        addItem(ItemFactory.createItem(
                3,
                "ARMOR",
                "Armor of Sir Jean Paul Gautier",
                0,
                50,
                0
        ));

        addItem(ItemFactory.createItem(
                4,
                "WEAPON",
                "Vampire's Bane",
                0,
                8,
                0
        ));

        addItem(ItemFactory.createItem(
                5,
                "ARMOR",
                "Viva la Vida",
                0,
                100,
                0
        ));

        addItem(ItemFactory.createItem(
                6,
                "POTION",
                "Large Health Potion",
                0,
                0,
                50
        ));
    }

    private void createEncounters() {
        Item ironMail = getItemByID(1);
        Item ironSword = getItemByID(2);
        Item armourOfSJPG = getItemByID(3);
        Item vBane = getItemByID(4);
        Item vLV = getItemByID(5);
        Item healthPotion = getItemByID(6);

        addEncounter(EncounterFactory.createEncounter(
                1,
                "STORY",
                player,
                "You wake up in a dark forest...",
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                2,
                "STORY",
                player,
                "Ahead you spot a grotesque skeletal figure",
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                3,
                "STORY",
                player,
                "As you venture closer you recognize him as Markus, one of Lord Danil's lesser undead knights, he spots you and charges",
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                3,
                "COMBAT",
                player,
                null,
                "Markus",
                1,
                ironSword
        ));

        addEncounter(EncounterFactory.createEncounter(
                4,
                "STORY",
                player,
                """
                    The dark forest abruptly ends and you gaze apon the plains you once held as your own
                    However, it isnt as you remembered it, The once peaceful green windswept plains are now covered with an eternal darkness
                    The sun now a pale blue staining the air around with permanent twilight
                    The ground now a corpsefilled marsh surrounding Castle Dior, the stench alone burns your entire body
                """,
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                5,
                "STORY",
                player,
                """
                    Ahead in the Marsh you spot a bandit looting the corpses, He sees you and prepares to attack.
                """,
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                6,
                "COMBAT",
                player,
                null,
                "Bandit",
                2,
                ironMail
        ));

        addEncounter(EncounterFactory.createEncounter(
                7,
                "STORY",
                player,
                "As you approach Castle Dior, you hear the creaking of undead bones patrolling the ramparts and peeking over the parapets. A Knight in black Armour guards the entrance. \"If you wish to enter and reclaim your former domain, first you must best me\" he decrees.",
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                8,
                "COMBAT",
                player,
                null,
                "Dark Knight",
                3,
                armourOfSJPG
        ));

        addEncounter(EncounterFactory.createEncounter(
                9,
                "STORY",
                player,
                "You open the gate and enter a barren courtyard once full with the vitality of a thriving market, covered in ash, rubble and dessicated corpses.",
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                10,
                "STORY",
                player,
                "A Banshee's scream rips through the air\n I am Victoria and you have stolen my secret Armor and slain my lover Markus while he was out gathering berries for our children, prepare to die",
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                11,
                "COMBAT",
                player,
                null,
                "Victoria",
                4,
                vBane
        ));

        addEncounter(EncounterFactory.createEncounter(
                12,
                "STORY",
                player,
                "Outside the keep you hear pure evil barking orders,\nYou know its time to finish what you started all those years ago",
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                13,
                "STORY",
                player,
                "As you push open the large wooden doors to the keep, You look to your throne where Danil currently sits\n I thought I got rid of you  40 years prior, yet you come crawling back here to evict me?\n Unfortunately for you, your fun ends here",
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                14,
                "COMBAT",
                player,
                null,
                "Lord Danil",
                5,
                vLV
        ));

        addEncounter(EncounterFactory.createEncounter(
                15,
                "STORY",
                player,
                "After defeating Lord Danil, the skies clear and the first signs of life start to appear in the corrupted plains surrounding castle dior. Even the river that flows through is slowly clearing up and the black water turning pure once again. Yet on the horizon another dark storm is brewing, will it come to your doorstep or will it pass? Regardless your work doesn't end here.",
                null,
                0,
                null
        ));
    }

    private void addItem(Item item) {
        gameItems.add(item);
    }

    private void addEncounter(Encounter encounter) {
        gameEncounters.add(encounter);
    }

    public void saveItemsToDatabase() {
        for (Item item : gameItems) {
            itemDAO.save(item);
        }

        System.out.println("All game items saved to database.");
    }

    public void saveEncountersToDatabase() {
        for (Encounter encounter : gameEncounters) {
            encounterDAO.save(encounter);
        }

        System.out.println("All game encounters saved to database.");
    }

    public void saveAllToDatabase() {
        saveItemsToDatabase();
        saveEncountersToDatabase();
    }

    public void initialiseAndSave() {
        initialiseGameData();
        saveAllToDatabase();
    }

    public ArrayList<Item> getGameItems() {
        return gameItems;
    }

    public ArrayList<Encounter> getGameEncounters() {
        return gameEncounters;
    }

    private Item getItemByID(int itemID) {
        for (Item item : gameItems) {
            if (item.getID() == itemID) {
                return item;
            }
        }

        return null;
    }
}
