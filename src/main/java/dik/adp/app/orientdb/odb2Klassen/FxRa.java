/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.orientdb.odb2Klassen;

import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author gu35nxt
 */
public class FxRa {
    private FxDfdElement fxDfdElement;
    private Map<Stride,FxBa> threats = new EnumMap<>(Stride.class);

    public FxRa(FxDfdElement fxDfdElement) {
        this.fxDfdElement = fxDfdElement;
    }

    public FxDfdElement getFxDfdElement() {
        return fxDfdElement;
    }

    public void setFxDfdElement(FxDfdElement fxDfdElement) {
        this.fxDfdElement = fxDfdElement;
    }

    public Map<Stride, FxBa> getThreats() {
        return threats;
    }

    public void setThreats(Map<Stride, FxBa> threats) {
        this.threats = threats;
    }
    
    public void addThreat(FxBa fxBa){
        threats.put(fxBa.getThreat(), fxBa);
    }
}
