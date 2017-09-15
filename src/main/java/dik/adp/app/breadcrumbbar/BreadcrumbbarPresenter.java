/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.breadcrumbbar;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import org.controlsfx.control.BreadCrumbBar;

/**
 *
 * @author gu35nxt
 */
public class BreadcrumbbarPresenter implements Initializable {

    @FXML
    private BreadCrumbBar breadCrumbBar;
    
    private ResourceBundle resources = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        
        TreeItem<String> dfdTI = new TreeItem<>("DFD");
        TreeItem<String> atTI = new TreeItem<>("AT");
        TreeItem<String> baTI = new TreeItem<>("BA");
        TreeItem<String> raTI = new TreeItem<>("RA");
        TreeItem<String> kkTI = new TreeItem<>("KK");
        
        dfdTI.getChildren().add(atTI);
        atTI.getChildren().add(baTI);
        baTI.getChildren().add(raTI);
        raTI.getChildren().add(kkTI);
        
        breadCrumbBar.setSelectedCrumb(kkTI);

        
    }

    
}
