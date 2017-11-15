/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.ra;

import dik.adp.app.gui.sharedcommunicationmodel.SelectedState;
import dik.adp.app.orientdb.Odb2Ra;
import dik.adp.app.orientdb.odb2Klassen.Dread;
import dik.adp.app.orientdb.odb2Klassen.FxDfdElement4TreeView;
import dik.adp.app.orientdb.odb2Klassen.FxKeyPairForGrid;
import dik.adp.app.orientdb.odb2Klassen.FxRa;
import dik.adp.app.orientdb.odb2Klassen.Rating;
import dik.adp.app.orientdb.odb2Klassen.Stride;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.inject.Inject;
import org.controlsfx.control.PopOver;

/**
 *
 * @author gu35nxt
 */
public class RaPresenter implements Initializable {

    @FXML
    private TreeView raTreeView;
    @FXML
    private HBox raViewHBox;
    @FXML
    private GridPane dreadGridPane;

    @Inject
    private Odb2Ra odb;
    @Inject
    private SelectedState selectedState;

    private Map<String, FxDfdElement4TreeView> treeViewElements;
//    private Button dreadButton;
    private FxRa fxRa;
//    private FxDreadGridPane fxDreadGridPane;
    private Map<FxKeyPairForGrid, Button> fxGridMap = new HashMap<>();

    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        //Notwendig weil das für Injected fxml das nicht nicht SceneBuilder gesetzt werden kann
        AnchorPane.setRightAnchor(raViewHBox, 0.0);
        AnchorPane.setLeftAnchor(raViewHBox, 0.0);
        AnchorPane.setTopAnchor(raViewHBox, 0.0);
        AnchorPane.setBottomAnchor(raViewHBox, 0.0);

