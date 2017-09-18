/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.at;

import dik.adp.app.gui.mainscene.MainscenePresenter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class AtPresenter implements Initializable {

        @FXML
    private AnchorPane atAnchorPane;
    
    
    @Inject
    MainscenePresenter mainscene;

    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    @FXML
    void clearMain(ActionEvent event) {

        this.atAnchorPane.getChildren().clear();
    }

}
