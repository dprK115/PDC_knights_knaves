/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author lukea
 */
public class Player extends Character implements CanEquip, CanUse {

    Inventory inventory = new Inventory();
    Item equippedArmor = new Armor("Dirty Clothes", 0, 0);
    Item equippedWeapon = new Weapon("Fists", 0, 0);
    static int difficultyModifier = difficulty.PlayerModifier;
    static int xpModifier = difficulty.xpModifier;
    int nextLevelXp;
    int id;

    public Player(String name) {
        super(name);
        this.level = 1;
        this.maxHealth = level * difficultyModifier;
        this.health = maxHealth;
        this.attack = 10;
        this.defense = 5;
        this.xp = 0;
        this.nextLevelXp = level * xpModifier;
    }

    public void levelUp() {

        if (xp >= level * xpModifier) {
            this.maxHealth += 100;
            this.health = maxHealth;
            this.attack += 10;
            this.defense += 10;
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

    public static int getDifficultyModifier() {
        return difficultyModifier;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
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
    public void equip(int index) {
        if (inventory.items.get(index) instanceof Armor) {
            equippedArmor = inventory.items.get(index);
            defense = equippedArmor.getStat();
        } else if (inventory.items.get(index) instanceof Weapon) {
            equippedWeapon = inventory.items.get(index);
            attack = equippedWeapon.getStat(); //fixed bug where attack is set to equipped armor stat.
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
