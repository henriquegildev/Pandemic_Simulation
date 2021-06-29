package pt.ipbeja.po2.contagious.model;

import org.junit.Test;
/**
 * Author: Henrique Gil
 * For: Programação Orientada a Objetos, 1º ano Eng. Informática, IPBeja ESTIG
 * Title: Pandemic Simulation
 */

public class Cell {
    private CellPosition cellPosition;
    private int dx;
    private int dy;

    public Cell(CellPosition cellPosition) {
        this.cellPosition = cellPosition;
    }


    public CellPosition cellPosition() {
        return cellPosition;
    }

    public CellPosition randomMove() {
        final int[] v = {-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5};
        this.dx = v[World.rand.nextInt(11)];
        this.dy = v[World.rand.nextInt(11)];
        checkIfOutOfBounds();
        if (this.cellPosition.getLine() + dy <= 0) {
            dy = 5;
        }
        if (this.cellPosition.getLine() + dy >= 100) {
            dy = -5;
        }
        if (this.cellPosition.getCol() + dx <= 0) {
            dx = 5;
        }
        if (this.cellPosition.getCol() + dx >= 100) {
            dx = -5;
        }

        return this.cellPosition.move(dx, dy);
    }

    /**
     * Checks if Cell is outside of defined parameters.
     */
    public boolean checkIfOutOfBounds(){
        if (this.cellPosition.getLine() + dy <= 5) {
            System.out.println("Cell is out of bounds");
            return false;
        }else if (this.cellPosition.getLine() + dy >= 100) {
            System.out.println("Cell is out of bounds");
            return false;
        }else if (this.cellPosition.getCol() + dx <= 15) {
            System.out.println("Cell is out of bounds");
            return false;
        } else if (this.cellPosition.getCol() + dx >= 200) {
            System.out.println("Cell is out of bounds");
            return false;
        }else{
            return true;
        }

    }

    public int dx() {
        return this.dx;
    }

    public int dy() {
        return this.dy;
    }

    /**
     * Sets the new position of the cell.
     */
    public void setPosition(CellPosition newPosition) {
        this.cellPosition = newPosition;
    }

    /**
     * Universal method.
     * In this case it is used to compare the positions of two cells.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return dx == cell.dx && dy == cell.dy
                && cellPosition.equals(cell.cellPosition);
    }
}
