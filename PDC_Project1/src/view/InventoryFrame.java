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
import model.Player;
import model.Game;
import model.Potion;
import model.Weapon;
import model.Armor;
import javax.swing.*;
import java.awt.*;


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

        System.out.println(
                "Inventory items: "
                + inventory.items.size()
        );

        for (Item item : inventory.items) {

            System.out.println(
                    "Adding item: "
                    + item.name
            );

            model.addElement(item.name);
        }

        itemList = new JList<>(model);

        itemList.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane = new JScrollPane(itemList);

        JPanel buttonPanel = new JPanel();

        useButton = new JButton("Use");
        equipButton = new JButton("Equip");
        closeButton = new JButton("Close");

        //buttonPanel.add(useButton);
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

            Item item = inventory.items.get(selectedIndex);

            if (item instanceof Potion) {

                player.use(selectedIndex);

                JOptionPane.showMessageDialog(
                        this,
                        item.name + " used."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "This item cannot be used."
                );
            }
        });

        equipButton.addActionListener(e -> {

            int selectedIndex = itemList.getSelectedIndex();

            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Select an item first."
                );
                return;
            }

            Item item = inventory.items.get(selectedIndex);

            if (item instanceof Weapon || item instanceof Armor) {

                player.equip(selectedIndex);

                JOptionPane.showMessageDialog(
                        this,
                        item.name + " equipped."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "This item cannot be equipped."
                );
            }
        });

        closeButton.addActionListener(e -> dispose());

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}