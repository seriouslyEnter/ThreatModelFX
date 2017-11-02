/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.orientdb.odb2Klassen;

import java.util.EnumMap;
import java.util.Objects;
import javafx.scene.control.CheckBox;

/**
 *
 * @author gu35nxt
 */
public class FxStride {

    private FxDfdElement dfdElement;
    private FxAT at;
    private Stride ba; //use when is selected/activated
    private CheckBox cb;
    private EnumMap<Stride, CheckBox> cbs = new EnumMap<>(Stride.class);

    public FxStride(FxDfdElement dfdElement, FxAT at, Stride ba) {
        this.dfdElement = dfdElement;
        this.at = at;
        this.ba = ba;
        this.cb = new CheckBox();

        for (Stride stride : Stride.values()) {
            CheckBox mapCb = new CheckBox();
            cbs.put(stride, mapCb);
        }
    }

    public FxDfdElement getDfdElement() {
        return dfdElement;
    }

    public void setDfdElement(FxDfdElement dfdElement) {
        this.dfdElement = dfdElement;
    }

    public FxAT getAt() {
        return at;
    }

    public void setAt(FxAT at) {
        this.at = at;
    }

    public Stride getBa() {
        return ba;
    }

    public void setBa(Stride ba) {
        this.ba = ba;
    }

    public CheckBox getCb() {
        return cb;
    }

    public void setCb(CheckBox cb) {
        this.cb = cb;
    }

    public EnumMap<Stride, CheckBox> getCbs() {
        return cbs;
    }

    public void setCbs(EnumMap<Stride, CheckBox> cbs) {
        this.cbs = cbs;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.dfdElement);
        hash = 17 * hash + Objects.hashCode(this.at);
        hash = 17 * hash + Objects.hashCode(this.ba);
        hash = 17 * hash + Objects.hashCode(this.cb);
        hash = 17 * hash + Objects.hashCode(this.cbs);
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
        final FxStride other = (FxStride) obj;
        if (!Objects.equals(this.dfdElement, other.dfdElement)) {
            return false;
        }
        if (!Objects.equals(this.at, other.at)) {
            return false;
        }
        if (this.ba != other.ba) {
            return false;
        }
        if (!Objects.equals(this.cb, other.cb)) {
            return false;
        }
        if (!Objects.equals(this.cbs, other.cbs)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FxStride{" + "dfdElement=" + dfdElement + ", at=" + at + ", ba=" + ba + ", cb=" + cb + ", cbs=" + cbs + '}';
    }

}
