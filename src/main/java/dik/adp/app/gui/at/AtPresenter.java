/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.at;

import dik.adp.app.gui.sharedcommunicationmodel.SharedDiagram;
import dik.adp.app.orientdb.odb2AT;
import dik.adp.app.orientdb.odb2Klassen.FxAT;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private VBox listATVBox;

    @FXML
    private TextField newATTextField;

    @Inject
    SharedDiagram selectedDiagram;

    @Inject
    odb2AT odb2at;

//    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    ObservableList<FxAT> obsListAT = FXCollections.<FxAT>observableArrayList();
    ObservableList<CheckBox> checkBoxList = FXCollections.<CheckBox>observableArrayList();

//    @Inject
//    MainscenePresenter mainscene;
    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        //Notwendig weil das für Injected fxml das nicht nicht SceneBuilder gesetzt werden kann
        AnchorPane.setRightAnchor(atVBox, 0.0);
        AnchorPane.setLeftAnchor(atVBox, 0.0);
        AnchorPane.setRightAnchor(atVBox, 0.0);
//        AnchorPane.setBottomAnchor(atVBox, 0.0);

        setupATCheckBoxes();
    }

    private void setupATCheckBoxes() {
        updateATCheckBoxes();

    }

    private void updateATCheckBoxes() {
        obsListAT.clear();
        obsListAT = odb2at.queryAT(obsListAT, selectedDiagram.isSelectedDiagram());
        //erste alte checkboxen entfernen
        checkBoxList.clear();
        listATVBox.getChildren().clear();
        //immer alle checkboxen geimeinsam hinzufügen
        for (FxAT at : obsListAT) {
            CheckBox cb = new CheckBox(at.getName());
            cb.setSelected(at.getSelected());
            this.checkBoxList.add(cb);
            //addlistener
            cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue ov,Boolean oldVal, Boolean newVal) {
                        System.out.println("CHECKBOX: " + newVal.toString());
                        System.out.println(cb.getText());
                        odb2at.saveSelectedAT(selectedDiagram.isSelectedDiagram(), cb.getText(), newVal);
                }
            });
            //add buttons to aktivate
        }
        listATVBox.getChildren().addAll(checkBoxList);

//        add and remove listener in loop here
    }

    private void change() {

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
