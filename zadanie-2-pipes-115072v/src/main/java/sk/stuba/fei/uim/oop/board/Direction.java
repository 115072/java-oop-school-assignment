package sk.stuba.fei.uim.oop.board;

public enum Direction {

    UP,
    DOWN,
    LEFT,
    RIGHT;

    public Direction nextDirection() {
        if (this.equals(UP)) {
            return RIGHT;
        } else if (this.equals(DOWN)) {
            return LEFT;
        } else if (this.equals(RIGHT)) {
            return DOWN;
        } else {
            return UP;
        }
    }
}
