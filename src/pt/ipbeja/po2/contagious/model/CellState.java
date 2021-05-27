package pt.ipbeja.po2.contagious.model;

/**
 * Author: Henrique Gil
 * For: Programação Orientada a Objetos, 1º ano Eng. Informática, IPBeja ESTIG
 * Title: Pandemic Simulation
 */
public enum CellState {

    EMPTY(-1),
    HEALTHY(0),
    SICK(1),
    IMMUNE(2);

    /**
     * Assigns an ID to each state of the Cell.
     */
    public static CellState from(Cell cell) {
        if (cell instanceof HealthyPerson)
            return CellState.HEALTHY;
        if (cell instanceof SickPerson)
            return CellState.SICK;
        if (cell instanceof ImmunePerson)
            return CellState.IMMUNE;
        else
            return CellState.EMPTY;
    }

    public int id;

    CellState(int id) {
        this.id = id;
    }
}
