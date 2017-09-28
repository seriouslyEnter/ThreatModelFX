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

    private StringProperty id;
    private StringProperty type;
    private StringProperty name;
    private ObservableList<FxDfdElement> elements = FXCollections.observableArrayList();

    public FxDfdElement(String id, String type, String name) {
        setId(id);
        setType(type);
        setName(name);
    }

    public final String getId() {
        return idProperty().get();
    }

    public final void setId(String id) {
        idProperty().set(id);
    }

    public StringProperty idProperty() {
        if (id == null) {
            id = new SimpleStringProperty();
        }
        return id;
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

    public ObservableList<FxDfdElement> employeesProperty() {
        return elements;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id);
        hash = 61 * hash + Objects.hashCode(this.type);
        hash = 61 * hash + Objects.hashCode(this.name);
        hash = 61 * hash + Objects.hashCode(this.elements);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.elements, other.elements)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fxDfdElement{" + "id=" + id + ", type=" + type + ", name=" + name + ", elements=" + elements + '}';
    }

}
