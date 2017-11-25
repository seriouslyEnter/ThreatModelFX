/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.sum;

import dik.adp.app.gui.sharedcommunicationmodel.SelectedState;
import dik.adp.app.orientdb.Odb2Sum;
import dik.adp.app.orientdb.odb2Klassen.FxDfdElement;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
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
    private BarChart barChart;

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
    }

    private void setupTreeTable() {
        // Create three columns
        TreeTableColumn<FxDfdElement, String> columnElements = new TreeTableColumn<>("Elements");
        TreeTableColumn<FxDfdElement, String> columnValues = new TreeTableColumn<>("Werte");
        TreeTableColumn<FxDfdElement, String> column3 = new TreeTableColumn<>("dsfgh");
        TreeTableColumn<FxDfdElement, String> column4 = new TreeTableColumn<>("Wdfgdsgerte");
        TreeTableColumn<FxDfdElement, String> column5 = new TreeTableColumn<>("adfgs");
        TreeTableColumn<FxDfdElement, String> column6 = new TreeTableColumn<>("gfhds");

        // Add columns to the TreeTableView
        treeTableView.getColumns().addAll(columnElements, columnValues);

        // Set the cell value factory for columns
        //ACHTUNG RID KEY SIND DIE DATEN DES OBJEKTS                         !!!!!!!!!!!
        columnElements.setCellValueFactory(new TreeItemPropertyValueFactory<>("rid"));
        columnValues.setCellValueFactory(new TreeItemPropertyValueFactory<>("key"));

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
        });

//        //funktioniert wollte aber .forEach ausprobieren
//        for (FxDfdElement fxDfdElement : listOfBoundary) {
//            TreeItem<FxDfdElement> treeItem = new TreeItem<>(fxDfdElement);
//            boundaryTItemList.add(treeItem);
//        }
//        listOfBoundary.forEach(item -> {
//            TreeItem<FxDfdElement> treeItem = new TreeItem<>(item);
//            rootNode.getChildren().add(treeItem);
//            System.out.println(treeItem.toString());
//        });
//        // Create TreeItems
//        FxDfdElement ram = new FxDfdElement("Ram", "Singh", LocalDate.of(1930, 1, 1));
//        FxDfdElement janki = new FxDfdElement("Janki", "Sharan", LocalDate.of(1956, 12, 17));
//        
//        
//        //Add TreeItems
// 
//        
//        TreeItem<FxDfdElement> jankiNode = new TreeItem<>(janki);
//
//        // Add children to the root node
//        rootNode.getChildren().addAll(jankiNode);
//        
//        // Set the model for the TreeTableView
//        treeTableView.setRoot(rootNode);
    }

}
