/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author vishw
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Game;
import view.DifficultyFrame;
import view.GameFrame;

public class GameController implements ActionListener {

    public GameController() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if (command.equals("NEW_GAME")) {
            openDifficultyMenu();
        }

        if (command.equals("EXIT")) {
            exitGame();
        }
    }

    public void exitGame() {
        Game game = new Game();
        game.quit();
    }

    public void openDifficultyMenu() {
        DifficultyFrame frame = new DifficultyFrame();
        frame.setVisible(true);
    }

    public void startNewGame() {
        GameFrame frame = new GameFrame();
        frame.setVisible(true);
    }
}