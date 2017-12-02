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
public class FxRisk {
    private Integer damage;
    private Integer reproducibility;
    private Integer exploitability;
    private Integer affected;
    private Integer discoverability;

    public FxRisk(Integer damage, Integer reproducibility, Integer exploitability, Integer affected, Integer discoverability) {
        this.damage = damage;
        this.reproducibility = reproducibility;
        this.exploitability = exploitability;
        this.affected = affected;
        this.discoverability = discoverability;
    }

    public Integer getDiscoverability() {
        return discoverability;
    }

    public void setDiscoverability(Integer discoverability) {
        this.discoverability = discoverability;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getReproducibility() {
        return reproducibility;
    }

    public void setReproducibility(Integer reproducibility) {
        this.reproducibility = reproducibility;
    }

    public Integer getExploitability() {
        return exploitability;
    }

    public void setExploitability(Integer exploitability) {
        this.exploitability = exploitability;
    }

    public Integer getAffected() {
        return affected;
    }

    public void setAffected(Integer affected) {
        this.affected = affected;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.damage);
        hash = 89 * hash + Objects.hashCode(this.reproducibility);
        hash = 89 * hash + Objects.hashCode(this.exploitability);
        hash = 89 * hash + Objects.hashCode(this.affected);
        hash = 89 * hash + Objects.hashCode(this.discoverability);
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
        final FxRisk other = (FxRisk) obj;
        if (!Objects.equals(this.damage, other.damage)) {
            return false;
        }
        if (!Objects.equals(this.reproducibility, other.reproducibility)) {
            return false;
        }
        if (!Objects.equals(this.exploitability, other.exploitability)) {
            return false;
        }
        if (!Objects.equals(this.affected, other.affected)) {
            return false;
        }
        if (!Objects.equals(this.discoverability, other.discoverability)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FxRisk{" + "damage=" + damage + ", reproducibility=" + reproducibility + ", exploitability=" + exploitability + ", affected=" + affected + ", discoverability=" + discoverability + '}';
    }
    
    

    
    
}
