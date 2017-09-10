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
import dik.adp.app.breadcrumbbar.BreadcrumbbarView;
import dik.adp.app.dfd.DfdView;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javax.inject.Inject;

/**
 *
 * @author adam-bien.com
 */
public class MainscenePresenter implements Initializable {

    @FXML
    Label message;

    @FXML
    Pane lightsBox;

    //Hiermit wird der Anchor festgelegt wo ich später breadcrumb injecte
    @FXML
    AnchorPane breadcrumbAnchor;

    @FXML
    AnchorPane dfdAnchor;

    @Inject
    Tower tower;

    @Inject
    private String prefix;

    @Inject
    private String happyEnding;

    @Inject
    private LocalDate date;

    private String theVeryEnd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //fetched from dashboard.properties
        this.theVeryEnd = rb.getString("theEnd");

        //Funktioniert
        BreadcrumbbarView breadcrumbbarView = new BreadcrumbbarView();
        breadcrumbbarView.getViewAsync(breadcrumbAnchor.getChildren()::add);
        
        DfdView dfdView = new DfdView();
        dfdView.getViewAsync(dfdAnchor.getChildren()::add);

        // Funktioniert auch
//        BreadcrumbbarView breadcrumbbarView = new BreadcrumbbarView();
//        Parent view = breadcrumbbarView.getView();
//        breadcrumbAnchor.getChildren().add(view);
    }

//      How to add new Nodes
//    public void createLights() {
//        for (int i = 0; i < 255; i++) {
//            final int red = i;
//            LightView view = new LightView((f) -> red);
//            view.getViewAsync(lightsBox.getChildren()::add);
//        }
//    }
    public void launch() {
        message.setText("Date: " + date + " -> " + prefix + tower.readyToTakeoff() + happyEnding + theVeryEnd
        );
    }

}
