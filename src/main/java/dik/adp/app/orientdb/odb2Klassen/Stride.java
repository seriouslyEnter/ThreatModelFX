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
    EMPTY("Empty");

    private final String bezeichnung;

    private Stride(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
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
                returnThis = "Empty";
                break;
            default:
                break;
        }

        return returnThis;
    }

//    public Stride fromString(String text) {
//        Stride returnThis = null;
//
//        switch (text) {
//            case "SPOOFING":
//                returnThis = Stride.SPOOFING;
//                break;
//            case "TAMPERING":
//                returnThis = Stride.TAMPERING;
//                break;
//            case "REPUDIATION":
//                returnThis = Stride.REPUDIATION;
//                break;
//            case "INFORMATION":
//                returnThis = Stride.INFORMATION;
//                break;
//            case "DENIAL":
//                returnThis = Stride.DENIAL;
//                break;
//            case "ELEVATION":
//                returnThis = Stride.ELEVATION;
//                break;
//            case "EMPTY":
//                returnThis = Stride.EMPTY;
//                break;
//            default:
//                break;
//        }
//
//        return returnThis;
//    }
}
