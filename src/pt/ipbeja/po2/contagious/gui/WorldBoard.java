package pt.ipbeja.po2.contagious.gui;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import pt.ipbeja.po2.contagious.model.CellPosition;
import pt.ipbeja.po2.contagious.model.CellState;
import pt.ipbeja.po2.contagious.model.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Henrique Gil
 * For: Programação Orientada a Objetos, 1º ano Eng. Informática, IPBeja ESTIG
 * Title: Pandemic Simulation
 */

public class WorldBoard extends Pane {

    static public final Color[] STATE_COLORS = {Color.BLUE, Color.RED, Color.GREEN};
    private final int CELL_SIZE;
    private final int nLinesPane;
    private final int nColsPane;

    private List<Rectangle> rectangles;
    /**
     * Creates the list of rectangles.
     * Set's the number of columns and lines.
     * And attributes the size of the cell defined in the code to the Cell.
     */
    public WorldBoard(World world, int size) {
        this.CELL_SIZE = size;
        this.nLinesPane = world.nLines() * CELL_SIZE;
        this.nColsPane = world.nCols() * CELL_SIZE;
        this.setPrefSize(this.nLinesPane, this.nColsPane);
        this.rectangles = new ArrayList<>();
    }
    /**
     * Adds the properties of the rectangles to a List of rectangles, so they can be seen.
     */
    public void populateWorld(CellPosition position, CellState state) {
        this.rectangles.add(addRectangle(position, state));
    }
    /**
     * Method to update the position of the Rectangles.
     * Could not find a way to animate the movement of the rectangles, since it broke the program.
     * Rectangles went out of sight quickly if Animations were on.
     * This way Rectangles have to teleport to position.
     */
    public void updatePosition(CellPosition position, int n, CellState cellState) {
        Rectangle rectangle = this.rectangles.get(n);
        rectangle.setX(position.getCol() * CELL_SIZE);
        //        rectangle.setY(position.getLine() * CELL_SIZE);
        //        //double x = rectangles.get(n).getX();
        //        //double y = rectangles.get(n).getY();
        rectangle.setFill(STATE_COLORS[cellState.id]);
    }
    /**
     * Effectively adds the rectangle to it's position in the scene.
     */
    private Rectangle addRectangle(CellPosition position, CellState cellState) {
        int line = position.getLine() * CELL_SIZE;
        int col = position.getCol() * CELL_SIZE;

        Rectangle r = new Rectangle(col, line, CELL_SIZE, CELL_SIZE);
        System.out.println(col + "," + line);
        r.setFill(STATE_COLORS[cellState.id]);
        r.setStroke(Color.BLACK);
        Platform.runLater(() -> {
            this.getChildren().add(r);
        });
        return r;
    }
}
