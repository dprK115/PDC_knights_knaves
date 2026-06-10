/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author vishw
 */
import model.Inventory;
import model.Item;

import javax.swing.*;
import java.awt.*;
import model.Game;
import model.Player;

public class InventoryFrame extends JFrame {

    JList<String> itemList;

    JButton useButton;
    JButton equipButton;
    JButton closeButton;

    public InventoryFrame(Inventory inventory) {

        setTitle("Inventory");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Inventory", JLabel.CENTER);

        DefaultListModel<String> model = new DefaultListModel<>();

        for (Item item : inventory.items) {
            model.addElement(item.name);
        }

        itemList = new JList<>(model);

        JScrollPane scrollPane = new JScrollPane(itemList);

        JPanel buttonPanel = new JPanel();

        useButton = new JButton("Use");
        equipButton = new JButton("Equip");
        closeButton = new JButton("Close");

        buttonPanel.add(useButton);
        buttonPanel.add(equipButton);
        buttonPanel.add(closeButton);
        
        Player player = Game.player;

        useButton.addActionListener(e -> {
        int selectedIndex = itemList.getSelectedIndex();
        if (selectedIndex == -1) {
        JOptionPane.showMessageDialog(
                this,
                "Select an item first."
            );
        return;
        }
        player.use(selectedIndex);
        JOptionPane.showMessageDialog(
            this,
            "Item used."
            );
        });

        closeButton.addActionListener(e -> dispose());

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}