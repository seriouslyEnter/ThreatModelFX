package dik.adp.app.gui.mainscene;

/*
 * #%L
 * igniter
 * %%
 * Copyright (C) 2013 - 2016 Adam Bien
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import dik.adp.app.gui.at.AtPresenter;
import dik.adp.app.gui.at.AtView;
import dik.adp.app.gui.ba.BaPresenter;
import dik.adp.app.gui.ba.BaView;
import dik.adp.app.gui.breadcrumbbar.BreadcrumbbarPresenter;
import dik.adp.app.gui.breadcrumbbar.BreadcrumbbarView;
import dik.adp.app.gui.dfd.DfdPresenter;
import dik.adp.app.gui.dfd.DfdView;
import dik.adp.app.gui.it.ItPresenter;
import dik.adp.app.gui.it.ItView;
import dik.adp.app.gui.menue.MenuePresenter;
import dik.adp.app.gui.menue.MenueView;
import dik.adp.app.gui.navigation.NavigationView;
import dik.adp.app.gui.sharedcommunicationmodel.ViewState;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javax.inject.Inject;

/**
 *
 * @author adam-bien.com
 */
public class MainscenePresenter implements Initializable {

    //describes view state for navigation. Used with addListener
    @Inject
    private ViewState viewState;

    //Views get loaded in these Anchor Panes
    @FXML
    private AnchorPane menueAnchorPane;
    @FXML
    private AnchorPane breadcrumbAnchorPane;
    @FXML
    private AnchorPane mainAnchorPane; //This is the main View
    @FXML
    private AnchorPane navigationAnchorPane;

    private MenuePresenter menuePresenter;
    private MenueView menueView;
    private BreadcrumbbarPresenter bcrumbPresenter;
    private BreadcrumbbarView bcrumbView;
    private DfdPresenter dfdPresenter;
    private DfdView dfdView;
    private AtPresenter atPresenter;
    private AtView atView;
    private BaPresenter baPresenter;
    private BaView baView;
    private ItPresenter itPresenter;
    private ItView itView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //-----------add always-------------------------------------------------
        //add Menue
        MenueView menueView = new MenueView();
        Parent menueViewParent = menueView.getView();
        menueAnchorPane.getChildren().add(menueViewParent);
        //add BreadCrumBar
        BreadcrumbbarView breadcrumbbarView = new BreadcrumbbarView();
        breadcrumbbarView.getViewAsync(breadcrumbAnchorPane.getChildren()::add);
        //add Navigation
        NavigationView navigationView = new NavigationView();
        Parent navViewParent = navigationView.getView();
        navigationAnchorPane.getChildren().add(navViewParent);
        //----------------------------------------------------------------------

        //-----------------------main Anchor control view state-----------------
        //controls what is shown in the mainAnchorPane
        //show dfd on startup
        this.dfdView = new DfdView();
        this.dfdPresenter = (DfdPresenter) this.dfdView.getPresenter();
        this.mainAnchorPane.getChildren().add(this.dfdView.getView());

        this.viewState.mainShowingProperty().addListener((obs, wasShowing, isMainShowing) -> {
            //first turn all off
            this.mainAnchorPane.getChildren().clear();

            //define the id of the nav Buttons. Must be the same as the id of the Buttons in navigation
            final String dfdNavButtonID = "dfdNavButtonID";
            final String atNavButtonID = "atNavButtonID";
            final String baNavButtonID = "baNavButtonID";
            final String itNavButtonID = "itNavButtonID";
            final String raNavButtonID = "raNavButtonID";
            final String sumNavButtonID = "sumNavButtonID";

            if (isMainShowing.equals(dfdNavButtonID)) {
//                this.dfdView = new DfdView();
                this.dfdPresenter = (DfdPresenter) this.dfdView.getPresenter();
                this.mainAnchorPane.getChildren().add(this.dfdView.getView());
            }
            if (isMainShowing.equals(atNavButtonID)) {
                this.atView = new AtView();
                this.atPresenter = (AtPresenter) this.atView.getPresenter();
                this.mainAnchorPane.getChildren().add(this.atView.getView());
            }
            if (isMainShowing.equals(baNavButtonID)) {
                this.baView = new BaView();
                this.baPresenter = (BaPresenter) this.baView.getPresenter();
                this.mainAnchorPane.getChildren().add(this.baView.getView());
//                this.mainAnchorPane.getChildren().add(this.baView.getView());
            }
            if (isMainShowing.equals(itNavButtonID)) {
                this.itView = new ItView();
                this.itPresenter = (ItPresenter) this.itView.getPresenter();
                this.mainAnchorPane.getChildren().add(this.itView.getView());
            }
//            if (isMainShowing.equals(raNavButtonID)) {
//                this.mainAnchorPane.getChildren().add(this.raView.getView());
//            }
//            if (isMainShowing.equals(sumNavButtonID)) {
//                this.mainAnchorPane.getChildren().add(this.sumView.getView());
//            }
        });
        //----------------------------------------------------------------------
    }
}
