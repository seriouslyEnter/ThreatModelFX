/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.navigation;

import dik.adp.app.sharedcommunicationmodel.ViewState;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private ToggleButton kkNavButton;

    @FXML
    private ToggleButton sumNavButton;

    @FXML
    private ToggleGroup navToggleGroup;

    @Inject
    private ViewState viewState;

//    @Inject
//    MainscenePresenter mainscene;
    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
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
        //get selcted Button from ToggleGroup
        ToggleButton selectedNavButton
                = (ToggleButton) navToggleGroup.getSelectedToggle();

        //get id(not fxid) of the selected Button
        String buttonID = selectedNavButton.getId();

        //set view state
        viewState.setMainShowing(buttonID);
    }
}
