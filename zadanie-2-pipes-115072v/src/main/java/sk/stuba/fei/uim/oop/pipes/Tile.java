package sk.stuba.fei.uim.oop.pipes;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Tile extends JPanel {
    @Getter
    protected ArrayList<Direction> directions;
    @Getter
    @Setter
    private boolean connected;
    @Getter
    @Setter
    private boolean visited;
    @Getter
    private int position;

    public Tile(int pos) {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.BLUE);
        this.position = pos;
        this.directions = new ArrayList<>();
    }

    public void rotate() {
        if (directions.size() != 0) {
            this.directions.set(0, this.directions.get(0).nextDirection());
            this.directions.set(1, this.directions.get(1).nextDirection());

        }
    }

    protected void initialize(Random rand) {
        if (this.directions.size() != 0) {
            int rotate = rand.nextInt(3) + 1;
            for (int i = 0; i < rotate; i++) {
                rotate();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setStroke(new BasicStroke(3));
        if (this.connected) {
            g.setColor(Color.CYAN);
        } else {
            g.setColor(Color.BLACK);
        }
        for (Direction direction : directions) {
            if (direction.equals(Direction.DOWN)) {
                g.fillRect((this.getWidth() / 4), this.getHeight() / 2, this.getWidth() / 4, this.getHeight());
            } else if (direction.equals(Direction.UP)) {
                g.fillRect((this.getWidth()) / 4, 0, (this.getWidth() / 4), (this.getHeight() / 4) * 3);
            } else if (direction.equals(Direction.RIGHT)) {
                g.fillRect((this.getWidth() / 4), this.getHeight() / 2, this.getWidth(), this.getHeight() / 4);
            } else if (direction.equals(Direction.LEFT)) {
                g.fillRect(0, this.getHeight() / 2, (this.getWidth() / 4), (this.getHeight() / 4));
            }
        }
    }

}
