/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.orientdb;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import dik.adp.app.orientdb.odb2Klassen.dfdVertex;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.ObservableList;

/**
 *
 * @author gu35nxt
 */
public class odb2DAO {

    public void eineMethode() {
        // AT THE BEGINNING
        OrientGraphFactory factory = new OrientGraphFactory("plocal:/tmp/graph/db", "username", "password").setupPool(1, 10);
        // EVERY TIME YOU NEED A GRAPH INSTANCE
        OrientGraph graph = factory.getTx();
        try {
            int modified = graph.command(
                    new OCommandSQL(
                            "UPDATE Customer SET local = TRUE "
                            + "WHERE 'Rome' IN out('lives').name")).execute();
        } finally {
            graph.shutdown();
        }
    }

    public ObservableList<dfdVertex> getDfds(ObservableList<dfdVertex> obsListOfDfds) { //maybe static
        // AT THE BEGINNING
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        // EVERY TIME YOU NEED A GRAPH INSTANCE
        OrientGraph graph = factory.getTx();
        try {
            for (Vertex v : (Iterable<Vertex>) graph.command(
                    new OCommandSQL(
                            "SELECT FROM DFD")).execute()) {
                System.out.println("Name: " + v + " " + v.getProperty("name"));
                dfdVertex dV = new dfdVertex(v.getProperty("name"));
                obsListOfDfds.add(dV);
            }
        } finally {
            graph.shutdown();
        }     
        //sortiere Ergebnis nach name Stichowort "Comparator"
        Collections.sort(obsListOfDfds, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        return obsListOfDfds;
    }

//        public ArrayList getDfds() { //maybe static
//        ArrayList<Vertex> dfdList = new ArrayList<>();
//        // AT THE BEGINNING
//        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
//        // EVERY TIME YOU NEED A GRAPH INSTANCE
//        OrientGraph graph = factory.getTx();
//        try {
//            for (Vertex v : (Iterable<Vertex>) graph.command(
//                    new OCommandSQL(
//                            "SELECT FROM DFD")).execute()) {
//                System.out.println("Name: " + v + " " + v.getProperty("name"));
//                dfdList.add(v);
//            }
//        } finally {
//            graph.shutdown();
//        }
//        return dfdList;
//    }
    public void addDfdToDb(String newDfd) {
        // AT THE BEGINNING
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        // EVERY TIME YOU NEED A GRAPH INSTANCE
        OrientGraph graph = factory.getTx();
        try {
            Vertex v = graph.addVertex("class:DFD");
            v.setProperty("name", newDfd);
            graph.commit();
        } catch (Exception e) {
            graph.rollback();
            graph.shutdown();
        }
    }
}
