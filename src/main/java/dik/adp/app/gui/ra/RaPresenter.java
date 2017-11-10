/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.ra;

import dik.adp.app.gui.sharedcommunicationmodel.SelectedState;
import dik.adp.app.orientdb.Odb2Ra;
import dik.adp.app.orientdb.odb2Klassen.FxDfdElement4Ra;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
//    @FXML
//    private VBox raViewVBox;
//    @FXML
//    private AnchorPane raViewAnchorPane;

    @Inject
    private Odb2Ra odb;
    @Inject
    private SelectedState selectedState;

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
        List<FxDfdElement4Ra> dfdElements;

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

        dfdElements = odb.queryDfdElements(selectedState.isSelectedDiagram());

        for (FxDfdElement4Ra dfdElement : dfdElements) {
            switch (dfdElement.getType()) {
                case "Process":
                    TreeItem p = new TreeItem(dfdElement);

                    pTI.getChildren().add(p);
                    break;
                case "Memory":
                    TreeItem m = new TreeItem(dfdElement);
                    mTI.getChildren().add(m);
                    break;
                case "DFlow":
                    TreeItem d = new TreeItem(dfdElement);
                    dTI.getChildren().add(d);
                    break;
                case "Kommunikationskanal":
                    TreeItem k = new TreeItem(dfdElement);
                    kTI.getChildren().add(k);
                    break;
                default:
                    break;
            }
        }

    }
}
