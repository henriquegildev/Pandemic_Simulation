package pt.ipbeja.po2.contagious.gui;


import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle.*;
import pt.ipbeja.po2.contagious.model.LineGraph;

import java.awt.*;

/**
 * Author: Henrique Gil
 * For: Programação Orientada a Objetos, 1º ano Eng. Informática, IPBeja ESTIG
 * Title: Pandemic Simulation
 */
public class GuiStart extends Application {

    /**
     * Sets the scene, adds the Title and properties of the window.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        ContagiousBoard board = new ContagiousBoard();
        Scene scene = new Scene(board);

        primaryStage.setScene(scene);


        board.setPrefSize(1920, 1080);
        primaryStage.setTitle("Contagious Disease Simulation");
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();

        primaryStage.setOnCloseRequest((e) -> {
            System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String args) {
        Application.launch(args);
    }



}

