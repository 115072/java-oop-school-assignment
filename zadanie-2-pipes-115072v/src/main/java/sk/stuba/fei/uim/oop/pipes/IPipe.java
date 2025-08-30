package sk.stuba.fei.uim.oop.pipes;

import sk.stuba.fei.uim.oop.board.Direction;

import java.util.Random;

public class IPipe extends Tile {
    public IPipe(int pos, Random rand) {
        super(pos);
        this.directions.clear();
        this.directions.add(Direction.UP);
        this.directions.add(Direction.DOWN);
        initialize(rand);
    }

}
