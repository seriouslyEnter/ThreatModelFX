/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.navigation;

import dik.adp.app.mainscene.MainscenePresenter;
import dik.adp.app.sharedcommunicationmodel.ViewState;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class NavigationPresenter implements Initializable {

    @FXML
    Button atNavButton;

    @FXML
    Button dfdNavButton;

    @Inject
    private ViewState viewState;

//    @Inject
//    MainscenePresenter mainscene;
    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    @FXML
    void showDfdScene(ActionEvent event) {
        viewState.setAtShowing(true);
    }

    @FXML
    void hideDfdScene(ActionEvent event) {
        viewState.setAtShowing(false);
    }

}
