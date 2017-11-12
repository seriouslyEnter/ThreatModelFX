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
public enum Dread {

    DAMAGE("Damage"),
    REPRODUCIBILITY("Reproducibility"),
    EXPOITABILITY("Exploitability"),
    AFFECTED("Affected"),
    DISCOVERABILITY("Discoverability");

    private String name;

    private Dread(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }


}