        setupTreeView();
        setupDreadGrid();
    }

    /**
     * adding TreeItems to TreeView
     */
    private void setupTreeView() {
        raTreeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        raTreeView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> this.updateSelectedDfdElement()
        );

        TreeItem root = new TreeItem("DfdElemente");
        raTreeView.setRoot(root);

        TreeItem pTI = new TreeItem("Process");
        root.getChildren().add(pTI);
        TreeItem mTI = new TreeItem("Memory");
        root.getChildren().add(mTI);
        TreeItem dTI = new TreeItem("DFlow");
        root.getChildren().add(dTI);
        TreeItem kTI = new TreeItem("Kommunikationskanal");
        root.getChildren().add(kTI);

        treeViewElements = odb.queryDfdElements(selectedState.isSelectedDiagram());

        treeViewElements.forEach((k, v) -> {
            switch (v.getFxDfdElement().getType()) {
                case "Process":
                    pTI.getChildren().add(v.getTreeItem());
                    break;
                case "Memory":
                    mTI.getChildren().add(v.getTreeItem());
                    break;
                case "DFlow":
                    dTI.getChildren().add(v.getTreeItem());
                    break;
                case "Kommunikationskanal":
                    kTI.getChildren().add(v.getTreeItem());
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * setup the select mechanics in TreeView
     */
    private void updateSelectedDfdElement() {
        TreeItem selectedItem = (TreeItem) raTreeView.getSelectionModel().getSelectedItem();
        FxDfdElement4TreeView element;
        System.out.println("Selecten TreeView item: " + selectedItem);
        String string = selectedItem.toString();

        element = treeViewElements.get(string);

        //prüfe ob selected TreeItem eins Kategorie oder Element ist
        if (element != null) {
            System.out.println(element.getFxDfdElement().getRid());

            this.fxRa = odb.queryBa(element.getFxDfdElement(), selectedState.getSelectedAt());
            updateDREADGrid(this.fxRa);
//            updateDREADGrid(odb.queryBa(element.getFxDfdElement(), selectedState.getSelectedAt()));
        } else {
            System.out.println("kein DFD Element");
            dreadGridPane.getChildren().clear();
            setupDreadGrid();
        }
//        treeViewElements.forEach((k, v) -> {
//            if (string.contentEquals(k)) {
//                System.out.println(v.getFxDfdElement().getRid());
////                odb.queryRa();
//            }
//        });
    }

    /**
     * update the Grid according to chosen DfdElement and disablenging/enabeling
     * the activatet Threats
     */
    private void updateDREADGrid(FxRa fxRa) {
        Label labelStride;
        int column;
        int row;

        System.out.println("updateDREAD()");

        dreadGridPane.getChildren().clear();
        setupGridFirstRow();

        column = 0;
        row = 0;
        for (Stride stride : Stride.values()) {
            row++;
//            labelStride = new Label(stride.getBezeichnung());
labelStride = new Label(stride.toString());

            //check ob diese Stride in Query, wenn nein, dann disable Label
            if (fxRa.getThreats().containsKey(stride) == true) {
                labelStride.setDisable(false);
                addDreadButtons(row, stride);
            } else {
                labelStride.setDisable(true);
            }
            dreadGridPane.add(labelStride, column, row);
        }
//        dreadGridPane.setGridLinesVisible(true);
    }

    /**
     * adding the Rating Buttons for on row
     */
    private void addDreadButtons(int row, Stride stride) {
        Button dreadButton;
        int column = 0;
        for (Dread dread : Dread.values()) {
            column++;
            dreadButton = new Button();
            dreadButton = setupDreadButton(dreadButton);
//            dreadButton.setOnAction(this::popOver);
//            dreadButton.hoverProperty().addListener((observable, oldValue, newValue) -> {
            dreadButton.addEventHandler(ActionEvent.ACTION, event -> {
                popOver(event);
            });
            dreadGridPane.add(dreadButton, column, row);
            
            //save position in MapMap
            FxKeyPairForGrid key = new FxKeyPairForGrid(stride, dread);
            fxGridMap.put(key, dreadButton);
        }
    }

    /**
     * setup the Popover
     */
    private void popOver(ActionEvent popEventLocation) {
        System.out.println("Popover");
        Button bt = new Button("test");
        PopOver po = new PopOver();
        po.setContentNode(setupPopOver(popEventLocation));
        po.show((Node) popEventLocation.getSource());
    }

    /**
     * setup the Popover content
     */
    private VBox setupPopOver(ActionEvent popEventLocation) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(0);
        Insets insetsVBox = new Insets(5);
        vBox.setPadding(insetsVBox);

        Button hB = new Button(Rating.HIGH.getRating());
        Button mB = new Button(Rating.MEDIUM.getRating());
        Button lB = new Button(Rating.LOW.getRating());

        Insets insetsButton = new Insets(1);

        hB.setFont(Font.font("System", FontWeight.BOLD, 15));
        hB.setTextFill(Color.RED);
        hB.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        hB.setPadding(insetsButton);
        hB.addEventHandler(ActionEvent.ACTION, event -> {
            System.out.println("high Button");
            updateRatingOnButton(popEventLocation, Rating.HIGH);
            updateRatingInDB(popEventLocation, Rating.HIGH);
        });

        mB.setFont(Font.font("System", FontWeight.BOLD, 15));
        mB.setTextFill(Color.ORANGE);
        mB.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        mB.setPadding(insetsButton);
        mB.addEventHandler(ActionEvent.ACTION, event -> {
            System.out.println("medium Button");
            updateRatingOnButton(popEventLocation, Rating.MEDIUM);
            updateRatingInDB(popEventLocation, Rating.MEDIUM);
        });

        lB.setFont(Font.font("System", FontWeight.BOLD, 15));
        lB.setTextFill(Color.GREENYELLOW);
        lB.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        lB.setPadding(insetsButton);
        lB.addEventHandler(ActionEvent.ACTION, event -> {
            System.out.println("low Button");
            updateRatingOnButton(popEventLocation, Rating.LOW);
            updateRatingInDB(popEventLocation, Rating.LOW);
        });

        vBox.getChildren().addAll(hB, mB, lB);
        return vBox;
    }

    private void updateRatingInDB(ActionEvent popEventLocation, Rating rating) {
        System.out.println("updateRatingInDB");
        Button buttonSource = (Button) popEventLocation.getSource();
        
        for (Stride stride : Stride.values()) {
            for (Dread dread : Dread.values()) {
                FxKeyPairForGrid key = new FxKeyPairForGrid(stride, dread);
                Button buttonMap = fxGridMap.get(key);
                if (buttonSource.equals(buttonMap)){
                    System.out.println("FOUND BUTTON");
                    System.out.println(buttonSource.toString());
                }
            }
        }
        
        
        

//        //find FxBa from Button maybe by iterating over GridPane
//        //Suche STRIDE by Iterating over Grid and compare to Button and Stride
//        // cant iterate a grid just a list 
//        for (FxBa stride : fxRa.getThreats().values()) {
////            dreadGridPane.getChildren().
//        }
    }

    /**
     * change Button text according to changed Rating
     */
    private void updateRatingOnButton(ActionEvent popEventLocation, Rating rating) {
        Button ratingButton = (Button) popEventLocation.getSource();

        switch (rating) {
            case HIGH:
                ratingButton.setText("H");
                ratingButton.setFont(Font.font("System", FontWeight.BOLD, 15));
                ratingButton.setTextFill(Color.RED);
                break;
            case MEDIUM:
                ratingButton.setText("M");
                ratingButton.setFont(Font.font("System", FontWeight.BOLD, 15));
                ratingButton.setTextFill(Color.ORANGE);
                break;
            case LOW:
                ratingButton.setText("L");
                ratingButton.setFont(Font.font("System", FontWeight.BOLD, 15));
                ratingButton.setTextFill(Color.GREENYELLOW);
                break;
            default:
                ratingButton.setText("O");
                ratingButton.setFont(Font.font("System", FontWeight.BOLD, 15));
                ratingButton.setTextFill(Color.BLUE);
                break;
        }
    }

    /**
     * Button mit "?" erstellen
     */
    private Button setupDreadButton(Button dreadButton) {
        dreadButton.setText("?");
        dreadButton.setFont(Font.font("System", FontWeight.BOLD, 15));
        dreadButton.setTextFill(Color.web("#0015ff"));
        dreadButton.setAlignment(Pos.CENTER);
//            dreadButton.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        dreadButton.setPrefSize(25, 25);
        dreadButton.setPadding(Insets.EMPTY);
        CornerRadii cr = new CornerRadii(5);
        dreadButton.setBackground(new Background(new BackgroundFill(Color.WHITE, cr, Insets.EMPTY)));
        return dreadButton;
    }

    /**
     * add column of STRIDE Labels
     */
    private void setupDreadGrid() {
        Label labelStride;
        int column;
        int row;

        setupGridFirstRow();

        column = 0;
        row = 0;
        for (Stride stride : Stride.values()) {
            row++;
//            labelStride = new Label(stride.getBezeichnung());
labelStride = new Label(stride.toString());
            dreadGridPane.add(labelStride, column, row);
        }
//        dreadGridPane.setGridLinesVisible(true);
    }

    /**
     * add the DREAD Labels to Grid
     */
    private void setupGridFirstRow() {
        int column = 0;
        int row = 0;
        Label firstLetter;
        for (Dread dread : Dread.values()) {
            column++;
            //Labes mit dem ersten Buchstaben aus DREAD Bezeichung
            firstLetter = new Label(dread.name().substring(0, 1));
            firstLetter.setFont(Font.font("System", FontWeight.BOLD, 36));
            dreadGridPane.add(firstLetter, column, row);
        }
    }
}
