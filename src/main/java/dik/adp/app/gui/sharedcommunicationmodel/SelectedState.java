/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.sharedcommunicationmodel;

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
    private final StringProperty selectedAt = new SimpleStringProperty();

    public StringProperty selectedAtProperty() {
        return selectedAt;
    }

    public final String isSelectedAt() {
        return selectedAtProperty().get();
    }

    public final void setSelectedAt(String selectedAt) {
        selectedAtProperty().set(selectedAt);
    }
    //==========================================================================

}
