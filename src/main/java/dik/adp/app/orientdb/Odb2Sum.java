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
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import dik.adp.app.orientdb.odb2Klassen.Dread;
import dik.adp.app.orientdb.odb2Klassen.FxBa;
import dik.adp.app.orientdb.odb2Klassen.FxDfdElement;
import dik.adp.app.orientdb.odb2Klassen.Rating;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class Odb2Sum {

    @Inject
    Odb2Helper odb2helper;

    private OrientGraphFactory ogf() {
        OrientGraphFactory factory = new OrientGraphFactory(
                "remote:localhost/ThreatModelDB", "admin", "admin"
        ).setupPool(1, 10);
        return factory;
    }

    public FxDfdElement querySystemElement(String selectedDiagram) {
        OrientGraphNoTx graph = ogf().getNoTx();
        FxDfdElement rootNode = null;
        try {
            for (Vertex v : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT FROM DfdDiagram WHERE name='" + selectedDiagram + "'"
            )).execute()) {
//                FxDfdElement newElement = new FxDfdElement(
//                        v.getId().toString(), "DfdDiagram", v.getProperty("name")
//                );
//                rootNode = new FxDfdElement(newElement);
                rootNode = new FxDfdElement(
                        v.getId().toString(), "DfdDiagram", v.getProperty("name")
                );
            }

        } catch (Exception e) {
            graph.shutdown();
        }
        if (rootNode == null) {
            System.out.println("Mist null");
        } else {
            System.out.println(rootNode.toString());
        }
        return rootNode;
    }

    public void createerftgsdfgsgDREAD(FxBa fxBa, Integer iteration, Rating rating, Dread dread) {
        OrientGraph graph = ogf().getTx();
        try {
            //finde die Iteration and die Dread angehängt wird
//            System.out.println(fxBa.getRid());
            for (Vertex vDread : (Iterable<Vertex>) graph.command(new OCommandSQL()).execute()) {

            }
            graph.commit();
        } catch (Exception e) {
            System.out.println(e);
            graph.rollback();
            graph.shutdown();
        }
//        Collections.sort(result, (a, b) -> a.getDfdElement().getKey().compareToIgnoreCase(b.getDfdElement().getKey()));
    }

    public List<FxDfdElement> queryTopBoundary(String selectedDiagram) {
        OrientGraphNoTx graph = ogf().getNoTx();
//        Vertex topBoundary = null;
        List<FxDfdElement> listOfTopBoundarys = new ArrayList<>();
        Boolean hasHigherBoundary = false;
        try {
            for (Vertex v1 : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT "
                    + " FROM DfdElement"
                    + " WHERE type='Boundary'"
                    + " AND out('inBoundary').size() = 0"
            )).execute()) {
                listOfTopBoundarys.add(odb2helper.vertexToFxDfdElement(v1));
              
//                FxDfdElement newBoundary = new FxDfdElement(
//                        odb2helper.vertexToFxDfdElement(v)
//                );
//                boundaryList.add(newBoundary);
//            }
                // ist ein topBoundary wenn es keine höheren gibt
//                if (listOfTopBoundarys.isEmpty()) {
//                    listOfTopBoundarys.add(new FxDfdElement(odb2helper.vertexToFxDfdElement(v1)));
//                }
//                hasHigherBoundary = false;
//                for (Vertex v2 : v1.getVertices(Direction.OUT, "inBoundary")) {
//                    hasHigherBoundary = true;
//                }
//                //neuer top Boundary nur wenn es einen auf höherer Ebene gibt
////                if (v2.getProperty("type") == "Boundary") {
//                if (hasHigherBoundary == false) {
//                    listOfTopBoundarys.add(new FxDfdElement(odb2helper.vertexToFxDfdElement(v2)));
//                }
                //                for (Vertex v2 : (Iterable<Vertex>) graph.command(new OCommandSQL(
                //                        "SELECT in('inBoundary') FROM " + v1.getId().toString()
                //                )).execute()) {
                //                    FxDfdElement newBoundary = new FxDfdElement(
                //                            odb2helper.vertexToFxDfdElement(v)
                //                    );
                //                    boundaryList.add(newBoundary);
                //                }
//                rootBoundary = new FxDfdElement(odb2helper.vertexToFxDfdElement(topBoundary));
            }

        } catch (Exception e) {
            graph.shutdown();
        }

        listOfTopBoundarys.forEach(item -> {
            System.out.println(item.getRid());
        });

        return listOfTopBoundarys;
    }

}
