/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.breadcrumbbar;

import dik.adp.app.gui.sharedcommunicationmodel.ViewState;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javax.inject.Inject;
import org.controlsfx.control.BreadCrumbBar;

/**
 *
 * @author gu35nxt
 */
public class BreadcrumbbarPresenter implements Initializable {

    @FXML
    private BreadCrumbBar breadCrumbBar;

    @Inject
    private ViewState viewState;

    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        TreeItem<String> dfdTI = new TreeItem<>("DFD");
        TreeItem<String> atTI = new TreeItem<>("AT");
        TreeItem<String> baTI = new TreeItem<>("BA");
        TreeItem<String> itTI = new TreeItem<>("IT");
        TreeItem<String> raTI = new TreeItem<>("RA");
//        TreeItem<String> sumTI = new TreeItem<>("adgfdg"); //Unicode Character 'N-ARY SUMMATION' (U+2211), Summenzeichen
        TreeItem<String> sumTI = new TreeItem<>("\u2211"); //Unicode Character 'N-ARY SUMMATION' (U+2211), Summenzeichen

        dfdTI.getChildren().add(atTI);
        atTI.getChildren().add(baTI);
        baTI.getChildren().add(itTI);
        itTI.getChildren().add(raTI);
        raTI.getChildren().add(sumTI);

        //startup on DFD
        breadCrumbBar.setSelectedCrumb(dfdTI);

        this.viewState.mainShowingProperty().addListener((obs, wasShowing, isMainShowing) -> {
            //define the id of the nav Buttons. Must be the same as the id of the Buttons in navigation
            final String dfdNavButtonID = "dfdNavButtonID";
            final String atNavButtonID = "atNavButtonID";
            final String baNavButtonID = "baNavButtonID";
            final String itNavButtonID = "itNavButtonID";
            final String raNavButtonID = "raNavButtonID";
            final String sumNavButtonID = "sumNavButtonID";

//            if (isMainShowing.endsWith(dfdNavButtonID)) {
//                breadCrumbBar.setSelectedCrumb(dfdTI);
//            } else if (isMainShowing.endsWith(atNavButtonID)) {
//                breadCrumbBar.setSelectedCrumb(atTI);
//            } else if (isMainShowing.endsWith(baNavButtonID)) {
//                breadCrumbBar.setSelectedCrumb(baTI);
//            } else if (isMainShowing.endsWith(itNavButtonID)) {
//                breadCrumbBar.setSelectedCrumb(itTI);
//            } else if (isMainShowing.endsWith(raNavButtonID)) {
//                breadCrumbBar.setSelectedCrumb(raTI);
//            } else if (isMainShowing.endsWith(sumNavButtonID)) {
//                breadCrumbBar.setSelectedCrumb(sumTI);
//            }
            if (isMainShowing.contentEquals(dfdNavButtonID)) {
                breadCrumbBar.setSelectedCrumb(dfdTI);
            } else if (isMainShowing.contentEquals(atNavButtonID)) {
                breadCrumbBar.setSelectedCrumb(atTI);
            } else if (isMainShowing.contentEquals(baNavButtonID)) {
                breadCrumbBar.setSelectedCrumb(baTI);
            } else if (isMainShowing.contentEquals(itNavButtonID)) {
                breadCrumbBar.setSelectedCrumb(itTI);
            } else if (isMainShowing.contentEquals(raNavButtonID)) {
                breadCrumbBar.setSelectedCrumb(raTI);
            } else if (isMainShowing.contentEquals(sumNavButtonID)) {
                breadCrumbBar.setSelectedCrumb(sumTI);
            }

        });

    }

}
