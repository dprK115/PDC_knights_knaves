/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_project1;

/**
 *
 * @author lukea
 */
public class DifficultySet {

    Difficulty difficulty;
    static int enemyModifier;
    static int PlayerModifier;
    static int xpModifier;

    public DifficultySet(Difficulty difficulty) {
        this.difficulty = difficulty;
        setDifficulty();
    }

    public DifficultySet(int difficulty) {
        this.difficulty = difficultyConverter(difficulty);
        setDifficulty();
    }

    private Difficulty difficultyConverter(int difficulty) {
        Difficulty convertedDifficulty;

        switch (difficulty) {
            case 1:
                convertedDifficulty = Difficulty.EASY;
                return convertedDifficulty;
            case 2:
                convertedDifficulty = Difficulty.MEDIUM;
                return convertedDifficulty;
            case 3:
                convertedDifficulty = Difficulty.HARD;
                return convertedDifficulty;
            default:
                return null;
        }
    }

    private void setDifficulty() {
        switch (difficulty) {
            case EASY:
                enemyModifier = 30;
                PlayerModifier = 50;
                xpModifier = 5;
                break;
            case MEDIUM:
                enemyModifier = 50;
                PlayerModifier = 35;
                xpModifier = 10;
                break;
            case HARD:
                enemyModifier = 100;
                PlayerModifier = 25;
                xpModifier = 15;
                break;
        }
    }

}
