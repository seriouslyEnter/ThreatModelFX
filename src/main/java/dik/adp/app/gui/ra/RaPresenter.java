/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.ra;

import dik.adp.app.gui.sharedcommunicationmodel.SelectedState;
import dik.adp.app.orientdb.Odb2Ra;
import dik.adp.app.orientdb.odb2Klassen.Dread;
import dik.adp.app.orientdb.odb2Klassen.FxDfdElement4TreeView;
import dik.adp.app.orientdb.odb2Klassen.Stride;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class RaPresenter implements Initializable {

    @FXML
    private TreeView raTreeView;
    @FXML
    private HBox raViewHBox;
    @FXML
    private GridPane dreadGridPane;

    @Inject
    private Odb2Ra odb;
    @Inject
    private SelectedState selectedState;

    private Map<String, FxDfdElement4TreeView> treeViewElements;

    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        //Notwendig weil das für Injected fxml das nicht nicht SceneBuilder gesetzt werden kann
        AnchorPane.setRightAnchor(raViewHBox, 0.0);
        AnchorPane.setLeftAnchor(raViewHBox, 0.0);
        AnchorPane.setTopAnchor(raViewHBox, 0.0);
        AnchorPane.setBottomAnchor(raViewHBox, 0.0);

        setupTreeView();
        setupDread();
    }

    private void setupTreeView() {

        raTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        raTreeView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> this.updateSelectedDfdElement()
        );

        TreeItem root = new TreeItem("DfdElemente");
        raTreeView.setRoot(root);

        TreeItem pTI = new TreeItem("Process");
        root.getChildren().add(pTI);
        TreeItem mTI = new TreeItem("Memory");
        root.getChildren().add(mTI);
        TreeItem dTI = new TreeItem("DFlow");
        root.getChildren().add(dTI);
        TreeItem kTI = new TreeItem("Kommunikationskanal");
        root.getChildren().add(kTI);

        treeViewElements = odb.queryDfdElements(selectedState.isSelectedDiagram());

        treeViewElements.forEach((k, v) -> {
            switch (v.getFxDfdElement().getType()) {
                case "Process":
                    pTI.getChildren().add(v.getTreeItem());
                    break;
                case "Memory":
                    mTI.getChildren().add(v.getTreeItem());
                    break;
                case "DFlow":
                    dTI.getChildren().add(v.getTreeItem());
                    break;
                case "Kommunikationskanal":
                    kTI.getChildren().add(v.getTreeItem());
                    break;
                default:
                    break;
            }
        });
    }

    private void updateSelectedDfdElement() {
        TreeItem selectedItem = (TreeItem) raTreeView.getSelectionModel().getSelectedItem();
        FxDfdElement4TreeView element;
        System.out.println("Selecten TreeView item: " + selectedItem);
        String string = selectedItem.toString();

        element = treeViewElements.get(string);
        if (element != null) {
            System.out.println(element.getFxDfdElement().getRid());

            odb.queryBa(element.getFxDfdElement(), selectedState.getSelectedAt());

            updateDREAD();
        } else {
            System.out.println("kein DFD Element");
            dreadGridPane.getChildren().clear();
            dreadGridPane.setGridLinesVisible(true);
        }
//        treeViewElements.forEach((k, v) -> {
//            if (string.contentEquals(k)) {
//                System.out.println(v.getFxDfdElement().getRid());
////                odb.queryRa();
//            }
//        });
    }

    private void updateDREAD() {
        System.out.println("updateDREAD()");
        
        
//        Label firstLetter;
//        Label labelStride;
//        Integer column = 0;
//        Integer row = 0;
//
//        for (Dread dread : Dread.values()) {
//            column++;
//            //Labes mit dem ersten Buchstaben aus DREAD Bezeichung
//            firstLetter = new Label(dread.name().substring(0, 1));
//            firstLetter.setFont(Font.font("System", FontWeight.BOLD, 36));
//            dreadGridPane.add(firstLetter, column, row);
//        }
//
//        column = 0;
//        row = 0;
//        for (Stride stride : Stride.values()) {
//            row++;
//            labelStride = new Label(stride.getBezeichnung());
//            dreadGridPane.add(labelStride, column, row);
//        }
//
////     ColumnConstraints column0 = new ColumnConstraints(200);
////     column0.setHgrow(Priority.ALWAYS);
////     dreadGridPane.getColumnConstraints().add(0, column0);
////        dreadGridPane.setPrefWidth(800);
//        dreadGridPane.setGridLinesVisible(true);
    }

    private void setupDread() {
        Label firstLetter;
        Label labelStride;
        Integer column = 0;
        Integer row = 0;

        for (Dread dread : Dread.values()) {
            column++;
            //Labes mit dem ersten Buchstaben aus DREAD Bezeichung
            firstLetter = new Label(dread.name().substring(0, 1));
            firstLetter.setFont(Font.font("System", FontWeight.BOLD, 36));
            dreadGridPane.add(firstLetter, column, row);
        }

        column = 0;
        row = 0;
        for (Stride stride : Stride.values()) {
            row++;
            labelStride = new Label(stride.getBezeichnung());
            dreadGridPane.add(labelStride, column, row);
        }
        dreadGridPane.setGridLinesVisible(true);
    }

}
