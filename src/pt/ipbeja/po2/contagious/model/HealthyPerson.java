package pt.ipbeja.po2.contagious.model;

/**
 * Author: Henrique Gil
 * For: Programação Orientada a Objetos, 1º ano Eng. Informática, IPBeja ESTIG
 * Title: Pandemic Simulation
 */
public class HealthyPerson extends Person {

    private boolean infected = false;

    public HealthyPerson(CellPosition cellPosition) {
        super(cellPosition);
    }

    public void infect() {
        infected = true;
    }

    public boolean isInfected() {
        return this.infected;
    }
}
