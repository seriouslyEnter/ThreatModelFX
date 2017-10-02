package dik.adp.app.gui.dfd;

import dik.adp.app.orientdb.odb2DAO;
import dik.adp.app.orientdb.odb2Klassen.DfdDiagram;
import dik.adp.app.orientdb.odb2Klassen.FxDFlow;
import dik.adp.app.orientdb.odb2Klassen.FxDfdElement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javax.inject.Inject;

/**
 * @author gu35nxt
 */
public class DfdPresenter implements Initializable {

    //===============================DFD Diagram================================
    @FXML
    private ComboBox dfdComboBox;
    @FXML
    private TextField newDfdTextField;
    private ObservableList<DfdDiagram> obsListOfDfds = FXCollections.<DfdDiagram>observableArrayList();
    //==========================================================================

    //==========================================================================
    //-----------------DFD Elements Table------------------------------------------------
    @FXML // fx:id="tableVDfdElements"
    private TableView<FxDfdElement> tableVDfdElements; // Value injected by FXMLLoader
    @FXML // fx:id="tableCDfdId"
    private TableColumn<FxDfdElement, String> tableColDfdKey; // Value injected by FXMLLoader
    @FXML // fx:id="tableCDfdType"
    private TableColumn<FxDfdElement, String> tableColDfdType; // Value injected by FXMLLoader
    @FXML // fx:id="tableCDfdName"
    private TableColumn<FxDfdElement, String> tableColDfdName; // Value injected by FXMLLoader
    private TableViewSelectionModel<FxDfdElement> tsmDfdElement;
    private FxDfdElement selectedDfdElement;
    //--------------------------------------------------------------------------

    //----------------------------DFD Elements Controls-------------------------
    @FXML
    private TextField keyDfdElementTextField;
    @FXML
    private TextField typeDfdElementTextField;
    @FXML
    private TextField nameDfdElementTextField;
    //--------------------------------------------------------------------------
    //==========================================================================

    //=======================Data Flow==========================================
    @FXML
    private TableView<FxDFlow> tableVDataFlows;
    @FXML
    private TableColumn<FxDFlow, String> tColDfKey;
    @FXML
    private TableColumn<FxDFlow, String> tColDfName;
    @FXML
    private TableColumn<FxDFlow, String> tColFromE;
    @FXML
    private TableColumn<FxDFlow, String> tColToE;

    private TableViewSelectionModel<FxDFlow> tsmDFlow;
    private FxDFlow selectedDFlow;

    @FXML
    private TextField keyDFlowTextField;
    @FXML
    private TextField nameDFlowTextField;
    @FXML
    private TextField fromDFlowTextField;
    @FXML
    private TextField toDFlowTextField;
    //==========================================================================

    @Inject
    private odb2DAO odb;

    private ResourceBundle resources = null;

    //Füllen der Controls beim ersten start
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        updateComboBox();
        setupDfdElementsTable();
        setupDfdDiagramComboBox();

