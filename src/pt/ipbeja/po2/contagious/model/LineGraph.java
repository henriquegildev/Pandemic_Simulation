package pt.ipbeja.po2.contagious.model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LineGraph extends Application {
    private ScheduledExecutorService scheduledExecutorService;
    final static String healthy = "Healthy";
    final static String sick = "Sick";
    final static String immune = "Immune";
    private int healthyNum;
    private int sickNum;
    private int immuneNum;
    private int iterations;
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final StackedBarChart<String, Number> sbc =
            new StackedBarChart<String, Number>(xAxis, yAxis);
    final XYChart.Series<String, Number> series1 =
            new XYChart.Series<String, Number>();
    final XYChart.Series<String, Number> series2 =
            new XYChart.Series<String, Number>();
    final XYChart.Series<String, Number> series3 =
            new XYChart.Series<String, Number>();
    public LineGraph(){}
    public void start(Stage stage) {
        stage.setTitle("Bar Chart for Pandemic Simulation");
        sbc.setTitle("Number of People");
        xAxis.setLabel("People");
        xAxis.setCategories(FXCollections.<String>observableArrayList(
                Arrays.asList(healthy, sick, immune)));
        yAxis.setLabel("Number of People");
        series1.setName("Healthy People");
        series1.getData().add(new XYChart.Data<String, Number>(healthy, healthyNum));
        series2.setName("Sick People");
        series2.getData().add(new XYChart.Data<String, Number>(sick, sickNum));
        series3.setName("Immune People");
        series3.getData().add(new XYChart.Data<String, Number>(immune, immuneNum));
        Scene scene = new Scene(sbc, 800, 600);
        sbc.getData().addAll(series1, series2, series3);
        stage.setScene(scene);
        stage.show();

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {

            Platform.runLater(() -> {
                series1.getData().get(0).setYValue(healthyNum);
                series2.getData().get(0).setYValue(sickNum);
                series3.getData().get(0).setYValue(immuneNum);
            });
        }, 0, 1, TimeUnit.SECONDS);
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        scheduledExecutorService.shutdownNow();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void updateData(int healthy, int sick, int immune){
        healthyNum = healthy;
        sickNum = sick;
        immuneNum = immune;

    }
}
