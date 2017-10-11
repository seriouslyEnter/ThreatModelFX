/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.ba;

import dik.adp.app.gui.sharedcommunicationmodel.SelectedState;
import dik.adp.app.orientdb.Odb2Ba;
import dik.adp.app.orientdb.odb2Klassen.FxDfdElement;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class BaPresenter implements Initializable {

    @FXML
    HBox baHBox;

    @FXML
    ToggleGroup baToggleGroup;

    @FXML
    GridPane baGridPane;

    @Inject
    Odb2Ba odb;

    @Inject
    SelectedState selectedState;

    String[] stride = {"S", "T", "R", "I", "N", "G"};

    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Notwendig weil das für Injected fxml das nicht nicht SceneBuilder gesetzt werden kann
        AnchorPane.setLeftAnchor(baHBox, 0.0);
        AnchorPane.setRightAnchor(baHBox, 0.0);
        AnchorPane.setTopAnchor(baHBox, 0.0);
        AnchorPane.setBottomAnchor(baHBox, 0.0);

        this.resources = resources;
    }

    private void setupToggleButtons() {

    }

    @FXML
    void pressToggleButton(ActionEvent event) {
        baGridPane.getChildren().clear();
        List<FxDfdElement> atList = new ArrayList<>();
        ToggleButton selectedTB = (ToggleButton) baToggleGroup.getSelectedToggle();
        String type = null;
        switch (selectedTB.getId()) {
            case "processToggleButton":
                type = "Process";
                break;
            case "speicherToggleButton":
                type = "Memory";
                break;
            case "datenToggleButton":
                type = "DFlow";
                break;
            case "komkanToggleButton":
                type = "Kommunikationskanal";
                break;
        }

        //adding STRIDE Label
        for (int i = 0; i < stride.length; i++) {
            Label stringLabel = new Label(stride[i]);
            stringLabel.setFont(Font.font("System", FontWeight.BOLD, 36));
            baGridPane.add(stringLabel, i + 1, 0);
        }
        //adding Query
        if (selectedTB != null) {
            System.out.println("ToggleButton: " + selectedTB.getId());
            atList = odb.queryProcesses(atList, selectedState.isSelectedDiagram(), type);
            //Rows
            for (int i = 0; i < atList.size(); i++) {
                Label lb = new Label(atList.get(i).getKey() + ": " + atList.get(i).getName());
//                baGridPane.getChildren().remove(0, i + 1);
                baGridPane.add(lb, 0, i + 1);
                //Columns
                for (int j = 1; j < 7; j++) {
                    CheckBox cb = new CheckBox();
//                    baGridPane.getChildren().remove(j, i + 1);
                    baGridPane.add(cb, j, i + 1);
                }
            }
        }
    }
}
