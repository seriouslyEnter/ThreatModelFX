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
public enum Rating {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low"),
    NONE("none");

    private final String name;

    private Rating(String name) {
        this.name = name;
    }

     @Override
    public String toString() {
        return name;
    }
    
    public Integer toNumber(){
        Integer number;
        
        switch (this.name) {
            case "high":
                number = 3;
                break;
            case "medium":
                number = 2;
                break;
            case "low":
                number = 1;
                break;
            default:
                number = 0;
                break;
        }
        
        return number;
    }

}
