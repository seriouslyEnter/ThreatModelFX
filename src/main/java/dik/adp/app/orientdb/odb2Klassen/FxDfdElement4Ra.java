/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.orientdb.odb2Klassen;

/**
 *
 * @author gu35nxt
 */
//DFD Element als ein JavaFX Bean um in ObservableList zu benutzen
public class FxDfdElement4Ra extends FxDfdElement{

    public FxDfdElement4Ra(String key, String type, String name, String diagram, String boundary) {
        super(key, type, name, diagram, boundary);
    }

    @Override
     public String toString() {
        return super.getKey() + ": " + super.getName();
    }

}
