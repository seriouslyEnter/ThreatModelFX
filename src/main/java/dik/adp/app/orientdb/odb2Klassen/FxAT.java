/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.orientdb.odb2Klassen;

import java.util.Objects;

/**
 *
 * @author gu35nxt
 */
public class FxAT {

    private String name;
    private Boolean selected;
    private DfdDiagram diagram;

    public FxAT(String name, Boolean selected, String diagram) {
        this.name = name;
        this.selected = selected;
//        this.diagram = diagram;
        this.diagram = new DfdDiagram(diagram);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public DfdDiagram getDiagram() {
        return diagram;
    }

    public void setDiagram(DfdDiagram diagram) {
        this.diagram = diagram;
    }

    public void setDiagram(String diagram) {
        this.diagram.setName(diagram);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.name);
        hash = 31 * hash + Objects.hashCode(this.selected);
        hash = 31 * hash + Objects.hashCode(this.diagram);
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
        final FxAT other = (FxAT) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.selected, other.selected)) {
            return false;
        }
        if (!Objects.equals(this.diagram, other.diagram)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FxAT{" + "name=" + name + ", selected=" + selected + ", diagram=" + diagram + '}';
    }

}