        setupDFlowTable();
        updateDFlowTable();
    }

    //==========================DFD Diagram=====================================
    //Setup der Diagram Combobox nur einmal beim Start aufgerufen
    private void setupDfdDiagramComboBox() {
        //Für die Diagram Combobox einen Listener setzen   
        this.dfdComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            updateDfdElementsTable();
            updateDFlowTable();
        });
    }

    //ruft alle Dfd Diagramme von der Datenbank ab und setzt sie in Combobox
    private void updateComboBox() {
        dfdComboBox.getItems().clear();
        odb.queryDfdDiagram(obsListOfDfds);
        dfdComboBox.setItems(obsListOfDfds);
    }

    //Event um neues Dfd Diagramm zu erstellen bei drücken des Buttons Erstellen
    @FXML
    void createNewDfdDiagram(ActionEvent event) {
        String newDfd;
        if (!newDfdTextField.getText().isEmpty()) {
            newDfd = newDfdTextField.getText();
            odb.addDfdToDb(newDfd);
            newDfdTextField.clear();
            updateComboBox();
        }
    }
    //==========================================================================

    //==========================Elements Table==================================
    //Einmal beim start die Tabelle für Dfd Elemente konfigurieren
    private void setupDfdElementsTable() {
        //?
        tableColDfdKey.setCellValueFactory(new PropertyValueFactory<>("key"));
        tableColDfdType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColDfdName.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Table Edit
        //tableView must be set to editable true. TableColumn and Cell are editable by default
//        tableVDfdElements.setEditable(true);
        tableColDfdKey.setCellFactory(TextFieldTableCell.<FxDfdElement>forTableColumn());

        //-----------setup for selection abilities------------------------------
        // Turn on single/multiple-selection mode for the TableView. Default ist single.
        this.tsmDfdElement = tableVDfdElements.getSelectionModel();
        this.tsmDfdElement.setSelectionMode(SelectionMode.SINGLE);
        // Disable/Enable cell-level selection. Default ist Disable anyway
        this.tsmDfdElement.setCellSelectionEnabled(false);
        //Liste mit Changelistener um auf selection change reagieren zu können
        ObservableList<Integer> list = this.tsmDfdElement.getSelectedIndices();
        // Add a ListChangeListener
        list.addListener((ListChangeListener.Change<? extends Integer> change) -> {
            this.selectedDfdElement = this.tsmDfdElement.getSelectedItem();
            updateDfdElementsTextFields();
            System.out.println("Row selection has changed");
        });
        //----------------------------------------------------------------------
    }

    //Immer dann aufrufen um änderungen an den Dfd Element in der Tabelle sichtbar zu machen
    private void updateDfdElementsTable() {
        //DFD Diagram aus ComboBox aber was wenn noch nicht ausgwählt
        DfdDiagram dfdcmbbx = new DfdDiagram("");
        if (dfdComboBox.getSelectionModel().getSelectedItem() != null) {
            dfdcmbbx = (DfdDiagram) dfdComboBox.getSelectionModel().getSelectedItem();
        }

        ObservableList<FxDfdElement> listDfdElemente = FXCollections.<FxDfdElement>observableArrayList();
        odb.queryDfdElements(listDfdElemente, dfdcmbbx);

        tableVDfdElements.getItems().clear();
        tableVDfdElements.getItems().addAll(listDfdElemente);

        System.out.println(listDfdElemente);
    }

    //Füllt die Textfelder mit Daten des ausgewählten Dfd Elements
    private void updateDfdElementsTextFields() {
        if (this.selectedDfdElement != null) {
            //updates Textfields with selected Dfd Element
            keyDfdElementTextField.setText(this.selectedDfdElement.getKey());
            typeDfdElementTextField.setText(this.selectedDfdElement.getType());
            nameDfdElementTextField.setText(this.selectedDfdElement.getName());
        } else {
            keyDfdElementTextField.clear();
            typeDfdElementTextField.clear();
            nameDfdElementTextField.clear();
        }
    }

    //reseted/leert die Textfelder wieder
    private void clearDfdElementsTextFields() {
        keyDfdElementTextField.clear();
        typeDfdElementTextField.clear();
        nameDfdElementTextField.clear();
    }

    //Bei drücken des Button add wird ein neues Dfd Element hinzugefügt
    @FXML
    void addDfdElement(ActionEvent event) {
        DfdDiagram selectedDfdDiagram = (DfdDiagram) dfdComboBox.getSelectionModel().getSelectedItem();
        FxDfdElement newDfdElement = new FxDfdElement(
                keyDfdElementTextField.getText(),
                typeDfdElementTextField.getText(),
                nameDfdElementTextField.getText(),
                selectedDfdDiagram.getName()
        );
        System.out.println(newDfdElement);
        System.out.println("textfeld " + nameDfdElementTextField.getText());
        odb.addNewDfdElementToDb(newDfdElement);
        clearDfdElementsTextFields();
        updateDfdElementsTable();
    }

    //Bei drücken des Button delete wird das ausgewählte Dfd Element gelöscht
    @FXML
    void deleteDfdElement(ActionEvent event) {
        odb.deleteDfdElement(this.selectedDfdElement);
        this.tsmDfdElement.clearSelection();
        updateDfdElementsTable();
    }

    //Bei drücken des edit Buttons wird das ausgewählte Dfd Elment mit den Daten der Textfelder geupdated
    @FXML
    void editDfdElement(ActionEvent event) {
        DfdDiagram selectedDfdDiagram = (DfdDiagram) dfdComboBox.getSelectionModel().getSelectedItem();
        FxDfdElement editedDfdElement = new FxDfdElement(
                keyDfdElementTextField.getText(),
                typeDfdElementTextField.getText(),
                nameDfdElementTextField.getText(),
                selectedDfdDiagram.getName()
        );
        odb.updateDfdElement(this.selectedDfdElement, editedDfdElement);
        updateDfdElementsTable();
    }
    //==========================================================================

    //=========================Datenfluss=======================================
    private void setupDFlowTable() {
        //?
        tColDfKey.setCellValueFactory(new PropertyValueFactory<>("key"));
        tColDfName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tColFromE.setCellValueFactory(new PropertyValueFactory<>("from"));
        tColToE.setCellValueFactory(new PropertyValueFactory<>("to"));

        //Table Edit
        //tableView must be set to editable true. TableColumn and Cell are editable by default
//        tableVDfdElements.setEditable(true);
//        tableColDfdKey.setCellFactory(TextFieldTableCell.<FxDfdElement>forTableColumn());
        //-----------setup for selection abilities------------------------------
        // Turn on single/multiple-selection mode for the TableView. Default ist single.
        this.tsmDFlow = tableVDataFlows.getSelectionModel();
        this.tsmDFlow.setSelectionMode(SelectionMode.SINGLE);
        // Disable/Enable cell-level selection. Default ist Disable anyway
        this.tsmDFlow.setCellSelectionEnabled(false);
        //Liste mit Changelistener um auf selection change reagieren zu können
        ObservableList<Integer> list = this.tsmDFlow.getSelectedIndices();
        // Add a ListChangeListener
        list.addListener((ListChangeListener.Change<? extends Integer> change) -> {
            this.selectedDFlow = this.tsmDFlow.getSelectedItem();
            updateDFlowTextFields();
            System.out.println("Row selection has changed");
        });
        //----------------------------------------------------------------------  
    }

    //Immer dann aufrufen um änderungen an den Dfd Element in der Tabelle sichtbar zu machen
    private void updateDFlowTable() {
        //DFD Diagram aus ComboBox aber was wenn noch nicht ausgwählt
        DfdDiagram dfdcmbbx = new DfdDiagram("");
        if (dfdComboBox.getSelectionModel().getSelectedItem() != null) {
            dfdcmbbx = (DfdDiagram) dfdComboBox.getSelectionModel().getSelectedItem();
        }

        ObservableList<FxDFlow> listDFlow = FXCollections.<FxDFlow>observableArrayList();
        odb.queryFxDFlows(listDFlow, dfdcmbbx);

        tableVDataFlows.getItems().clear();
        tableVDataFlows.getItems().addAll(listDFlow);

        System.out.println(listDFlow);
    }

    private void updateDFlowTextFields() {
        if (this.selectedDFlow != null) {
            //updates Textfields with selected Dfd Element
            keyDFlowTextField.setText(this.selectedDFlow.getKey());
            nameDFlowTextField.setText(this.selectedDFlow.getName());
            fromDFlowTextField.setText(this.selectedDFlow.getFrom());
            toDFlowTextField.setText(this.selectedDFlow.getTo());
        } else {
            keyDFlowTextField.clear();
            nameDFlowTextField.clear();
            fromDFlowTextField.clear();
            toDFlowTextField.clear();
        }
    }

    @FXML //on Button add for DF
    void addDFlow(ActionEvent event) {
        DfdDiagram selectedDfdDiagram = (DfdDiagram) dfdComboBox.getSelectionModel().getSelectedItem();
        FxDFlow newDFlow = new FxDFlow(
                keyDFlowTextField.getText(),
                nameDFlowTextField.getText(),
                selectedDfdDiagram.getName(),
                fromDFlowTextField.getText(),
                toDFlowTextField.getText()
        );
        System.out.println(newDFlow);
        odb.addDFlowToDB(newDFlow);
        clearDFlowTextFields();
        updateDFlowTable();
    }

    private void clearDFlowTextFields() {
        keyDFlowTextField.clear();
        nameDFlowTextField.clear();
        fromDFlowTextField.clear();
        toDFlowTextField.clear();
    }

    @FXML
    void deleteDFlow(ActionEvent event) {

    }

    @FXML
    void editDFlow(ActionEvent event) {

    }
    //==========================================================================

}
