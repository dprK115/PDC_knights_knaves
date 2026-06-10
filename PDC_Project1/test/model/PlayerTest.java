/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lukea
 */
public class PlayerTest {

    @Test
    public void constructor_createsPlayerWithCorrectNameAndDefaults() {
        // Arrange + Act
        Player player = new Player("Arthur", Difficulty.EASY);

        // Assert
        assertEquals("Arthur", player.name);
        assertEquals(1, player.level);
        assertEquals(10, player.attack);
        assertEquals(5, player.defense);
        assertEquals(0, player.xp);
        assertNotNull(player.inventory);
        assertNotNull(player.getEquippedArmor());
        assertNotNull(player.getEquippedWeapon());
        assertEquals("Dirty Clothes", player.getEquippedArmor().name);
        assertEquals("Fists", player.getEquippedWeapon().name);
    }

    @Test
    public void attack_reducesEnemyHealthByAttackMinusDefense() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);

        TestCharacter enemy = new TestCharacter("Goblin");
        enemy.health = 50;
        enemy.defense = 3;

        // Act
        player.attack(enemy);

        // Assert
        // Player attack = 10, enemy defense = 3, damage = 7
        assertEquals(43, enemy.health);
    }

    @Test
    public void attack_damageCannotBeNegative() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);

        TestCharacter enemy = new TestCharacter("Armoured Goblin");
        enemy.health = 50;
        enemy.defense = 100;

        // Act
        player.attack(enemy);

        // Assert
        // Damage should become 0, not negative
        assertEquals(50, enemy.health);
    }

    @Test
    public void defend_increasesDefenseByFive() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);
        int originalDefense = player.defense;

        // Act
        player.defend();

        // Assert
        assertEquals(originalDefense + 5, player.defense);
    }

    @Test
    public void undefend_decreasesDefenseByFive() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);
        player.defend();

        int defendedValue = player.defense;

        // Act
        player.undefend();

        // Assert
        assertEquals(defendedValue - 5, player.defense);
    }

    @Test
    public void equip_whenItemIsWeapon_equipsWeaponAndIncreasesAttack() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);

        Weapon sword = new Weapon("Iron Sword", 1, 10);
        player.inventory.addItem(sword);

        int originalAttack = player.attack;

        // Act
        Item equippedItem = player.equip(0);

        // Assert
        assertEquals(sword, equippedItem);
        assertEquals(sword, player.getEquippedWeapon());
        assertEquals(originalAttack + 10, player.attack);
    }

    @Test
    public void equip_whenItemIsArmor_equipsArmorAndIncreasesDefense() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);

        Armor armor = new Armor("Iron Armor", 2, 8);
        player.inventory.addItem(armor);

        int originalDefense = player.defense;

        // Act
        Item equippedItem = player.equip(0);

        // Assert
        assertEquals(armor, equippedItem);
        assertEquals(armor, player.getEquippedArmor());
        assertEquals(originalDefense + 8, player.defense);
    }

    @Test
    public void equip_whenItemIsPotion_returnsNull() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);

        Potion potion = new Potion("Health Potion", 3, 20);
        player.inventory.addItem(potion);

        // Act
        Item equippedItem = player.equip(0);

        // Assert
        assertNull(equippedItem);
    }

    @Test
    public void use_whenItemIsPotion_healsPlayerAndRemovesPotion() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);

        player.health = player.maxHealth - 10;

        Potion potion = new Potion("Health Potion", 3, 20);
        player.inventory.addItem(potion);

        // Act
        player.use(0);

        // Assert
        assertEquals(player.maxHealth, player.health);
        assertEquals(0, player.inventory.items.size());
    }

    @Test
    public void use_whenPotionWouldOverheal_healthDoesNotExceedMaxHealth() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);

        player.health = player.maxHealth - 5;

        Potion potion = new Potion("Large Potion", 4, 100);
        player.inventory.addItem(potion);

        // Act
        player.use(0);

        // Assert
        assertEquals(player.maxHealth, player.health);
    }

    @Test
    public void use_whenItemIsNotPotion_doesNotRemoveItem() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);

        Weapon sword = new Weapon("Iron Sword", 5, 10);
        player.inventory.addItem(sword);

        // Act
        player.use(0);

        // Assert
        assertEquals(1, player.inventory.items.size());
        assertEquals(sword, player.inventory.items.get(0));
    }

    @Test
    public void levelUp_whenEnoughXp_increasesStatsAndLevel() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);

        int originalLevel = player.level;
        int originalAttack = player.attack;
        int originalDefense = player.defense;
        int originalMaxHealth = player.maxHealth;

        player.xp = player.level * player.xpModifier;

        // Act
        player.levelUp();

        // Assert
        assertEquals(originalLevel + 1, player.level);
        assertEquals(originalAttack + 10, player.attack);
        assertEquals(originalDefense + 10, player.defense);
        assertEquals(originalMaxHealth + 100, player.maxHealth);
        assertEquals(player.maxHealth, player.health);
    }

    @Test
    public void levelUp_whenNotEnoughXp_doesNotIncreaseLevel() {
        // Arrange
        Player player = new Player("Arthur", Difficulty.EASY);

        int originalLevel = player.level;
        int originalAttack = player.attack;
        int originalDefense = player.defense;
        int originalMaxHealth = player.maxHealth;

        player.xp = 0;

        // Act
        player.levelUp();

        // Assert
        assertEquals(originalLevel, player.level);
        assertEquals(originalAttack, player.attack);
        assertEquals(originalDefense, player.defense);
        assertEquals(originalMaxHealth, player.maxHealth);
    }

    private static class TestCharacter extends Character {

        public TestCharacter(String name) {
            super(name);
        }

        @Override
        public void attack(Character enemy) {
        }

        @Override
        public void defend() {
        }

        @Override
        public void undefend() {
        }
    }
}
