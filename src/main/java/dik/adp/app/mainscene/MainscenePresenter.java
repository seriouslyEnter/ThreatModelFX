package dik.adp.app.mainscene;

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
import dik.adp.app.at.AtPresenter;
import dik.adp.app.at.AtView;
import dik.adp.app.breadcrumbbar.BreadcrumbbarView;
import dik.adp.app.navigation.NavigationView;
import dik.adp.app.sharedcommunicationmodel.ViewState;
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

    @Inject
    private ViewState viewState;

    //Hiermit wird der Anchor festgelegt wo ich später breadcrumb injecte
    @FXML
    AnchorPane breadcrumbAnchorPane;

    @FXML
    AnchorPane navigationAnchorPane;

    //--------------------------------------------------------
    @FXML
    private AnchorPane mainAnchorPane; //Hier wird es reingeladen

    private AtPresenter atPresenter;
    private AtView atView;
    //--------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //add BreadCrumBar
        BreadcrumbbarView breadcrumbbarView = new BreadcrumbbarView();
        breadcrumbbarView.getViewAsync(breadcrumbAnchorPane.getChildren()::add);

//        //add DFD
//        DfdView dfdView = new DfdView();
//        Parent view2 = dfdView.getView();
//        this.mainAnchorPane.getChildren().add(view2);

//        this.atView = new AtView();
//        this.atPresenter = (AtPresenter) this.atView.getPresenter();

//        this.showAt();
        //add Navigation
        NavigationView navigationView = new NavigationView();
        Parent view = navigationView.getView();
        navigationAnchorPane.getChildren().add(view);
        
        
        //add AT
        this.atView = new AtView();
        this.atPresenter = (AtPresenter) this.atView.getPresenter();

        this.viewState.atShowingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (isNowShowing) {
                this.mainAnchorPane.getChildren().add(this.atView.getView());
            } else {
                this.mainAnchorPane.getChildren().remove(this.atView.getView());
            }
        });

    }

    void showAt() {
        this.mainAnchorPane.getChildren().add(this.atView.getView());
    }

    public void buttonAt() {
        this.mainAnchorPane.getChildren().clear();
//        this.showAt();
    }

}
