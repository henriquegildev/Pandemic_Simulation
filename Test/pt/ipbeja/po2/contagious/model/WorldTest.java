package pt.ipbeja.po2.contagious.model;

import org.junit.Test;
import org.omg.PortableServer.THREAD_POLICY_ID;

import static org.junit.Assert.*;

public class WorldTest {

    @Test
    public void willIntersect() {
        CellPosition cellPosition = new CellPosition(10, 10);
        CellPosition cellPosition2 = new CellPosition(10, 10);
        boolean test = cellPosition.equals(cellPosition2);
        assertEquals(true, test);
    }

    @Test
    public void simulate(){
        int THRESHOLD = 10;
        CellPosition cellPosition1 = new CellPosition(20,20);
        CellPosition cellPosition2 = new CellPosition(30,30);
        CellPosition cellPosition3 = new CellPosition(20,30);
        CellPosition cellPosition4 = new CellPosition(30,20);
        CellPosition cellPosition5 = new CellPosition(40,20);
        Cell cell1 = new SickPerson(cellPosition1);
        Cell cell2 = new HealthyPerson(cellPosition2);
        Cell cell3 = new SickPerson(cellPosition3);
        Cell cell4 = new HealthyPerson(cellPosition4);
        Cell cell5 = new ImmunePerson(cellPosition5);
        cellPosition1.move(2,2);
        cellPosition2.move(2,2);
        cellPosition3.move(2,2);
        cellPosition4.move(2,2);
        cellPosition5.move(2,5);

        double x1 = cellPosition1.getCol();
        double x2 = cellPosition2.getCol();
        double x3 = cellPosition3.getCol();
        double x4 = cellPosition4.getCol();
        double x5 = cellPosition5.getCol();

        double y1 = cellPosition1.getLine();
        double y2 = cellPosition2.getLine();
        double y3 = cellPosition3.getLine();
        double y4 = cellPosition4.getLine();
        double y5 = cellPosition5.getLine();

        int a = 1;
        int b = 2;
        int c = 3;
        int d = 4;

        int x = 0;


        double distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));

        if (distance <= THRESHOLD) {
            x += a;
        }
        double distance2 = Math.sqrt(Math.pow((x3 - x2), 2) + Math.pow((y3 - y2), 2));
        if (distance2 <= THRESHOLD) {
            x += b; //Adds
        }
        double distance3 = Math.sqrt(Math.pow((x4 - x3), 2) + Math.pow((y4 - y3), 2));
        if (distance3 <= THRESHOLD) {
            x += c;
        }
        double distance4 = Math.sqrt(Math.pow((x5 - x4), 2) + Math.pow((y5 - y4), 2));
        if (distance4 <= THRESHOLD) {
            x += d; //Adds
        }

        System.out.println(distance);
        System.out.println(distance2);
        System.out.println(distance3);
        System.out.println(distance4);



        if(cell1 instanceof HealthyPerson && cell2 instanceof SickPerson){
            x += a; //Adds
            System.out.println("infect");
        }else if(cell1 instanceof SickPerson && cell2 instanceof HealthyPerson){
            x += b;
            System.out.println("infect");
        }else if(cell3 instanceof SickPerson && cell4 instanceof SickPerson){
            x += c;
            System.out.println("infect");
        }else if(cell4 instanceof SickPerson && cell5 instanceof ImmunePerson){
            x += d;
            System.out.println("infect");
        }

        assertEquals(8, x);

    }
}