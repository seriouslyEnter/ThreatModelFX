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
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class odb2It {
    
        @Inject
    OdbConnection odbc;

    private OrientGraphFactory ogf() {
        OrientGraphFactory factory = new OrientGraphFactory(
                "remote:localhost/ThreatModelDB", "admin", "admin"
        ).setupPool(1, 10);
        return factory;
    }

    public Boolean addNewIteration(String dfdDiagram) {
        OrientGraph graph = ogf().getTx();
        Integer newIteration = findMaxIteration(dfdDiagram) + 1;
        Boolean success;
        try {
            for (Vertex vBa : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT expand(out('hasAT').in('realized_by'))"
                    + " FROM DfdDiagram"
                    + " WHERE name='DFD Eins'"
            )).execute()) {
                System.out.println(vBa.toString());
                OrientVertex vIt = graph.addVertex("class:Iteration", "iteration", newIteration);
                graph.addEdge(null, vBa, vIt, "has_iteration");
            }
            graph.commit();
            success = true;
        } catch (Exception e) {
            graph.rollback();
            graph.shutdown();
            success = false;
        }
        return success;
    }

    public Integer findMaxIteration(String dfdDiagram) {
        OrientGraph graph = ogf().getTx();
        Integer maxIiteration = 0;
        try {
            for (Vertex maxIt : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT ifnull(max,0) as max"
                    + " FROM"
                    + " ( "
                    + " SELECT max(out('hasAT').in('realized_by').out('has_iteration').iteration) as max"
                    + " FROM DfdDiagram"
                    + " WHERE name='" + dfdDiagram + "'"
                    + " )"
            )).execute()) {
                System.out.println("query:" + maxIt.getProperty("max"));
                maxIiteration = maxIt.getProperty("max");
            }
        } catch (Exception e) {
            graph.shutdown();
        }
        return maxIiteration;
    }

    //TODO: Methode die Iterationen für nachträglich hinzugefügte Bedrohungen erstellt
    private void fixMissingIteration() {

    }

}
