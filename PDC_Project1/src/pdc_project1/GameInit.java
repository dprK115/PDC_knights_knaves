/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

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
                "WEAPON",
                "Rusty Sword",
                5,
                0,
                0
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
                "Leather Armor",
                0,
                4,
                0
        ));

        addItem(ItemFactory.createItem(
                4,
                "ARMOR",
                "Iron Armor",
                0,
                8,
                0
        ));

        addItem(ItemFactory.createItem(
                5,
                "POTION",
                "Small Health Potion",
                0,
                0,
                20
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
        Item rustySword = getItemByID(1);
        Item ironSword = getItemByID(2);
        Item leatherArmor = getItemByID(3);
        Item healthPotion = getItemByID(5);

        addEncounter(EncounterFactory.createEncounter(
                1,
                "STORY",
                player,
                "You wake up outside a ruined village. The air is cold and the road ahead is silent.",
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                2,
                "STORY",
                player,
                "A broken sign points toward an old forest path. You decide to move forward.",
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                3,
                "COMBAT",
                player,
                null,
                "Goblin",
                1,
                rustySword
        ));

        addEncounter(EncounterFactory.createEncounter(
                4,
                "STORY",
                player,
                "After defeating the goblin, you find tracks leading deeper into the forest.",
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                5,
                "COMBAT",
                player,
                null,
                "Bandit",
                2,
                healthPotion
        ));

        addEncounter(EncounterFactory.createEncounter(
                6,
                "STORY",
                player,
                "You discover a small abandoned camp. There are signs that someone left in a hurry.",
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                7,
                "COMBAT",
                player,
                null,
                "Dark Knight",
                3,
                leatherArmor
        ));

        addEncounter(EncounterFactory.createEncounter(
                8,
                "STORY",
                player,
                "The path opens into a ruined courtyard. A final enemy waits ahead.",
                null,
                0,
                null
        ));

        addEncounter(EncounterFactory.createEncounter(
                9,
                "COMBAT",
                player,
                null,
                "Knight Commander",
                4,
                ironSword
        ));

        addEncounter(EncounterFactory.createEncounter(
                10,
                "STORY",
                player,
                "With the final enemy defeated, peace returns to the village. Your journey is complete.",
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
