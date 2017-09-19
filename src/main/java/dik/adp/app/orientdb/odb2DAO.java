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

    public void createDfd() {

    }

    public void listOfDfds() { //maybe static
        // AT THE BEGINNING
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10);

        // EVERY TIME YOU NEED A GRAPH INSTANCE
        OrientGraph graph = factory.getTx();
        try {

            for (Vertex v : (Iterable<Vertex>) graph.command(
                    new OCommandSQL(
                            "SELECT name FROM DFD")).execute()) {
                System.out.println("Name: " + v);
            }

        } finally {
            graph.shutdown();
        }

    }
}
