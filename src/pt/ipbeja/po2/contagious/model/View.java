package pt.ipbeja.po2.contagious.model;

/**
 * Author: Henrique Gil
 * For: Programação Orientada a Objetos, 1º ano Eng. Informática, IPBeja ESTIG
 * Title: Pandemic Simulation
 */

/**
 * Makes the scene get the rectangles and updates it.
 */
public interface View {
    void populateWorld(CellPosition position, CellState cellState);

    void updatePosition(CellPosition cellPosition, int i, int n, CellState from);
    void countPeople(int healthyPeople, int sickPeople, int immunePeople);
}
