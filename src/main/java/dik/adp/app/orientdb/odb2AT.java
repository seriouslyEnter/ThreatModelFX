/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.orientdb;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import dik.adp.app.orientdb.odb2Klassen.FxAT;
import java.util.Collections;
import javafx.collections.ObservableList;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class odb2AT {

    @Inject
    OdbConnection odbc;

    public void addATtoDB(String newATextField, String selectedDiagramShowing) {
//        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
//        OrientGraph graph = factory.getTx();

        OrientGraph graph = odbc.ogf().getTx();
        try {
            //create new AT
            Vertex at = graph.addVertex("class:AT");
            at.setProperty("name", newATextField);
            //on creation alwys false for selected
            at.setProperty("selected", false);
            //get DFD from DB
            Vertex dfd = null;
            for (Vertex d : (Iterable<Vertex>) graph.getVertices("DfdDiagram.name", selectedDiagramShowing)) {
                dfd = d;
            }
            //connect DFD with new AT
            Edge e = graph.addEdge(null, dfd, at, "hasAT");
            graph.commit();
        } catch (Exception e) {
            graph.rollback();
            graph.shutdown();
        }
    }

    public ObservableList<FxAT> queryAT(ObservableList<FxAT> obsListAT, String selectedDiagram) {
//        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
//        OrientGraph graph = factory.getTx();

        OrientGraph graph = odbc.ogf().getTx();
        try {
            for (Vertex v : (Iterable<Vertex>) graph.command(
                    new OCommandSQL(
                            "SELECT expand(out('hasAT'))"
                            + "FROM DfdDiagram "
                            + "WHERE name='" + selectedDiagram + "'"
                    )).execute()) {
                System.out.println("Name : " + v.getProperty("name") + " selecte: " + v.getProperty("selected"));
                FxAT at = new FxAT(v.getProperty("name"), v.getProperty("selected"), v.getProperty("diagram"));
                if (at != null) {
                    obsListAT.add(at);
                }
            }
        } finally {
            graph.shutdown();
        }
        //sortiere Ergebnis nach name Stichowort "Comparator"
        Collections.sort(obsListAT, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        return obsListAT;
    }

    public void saveSelectedAT(String selectedDiagram, String at, Boolean newVal) {
//        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
//        OrientGraph graph = factory.getTx();
        OrientGraph graph = odbc.ogf().getTx();
        try {
            for (Vertex v : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT FROM AT WHERE name='" + at + "' "
                    + "AND in('hasAT').name = '" + selectedDiagram + "'"
            )).execute()) {
                System.out.println("Name : " + v.getProperty("name"));
                v.setProperty("selected", newVal.toString());
            }
            graph.commit();
        } finally {
            graph.shutdown();
        }
    }

}
