/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.orientdb.odb2Klassen;

import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author gu35nxt
 */
public class FxDFlow {

    private StringProperty key; //cant use "id" its a reserved word in ThinkerPop
    private StringProperty name;
    private StringProperty diagram;
//    private FxDfdElement from;
//    private FxDfdElement to;
    private StringProperty from;
    private StringProperty to;

    public FxDFlow(String key, String name, String diagram, String from, String to) {
        setKey(key);
        setName(name);
        setDiagram(diagram);
        setFrom(from);
        setTo(to);
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

    public final String getFrom() {
        return fromProperty().get();
    }

    public final void setFrom(String from) {
        fromProperty().set(from);
    }

    public StringProperty fromProperty() {
        if (from == null) {
            from = new SimpleStringProperty();
        }
        return from;
    }

    public final String getTo() {
        return toProperty().get();
    }

    public final void setTo(String to) {
        toProperty().set(to);
    }

    public StringProperty toProperty() {
        if (to == null) {
            to = new SimpleStringProperty();
        }
        return to;
    }

//    public FxDfdElement getFrom() {
//        return from;
//    }
//
//    public void setFrom(FxDfdElement from) {
//        this.from = from;
//    }
//
//    public FxDfdElement getTo() {
//        return to;
//    }
//
//    public void setTo(FxDfdElement to) {
//        this.to = to;
//    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.key);
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.diagram);
        hash = 23 * hash + Objects.hashCode(this.from);
        hash = 23 * hash + Objects.hashCode(this.to);
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
        final FxDFlow other = (FxDFlow) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.diagram, other.diagram)) {
            return false;
        }
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.to, other.to)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FxDf{" + "key=" + key + ", name=" + name + ", diagram=" + diagram + ", from=" + from + ", to=" + to + '}';
    }

}
