package sk.stuba.fei.uim.oop.pipes;

import sk.stuba.fei.uim.oop.board.Direction;

import java.awt.*;
import java.util.Random;

public class End extends Tile {
    public End(int pos, Random random) {
        super(pos);
        this.directions.clear();
        this.setBackground(Color.RED);
        this.directions.add(Direction.UP);
        this.directions.add(Direction.DOWN);
        initialize(random);
    }

}
