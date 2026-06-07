/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author vishw
 */
import java.util.Scanner;


public class Game {

    static Player player;
    static StoryManager manager = new StoryManager();
    static SaveManager sm = new SaveManager();
    static MainMenu mainMenu = new MainMenu();
    static GameMenu gameMenu = new GameMenu();
    boolean loadedGameState = false;
    Encounter encounter;
    Scanner input = new Scanner(System.in);
    static Difficulty difficulty;
    static DifficultySet difficultySet;

    public static void main(String[] args) {
        difficulty = difficulty.EASY;
        difficultySet = new DifficultySet(difficulty);
        mainMenu.mainMenuStart();

        gameMenu.gameMenuStart();

    }

    public void quit() {
        System.out.println("Closing game, Thanks for Plaing");
        input.close();
        System.exit(0);
    }
    // changed enemy creation to require a level input aswell, which determines xp gain from each fight,
    public void initializeNewGame() {
        manager.addEncounter(new Story("You wake up in a dark forest..."));
        manager.addEncounter(new Story("Ahead you spot a grotesque skeletal figure"));
        manager.addEncounter(new Story("As you venture closer you recognize him as Markus, one of Lord Danil's lesser undead knights, he spots you and charges"));
        manager.addEncounter(new Combat(player, new Enemy("Markus", 2), new Armor("Armor of Sir Jean Paul Gautier", 25)));
        manager.addEncounter(new Story("You defeated Markus and move forward..."));
        manager.addEncounter(new Story("The dark forest abruptly ends and you gaze apon the plains you once held as your own"
                + "\nHowever, it isnt as you remembered it, The once peaceful green windswept plains are now covered with an eternal darkness\nThe sun now a pale blue staining the air around with permanent twilight"
                + "\nThe ground now a corpsefilled marsh surrounding Castle Dior, the stench alone burns your entire body")
        );
        manager.addEncounter(new Story("As you approach Castle Dior, you hear the creaking of undead bones patrolling the ramparts and peeking over the parapets."));
        manager.addEncounter(new Story("You enter the barren courtyard once full with the vitality of a thriving market"));
        manager.addEncounter(new Story("A Banshee's scream rips through the air\n I am Victoria and you have stolen my secret Armor and Slain my lover, prepare to die"));
        manager.addEncounter(new Combat(player, new Enemy("Victoria", 2), new Weapon("Vampire's Bane", 50)));
        manager.addEncounter(new Story("You defeated Victoria and move on to the keep"));
        manager.addEncounter(new Story("Outside the keep you hear pure evil barking orders,\nYou know its time to finish what you started all those years ago"));
        manager.addEncounter(new Story("As you push open the large wooden doors to the keep, You look to your throne where Danil currently sits\n I thought I got rid of you  40 years prior, yet you come crawling back here to evict me?\n Unfortunately for you, your fun ends here"));
        manager.addEncounter(new Combat(player, new Enemy("Lord Danil", 3), new Armor("Viva la Vida", 100)));
    }

}
