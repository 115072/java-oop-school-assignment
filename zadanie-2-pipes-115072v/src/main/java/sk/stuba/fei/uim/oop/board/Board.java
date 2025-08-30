package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.pipes.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Board extends JPanel {
    private final Tile[][] board;
    private int startCoordinate;
    private int endCoordinate;

    public Board(int dimension, Random rand) {
        generateStartAndEnd(rand, dimension);
        ArrayList<Integer> tileIndexes;
        tileIndexes = createMaze(dimension);
        this.board = new Tile[dimension][dimension];
        this.setLayout(new GridLayout(dimension, dimension));
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tileIndexes.contains((i * dimension) + j)) {
                    int currentIndex = tileIndexes.get(tileIndexes.indexOf((i * dimension) + j));
                    if (currentIndex == tileIndexes.get(0)) {
                        this.board[i][j] = new Start((i * dimension) + j, rand);
                    } else if (currentIndex == tileIndexes.get(tileIndexes.size() - 1)) {
                        this.board[i][j] = new End((i * dimension) + j, rand);
                    } else {
                        int previousIndex = tileIndexes.get(tileIndexes.indexOf((i * dimension) + j) - 1);
                        int nextIndex = tileIndexes.get(tileIndexes.indexOf((i * dimension) + j) + 1);
                        if (previousIndex + 1 == currentIndex && nextIndex - 1 == currentIndex || previousIndex - 1 == currentIndex && nextIndex + 1 == currentIndex) {
                            this.board[i][j] = new IPipe((i * dimension) + j, rand);
                        } else if ((previousIndex + dimension == currentIndex && nextIndex - dimension == currentIndex) || (previousIndex - dimension == currentIndex && nextIndex + dimension == currentIndex)) {
                            this.board[i][j] = new IPipe((i * dimension) + j, rand);
                        } else {
                            this.board[i][j] = new LPipe((i * dimension) + j, rand);
                        }
                    }
                } else {
                    this.board[i][j] = new Tile((i * dimension) + j);
                }
                this.add(this.board[i][j]);
            }
        }
    }

    private void generateStartAndEnd(Random rand, int sliderValue) {
        int position = rand.nextInt(4);
        if (position == 0) {
            this.startCoordinate = rand.nextInt(sliderValue);
            this.endCoordinate = rand.nextInt(sliderValue) + (sliderValue * (sliderValue - 1));
        } else if (position == 1) {
            this.endCoordinate = rand.nextInt(sliderValue);
            this.startCoordinate = rand.nextInt(sliderValue) + (sliderValue * (sliderValue - 1));
        } else if (position == 2) {
            this.startCoordinate = rand.nextInt(sliderValue) * sliderValue;
            this.endCoordinate = ((rand.nextInt(sliderValue - 1) + 1) * sliderValue) - 1;
        } else {
            this.endCoordinate = rand.nextInt(sliderValue) * sliderValue;
            this.startCoordinate = ((rand.nextInt(sliderValue - 1) + 1) * sliderValue) - 1;
        }
    }

    private ArrayList<Integer> createMaze(int sliderValue) {
        ArrayList<Integer> theWayGeneratedByRandomizedDFS = new ArrayList<>();
        theWayGeneratedByRandomizedDFS.add(startCoordinate);
        Tile[][] twoDArray = new Tile[sliderValue][sliderValue];
        for (int i = 0; i < sliderValue; i++) {
            for (int j = 0; j < sliderValue; j++) {
                twoDArray[i][j] = new Tile((i * sliderValue) + j);
            }
        }
        randomDFS(twoDArray, theWayGeneratedByRandomizedDFS, sliderValue);

        return theWayGeneratedByRandomizedDFS;
    }

    private void randomDFS(Tile[][] twoDArray, ArrayList<Integer> theWay, int sliderValue) {
        int row = theWay.get(theWay.size() - 1) / sliderValue;
        int col = theWay.get(theWay.size() - 1) % sliderValue;
        twoDArray[row][col].setVisited(true);
        ArrayList<Integer> possibleNeighbours = new ArrayList<>();
        if (row - 1 >= 0) {
            possibleNeighbours.add(((row - 1) * sliderValue) + col);
        }
        if (row + 1 < twoDArray.length) {
            possibleNeighbours.add(((row + 1) * sliderValue) + col);
        }
        if (col - 1 >= 0) {
            possibleNeighbours.add((row * sliderValue) + (col - 1));
        }
        if (col + 1 < twoDArray[row].length) {
            possibleNeighbours.add((row * sliderValue) + (col + 1));
        }
        Collections.shuffle(possibleNeighbours);
        boolean canChooseNextVertex = true;
        for (Integer possibleNeighbour : possibleNeighbours) {
            row = possibleNeighbour / sliderValue;
            col = possibleNeighbour % sliderValue;
            if (!twoDArray[row][col].isVisited()) {
                theWay.add(possibleNeighbour);
                canChooseNextVertex = false;
                break;
            }
        }
        if (canChooseNextVertex) {
            theWay.remove(theWay.size() - 1);
        }

        while (theWay.get(theWay.size() - 1) != endCoordinate) {
            randomDFS(twoDArray, theWay, sliderValue);
        }
    }

    public boolean checkThePath(int sliderValue) {
        ArrayList<Tile> path = new ArrayList<>();
        Tile starterTile = (Tile) this.getComponent(startCoordinate);
        starterTile.setConnected(true);
        path.add(starterTile);
        while (true) {
            if (path.get(0) instanceof Start && path.get(path.size() - 1) instanceof End) {
                return true;
            }
            int row = path.get(path.size() - 1).getPosition() / sliderValue;
            int col = path.get(path.size() - 1).getPosition() % sliderValue;
            Tile thisTile = path.get(path.size() - 1);
            boolean gotNewTile = false;
            for (int i = 0; i < 2; i++) {
                if (thisTile.getDirections().get(i).equals(Direction.UP)) {
                    if (row - 1 >= 0) {
                        Tile potentialTile = this.board[row - 1][col];
                        gotNewTile = checkIfConnected(potentialTile, path, Direction.DOWN);
                        if (gotNewTile) {
                            break;
                        }
                    }
                } else if (thisTile.getDirections().get(i).equals(Direction.DOWN)) {
                    if (row + 1 < sliderValue) {
                        Tile potentialTile = this.board[row + 1][col];
                        gotNewTile = checkIfConnected(potentialTile, path, Direction.UP);
                        if (gotNewTile) {
                            break;
                        }
                    }
                } else if (thisTile.getDirections().get(i).equals(Direction.LEFT)) {
                    if (col - 1 >= 0) {
                        Tile potentialTile = this.board[row][col - 1];
                        gotNewTile = checkIfConnected(potentialTile, path, Direction.RIGHT);
                        if (gotNewTile) {
                            break;
                        }
                    }
                } else if (thisTile.getDirections().get(i).equals(Direction.RIGHT)) {
                    if (col + 1 < sliderValue) {
                        Tile potentialTile = this.board[row][col + 1];
                        gotNewTile = checkIfConnected(potentialTile, path, Direction.LEFT);
                        if (gotNewTile) {
                            break;
                        }
                    }
                }
            }
            if (!gotNewTile) {
                return false;
            }
        }

    }

    private boolean checkIfConnected(Tile potentialTile, ArrayList<Tile> path, Direction dir) {
        if (potentialTile instanceof LPipe || potentialTile instanceof IPipe || potentialTile instanceof End) {
            for (int j = 0; j < 2; j++) {
                if (potentialTile.getDirections().get(j).equals(dir) && !path.contains(potentialTile)) {
                    path.add(potentialTile);
                    potentialTile.setConnected(true);
                    return true;
                }
            }
        }
        return false;
    }


}
