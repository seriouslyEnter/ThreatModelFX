package dik.adp.app.gui.sharedcommunicationmodel;

import dik.adp.app.orientdb.odb2Klassen.FxAT;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author gu35nxt
 */
public class SelectedState {

    //==============================Diagram=====================================
    private final StringProperty selectedDiagram = new SimpleStringProperty();

    public StringProperty selectedDiagramProperty() {
        return selectedDiagram;
    }

    public final String isSelectedDiagram() {
        return selectedDiagramProperty().get();
    }

    public final void setSelectedDiagram(String selectedDiagram) {
        selectedDiagramProperty().set(selectedDiagram);
    }
    //==========================================================================

    //==============================AT==========================================
//    private final StringProperty selectedAt = new SimpleStringProperty();
//
//    public StringProperty selectedAtProperty() {
//        return selectedAt;
//    }
//
//    public final String isSelectedAt() {
//        return selectedAtProperty().get();
//    }
//
//    public final void setSelectedAt(String selectedAt) {
//        selectedAtProperty().set(selectedAt);
//    }
    //==========================================================================

    //=========AT2============
    private ObjectProperty<FxAT> selectedAt = new SimpleObjectProperty<>();

    public FxAT getSelectedAt() {
        return selectedAt.get();
    }

    public void setSelectedAt(FxAT selectedAt) {
        this.selectedAt.set(selectedAt);
    }
    
    public ObjectProperty<FxAT> selectedAtProperty() {
        return selectedAt;
    }

}
