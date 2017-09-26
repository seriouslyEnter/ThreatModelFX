/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.dfd;

import com.tinkerpop.blueprints.Vertex;
import dik.adp.app.orientdb.odb2DAO;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
    @FXML
    private TextField newDfdTextField;
    
    @Inject
    private odb2DAO odb;

    private ArrayList<Vertex> listOfDfds;
    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        //populate ComboBox on start
        addVertexToComboBox(odb.getDfds());
    }

    private void addVertexToComboBox(ArrayList<Vertex> listOfDfds) {
        for (Vertex v : listOfDfds) {
            dfdComboBox.getItems().add(v.getProperty("name"));
        }
    }
    
    private void updateComboBox() {
        dfdComboBox.getItems().clear();
        this.listOfDfds = odb.getDfds();
        for (Vertex v : listOfDfds) {
            dfdComboBox.getItems().add(v.getProperty("name"));
        }
    }

    @FXML
    void createNewDfd(ActionEvent event) {
        String newDfd;
        if (!newDfdTextField.getText().isEmpty()) {
            newDfd = newDfdTextField.getText();
            odb.addDfdToDb(newDfd);
            newDfdTextField.clear();
            updateComboBox();
        }
    }
}
