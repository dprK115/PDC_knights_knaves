/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author vishw
 */
import controller.GameController;
import javax.swing.*;
import java.awt.*;
import view.DifficultyFrame;
import model.Game;
import model.GameState;
import model.SaveManager;

public class MainMenuFrame extends JFrame {

    JButton newGameButton;
    JButton loadGameButton;
    JButton exitButton;

    GameController controller;

    public MainMenuFrame() {

        controller = new GameController();

        setTitle("Knights and Knaves");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("KNIGHTS & KNAVES");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        newGameButton = new JButton("New Game");
        loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(e -> {

    GameState loadedState = SaveManager.loadGame(1);

    if (loadedState != null) {

        Game.player = loadedState.getPlayer();
        Game.manager = loadedState.getStoryManager();

        GameFrame frame = new GameFrame();
        frame.setVisible(true);

        dispose();

    } else {

        JOptionPane.showMessageDialog(
                this,
                "No save found."
        );
    }
});
        exitButton = new JButton("Exit");

        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        exitButton.setActionCommand("EXIT");
        exitButton.addActionListener(controller);

        newGameButton.setActionCommand("NEW_GAME");
        newGameButton.addActionListener(controller);
        newGameButton.addActionListener(e -> dispose());

        panel.add(Box.createVerticalStrut(40));
        panel.add(title);
        panel.add(Box.createVerticalStrut(30));
        panel.add(newGameButton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(loadGameButton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(exitButton);

        add(panel);
    }

    public static void main(String[] args) {
        MainMenuFrame frame = new MainMenuFrame();
        frame.setVisible(true);
    }
}