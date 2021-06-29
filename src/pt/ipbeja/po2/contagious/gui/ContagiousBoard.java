package pt.ipbeja.po2.contagious.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipbeja.po2.contagious.model.*;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Author: Henrique Gil
 * For: Programação Orientada a Objetos, 1º ano Eng. Informática, IPBeja ESTIG
 * Title: Pandemic Simulation
 */
public class ContagiousBoard extends VBox implements View {
    public static World world;
    private WorldBoard pane;
    private Label counterLabel;
    private Label healthyPersonCounter;
    private Label sickPersonCounter;
    private Label immunePersonCounter;
    private Label preLoadNumberHealthy;
    private Label preLoadNumberSick;
    private Label preLoadNumberImmune;
    private Label preLoadNumberLines;
    private Label preLoadNumberColumns;
    private BarChart chart1;
    public int healthyPersonNumber = 0;
    public int sickPersonNumber = 0;
    public int immunePersonNumber = 0;
    public static int healthyNumber;
    private int specificLines = 0;
    private int specificColumns = 0;
    private boolean isNormalRun = true;
    public String filePath = "";
    public Stage secondaryStage = new Stage();
    public LineGraph graph = new LineGraph();
    /**
     * Asks the user for the number of Healthy and Sick people.
     * Creates the scene, adding the Start button and later the Pause and Exit button.
     * Also adds the labels for the interactions, healthy people, sick people and immune people.
     * If user does not input values it adds default ones.
     * Adds functions to buttons.
     */
    public ContagiousBoard() {
        Button startButton = new Button("Start Simulation");
        Button exitButton = new Button("Exit Simulation");
        Button stopButton = new Button("Pause Simulation");
        Button fileWriteButton = new Button("Save Current Values");
        Button printButton = new Button("Print Current Numbers to Console");
        MenuItem normal = new MenuItem("Run Default Numbers - H: 100 | S: 1");
        MenuItem request = new MenuItem("Input Specific Values");
        MenuItem loadFile = new MenuItem("Load Values From File");
        MenuButton optionsButton = new MenuButton("Options", null, normal, request, loadFile);

        startButton.setPrefWidth(200);
        startButton.setPrefHeight(50);
        optionsButton.setPrefWidth(200);
        optionsButton.setPrefHeight(50);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(startButton);
        this.getChildren().add(optionsButton);

        normal.setOnAction(ex2 ->{
            specificLines = 100;
            specificColumns = 100;
            healthyPersonNumber = 100;
            sickPersonNumber = 1;
            isNormalRun = true;
            updateFirstLabels();
        });
        request.setOnAction(ex3 -> {
            informationRequests();
            isNormalRun = true;
            updateFirstLabels();
        });
        loadFile.setOnAction(ex4 -> {
            isNormalRun = false;
            filePath = filePathRequest();
            if(filePath == "Empty"){
                filePath = "E:\\Program Files\\IntelliJ Project Files\\tp2-20435-po2\\FileTest.txt";
            }
            System.out.println("filepath" + filePath);
            try {
                loadFromFile(filePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            updateFirstLabels();
        });
        firstLabels();
        this.getChildren().addAll(this.preLoadNumberColumns, this.preLoadNumberLines, this.preLoadNumberHealthy, this.preLoadNumberSick, this.preLoadNumberImmune);
        /**
         * START BUTTON is Pressed
         */
        startButton.setOnMouseClicked((e) -> {

            removeFirstLabels();
            this.setAlignment(Pos.TOP_LEFT);
            numInputCheck();
            if(isNormalRun == true){
                world = new World(this, 100, 100, healthyPersonNumber, sickPersonNumber, immunePersonNumber);
            }else{
                List<Cell> cells = null;
                try {
                    cells = loadFromFile("E:\\Program Files\\IntelliJ Project Files\\tp2-20435-po2\\FileTest.txt");
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                this.world = new World(this, this.specificLines, this.specificColumns, cells, healthyPersonNumber, sickPersonNumber, immunePersonNumber);
            }
            this.pane = new WorldBoard(this.world, 10);
            graph.start(secondaryStage);
            graph.updateData(getHealthyPersonNumber(), getSickPersonNumber(), getImmunePersonNumber());
            //Counters & Labels
            countersAndLabels(); //Adds the Counters and Labels to the scene.
            this.getChildren().remove(optionsButton);
            this.getChildren().remove(startButton);
            this.getChildren().add(exitButton);
            this.getChildren().add(stopButton);
            exitButton.setOnMouseClicked((e2) -> {
                System.exit(0);
            });
            stopButton.setOnMouseClicked((e3) -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e4) {
                    e4.printStackTrace();
                }
                printButton.setOnMouseClicked(ex1 ->{
                    System.out.println("Healthy People" + healthyPersonNumber + "\n" +
                            "Sick People: " + sickPersonNumber + "\n" +
                            "Immune People: " + immunePersonNumber + "\n");
                });
            });
            this.getChildren().addAll(this.counterLabel, this.pane, this.healthyPersonCounter, this.sickPersonCounter, this.immunePersonCounter);
            this.setAlignment(Pos.TOP_RIGHT);
            this.getChildren().add(fileWriteButton);


            fileWriteButton.setOnMouseClicked((e5 -> {
                try {
                    writeToFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }));
            this.getScene().getWindow().sizeToScene();
            world.start();
        });
    }

    public void informationRequests(){
        specificColumns = informationRequest4();
        specificLines = informationRequest5();
        healthyPersonNumber = informationRequest();
        sickPersonNumber = informationRequest2();
        immunePersonNumber = informationRequest3();
        numInputCheck();
    }

    public void numInputCheck(){
        if (healthyPersonNumber <= 0.0){
            healthyPersonNumber = 100;
        }
        if (sickPersonNumber <= 0.0) {
            sickPersonNumber = 1;
        }
        if(immunePersonNumber <= 0.0){
            immunePersonNumber = 1;
        }
    }


    /**
     * Prints out the positions of the cells when they are made.
     */
    @Override
    public void populateWorld(CellPosition position, CellState state) {
        System.out.println(position.getCol() + "," + position.getLine());
        pane.populateWorld(position, state);
    }


    /**
     * Updates the position of the cells in the scene and updates the values of the labels.
     */
    @Override
    public void updatePosition(CellPosition position, int i, int n, CellState cellState) {
        Platform.runLater(() -> {
            countPeople(this.healthyPersonNumber, this.sickPersonNumber, this.immunePersonNumber);
            if ((i + 1) % 10 == 0) {
                this.counterLabel.setText(("MILESTONE - " + "Iterations: " + "" + (i + 1)));
                this.healthyPersonCounter.setText(("Healthy People: " + this.healthyPersonNumber));
                this.sickPersonCounter.setText(("Sick People: " + this.sickPersonNumber));
                this.immunePersonCounter.setText(("Immune People: " + this.immunePersonNumber));
            } else {
                this.counterLabel.setText("Iterations: " + "" + (i + 1));
                this.healthyPersonCounter.setText(("Healthy People: " + this.healthyPersonNumber));
                this.sickPersonCounter.setText(("Sick People: " + this.sickPersonNumber));
                this.immunePersonCounter.setText(("Immune People: " + this.immunePersonNumber));
            }
            graph.updateData(getHealthyPersonNumber(), getSickPersonNumber(), getImmunePersonNumber());
            pane.updatePosition(position, n, cellState);
            System.out.println(i);
        });
    }

    /**
     * Adds the number of Healthy, Sick and Immune people to the respective labels.
     */
    @Override
    public void countPeople(int healthyPeople, int sickPeople, int immunePeople) {
        healthyPersonNumber = healthyPeople;
        sickPersonNumber = sickPeople;
        immunePersonNumber = immunePeople;
    }
    public int getHealthyPersonNumber(){
        return this.healthyPersonNumber;
    }
    public int getSickPersonNumber(){
        return this.sickPersonNumber;
    }
    public int getImmunePersonNumber(){
        return this.immunePersonNumber;
    }
    public List<Cell> loadFromFile(String filePath) throws FileNotFoundException {
        FileReader fileReader = new FileReader();
        fileReader.fileReader(filePath);
        this.specificLines = fileReader.numLines;
        this.specificColumns = fileReader.numCols;
        this.healthyPersonNumber = fileReader.healthyNum;
        this.sickPersonNumber = fileReader.sickNum;
        this.immunePersonNumber = fileReader.immuneNum;
        return fileReader.listCells;
    }

    public void writeToFile() throws IOException {
        FileWriter fileWriter = new FileWriter();
        fileWriter.fileWriter(this.world.cells, specificColumns, specificLines);
    }

    public void firstLabels(){
        this.preLoadNumberColumns = new Label("Columns: " + this.specificColumns);
        this.preLoadNumberLines = new Label("Lines: " + this.specificLines);
        this.preLoadNumberHealthy = new Label("Healthy People: " + this.healthyPersonNumber);
        this.preLoadNumberSick = new Label("Sick People: " + this.sickPersonNumber);
        this.preLoadNumberImmune = new Label("Immune People: " + this.immunePersonNumber);
    /*
        this.preLoadNumberColumns.setPrefWidth(pane.getPrefWidth());
        this.preLoadNumberLines.setPrefWidth(pane.getPrefWidth());
        this.preLoadNumberHealthy.setPrefWidth(pane.getPrefWidth());
        this.preLoadNumberSick.setPrefWidth(pane.getPrefWidth());
        this.preLoadNumberImmune.setPrefWidth(pane.getPrefWidth());
     */
    }

    public void updateFirstLabels(){
        this.preLoadNumberColumns.setText("Columns: " + this.specificColumns);
        this.preLoadNumberLines.setText("Lines: " + this.specificLines);
        this.preLoadNumberHealthy.setText("Healthy People: " + this.healthyPersonNumber);
        this.preLoadNumberSick.setText("Sick People: " + this.sickPersonNumber);
        this.preLoadNumberImmune.setText("Immune People: " + this.immunePersonNumber);
    }

    public void countersAndLabels(){
        this.counterLabel = new Label(("0"));
        this.healthyPersonCounter = new Label(("Healthy People: " + this.healthyPersonNumber));
        this.sickPersonCounter = new Label(("Sick People: " + this.sickPersonNumber));
        this.immunePersonCounter = new Label(("Immune People: " + this.immunePersonNumber));
        this.counterLabel.setPrefWidth(pane.getPrefWidth());
        this.healthyPersonCounter.setPrefWidth(pane.getPrefWidth());
        this.sickPersonCounter.setPrefWidth(pane.getPrefWidth());
        this.immunePersonCounter.setPrefWidth(pane.getPrefWidth());
    }

    public void removeFirstLabels(){
        this.getChildren().remove(this.preLoadNumberColumns);
        this.getChildren().remove(this.preLoadNumberLines);
        this.getChildren().remove(this.preLoadNumberHealthy);
        this.getChildren().remove(this.preLoadNumberSick);
        this.getChildren().remove(this.preLoadNumberImmune);
    }
    public void chartData(){

    }

    /**
     * Asks the user for the number of Healthy people.
     */
    private int informationRequest() {
        int healthyPeople;
        double healthy = 0.0;
        healthy = Double.parseDouble(JOptionPane.showInputDialog("How many healthy people in the simulation?", healthy));
        healthyPeople = (int) healthy;
        System.out.println("Healthy people = " + healthyPeople);
        return healthyPeople;
    }

    /**
     * Asks the user for the number of Sick people.
     */
    private int informationRequest2() {
        int sickPeople;
        double sick = 0.0;
        sick = Double.parseDouble(JOptionPane.showInputDialog("How many sick people in the simulation?", sick));
        sickPeople = (int) sick;
        System.out.println("Sick people = " + sickPeople);
        return sickPeople;
    }

    private int informationRequest3() {
        int immunePeople;
        double immune = 0.0;
        immune = Double.parseDouble(JOptionPane.showInputDialog("How many immune people in the simulation?", immune));
        immunePeople = (int) immune;
        System.out.println("Immune people = " + immunePeople);
        return immunePeople;
    }

    private int informationRequest4() {
        int col;
        double colIn = 0.0;
        colIn = Double.parseDouble(JOptionPane.showInputDialog("How many columns in the grid?", colIn));
        col = (int) colIn;
        System.out.println("Columns = " + col);
        return col;
    }

    private int informationRequest5() {
        int lines;
        double linesIn = 0.0;
        linesIn = Double.parseDouble(JOptionPane.showInputDialog("How many lines in the grid?", linesIn));
        lines = (int) linesIn;
        System.out.println("Lines = " + lines);
        return lines;
    }

    private String filePathRequest(){
        String file = "";
        file = String.valueOf(JOptionPane.showInputDialog("Input File Path", file));
        if(file.isEmpty())
            file = "E:\\Program Files\\IntelliJ Project Files\\tp2-20435-po2\\FileTest.txt";
        System.out.println("FILE:" + file);
        return file;
    }

}
