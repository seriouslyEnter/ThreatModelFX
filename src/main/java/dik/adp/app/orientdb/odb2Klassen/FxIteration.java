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
public class FxIteration {
    private Integer iteratoin;
    private Number risk;

    public FxIteration(Integer iteratoin, Number risk) {
        this.iteratoin = iteratoin;
        this.risk = risk;
    }

    public Number getRisk() {
        return risk;
    }

    public void setRisk(Number risk) {
        this.risk = risk;
    }

    public Integer getIteratoin() {
        return iteratoin;
    }

    public void setIteratoin(Integer iteratoin) {
        this.iteratoin = iteratoin;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.iteratoin);
        hash = 23 * hash + Objects.hashCode(this.risk);
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
        final FxIteration other = (FxIteration) obj;
        if (!Objects.equals(this.iteratoin, other.iteratoin)) {
            return false;
        }
        if (!Objects.equals(this.risk, other.risk)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FxIteration{" + "iteratoin=" + iteratoin + ", risk=" + risk + '}';
    }
    
    
    
}
