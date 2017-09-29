package dik.adp.app.gui.dfd;

import dik.adp.app.orientdb.odb2DAO;
import dik.adp.app.orientdb.odb2Klassen.DfdDiagram;
import dik.adp.app.orientdb.odb2Klassen.FxDfdElement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

    //----------------New DFD---------------------------------------------------
    @FXML
    private ComboBox dfdComboBox;
    @FXML
    private TextField newDfdTextField;
    private ObservableList<DfdDiagram> obsListOfDfds = FXCollections.<DfdDiagram>observableArrayList();
    //--------------------------------------------------------------------------

    //-----------------DFD Table------------------------------------------------
    @FXML // fx:id="tableVDfdElements"
    private TableView<FxDfdElement> tableVDfdElements; // Value injected by FXMLLoader
    @FXML // fx:id="tableCDfdId"
    private TableColumn<FxDfdElement, String> tableColDfdKey; // Value injected by FXMLLoader
    @FXML // fx:id="tableCDfdType"
    private TableColumn<?, ?> tableColDfdType; // Value injected by FXMLLoader
    @FXML // fx:id="tableCDfdName"
    private TableColumn<?, ?> tableColDfdName; // Value injected by FXMLLoader
    //--------------------------------------------------------------------------

    //----------------------------DFD Elements Controls-------------------------
    @FXML
    private Button addDfdElementButton;
    @FXML
    private Button editDfdElementButton;
    @FXML
    private Button deleteDfdElementButton;
    @FXML
    private TextField keyDfdElementTextField;
    @FXML
    private TextField typeDfdElementTextField;
    @FXML
    private TextField nameDfdElementTextField;

    private FxDfdElement selectedDfdElement;
    private TableViewSelectionModel<FxDfdElement> tsm;
    //--------------------------------------------------------------------------

    @Inject
    private odb2DAO odb;

    private ResourceBundle resources = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        //populate ComboBox on start
//        addVertexToComboBox(odb.getDfds());

        updateComboBox();

        setupDfdElementsTable();
        updateDfdElementsTable();
    }

    private void updateDfdElementsTable() {
        ObservableList<FxDfdElement> listDfdElemente = FXCollections.<FxDfdElement>observableArrayList();
        odb.queryDfdElements(listDfdElemente);

        tableVDfdElements.getItems().clear();
        tableVDfdElements.getItems().addAll(listDfdElemente);

        System.out.println(listDfdElemente);
    }

    private void setupDfdElementsTable() {
        //
        tableColDfdKey.setCellValueFactory(new PropertyValueFactory<>("key"));
        tableColDfdType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColDfdName.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Table Edit
        //tableView must be set to editable true. TableColumn and Cell are editable by default
        tableVDfdElements.setEditable(true);
        tableColDfdKey.setCellFactory(TextFieldTableCell.<FxDfdElement>forTableColumn());

        //-----------setup for selection abilities------------------------------
        // Turn on single/multiple-selection mode for the TableView. Default ist single.
        this.tsm = tableVDfdElements.getSelectionModel();
        this.tsm.setSelectionMode(SelectionMode.SINGLE);
        // Disable/Enable cell-level selection. Default ist Disable anyway
        this.tsm.setCellSelectionEnabled(false);
        //Liste mit Changelistener um auf selection change reagieren zu können
        ObservableList<Integer> list = this.tsm.getSelectedIndices();
        // Add a ListChangeListener
        list.addListener((ListChangeListener.Change<? extends Integer> change) -> {
            this.selectedDfdElement = this.tsm.getSelectedItem();
            updateDfdElementsTextFields();
            System.out.println("Row selection has changed");
        });
        //----------------------------------------------------------------------
    }

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

    private void clearDfdElementsTextFields() {
        keyDfdElementTextField.clear();
        typeDfdElementTextField.clear();
        nameDfdElementTextField.clear();

    }

    private void updateComboBox() {
        dfdComboBox.getItems().clear();
        odb.queryDfdDiagram(obsListOfDfds);
        dfdComboBox.setItems(obsListOfDfds);

//        //aus DB in ArrayList in ComboBox
//        this.listOfDfds = odb.getDfds();    
//        for (Vertex v : listOfDfds) {
//            dfdComboBox.getItems().add(v.getProperty("name"));
//        }
    }

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

    @FXML
    void addDfdElement(ActionEvent event) {
        FxDfdElement newDfdElement = new FxDfdElement(
                keyDfdElementTextField.getText(),
                typeDfdElementTextField.getText(),
                nameDfdElementTextField.getText()
        );
        System.out.println(newDfdElement);
        odb.addNewDfdElementToDb(newDfdElement);
        clearDfdElementsTextFields();
        updateDfdElementsTable();
    }

    @FXML
    void deleteDfdElement(ActionEvent event) {
        odb.deleteDfdElement(this.selectedDfdElement);
        this.tsm.clearSelection();
        updateDfdElementsTable();
    }

    @FXML
    void editDfdElement(ActionEvent event) {

    }
}
