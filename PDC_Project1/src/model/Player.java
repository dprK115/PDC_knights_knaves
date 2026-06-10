/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lukea
 */
public class Player extends Character implements CanEquip, CanUse {

    public Inventory inventory = new Inventory();
    Item equippedArmor;
    Item equippedWeapon;
    int difficultyModifier;
    int xpModifier;
    int nextLevelXp;
    int id = 1;
    int currentStoryIndex;

    public Player(String name, Difficulty difficulty) {
        super(name);
        setDifficulty(difficulty.ordinal() +1);
        this.difficultyModifier = DifficultySet.playerModifier;
        this.xpModifier = DifficultySet.xpModifier;
        this.level = 1;
        this.maxHealth = level * difficultyModifier;
        this.health = maxHealth;
        this.attack = 10;
        this.defense = 5;
        this.xp = 0;
        this.nextLevelXp = level * xpModifier;
        equippedArmor = new Armor("Dirty Clothes", 0, 0);
        equippedWeapon = new Weapon("Fists", 0, 0);
        System.out.println("Difficulty selected: " + difficulty);
        System.out.println("Player modifier: " + DifficultySet.playerModifier);
    }

    public Player(String name, int savedLevel, int difficultyValue) {

        super(name);
        setDifficulty(difficultyValue);
        this.level = savedLevel;
        this.difficultyModifier = DifficultySet.playerModifier;
        this.maxHealth = level * difficultyModifier;
        this.health = maxHealth;
        this.attack = level * 10;
        this.defense = level * 10;
        this.xp = 0;
        this.nextLevelXp = level * xpModifier;
        equippedArmor = new Armor("Dirty Clothes", 0, 0);
        equippedWeapon = new Weapon("Fists", 0, 0);
    }

    public void levelUp() {

        if (xp >= level * xpModifier) {
            this.maxHealth += 100;
            this.health = maxHealth;
            this.attack += 10;
            this.defense += 10;
            this.level++;
            System.out.println("You leveled up");
        } else {
            System.out.println("you are " + ((nextLevelXp) - xp) + " xp away from leveling up");
        }  // reworking level up system to be xp point based.

    }

    public Item getEquippedArmor() {
        return equippedArmor;
    }

    public Item getEquippedWeapon() {
        return equippedWeapon;
    }

    public int getDifficultyModifier() {
        return difficultyModifier;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setEquippedArmor(Item equippedArmor) {
        this.equippedArmor = equippedArmor;
        this.defense += this.equippedArmor.getStat();
    }

    public void setEquippedWeapon(Item equippedWeapon) {
            this.equippedWeapon = equippedWeapon;
            this.attack += this.equippedWeapon.getStat();
        

    }

    public void setEncounterIndex(int index) {
        this.currentStoryIndex = index;
    }

    public void getStatCard() {
        System.out.println("=================================");
        System.out.println("        PLAYER STAT CARD         ");
        System.out.println("=================================");
        System.out.println("Name:        " + name);
        System.out.println("Health:      " + health + "/" + maxHealth);
        System.out.println("Attack:      " + attack);
        System.out.println("Defense:     " + defense);
        System.out.println("Level:       " + level);
        System.out.println("---------------------------------");

        System.out.println("Weapon:      "
                + (equippedWeapon != null ? equippedWeapon.name : "None"));

        System.out.println("Armor:       "
                + (equippedArmor != null ? equippedArmor.name : "None"));

        System.out.println("=================================");
    }

    @Override
    public void attack(Character Enemy) {
        int damage = this.attack - Enemy.defense;

        if (damage < 0) {
            damage = 0;
        }

        Enemy.health -= damage;
    }

    @Override
    public void defend() {
        this.defense += 5;
    }

    @Override
    public void undefend() {
        this.defense -= 5;
    }

    @Override
    public Item equip(int index) {
        if (inventory.items.get(index) instanceof Armor) {
            this.defense -= this.equippedArmor.getStat();
            this.equippedArmor = inventory.items.get(index);
            this.defense += equippedArmor.getStat();
            return equippedArmor;
        } else if (inventory.items.get(index) instanceof Weapon) {
            this.attack -= this.equippedWeapon.getStat();
            this.equippedWeapon = inventory.items.get(index);
            this.attack += this.equippedWeapon.getStat(); //fixed bug where this would acutally lower the atk stat same with defense
            return equippedWeapon;
        } else {
            return null;
        }

    }

    @Override
    public void use(int index) {

        Item item = inventory.items.get(index);

        if (item instanceof Potion) {
            Potion potion = (Potion) item;

            this.health += potion.healAmount;

            if (this.health > this.maxHealth) {
                this.health = this.maxHealth;
            }

            System.out.println("You used " + potion.name + " and healed " + potion.healAmount);

            inventory.removeItem(item);
        } else {
            System.out.println("Item cannot be used");
        }
    }
}
