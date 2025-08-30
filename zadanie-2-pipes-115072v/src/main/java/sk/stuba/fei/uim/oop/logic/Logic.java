package sk.stuba.fei.uim.oop.logic;

import lombok.Getter;
import sk.stuba.fei.uim.oop.adapter.Adapters;
import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.pipes.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Logic extends Adapters {
    public static final String RESTART = "RESTART";
    public static final String CHECK = "CHECK";
    private final JFrame frame;
    private final Random rand = new Random();
    private Board board;
    private int sliderValue;
    @Getter
    private JLabel level;
    @Getter
    private JLabel boardSize;
    private int levelCounter;


    public Logic(JFrame mainGame) {
        this.frame = mainGame;
        sliderValue = 8;
        this.initializeNewBoard(sliderValue);
        this.frame.add(this.board);
        this.frame.setFocusable(true);
        this.levelCounter = 1;
        this.level = new JLabel();
        this.boardSize = new JLabel();
        this.updateLevel();
        this.updateBoardSizeLabel();
        this.frame.revalidate();
        this.frame.repaint();
    }

    private void updateLevel() {
        this.level.setText("LEVEL: " + this.levelCounter);
        this.frame.repaint();
    }

    private void updateBoardSizeLabel() {
        this.boardSize.setText("BOARD SIZE: " + this.sliderValue + "X" + this.sliderValue);
        this.frame.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Component current = this.board.getComponentAt(e.getX(), e.getY());
        if ((current instanceof Tile)) {
            if (!(current instanceof Start) && !(current instanceof End)) {
                current.setBackground(Color.YELLOW);

            }
        }
        for (int i = 0; i < sliderValue * sliderValue; i++) {
            if (current != this.board.getComponent(i) && !(this.board.getComponent(i) instanceof Start) && !(this.board.getComponent(i) instanceof End)) {
                this.board.getComponent(i).setBackground(Color.BLUE);
            }
        }
        this.board.repaint();
    }

    public void mouseExited(MouseEvent e) {
        Component current = this.board.getComponentAt(e.getX(), e.getY());
        if ((current instanceof Tile)) {
            current.setBackground(Color.BLUE);
        }
        this.board.repaint();
    }


    public void mouseEntered(MouseEvent e) {
        for (int i = 0; i < sliderValue * sliderValue; i++) {
            Tile tile = (Tile) this.board.getComponent(i);
            if ((tile instanceof Start || tile instanceof LPipe || tile instanceof IPipe) && tile.isConnected()) {
                tile.setConnected(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(RESTART)) {
            levelCounter = 1;
            restartGame();
        } else if (e.getActionCommand().equals(CHECK)) {
            if (board.checkThePath(sliderValue)) {
                this.levelCounter++;
                this.updateLevel();
                restartGame();
            }
            this.frame.repaint();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        if (!slider.getValueIsAdjusting()) {
            sliderValue = slider.getValue();
            this.levelCounter = 1;
            this.updateBoardSizeLabel();
            this.updateLevel();
            restartGame();
            this.frame.setFocusable(true);
            this.frame.requestFocus();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component current = this.board.getComponentAt(e.getX(), e.getY());
        if (current instanceof Tile) {
            ((Tile) current).rotate();
            this.frame.repaint();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_R) {
            levelCounter = 1;
            restartGame();
        }
        if (key == KeyEvent.VK_ESCAPE) {
            this.frame.dispose();
            System.exit(0);
        }
        if (key == KeyEvent.VK_ENTER) {
            if (board.checkThePath(sliderValue)) {
                this.levelCounter++;
                this.updateLevel();
                restartGame();
            }
            this.frame.repaint();
        }
    }

    private void restartGame() {
        this.frame.remove(this.board);
        initializeNewBoard(sliderValue);
        this.frame.add(this.board);
        this.updateLevel();
        this.frame.revalidate();
        this.frame.repaint();
    }

    private void initializeNewBoard(int dimension) {
        this.board = new Board(dimension, this.rand);
        this.board.addMouseMotionListener(this);
        this.board.addMouseListener(this);
    }
}
