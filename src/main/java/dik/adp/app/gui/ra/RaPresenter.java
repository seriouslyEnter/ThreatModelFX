/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.ra;

import dik.adp.app.gui.sharedcommunicationmodel.SelectedState;
import dik.adp.app.orientdb.Odb2Ra;
import dik.adp.app.orientdb.odb2Klassen.FxDfdElement4TreeView;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
        System.out.println("Selecten TreeView item: " + selectedItem);
        String string = selectedItem.toString();
        treeViewElements.forEach((k, v) -> {
            if (string.contentEquals(k)) {
                System.out.println("Inhalt von k: " + k);
                System.out.println("Inhalt von k Value: " + v.getTreeItem().getValue().toString());
                System.out.println("Inhalt von v: " + v.getFxDfdElement().toString());
            }
        });

    }
}
