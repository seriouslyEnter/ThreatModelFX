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
    LOW("low");

    private String rating;

    private Rating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

}
