package pt.ipbeja.po2.contagious.model;

import java.util.Objects;
/**
 * Author: Henrique Gil
 * For: Programação Orientada a Objetos, 1º ano Eng. Informática, IPBeja ESTIG
 * Title: Pandemic Simulation
 */

public class CellPosition {
    private int line;
    private int col;

    public CellPosition(int col, int line) {
        this.line = line;
        this.col = col;
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellPosition cellPosition = (CellPosition) o;
        return line == cellPosition.line &&
                col == cellPosition.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, col);
    }
    /**
     * Moves the cell in a specified way.
     */
    public CellPosition move(int dx, int dy) {
        return new CellPosition(this.col + dx, this.line + dy);
    }
    /**
     * Checks if the cell is inside of the same EmptyCell as another.
     */
    public boolean isInside(int nLines, int nCols) {
        return 0 <= this.line && this.line < nLines && 0 <= this.col && this.col < nCols;
    }
}
