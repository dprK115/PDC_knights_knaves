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

public class DifficultyFrame extends JFrame {

    JButton easyButton;
    JButton mediumButton;
    JButton hardButton;

    public DifficultyFrame() {

        setTitle("Select Difficulty");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("SELECT DIFFICULTY");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        easyButton = new JButton("Easy");
        mediumButton = new JButton("Medium");
        hardButton = new JButton("Hard");

        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mediumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        easyButton.addActionListener(e -> {
            GameFrame frame = new GameFrame();
            frame.setVisible(true);
            dispose();
        });

        mediumButton.addActionListener(e -> {
            GameFrame frame = new GameFrame();
            frame.setVisible(true);
            dispose();
        });

        hardButton.addActionListener(e -> {
            GameFrame frame = new GameFrame();
            frame.setVisible(true);
            dispose();
        });

        panel.add(Box.createVerticalStrut(40));
        panel.add(title);
        panel.add(Box.createVerticalStrut(30));
        panel.add(easyButton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(mediumButton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(hardButton);

        add(panel);
    }

    public static void main(String[] args) {
        DifficultyFrame frame = new DifficultyFrame();
        frame.setVisible(true);
    }
}