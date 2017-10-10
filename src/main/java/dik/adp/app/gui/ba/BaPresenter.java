/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.ba;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

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

    @FXML
    void pressToggleButton(ActionEvent event) {
        ToggleButton selectedTB = (ToggleButton) baToggleGroup.getSelectedToggle();
        System.out.println("ToggleButton: " + selectedTB.getId());

        for (int i = 1; i < 5; i++) {
//            hier db abfrage
            for (int j = 1; j < 7; j++) {
                CheckBox cb = new CheckBox();
                baGridPane.add(cb, j, i);
            }
        }

    }

}
