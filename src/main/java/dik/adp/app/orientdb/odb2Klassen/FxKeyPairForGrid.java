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
//public class FxKeyPairForGrid<stride, dread> {
    public class FxKeyPairForGrid {
    
    private final Stride stride;
    private final Dread dread;

    public FxKeyPairForGrid(Stride stride, Dread dread) {
        this.stride = stride;
        this.dread = dread;
    }

    public Dread getDread() {
        return dread;
    }

//    public void setDread(dread dread) {
//        this.dread = dread;
//    }

    public Stride getStride() {
        return stride;
    }

//    public void setStride(Stride stride) {
//        this.stride = stride;
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.stride);
        hash = 71 * hash + Objects.hashCode(this.dread);
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
        final FxKeyPairForGrid other = (FxKeyPairForGrid) obj;
        if (this.stride != other.stride) {
            return false;
        }
        if (this.dread != other.dread) {
            return false;
        }
        return true;
    }

    
    
    
}
