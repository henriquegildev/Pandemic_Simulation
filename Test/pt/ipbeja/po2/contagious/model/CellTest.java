package pt.ipbeja.po2.contagious.model;

import org.junit.Test;


import static org.junit.Assert.*;

public class CellTest {

    @Test
    public void checkIfOutOfBounds(){
        CellPosition cellPosition = new CellPosition(3,50);
        Cell cell1 = new Cell(cellPosition);
        System.out.println("Left test");
        boolean test1 = cell1.checkIfOutOfBounds();
        assertEquals(false, test1); //Test 1 Left

        CellPosition cellPosition2 = new CellPosition(250,25);
        Cell cell2 = new Cell(cellPosition);
        System.out.println("Right test");
        boolean test2 = cell2.checkIfOutOfBounds();
        assertEquals(false, test2); //Test 2 Right

        CellPosition cellPosition3 = new CellPosition(25,300);
        Cell cell3 = new Cell(cellPosition);
        System.out.println("Bottom test");
        boolean test3 = cell3.checkIfOutOfBounds();
        assertEquals(false, test3); //Test 3 Bottom

        CellPosition cellPosition4 = new CellPosition(25,3);
        Cell cell4 = new Cell(cellPosition);
        System.out.println("Top Test");
        boolean test4 = cell1.checkIfOutOfBounds();
        assertEquals(false, test4); //Test 4 Top



    }

/*
    @Test
    public void randomMove() {
       CellPosition position = new CellPosition(3,3);
        CellPosition positionTest = new CellPosition(3, 3);
        assertEquals(positionTest, position);
    }
 */
}