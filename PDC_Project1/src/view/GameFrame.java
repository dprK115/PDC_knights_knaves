package view;

import javax.swing.*;
import java.awt.*;

import model.GameState;
import model.SaveManager;
import model.Game;
import model.Player;
import model.Inventory;
import model.Encounter;
import model.Story;
import model.Combat;

public class GameFrame extends JFrame {

    JTextArea storyArea;

    JButton continueButton;
    JButton inventoryButton;
    JButton statsButton;
    JButton saveButton;

    public GameFrame() {

        setTitle("Knights and Knaves");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        storyArea = new JTextArea();
        storyArea.setEditable(false);
        storyArea.setLineWrap(true);
        storyArea.setWrapStyleWord(true);

        storyArea.setText(
                "Welcome to Knights and Knaves.\n\n"
                + "You awaken in darkness and begin your journey to reclaim Castle Dior."
        );

        JScrollPane scrollPane = new JScrollPane(storyArea);

        JPanel buttonPanel = new JPanel();

        continueButton = new JButton("Continue");
        inventoryButton = new JButton("Inventory");
        statsButton = new JButton("Stats");
        saveButton = new JButton("Save");

        continueButton.addActionListener(e -> {

            Encounter encounter = Game.manager.getNextEncounter();

            if (encounter == null) {
                storyArea.setText("The End");
                return;
            }

            if (encounter instanceof Story) {

                Story story = (Story) encounter;

                storyArea.setText(
                        story.getText()
                );

            } else if (encounter instanceof Combat) {

                Combat combat = (Combat) encounter;

                CombatFrame frame = new CombatFrame(combat);
                frame.setVisible(true);
            }

        });

        inventoryButton.addActionListener(e -> {

            System.out.println(
                    "Inventory size: "
                    + Game.player.inventory.items.size()
            );

            Inventory inventory = Game.player.inventory;

            InventoryFrame frame = new InventoryFrame(inventory);
            frame.setVisible(true);
        });

        statsButton.addActionListener(e -> {

            Player player = Game.player;

            JOptionPane.showMessageDialog(
                    this,
                    "Player Stats\n\n"
                    + "Name: " + player.name
                    + "\nHealth: " + player.health
                    + "\nAttack: " + player.attack
                    + "\nDefense: " + player.defense
                    + "\nDifficulty Modifier: "
                    + player.getDifficultyModifier()
            );
        });

        saveButton.addActionListener(e -> {

            GameState gameState =
                    new GameState(
                            Game.player,
                            Game.manager
                    );

            SaveManager.saveGame(gameState);

            JOptionPane.showMessageDialog(
                    this,
                    "Game Saved"
            );
        });

        buttonPanel.add(continueButton);
        buttonPanel.add(inventoryButton);
        buttonPanel.add(statsButton);
        buttonPanel.add(saveButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        frame.setVisible(true);
    }
}