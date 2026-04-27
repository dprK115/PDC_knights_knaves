/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author lukea
 */
import java.util.Scanner;

public class GameMenu extends Game {

    boolean gameMenuFlag = true;
    boolean option = true;

    public void gameMenuStart() {
        encounter = manager.getCurrentEncounter();
        while (gameMenuFlag && encounter != null) {

            encounter.start(player);
            option = true;
            while (option) {

                System.out.println("--------" + player.name + "--------");
                System.out.println("Please Choose and Action");
                System.out.println("1. Proceed Forward\n2. Check Inventory\n3. View Stats\n4. Return to Main Menu");
                int userInput = input.nextInt();

                switch (userInput) {
                    case 1:
                        encounter = manager.getNextEncounter();
                        option = false;
                        break;
                    case 2:
                        player.inventory.printInventory();
                        inventorySelect();

                        break;
                    case 3:
                        player.getStatCard();
                        break;
                    case 4:
                        mainMenu.mainMenuStart();
                        gameMenuFlag = false;
                        break;
                    default:
                        System.out.println("Invalid input, please enter a number for an option onscreen");
                        break;
                }
            }

        }

    }

    private void inventorySelect() {
        System.out.println("Would you like to equip anything y/n ");
        String yesNo = input.next();
        if (yesNo.equals("y")) {
            System.out.println("please enter the number next to the item you would like to equip");
            int itemIndex = input.nextInt();
            player.equip(itemIndex);
            System.out.println("you have equipped " + player.equippedArmor.name);
        } else if (yesNo.equals("n")) {
            return;

        } else {
            System.out.println("Invalid input, please enter y or n");
            inventorySelect();
        }

    }

}
