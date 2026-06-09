/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author vishw
 */
import model.Game;
import view.GameFrame;

public class GameController {

    public GameController() {

    }

    public void exitGame() {
        Game game = new Game();
        game.quit();
    }

    public void startNewGame() {
        GameFrame frame = new GameFrame();
        frame.setVisible(true);
    }
}