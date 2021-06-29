package pt.ipbeja.po2.contagious.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileWriter {
    public List<Cell> writeCells;
    public List<Cell> healthyList;
    public List<Cell> sickList;
    public List<Cell> immuneList;

    public FileWriter() {
    }

    public void fileWriter(List<Cell> cells, int numCols, int numLines) throws IOException {
        System.out.println("Writing file");
        String fileContent = "";
        healthyList = new ArrayList<>();
        sickList = new ArrayList<>();
        immuneList = new ArrayList<>();
        fileContent = fileContent.concat(numLines + "\n" + numCols + "\n" + "healthy" + "\n");
        for (Cell cell : cells) {
            switch (CellState.from(cell)) {
                case HEALTHY:
                    healthyList.add(cell);
                    break;
                case SICK:
                    sickList.add(cell);
                    break;
                case IMMUNE:
                    immuneList.add(cell);
                    break;
            }
        }
        for (Cell cellHealthy : healthyList) {
            fileContent = fileContent.concat(cellHealthy.cellPosition().getLine() + " " + cellHealthy.cellPosition().getCol() + "\n");
        }
        fileContent = fileContent.concat("immune" + "\n");
        for (Cell cellImmune : immuneList) {
            fileContent = fileContent.concat(cellImmune.cellPosition().getLine() + " " + cellImmune.cellPosition().getCol() + "\n");
        }
        fileContent = fileContent.concat("sick" + "\n");
        for (Cell cellSick : sickList) {
            fileContent = fileContent.concat(cellSick.cellPosition().getLine() + " " + cellSick.cellPosition().getCol() + "\n");
        }

        java.io.FileWriter writer = new java.io.FileWriter("C:\\PeopleInPandemicSimulation.txt");
        writer.write(fileContent);
        writer.close();
    }
}
