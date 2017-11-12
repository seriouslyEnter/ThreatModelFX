package dik.adp.app.gui.ba;

import dik.adp.app.gui.sharedcommunicationmodel.SelectedState;
import dik.adp.app.orientdb.Odb2Ba;
import dik.adp.app.orientdb.odb2Klassen.FxStride;
import dik.adp.app.orientdb.odb2Klassen.Stride;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.inject.Inject;

public class BaPresenter implements Initializable {

    @FXML
    HBox baHBox;
    @FXML
    ToggleGroup baToggleGroup;
    @FXML
    GridPane baGridPane;

    @Inject
    Odb2Ba odb;
    @Inject
    SelectedState selectedState;

    private String[] stride = {"S", "T", "R", "I", "D", "E", "EMPTY"};
//    private ObservableList<CheckBox> cbList = FXCollections.<CheckBox>observableArrayList();
    private List<FxStride> elements = new ArrayList<>();

    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Notwendig weil das für Injected fxml das nicht nicht SceneBuilder gesetzt werden kann
        AnchorPane.setLeftAnchor(baHBox, 0.0);
        AnchorPane.setRightAnchor(baHBox, 0.0);
        AnchorPane.setTopAnchor(baHBox, 0.0);
        AnchorPane.setBottomAnchor(baHBox, 0.0);

        this.resources = resources;
    }

    private void setupToggleButtons() {
        // setup alles in fxml
    }

    @FXML
    void pressToggleButton(ActionEvent event) {
        baGridPane.getChildren().clear();
        ToggleButton selectedTB = (ToggleButton) baToggleGroup.getSelectedToggle();
        String dfdElementType = null;
        switch (selectedTB.getId()) {
            case "processToggleButton":
                dfdElementType = "Process";
                break;
            case "speicherToggleButton":
                dfdElementType = "Memory";
                break;
            case "datenToggleButton":
                dfdElementType = "DFlow";
                break;
            case "komkanToggleButton":
                dfdElementType = "Kommunikationskanal";
                break;
        }
        //adding STRIDE Label
        for (int i = 0; i < stride.length; i++) {
            Label stringLabel = new Label(stride[i]);
            stringLabel.setFont(Font.font("System", FontWeight.BOLD, 36));
            baGridPane.add(stringLabel, i + 1, 0);
        }
        setupCheckBoxes(selectedTB, dfdElementType);
    }

    private void setupCheckBoxes(ToggleButton selectedTB, String dfdElementType) {
        //adding Query
        if (selectedTB != null) {
            System.out.println("ToggleButton: " + selectedTB.getId());
            elements = odb.queryDfdElements(selectedState.getSelectedAt(),
                    selectedState.isSelectedDiagram(),
                    dfdElementType
            );
            //Rows
            for (int i = 0; i < elements.size(); i++) {
                Label lb = new Label(
                        elements.get(i).getDfdElement().getKey()
                        + ": "
                        + elements.get(i).getDfdElement().getName()
                );
                baGridPane.add(lb, 0, i + 1);
                //Columns
                int j = 0;
                for (Stride s : Stride.values()) {
                    j++;
                    elements.get(i).getCbs().get(s).setOnAction(this::pressCheckbox);
                    baGridPane.add(elements.get(i).getCbs().get(s), j, i + 1);
                    baGridPane.setHalignment(elements.get(i).getCbs().get(s), HPos.CENTER);

                    //add Listener
//                    elements.get(i).getCbs().get(stride).selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//                        if (newValue = false) {
//                            System.out.println("Checkbox aus");
//                        } else if (newValue = true) {
//                            System.out.println("Checkbox ein");
//                        }
//                    });
                }
            }
        }

//        baGridPane.setGridLinesVisible(true);
    }

    void pressCheckbox(ActionEvent event) {
        CheckBox cb = (CheckBox) event.getSource();
        FxStride foundFxStrideAndSetBa = null;
        //für jedes element...
        for (FxStride element : elements) {
            //...die einzelnen STRIDE keys nach dem Object mit der CheckBox durchsuchen
            for (Stride key : Stride.values()) {
                if (cb.equals(element.getCbs().get(key))) {
                    if (cb.isSelected() == true) {
                        //die Bedrohung setzen
                        element.setBa(key);
                        odb.addThreatForElement(element);
                    } else {
                        //die Bedrohung entfernen
                        element.setBa(key);
                        odb.removeThreatForElement(element);
                    }
                    foundFxStrideAndSetBa = element;
                    System.out.println(foundFxStrideAndSetBa.toString());
                }
            }
        }
        System.out.println("fertig");
    }
}
