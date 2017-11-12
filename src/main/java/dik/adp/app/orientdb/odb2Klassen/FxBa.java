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
public class FxBa {

    private String rid;
    private Stride threat;

//    public FxBa(String rid, Stride threat) {
//        this.rid = rid;
//        this.threat = threat;
//    }

    public FxBa(String rid, String threat) {
        this.rid = rid;
        this.threat = Stride.valueOf(threat);
    }

    public Stride getThreat() {
        return threat;
    }

    public void setThreat(Stride threat) {
        this.threat = threat;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

}
