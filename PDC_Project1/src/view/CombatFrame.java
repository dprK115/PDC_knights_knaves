/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author vishw
 */
import javax.swing.*;
import java.awt.*;

public class CombatFrame extends JFrame {

    JLabel playerHealthLabel;
    JLabel enemyHealthLabel;

    JButton attackButton;
    JButton defendButton;
    JButton useItemButton;

    public CombatFrame() {

        setTitle("Combat");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 1));

        enemyHealthLabel = new JLabel("Enemy: Markus    HP: 30");
        playerHealthLabel = new JLabel("Player: Current Player HP: 50");

        infoPanel.add(enemyHealthLabel);
        infoPanel.add(playerHealthLabel);

        JPanel buttonPanel = new JPanel();

        attackButton = new JButton("Attack");
        defendButton = new JButton("Defend");
        useItemButton = new JButton("Use Item");

        buttonPanel.add(attackButton);
        buttonPanel.add(defendButton);
        buttonPanel.add(useItemButton);

        JTextArea combatLog = new JTextArea();
        combatLog.setEditable(false);
        combatLog.setText("Combat started...\n");

        JScrollPane scrollPane = new JScrollPane(combatLog);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public static void main(String[] args) {
        CombatFrame frame = new CombatFrame();
        frame.setVisible(true);
    }
}