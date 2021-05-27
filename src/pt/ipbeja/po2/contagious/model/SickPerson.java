package pt.ipbeja.po2.contagious.model;

import java.util.Random;

/**
 * Author: Henrique Gil
 * For: Programação Orientada a Objetos, 1º ano Eng. Informática, IPBeja ESTIG
 * Title: Pandemic Simulation
 */

/**
 * Assigns min. and max. time to Infection.
 * Assigns each SickPerson to a random cure time.
 * Checks if the SickPerson can become immune.
 */
public class SickPerson extends Person {

    private final static int MIN_TIME = 10;
    private final static int MAX_TIME = 20;
    public boolean asymptomatic;

    private int time;
    private final int cureTime;

    public boolean isImmune;


    public SickPerson(CellPosition cellPosition) {
        super(cellPosition);

        this.time = 0;
        this.cureTime = new Random().nextInt(MAX_TIME - MIN_TIME) + MIN_TIME + 1;

    }

    public Person checkCure() {
        if (time >= cureTime) {
            isImmune = true;
            return new ImmunePerson(cellPosition());
        }
        time++;
        return this;
    }
}
