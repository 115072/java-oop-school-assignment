package sk.stuba.fei.uim.oop.pipes;

import sk.stuba.fei.uim.oop.board.Direction;

import java.awt.*;
import java.util.Random;

public class Start extends Tile {
    public Start(int pos, Random rand) {
        super(pos);
        this.directions.clear();
        this.setBackground(Color.GREEN);
        this.directions.add(Direction.UP);
        this.directions.add(Direction.DOWN);
        initialize(rand);
    }

}
