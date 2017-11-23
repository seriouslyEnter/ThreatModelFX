/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.sum;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author gu35nxt
 */
public class SumPresenter implements Initializable {

    @FXML
    private HBox sumHBox;

    @FXML
    private TreeTableView treeTableView;
    @FXML
    private TreeTableColumn columnElements;
    @FXML
    private TreeTableColumn columnValues;
    
    @FXML
    private BarChart barChart;

    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        //Notwendig weil das für Injected fxml das nicht nicht SceneBuilder gesetzt werden kann
        AnchorPane.setRightAnchor(sumHBox, 0.0);
        AnchorPane.setLeftAnchor(sumHBox, 0.0);
        AnchorPane.setTopAnchor(sumHBox, 0.0);
        AnchorPane.setBottomAnchor(sumHBox, 0.0);
    }

    
    
    
    
    
    
    
    
}
