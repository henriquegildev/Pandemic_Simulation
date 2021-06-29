package pt.ipbeja.po2.contagious.model;

import pt.ipbeja.po2.contagious.gui.ContagiousBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author: Henrique Gil
 * For: Programação Orientada a Objetos, 1º ano Eng. Informática, IPBeja ESTIG
 * Title: Pandemic Simulation
 */

public class World {
    private static final double THRESHOLD = 5.0;

    public static final Random rand = new Random();

    private View view;
    public List<Cell> cells;
    private final int nLines;
    private final int nCols;
    private int healthyPeople;
    private int sickPeople;
    private int immunePeople;

    /**
     *
     * @param healthy
     * Adds the number of HealthyPersons that the user decided to the list HealthyPerson.
     * @param sick
     * Adds the numbers of SickPersons that the user decided to the list SickPerson.
     */
    public World(View view, int nLines, int nCols, int healthy, int sick, int immune) {
        this.view = view;
        this.nLines = nLines;
        this.nCols = nCols;
        this.cells = new ArrayList<>();
        healthyPeople = healthy;
        sickPeople = sick;
        immunePeople = immune;
        System.out.println("Number of Immune" + immune);
        for (int i = 0; i < healthy + sick + immune; i++) {
            CellPosition newPosition = new CellPosition(this.rand.nextInt(nCols - 1) + 1, this.rand.nextInt(nLines - 1) + 1);
            while (this.willIntersect(newPosition))
                newPosition = new CellPosition(this.rand.nextInt(nCols - 1) + 1, this.rand.nextInt(nLines - 1) + 1);

            if (i < healthy){
                this.cells.add(new HealthyPerson(newPosition));
                System.out.println("healthy");
            }
            if(i < healthy + sick && i >= healthy) {
                this.cells.add(new SickPerson(newPosition));
                System.out.println("sick");
            }
            if(i >= healthy + sick){ this.cells.add(new ImmunePerson(newPosition));
                System.out.println("immune");}
        }
    }


    /**
    * This constructor is designed to create a World from a file.
     */
    public World(View view, int nLines, int nCols, List<Cell> cells, int healthy, int sick, int immune){
        this.view = view;
        this.nLines = nLines;
        this.nCols = nCols;
        this.cells = cells;
        this.healthyPeople = healthy;
        this.sickPeople = sick;
        this.immunePeople = immune;
    }
    /**
     * Starts the thread, giving it the number of interactions.
     * The method gives the CPU the instruction to run the code.
     */
    public void start() {
        new Thread(() -> {
            this.populate();
            this.simulate(100);
        }).start();
    }

    /**
     * Returns the Lines added in the board.
     */
    public int nLines() {
        return this.nLines;
    }
    /**
     * Returns the Columns added in the board.
     */
    public int nCols() {
        return this.nCols;
    }

    /**
     * Fills the scene with cells.
     * Since it runs throught the cell list it adds every cell that the user chooses to generate.
     */
    private void populate() {
        cells.forEach(cell -> view.populateWorld(cell.cellPosition(), CellState.from(cell)));
    }

    /**
     * Checks if the position of a cell that it is trying to move intercepts with a cell.
     * Checks if there are any cells that have the same position that the Cell is going to be moved to.
     */
    public boolean willIntersect(CellPosition position) {
        return cells.stream().anyMatch(cell -> cell.cellPosition().equals(position));
    }

    /**
     * Runs the simulation.
     * Pauses the simulation at every "milestone" of 10, so at 10 iterarions, pause, at 20 iterarions, pause, etc.
     * Removes and adds HealthyPerson to SickPerson list, basically replacing the Healthy Cell with a Sick Cell.
     * Runs the "willIntersect" method to see if it's able to move cell to a position of an EmptyCell.
     * Checks time necessary for SickPerson to become ImmunePerson.
     * Updates HealthyPerson, SickPerson and ImmunePerson count, so it can be updates in the scene.
     * Finally updates position of cells.
     */
    public void simulate(int nIter) {
        for (int i = 0; i < nIter; i++) {
            try {
                if (i % 10 == 0 && i != 0) {
                    Thread.sleep(5000); //Milestone sleep
                } else if (i == 0) {
                    Thread.sleep(3000); //Beginning of Simulation Sleep
                } else {
                    Thread.sleep(500); //Pause between iterations
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int j = this.cells.size() - 1; j >= 0; j--) {
                if (this.cells.get(j) instanceof HealthyPerson) {
                    HealthyPerson person = (HealthyPerson) this.cells.get(j);
                    if (person.isInfected()) {
                        this.cells.remove(j);
                        healthyPeople--;
                        sickPeople++;
                        this.cells.add(new SickPerson(person.cellPosition()));
                    }
                }
            }

            for (int n = this.cells.size() - 1; n >= 0; n--) {
                final Cell cell = this.cells.get(n);
                CellPosition newPosition = cell.randomMove();

                while (!newPosition.isInside(this.nLines, this.nCols) && this.willIntersect(newPosition))
                    newPosition = cell.randomMove();

                cell.setPosition(newPosition);

                this.checkInfected(n);
                this.checkCure(n);

                // GUI STUFF
                this.view.countPeople(this.healthyPeople, this.sickPeople, this.immunePeople);
                this.view.updatePosition(cell.cellPosition(), i, n, CellState.from(cell));
            }
        }
    }

    /**
     * This method checks the distance between two cells.
     * It runs through all of the cells and compares each cell to the rest of them.
     */
    public void checkInfected(int n) {
        Cell cell1 = this.cells.get(n);
        final double x1 = cell1.cellPosition().getCol();
        final double y1 = cell1.cellPosition().getLine();

        for (int i = this.cells.size() - 1; i >= 0; i--) {
            if (n == i) continue;

            Cell cell2 = this.cells.get(i);

            final double x2 = cell2.cellPosition().getCol();
            final double y2 = cell2.cellPosition().getLine();

            double distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
            if (distance < THRESHOLD) {
                infects(this.cells.get(n), this.cells.get(i));
            }
        }
    }

    /**
     * Checks if each of the cells is able to be infected.
     * Infects the healthy of the two cells.
     */
    public void infects(Cell cell1, Cell cell2){
        if (cell1 instanceof SickPerson && cell2 instanceof HealthyPerson) {
            HealthyPerson person = (HealthyPerson) cell2;
            person.infect();
            wasInfected(true);
        } else if (cell2 instanceof SickPerson && cell1 instanceof HealthyPerson) {
            HealthyPerson person = (HealthyPerson) cell1;
            person.infect();
            wasInfected(true);
        }else if(cell2 instanceof ImmunePerson || cell2 instanceof ImmunePerson){
            wasInfected(false);
        }
    }
     

    /**
     *  Simple return method to see if there was infection or not.
     */
    public boolean wasInfected(boolean wasIt){
        if(wasIt == true)
            return true;
        else
            return false;
    }

    /**
     * Adds timer to SickPerson so it can become an ImmunePerson.
     */
    private void checkCure(int n) {
        final Cell cell = this.cells.get(n);
        if (cell instanceof SickPerson) {
            SickPerson person = (SickPerson) cell;
            this.cells.remove(n);
            this.cells.add(person.checkCure());
            if (person.isImmune) {
                immunePeople++;
                sickPeople--;
            }
        }
    }

    public static List<Cell> cellPickUp(List<Cell> cells){
        List<Cell> cellPUs = cells;
        FileWriter fileWriter = new FileWriter();
        fileWriter.writeCells = cells;
        return cellPUs;
    }

}