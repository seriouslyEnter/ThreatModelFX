/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.navigation;

import dik.adp.app.gui.sharedcommunicationmodel.ViewState;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class NavigationPresenter implements Initializable {

    @FXML
    private ToggleButton dfdNavButton;
    @FXML
    private ToggleButton atNavButton;
    @FXML
    private ToggleButton baNavButton;
    @FXML
    private ToggleButton raNavButton;
    @FXML
    private ToggleButton sumNavButton;
    @FXML
    private ToggleGroup navToggleGroup;

    @Inject
    private ViewState viewState;

    private ToggleButton previouslySelectedToggleButton;

//    @Inject
//    MainscenePresenter mainscene;
    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        //set this on startup, to prevent null
        this.previouslySelectedToggleButton = dfdNavButton;

    }

//    @FXML
//    void showDfdScene(ActionEvent event) {
//        viewState.setAtShowing(true);
//    }
//
//    @FXML
//    void hideDfdScene(ActionEvent event) {
//        viewState.setAtShowing(false);
//    }
//    @FXML
//    void showDfd(ActionEvent event) {
//        viewState.setDfdShowing(true);
//    }
//
//    @FXML
//    void hideDfd(ActionEvent event) {
//        viewState.setDfdShowing(false);
//    }
    @FXML
    void navButtonAction(ActionEvent event) {

        Toggle selectedToggle;
        ToggleButton selectedNavButton;

        selectedToggle = navToggleGroup.getSelectedToggle();

        /*Wenn ein Button kann selectiert und unselectiert werden,
        deshalb checken wir ob null (kein button slectiert),
        dann wird auf alten button selectiert,
        oder wenn selectiert dann gehts weiter
        !null -> ok -> get selectedNavButton
        null means -> unselected -> set to previous selected -> get selectedNavButton
         */
        if (selectedToggle != null) { //
            selectedNavButton = (ToggleButton) selectedToggle;
            this.previouslySelectedToggleButton = selectedNavButton;
        } else {
            navToggleGroup.selectToggle(this.previouslySelectedToggleButton);
            selectedToggle = navToggleGroup.getSelectedToggle();
            selectedNavButton = (ToggleButton) selectedToggle;
        }

        //update view state
        viewState.setMainShowing(selectedNavButton.getId());
    }
}
