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
public enum StrideEnum {
    SPOOFING("Spoofing of user identity"),
    TAMPERING("Tampering"),
    REPUDIATION("Repudiation"),
    INFORMATION("Information disclosure"),
    DENIAL("Denial of service"),
    ELEVATION("Elevation of privilege");
    
    private final String name;
    
    StrideEnum(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }

}

//    SPOOFING("Spoofing of user identity"), TAMPERING, REPUDIATION, INFORMATION, DENIAL, ELEVATION;
//    String bedrohung;
//    
//    public enum StrideType {
//    SPOOFING("Spoofing of user identity"),
//    TAMPERING("Tampering"),
//    REPUDIATION("Repudiation"),
//    INFORMATION("Information disclosure"),
//    DENIAL("Denial of service"),
//    ELEVATION("Elevation of privilege");
//}
//    

