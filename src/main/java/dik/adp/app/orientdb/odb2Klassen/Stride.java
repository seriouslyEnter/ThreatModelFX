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
public enum Stride {

    SPOOFING("Spoofing of user identity"),
    TAMPERING("Tampering"),
    REPUDIATION("Repudiation"),
    INFORMATION("Information disclosure"),
    DENIAL("Denial of service"),
    ELEVATION("Elevation of privilege"),
    EMPTY("");

    private String name;

    private Stride(String name) {
        this.name = name;
    }

    public String toString(Stride stride) {
        String returnThis = "";

        switch (stride) {
            case SPOOFING:
                returnThis = "Spoofing of user identity";
                break;
            case TAMPERING:
                returnThis = "Tampering";
                break;
            case REPUDIATION:
                returnThis = "Repudiation";
                break;
            case INFORMATION:
                returnThis = "Information disclosure";
                break;
            case DENIAL:
                returnThis = "Denial of service";
                break;
            case ELEVATION:
                returnThis = "Elevation of privilege";
                break;
            case EMPTY:
                returnThis = "";
                break;
            default:
                break;
        }

        return returnThis;
    }
}
