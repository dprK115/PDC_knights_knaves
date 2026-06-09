/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author lukea
 */
// CharacterCreation extends Game now to use the same scanner that is opened in Game.java
public class CharacterCreation extends Game {

    String charName;
    String yesNo;
   

    public CharacterCreation() {

    }

    public final Player createMenu() {

        System.out.println("The smell of damp and mildew hang heavy in the air");
        System.out.println("Your eyes while open, still cannot discern any noticible light");
        System.out.println("A feint white glow starts eminating from what appears to be a glass orb of sorts with tempests contained inside.\n"
                + "Standing behind the orb is a ragged, discheveled looking man in what appears to be mages robes"
        );
        System.out.println("The man begins to speak");
        System.out.println("First, please tell me your name adventurer");
        charName = input.nextLine();
        System.out.println("Hmmm, are you certain that " + charName + " is what they call you? (y/n)");
        yesNo = input.nextLine();

        if (yesNo.equals("y")) {

            System.out.println("Ahhh yes, I remember you, Sir " + charName + " of Castle Dior");
            System.out.println("You fell to the Vampire Lord Danil just shy of 2 decades ago");
            System.out.println("You must now reclaim your former domain and save the people of Dior");
            System.out.println("Best of luck in your journey " + charName);
            
            Player createdPlayer = new Player(charName, Difficulty.EASY);
            return createdPlayer;
        } else if (yesNo.equals("n")) {
            return createMenu();
        } else {
            System.out.println("Invalid input, please enter y or n");
            return createMenu();
        }

    }

}
