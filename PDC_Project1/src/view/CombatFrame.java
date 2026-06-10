/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author vishw
 */
import controller.CombatController;
import javax.swing.*;
import java.awt.*;
import model.Enemy;
import model.Player;
import model.Game;
import model.Combat;

public class CombatFrame extends JFrame {

    private JLabel playerHealthLabel;
    private JLabel enemyHealthLabel;

    private JButton attackButton;
    private JButton defendButton;
    private JButton useItemButton;

    private JTextArea combatLog;

    private CombatController controller;

    public CombatFrame(Combat combatEncounter) {

        Player player = Game.player;
        Enemy enemy = combatEncounter.getEnemy();
        final Combat currentCombat = combatEncounter;

        System.out.println("Enemy: " + enemy.getName());
        System.out.println("Enemy HP: " + enemy.health);
        System.out.println("Enemy Defense: " + enemy.defense);
        System.out.println("Player Attack: " + player.attack);

        controller = new CombatController(player, enemy);

        setTitle("Combat");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));

        enemyHealthLabel = new JLabel();
        playerHealthLabel = new JLabel();

        updateLabels();

        infoPanel.add(enemyHealthLabel);
        infoPanel.add(playerHealthLabel);

        attackButton = new JButton("Attack");
        defendButton = new JButton("Defend");
        useItemButton = new JButton("Use Item");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(attackButton);
        buttonPanel.add(defendButton);
        

        combatLog = new JTextArea();
        combatLog.setEditable(false);
        combatLog.append(
                "Combat started against "
                + enemy.getName()
                + "\n"
        );

        JScrollPane scrollPane = new JScrollPane(combatLog);

        attackButton.addActionListener(e -> {

            controller.attack();

            combatLog.append(
                    controller.getPlayer().name
                    + " attacks "
                    + controller.getEnemy().name
                    + "\n"
            );

            if (!controller.enemyDefeated()) {

                controller.getEnemy().attack(
                        controller.getPlayer()
                );

                if (controller.getPlayer().health <= 0) {

                    controller.getPlayer().health = 0;

                    updateLabels();

                    attackButton.setEnabled(false);
                    defendButton.setEnabled(false);
                    useItemButton.setEnabled(false);

                    JOptionPane.showMessageDialog(
                            this,
                            "Game Over!"
                    );

                    return;
                }

                combatLog.append(
                        controller.getEnemy().name
                        + " attacks "
                        + controller.getPlayer().name
                        + "\n"
                );
            }

            updateLabels();

            if (controller.enemyDefeated()) {

                combatLog.append(
                        controller.getEnemy().name
                        + " has been defeated!\n"
                );

                Game.player.inventory.addItem(
                        currentCombat.getLoot()
                );

                JOptionPane.showMessageDialog(
                        this,
                        "You received "
                        + currentCombat.getLoot().name
                );

                System.out.println(
                        "Before heal: "
                        + controller.getPlayer().health
                );

                controller.getPlayer().health =
                        controller.getPlayer().maxHealth;

                System.out.println(
                        "After heal: "
                        + controller.getPlayer().health
                );

                updateLabels();

                attackButton.setEnabled(false);
                defendButton.setEnabled(false);
                useItemButton.setEnabled(false);

                JOptionPane.showMessageDialog(
                        this,
                        "Victory! "
                        + controller.getEnemy().getName()
                        + " has been defeated."
                );
            }
        });

        defendButton.addActionListener(e -> {

            controller.defend();

            combatLog.append(
                    controller.getPlayer().name
                    + " raises their defence.\n"
            );

            updateLabels();
        });


        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updateLabels() {

        enemyHealthLabel.setText(
                "Enemy: "
                + controller.getEnemy().name
                + "    HP: "
                + controller.getEnemy().health
        );

        playerHealthLabel.setText(
                "Player: "
                + controller.getPlayer().name
                + "    HP: "
                + controller.getPlayer().health
        );
    }
}