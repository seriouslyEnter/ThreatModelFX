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
public class SharedDiagram {
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
}
