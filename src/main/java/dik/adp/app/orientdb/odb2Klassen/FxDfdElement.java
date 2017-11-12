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

    private StringProperty rid;
    private StringProperty key; //cant use "id" its a reserved word in ThinkerPop
    private StringProperty type;
    private StringProperty name;
    private StringProperty diagram;
    private StringProperty boundary;

    private ObservableList<FxDfdElement> elements = FXCollections.observableArrayList();

    public FxDfdElement(String rid, String key, String type, String name, String diagram, String boundary) {
        setRid(rid);
        setKey(key);
        setType(type);
        setName(name);
        setDiagram(diagram);
        setBoundary(boundary);
    }

    public FxDfdElement(String key, String type, String name, String diagram, String boundary) {
        setKey(key);
        setType(type);
        setName(name);
        setDiagram(diagram);
        setBoundary(boundary);
    }

    public FxDfdElement(FxDfdElement fxDfdElement) {
        setKey(fxDfdElement.getKey());
        setType(fxDfdElement.getType());
        setName(fxDfdElement.getName());
        setDiagram(fxDfdElement.getDiagram());
        setBoundary(fxDfdElement.getBoundary());
    }

    public final String getRid() {
        return ridProperty().get();
    }

    public final void setRid(String rid) {
        ridProperty().set(rid);
    }

    public StringProperty ridProperty() {
        if (rid == null) {
            rid = new SimpleStringProperty();
        }
        return rid;
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

    public final String getBoundary() {
        return boundaryProperty().get();
    }

    public final void setBoundary(String boundary) {
        boundaryProperty().set(boundary);
    }

    public StringProperty boundaryProperty() {
        if (boundary == null) {
            boundary = new SimpleStringProperty();
        }
        return boundary;
    }

    public ObservableList<FxDfdElement> employeesProperty() {
        return elements;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.rid);
        hash = 59 * hash + Objects.hashCode(this.key);
        hash = 59 * hash + Objects.hashCode(this.type);
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.diagram);
        hash = 59 * hash + Objects.hashCode(this.boundary);
        hash = 59 * hash + Objects.hashCode(this.elements);
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
        if (!Objects.equals(this.rid, other.rid)) {
            return false;
        }
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
        if (!Objects.equals(this.boundary, other.boundary)) {
            return false;
        }
        if (!Objects.equals(this.elements, other.elements)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FxDfdElement{" + "rid=" + rid + ", key=" + key + ", type=" + type + ", name=" + name + ", diagram=" + diagram + ", boundary=" + boundary + ", elements=" + elements + '}';
    }

}
