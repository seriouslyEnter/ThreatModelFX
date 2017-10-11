/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.at;

import dik.adp.app.gui.sharedcommunicationmodel.SelectedState;
import dik.adp.app.orientdb.odb2AT;
import dik.adp.app.orientdb.odb2Klassen.FxAT;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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

//    @FXML
//    private VBox listATVBox;
    @FXML
    private GridPane atGridPane;

    @FXML
    private TextField newATTextField;

    @Inject
    private SelectedState selectedState;

    @Inject
    private odb2AT odb2at;

//    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    ObservableList<FxAT> obsListAT = FXCollections.<FxAT>observableArrayList();
    ObservableList<CheckBox> checkBoxList = FXCollections.<CheckBox>observableArrayList();
    private List<ToggleButton> toggleButtonList = new ArrayList<>();

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
        obsListAT = odb2at.queryAT(obsListAT, selectedState.isSelectedDiagram());
        //erste alte checkboxen entfernen
        checkBoxList.clear();
//        listATVBox.getChildren().clear();
        atGridPane.getChildren().clear();
        //immer alle checkboxen geimeinsam hinzufügen
        for (FxAT at : obsListAT) {
            CheckBox cb = new CheckBox(at.getName());
            cb.setId(at.getName());
            cb.setSelected(at.getSelected());
//            checkBoxList.add(cb);
            atGridPane.add(cb, 0, obsListAT.indexOf(at));
            //addlistener
//                        cb.selectedProperty().addListener((ObservableValue ov, Boolean oldVal, Boolean newVal) -> {
            cb.selectedProperty().addListener((ov, oldVal, newVal) -> {
                System.out.println("CHECKBOX: " + newVal.toString());
                System.out.println(cb.getText());
                odb2at.saveSelectedAT(selectedState.isSelectedDiagram(), cb.getText(), newVal);
                updateATCheckBoxes();
                //selected State zurücksetzen falls der ausgeschaltete checkox gerade aktiv geschaltete ist
                if (selectedState.isSelectedAt() != null) {
                    if (selectedState.isSelectedAt().equals(cb.getId())) {
                        selectedState.setSelectedAt(null);
                        System.out.println("Selected AT State: " + selectedState.isSelectedAt());
                    }
                }
            });
            //ToggleButton
            ToggleButton tb = new ToggleButton("inactive");
            //zu einer List hinzufügen um später für activateToggleButton() zu benutzen
            toggleButtonList.add(tb);
            atGridPane.add(tb, 1, obsListAT.indexOf(at));
            tb.setId(at.getName());
            tb.setDisable(!cb.isSelected());
            if (selectedState.isSelectedAt() != null && selectedState.isSelectedAt().equals(tb.getId())){
                activateToggleButton(tb.getId());
            }
            tb.setOnAction(e -> activateToggleButton(tb.getId()));

        }
//        listATVBox.getChildren().addAll(checkBoxList);
//        add and remove listener in loop here
    }

    void activateToggleButton(String toggleButtonId) {
        System.out.println("ToggleButton");
        //erst alle auf fals unselected setzen

        for (ToggleButton toggleButton : toggleButtonList) {
            {
                if (!toggleButton.getId().equals(toggleButtonId)) {
                    toggleButton.setSelected(false);
                    toggleButton.setText("inactive");
                } else {
                    if (true) {
                        //dann den einen Button auf selected(true) setzen
                        toggleButton.setSelected(true);
                        toggleButton.setText("active");
                        //save selceted AT in shared State
                        selectedState.setSelectedAt(toggleButtonId);
                    }
                }
            }
        }
        System.out.println("Selected AT State: " + selectedState.isSelectedAt());
    }

    @FXML
    void createNewAT(ActionEvent event
    ) {
        System.out.println(selectedState.isSelectedDiagram());
        System.out.println(newATTextField.getText());
        odb2at.addATtoDB(newATTextField.getText(), selectedState.isSelectedDiagram());
        newATTextField.clear();
        updateATCheckBoxes();
    }

}
