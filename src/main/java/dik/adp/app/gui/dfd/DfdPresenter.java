/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.dfd;

import dik.adp.app.orientdb.odb2DAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class DfdPresenter implements Initializable {

    @FXML
    private Button createNewDfdButton;
    
    @FXML
    private ComboBox dfdComboBox;
    
    @Inject
    private odb2DAO odb;
    
    
    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        
        dfdComboBox.getItems().addAll("safgds","sfgaaf");
        dfdComboBox.getItems().add("dsfgsfdg");
        
        odb.listOfDfds();
        
    }

    @FXML
    void createNewDfd(ActionEvent event) {

    }

}
