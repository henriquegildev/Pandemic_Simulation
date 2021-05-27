package pt.ipbeja.po2.contagious.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import pt.ipbeja.po2.contagious.*;

public class FileReader {
    /*
    public static void main(String[] args) throws FileNotFoundException{
        File file = new File("E:\\Program Files\\IntelliJ Project Files\\tp2-20435-po2\\FileTest.txt");
        Scanner scanner = new Scanner(file);
        String reader = "";
        fileReader(scanner);
    }
     */
    public static final String immune = "immune";
    public static final String sick = "sick";
    public int numLines;
    public int numCols;
    public List<Cell> listCells;
    public int healthyNum;
    public int sickNum;
    public int immuneNum;
    public FileReader(){}
    public void fileReader(String FilePath) throws FileNotFoundException {
        File file = new File(FilePath);
        Scanner scanner = new Scanner(file);
        ArrayList<String> listString = new ArrayList<>();
        String[] coordinates;
        listCells = new ArrayList<>();

        int count;
        int x;
        int y;
        System.out.println("Reading File");
        while(scanner.hasNextLine()){
            listString.add(scanner.nextLine());
        }
        try {
            numLines = Integer.parseInt(listString.get(0));
            numCols = Integer.parseInt(listString.get(1));
            count = 3;
            while (!listString.get(count).equals(immune)){
                coordinates = listString.get(count).split(" ");
                x = Integer.parseInt(coordinates[0]);
                y = Integer.parseInt(coordinates[1]);
                listCells.add(new HealthyPerson(new CellPosition(y, x)));
                count++;
                healthyNum++;
                //System.out.println(listString.get(count));
            }
            count++;
            while (!listString.get(count).equals(sick)){
                coordinates = listString.get(count).split(" ");
                x = Integer.parseInt(coordinates[0]);
                y = Integer.parseInt(coordinates[1]);
                listCells.add(new ImmunePerson(new CellPosition(y, x)));
                count++;
                immuneNum++;
            }
            count++;
            while (count < listString.size()){
                coordinates = listString.get(count).split(" ");
                x = Integer.parseInt(coordinates[0]);
                y = Integer.parseInt(coordinates[1]);
                listCells.add(new SickPerson(new CellPosition(y, x)));
                count++;
                sickNum++;
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
