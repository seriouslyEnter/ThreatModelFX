/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.orientdb.odb2Klassen;

import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author gu35nxt
 */
//DFD Element als ein JavaFX Bean um in ObservableList zu benutzen
public class FxDfdElement {

    private StringProperty key; //cant use "id" its a reserved word in ThinkerPop
    private StringProperty type;
    private StringProperty name;
    private StringProperty diagram;

    private ObservableList<FxDfdElement> elements = FXCollections.observableArrayList();

    public FxDfdElement(String key, String type, String name, String diagram) {
        setKey(key);
        setType(type);
        setName(name);
        setDiagram(diagram);
    }

    public final String getKey() {
        return keyProperty().get();
    }

    public final void setKey(String key) {
        keyProperty().set(key);
    }

    public StringProperty keyProperty() {
        if (key == null) {
            key = new SimpleStringProperty();
        }
        return key;
    }

    public final String getType() {
        return typeProperty().get();
    }

    public final void setType(String type) {
        typeProperty().set(type);
    }

    public StringProperty typeProperty() {
        if (type == null) {
            type = new SimpleStringProperty();
        }
        return type;
    }

    public final String getName() {
        return nameProperty().get();
    }

    public final void setName(String name) {
        nameProperty().set(name);
    }

    public StringProperty nameProperty() {
        if (name == null) {
            name = new SimpleStringProperty();
        }
        return name;
    }

    public final String getDiagram() {
        return diagramProperty().get();
    }

    public final void setDiagram(String diagram) {
        diagramProperty().set(diagram);
    }

    public StringProperty diagramProperty() {
        if (diagram == null) {
            diagram = new SimpleStringProperty();
        }
        return diagram;
    }

    public ObservableList<FxDfdElement> employeesProperty() {
        return elements;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.key);
        hash = 67 * hash + Objects.hashCode(this.type);
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.diagram);
        hash = 67 * hash + Objects.hashCode(this.elements);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FxDfdElement other = (FxDfdElement) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.diagram, other.diagram)) {
            return false;
        }
        if (!Objects.equals(this.elements, other.elements)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FxDfdElement{" + "key=" + key + ", type=" + type + ", name=" + name + ", diagram=" + diagram + ", elements=" + elements + '}';
    }

}
