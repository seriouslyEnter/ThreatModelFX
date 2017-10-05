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
import java.util.Collections;
import javafx.collections.ObservableList;

/**
 *
 * @author gu35nxt
 */
public class odb2AT {

    public void addATtoDB(String newATextField, String selectedDiagramShowing) {
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        OrientGraph graph = factory.getTx();
        try {
            //create new AT
            Vertex at = graph.addVertex("class:AT");
            at.setProperty("name", newATextField);
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

    public ObservableList<String> queryAT(ObservableList<String> obsListAT, String selectedDiagram) {
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        OrientGraph graph = factory.getTx();
        try {
            for (Vertex v : (Iterable<Vertex>) graph.command(
                    new OCommandSQL(
                            "SELECT out('hasAT').name AS name "
                                    + "FROM DfdDiagram "
                                    + "WHERE name='" + selectedDiagram + "' "
                                            + "UNWIND name")).execute()
                    ) {
                System.out.println("Name : " + v.getProperty("name"));
                String at = v.getProperty("name");
                if (at != null) {
                    obsListAT.add(at);
                }
            }
        } finally {
            graph.shutdown();
        }
        //sortiere Ergebnis nach name Stichowort "Comparator"
        Collections.sort(obsListAT, (a, b) -> a.compareToIgnoreCase(b));
        return obsListAT;
    }

}
