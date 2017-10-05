/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.at;

import dik.adp.app.gui.mainscene.MainscenePresenter;
import dik.adp.app.gui.sharedcommunicationmodel.SharedDiagram;
import dik.adp.app.orientdb.odb2AT;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class AtPresenter implements Initializable {

    @FXML
    private AnchorPane atAnchorPane;

    @FXML
    private VBox atVBox;

    @FXML
    private TextField newATTextField;

    @Inject
    SharedDiagram selectedDiagram;

    @Inject
    odb2AT odb2at;

    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    ObservableList<String> obsListAT = FXCollections.<String>observableArrayList();

//    @Inject
//    MainscenePresenter mainscene;
    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        setupATCheckBoxes();
    }

    private void setupATCheckBoxes() {
        updateATCheckBoxes();
    }

    private void updateATCheckBoxes() {
        obsListAT.clear();
        obsListAT = odb2at.queryAT(obsListAT, selectedDiagram.isSelectedDiagram());
        //erste alte checkboxen entfernen
        checkBoxes.clear();
        atVBox.getChildren().clear();
        //immer alle checkboxen geimeinsam hinzufügen
        for (String atName : obsListAT) {
            CheckBox cb = new CheckBox(atName);
            this.checkBoxes.add(cb);
        }
        atVBox.getChildren().addAll(checkBoxes);
    }

    @FXML
    void createNewAT(ActionEvent event) {
        System.out.println(selectedDiagram.isSelectedDiagram());
        System.out.println(newATTextField.getText());
        odb2at.addATtoDB(newATTextField.getText(), selectedDiagram.isSelectedDiagram());
        newATTextField.clear();
        updateATCheckBoxes();
    }

}
