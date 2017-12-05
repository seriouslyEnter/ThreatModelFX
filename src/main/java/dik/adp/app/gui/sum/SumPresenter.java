/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.sum;

import dik.adp.app.gui.sharedcommunicationmodel.SelectedState;
import dik.adp.app.orientdb.Odb2Sum;
import dik.adp.app.orientdb.odb2Klassen.FxDfdElement;
import dik.adp.app.orientdb.odb2Klassen.FxIteration;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class SumPresenter implements Initializable {

    @FXML
    private HBox sumHBox;
    @FXML
    private TreeTableView<FxDfdElement> treeTableView;
//    @FXML
//    private TreeTableColumn columnElements;
//    @FXML
//    private TreeTableColumn columnValues;

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private Map<TreeItem, String> treeMap = new HashMap<>();

    @Inject
    Odb2Sum odb;
    @Inject
    SelectedState selectedState;

    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        //Notwendig weil das für Injected fxml das nicht nicht SceneBuilder gesetzt werden kann
        AnchorPane.setRightAnchor(sumHBox, 0.0);
        AnchorPane.setLeftAnchor(sumHBox, 0.0);
        AnchorPane.setTopAnchor(sumHBox, 0.0);
        AnchorPane.setBottomAnchor(sumHBox, 0.0);

        setupTreeTable();

        setupChart();
    }

    
    //TODO: Elemente die nicht unter einer Vertauengrenze müssen noch hinzugefügt werden unter der rootNode
    private void setupTreeTable() {
        Queue<TreeItem<FxDfdElement>> childElementsQueue = new LinkedList<>();
        List<FxDfdElement> childElementList = new ArrayList<>();

        // Create three columns
        TreeTableColumn<FxDfdElement, String> columnKey = new TreeTableColumn<>("Key");
        TreeTableColumn<FxDfdElement, String> columnName = new TreeTableColumn<>("Name");
        TreeTableColumn<FxDfdElement, String> columnType = new TreeTableColumn<>("Type");

        // Add columns to the TreeTableView
        treeTableView.getColumns().addAll(columnKey, columnName, columnType);

        // Set the cell value factory for columns
        columnKey.setCellValueFactory(new TreeItemPropertyValueFactory<>("key"));
        columnName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        columnType.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));

        //rootNode
        TreeItem<FxDfdElement> rootNode = new TreeItem<>(
                odb.querySystemElement(selectedState.getSelectedDiagram())
        );
        treeTableView.setRoot(rootNode);

        //Vertrauensgrenze oder System
        List<FxDfdElement> rootBoundary = odb.queryTopBoundary(
                selectedState.getSelectedDiagram()
        );

        rootBoundary.forEach(item -> {
            TreeItem<FxDfdElement> newItem = new TreeItem<>(item);
            rootNode.getChildren().add(newItem);
            childElementsQueue.add(newItem);
        });

        while (!childElementsQueue.isEmpty()) {
//            childElementList.addAll(odb.queryChildElements(childElementsQueue, selectedState.getSelectedDiagram()));
            childElementsQueue.peek().getChildren().addAll(odb.queryChildElements(childElementsQueue, selectedState.getSelectedDiagram()));

            System.out.println(childElementsQueue.peek().getValue().toString());
            childElementsQueue.poll().getChildren().forEach(item -> {
                System.out.println(item.getValue().toString());
            });
        }

        // Turn off multiple-selection mode for the TreeTableView
        TreeTableViewSelectionModel<FxDfdElement> selectionModel = treeTableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        //Add ChangeListener
        ObservableList<Integer> list = selectionModel.getSelectedIndices();
        // Add a ListChangeListener
        list.addListener((ListChangeListener.Change<? extends Integer> change) -> {
            System.out.println("Row selection has changed");
            System.out.println(selectionModel.getSelectedItem().getValue().toString());
            updateChart(selectionModel.getSelectedItem().getValue());
        });

    }

    private void updateChart(FxDfdElement fxDfdElement) {

        ObservableList<XYChart.Series<String, Number>> dataList
                = FXCollections.<XYChart.Series<String, Number>>observableArrayList();

        XYChart.Series<String, Number> seriesData = new XYChart.Series<>();

        Map<Integer, FxIteration> dProIt;
        if (fxDfdElement.getType().contentEquals("Boundary") || fxDfdElement.getType().contentEquals("DfdDiagram")) {
            dProIt = odb.calculateRiskOfParentElement(fxDfdElement, selectedState.getSelectedDiagram());
        } else {
            dProIt = odb.calculateRiskOfLeafElement(fxDfdElement, selectedState.getSelectedDiagram());
        }

        dProIt.forEach((i, a) -> {
            XYChart.Data<String, Number> data = new XYChart.Data<>("Iteration " + i.toString(), a.getRisk());
            seriesData.getData().add(data);
        });

        dataList.addAll(seriesData);
        barChart.setData(dataList);

        //color only after "this.barChart.setData(dataList);" otherwise nullpointer
        seriesData.getData().forEach((d) -> {
            if (d.YValueProperty().getValue().doubleValue() >= 2.5) {
                d.getNode().setStyle("-fx-bar-fill: red");
            } else if (d.YValueProperty().getValue().doubleValue() > 1.5 & d.YValueProperty().getValue().doubleValue() < 2.5) {
                d.getNode().setStyle("-fx-bar-fill: orange");
            } else if (d.YValueProperty().getValue().doubleValue() <= 1.5) {
                d.getNode().setStyle("-fx-bar-fill: greenyellow");
            }
        });

//        //color
//        this.barChart.getData().get(0).getNode().getProperties().
//                
//        if (a.getRisk().doubleValue() >= 2.5) {
//            data.getNode().setStyle("-fx-bar-fill: red");
//
//        } else if (a.getRisk().doubleValue() > 1.5 & a.getRisk().doubleValue() < 2.5) {
//            data.getNode().setStyle("-fx-bar-fill: yellow");
//
//        } else if (a.getRisk().doubleValue() <= 1.5) {
//            data.getNode().setStyle("-fx-bar-fill: green");
//
//        }
//        if () {
//            Node n0 = this.barChart.lookup(".data0.chart-bar");
//            n0.setStyle("-fx-bar-fill: red");
//        } else if () {
//            Node n1 = this.barChart.lookup(".data1.chart-bar");
//            n1.setStyle("-fx-bar-fill: green");
//        } else if () {
//            Node n2 = this.barChart.lookup(".data2.chart-bar");
//            n2.setStyle("-fx-bar-fill: #ee42f4");
//        } else {
//
//        }
    }

    private void setupChart() {

//        CategoryAxis iterationXAxis = new CategoryAxis();
//        NumberAxis riskYAxis = new NumberAxis();
////        iterationXAxis.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
////        barChart = new BarChart<String, Number>(xAxis, yAxis);
//        this.barChart = new BarChart<String, Number>(iterationXAxis, riskYAxis);
        barChart.setTitle("Risikoentwicklung");
//        iterationXAxis.setLabel("Iterations");
//        riskYAxis.setLabel("Risk");

        xAxis.setLabel("Iterations");
        yAxis.setLabel("Risk");

        barChart.setLegendVisible(false);
        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(3.5d);
//        riskYAxis.setAutoRanging(false);
//        riskYAxis.setUpperBound(3.5d);

//                sumHBox.getChildren().add(barChart);
//        final String austria = "Austria";
//        final String brazil = "Brazil";
//        final String france = "France";
//        final String italy = "Italy";
//        final String usa = "USA";
//        XYChart.Series series1 = new XYChart.Series();
//        series1.setName("2003");
//        series1.getData().add(new XYChart.Data(austria, 25601.34));
//        series1.getData().add(new XYChart.Data(brazil, 20148.82));
//        series1.getData().add(new XYChart.Data(france, 10000));
//        series1.getData().add(new XYChart.Data(italy, 35407.15));
//        series1.getData().add(new XYChart.Data(usa, 12000));
//
//        XYChart.Series series2 = new XYChart.Series();
//        series2.setName("2004");
//        series2.getData().add(new XYChart.Data(austria, 57401.85));
//        series2.getData().add(new XYChart.Data(brazil, 41941.19));
//        series2.getData().add(new XYChart.Data(france, 45263.37));
//        series2.getData().add(new XYChart.Data(italy, 117320.16));
//        series2.getData().add(new XYChart.Data(usa, 14845.27));
//
//        XYChart.Series series3 = new XYChart.Series();
//        series3.setName("2005");
//        series3.getData().add(new XYChart.Data(austria, 45000.65));
//        series3.getData().add(new XYChart.Data(brazil, 44835.76));
//        series3.getData().add(new XYChart.Data(france, 18722.18));
//        series3.getData().add(new XYChart.Data(italy, 17557.31));
//        series3.getData().add(new XYChart.Data(usa, 92633.68));
//
//        barChart.getData().addAll(series1, series2, series3);
//        CategoryAxis xAxis = new CategoryAxis();
//        xAxis.setLabel("Iteration");
//        NumberAxis yAxis = new NumberAxis();
//        yAxis.setLabel("Risk");
////        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
//        barChart = new BarChart<>(xAxis, yAxis);
//        barChart.setTitle("Risikoentwicklung");
//
//        XYChart.Data<String, Number> data1 = new XYChart.Data<>("Iteration 1", 3);
//        XYChart.Data<String, Number> data2 = new XYChart.Data<>("Iteration 2", 2);
//        XYChart.Data<String, Number> data3 = new XYChart.Data<>("Iteration 3", 1);
//
//        XYChart.Series<String, Number> seriesIteration = new XYChart.Series<>();
//        seriesIteration.setName("Iteration");
//        seriesIteration.getData().addAll(data1, data2, data3);
//
//        ObservableList<XYChart.Series<String, Number>> data
//                = FXCollections.<XYChart.Series<String, Number>>observableArrayList();
//        data.addAll(seriesIteration);
//
//        // Set the data for the chart
////ObservableList<XYChart.Series<String,Number>> chartData = new a
////chart.setData(chartData);
////        
//        // Set the data for the chart
//        barChart.setData(data);
//
////// Set the data for the chart
////        ObservableList<XYChart.Series<String, Number>> chartData
////                = XYChartDataUtil.getYearSeries();
////        barChart.setData(chartData);
    }

}
