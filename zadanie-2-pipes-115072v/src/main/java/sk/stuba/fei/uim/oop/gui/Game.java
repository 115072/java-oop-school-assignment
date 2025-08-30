package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.logic.Logic;

import javax.swing.*;
import java.awt.*;

public class Game {
    public Game() {
        JFrame frame = new JFrame("Water Pipes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(690, 690);
        frame.setResizable(false);
        frame.setFocusable(true);

        JPanel panel = new JPanel();

        Logic logic = new Logic(frame);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 8, 12, 8);
        slider.setMajorTickSpacing(2);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.addChangeListener(logic);

        JButton check = new JButton("CHECK");
        check.setFocusable(false);
        check.addActionListener(logic);

        JButton restart = new JButton("RESTART");
        restart.setFocusable(false);
        restart.addActionListener(logic);

        frame.addKeyListener(logic);

        panel.setLayout(new GridLayout(3, 2));
        panel.add(logic.getLevel());
        panel.add(check);
        panel.add(logic.getBoardSize());
        panel.add(restart);
        panel.add(slider);
        frame.add(panel, BorderLayout.PAGE_START);
        frame.setVisible(true);
    }
}

