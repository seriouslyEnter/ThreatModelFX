/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.it;

import dik.adp.app.gui.sharedcommunicationmodel.SelectedState;
import dik.adp.app.orientdb.odb2It;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javax.inject.Inject;

public class ItPresenter implements Initializable {

    @FXML
    private VBox itViewVBox;

    @Inject
    private odb2It odb;
    @Inject
    private SelectedState selectedState;

    ToggleGroup tg = new ToggleGroup();

    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        //Notwendig weil das für Injected fxml das nicht nicht SceneBuilder gesetzt werden kann
        AnchorPane.setRightAnchor(itViewVBox, 0.0);
        AnchorPane.setLeftAnchor(itViewVBox, 0.0);
        AnchorPane.setRightAnchor(itViewVBox, 0.0);
//        AnchorPane.setBottomAnchor(atVBox, 0.0);

        setupRadioButton();
    }

    private void setupRadioButton() {
        Integer maxIteration = odb.findMaxIteration(selectedState.isSelectedDiagram());
        System.out.println(maxIteration);

        for (int i = 0; i < maxIteration; i++) {
            RadioButton iterationRB = new RadioButton();
            iterationRB.setToggleGroup(this.tg);
            iterationRB.setText("Iteration: " + (i + 1));
            iterationRB.setUserData(i + 1);
            iterationRB.setOnAction(this::chooseRadioButton);
            if ((Integer) iterationRB.getUserData() == 1 || (Integer) iterationRB.getUserData() == selectedState.getSelectedIt()) {
                iterationRB.setSelected(true);
            }
            this.itViewVBox.getChildren().add(iterationRB);
        }
    }

    @FXML
    private void addNewIteration(ActionEvent event) {
        if (odb.addNewIteration(selectedState.isSelectedDiagram()) == true) {
            RadioButton iterationRB = new RadioButton();
            iterationRB.setToggleGroup(this.tg);
            iterationRB.setText("Iteration: " + odb.findMaxIteration(selectedState.isSelectedDiagram()));
            iterationRB.setUserData(odb.findMaxIteration(selectedState.isSelectedDiagram()));
            this.itViewVBox.getChildren().add(iterationRB);
        } else {
            System.out.println("addNewIteration: FEHLER");
        }
    }

    private void chooseRadioButton(ActionEvent event) {
        Toggle selectedToggle = tg.getSelectedToggle();
        Integer selectedIteration = (Integer) selectedToggle.getUserData();
        selectedState.setSelectedIt(selectedIteration);
        System.out.println("selected iteration: " + selectedIteration);
    }

}
