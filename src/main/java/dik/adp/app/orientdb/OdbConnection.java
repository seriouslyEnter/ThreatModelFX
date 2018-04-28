/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.orientdb;

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

/**
 *
 * @author gu35nxt
 */
public class OdbConnection {

    public OrientGraphFactory ogf() {
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
//        OrientGraphFactory factory = new OrientGraphFactory("plocal:.\\databases\\ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
//        OrientGraphFactory factory = new OrientGraphFactory("plocal:G:\\LAGER\\NETBEANS PROJEKTE\\ThreatModelDB\\databases\\ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
//        OrientGraph graph = factory.getTx();
        return factory;
    }

}
