package dik.adp.app.gui.dfd;

import dik.adp.app.orientdb.odb2DAO;
import dik.adp.app.orientdb.odb2Klassen.DfdDiagram;
import dik.adp.app.orientdb.odb2Klassen.fxDfdElement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

    //-----------------Table----------------------------------------------------
    @FXML // fx:id="tableVDfdElements"
    private TableView<?> tableVDfdElements; // Value injected by FXMLLoader

    @FXML // fx:id="tableCDfdId"
    private TableColumn<?, ?> tableCDfdId; // Value injected by FXMLLoader

    @FXML // fx:id="tableCDfdType"
    private TableColumn<?, ?> tableCDfdType; // Value injected by FXMLLoader

    @FXML // fx:id="tableCDfdName"
    private TableColumn<?, ?> tableCDfdName; // Value injected by FXMLLoader
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

        //??????????????????????????????????????????????????????????????
        tableCDfdName.setCellValueFactory(new PropertyValueFactory("name"));
    }

    private void updateComboBox() {
        dfdComboBox.getItems().clear();
        odb.getDfds(obsListOfDfds);
        dfdComboBox.setItems(obsListOfDfds);

//        //aus DB in ArrayList in ComboBox
//        this.listOfDfds = odb.getDfds();    
//        for (Vertex v : listOfDfds) {
//            dfdComboBox.getItems().add(v.getProperty("name"));
//        }
    }

    @FXML
    void createNewDfd(ActionEvent event) {
        String newDfd;
        if (!newDfdTextField.getText().isEmpty()) {
            newDfd = newDfdTextField.getText();
            odb.addDfdToDb(newDfd);
            newDfdTextField.clear();
            updateComboBox();
        }
    }

}
