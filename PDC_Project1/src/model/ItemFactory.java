/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lukea
 */
public class ItemFactory {

    public static Item createItem(
            int itemId,
            String itemType,
            String name,
            int attackModifier,
            int defenseModifier,
            int healAmount
    ) {

        if (itemType.equalsIgnoreCase("WEAPON")) {
            return new Weapon(name, itemId, attackModifier);
        }

        if (itemType.equalsIgnoreCase("ARMOR")) {
            return new Armor(name, itemId, defenseModifier);
        }

        if (itemType.equalsIgnoreCase("POTION")) {
            return new Potion(name, itemId, healAmount);
        }

        throw new IllegalArgumentException("Unknown item type: " + itemType);
    }
}
