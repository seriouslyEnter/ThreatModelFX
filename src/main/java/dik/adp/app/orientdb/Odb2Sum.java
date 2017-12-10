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
import dik.adp.app.orientdb.odb2Klassen.FxIteration;
import dik.adp.app.orientdb.odb2Klassen.Rating;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javafx.scene.control.TreeItem;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class Odb2Sum {

    @Inject
    Odb2Helper odb2helper;

    @Inject
    odb2It odb2It;

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
                        v.getId().toString(),
                        "",
                        v.getProperty("@class"),
                        v.getProperty("name"),
                        v.getProperty("name")
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

    public List<FxDfdElement> queryTopBoundary(String selectedDiagram) {
        OrientGraphNoTx graph = ogf().getNoTx();
//        Vertex topBoundary = null;
        List<FxDfdElement> listOfTopBoundarys = new ArrayList<>();
//        Boolean hasHigherBoundary = false;
        try {
            for (Vertex v1 : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT "
                    + " FROM DfdElement"
                    + " WHERE diagram='" + selectedDiagram + "'"
                    + " AND type='Boundary'"
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

        listOfTopBoundarys.forEach(item
                -> {
            System.out.println(item.getRid());
        }
        );
        return listOfTopBoundarys;
    }

    public List<TreeItem<FxDfdElement>> queryChildElements(Queue<TreeItem<FxDfdElement>> childElementsQueue, String selectedDiagram) {
        OrientGraphNoTx graph = ogf().getNoTx();
        List<TreeItem<FxDfdElement>> childElementList = new ArrayList<>();
        TreeItem<FxDfdElement> treeItem;
        if (childElementsQueue.peek() != null) {
            try {
                for (Vertex v : (Iterable<Vertex>) graph.command(new OCommandSQL(
                        "SELECT expand(in('inBoundary'))"
                        + " FROM " + childElementsQueue.peek().getValue().getRid()
                )).execute()) {
                    treeItem = new TreeItem<>(odb2helper.vertexToFxDfdElement(v));
                    //zu childListe hinzufügen
                    childElementList.add(treeItem);
                    //zu Queue hinzufügen
                    childElementsQueue.add(treeItem);
                }
            } catch (Exception e) {
                graph.shutdown();
            }
        }
        return childElementList;
    }

    public List<TreeItem<FxDfdElement>> queryElementsWithoutBoundary(String selectedDiagram) {
        OrientGraphNoTx graph = ogf().getNoTx();
        List<TreeItem<FxDfdElement>> elementList = new ArrayList<>();
        try {
            //finde die Iteration and die Dread angehängt wird
//            System.out.println(fxBa.getRid());
            for (Vertex v : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT"
                    + " FROM DfdElement"
                    + " WHERE out('inBoundary').size() = 0 AND type!='Boundary'"
            )).execute()) {
                TreeItem treeItem = new TreeItem<>(odb2helper.vertexToFxDfdElement(v));
                elementList.add(treeItem);
            }
            graph.commit();
        } catch (Exception e) {
            System.out.println(e);
            graph.rollback();
            graph.shutdown();
        }
//        Collections.sort(result, (a, b) -> a.getDfdElement().getKey().compareToIgnoreCase(b.getDfdElement().getKey()));
        return elementList;
    }

    public Map<Integer, FxIteration> calculateRiskOfLeafElement(FxDfdElement fxDfdElement, String selectedDiagram) {
        Map<Integer, FxIteration> dProIt = new HashMap<>();
        FxIteration averageRiskProIteration;
        OrientGraphNoTx graph = ogf().getNoTx();
        try {
            Integer maxIteration = odb2It.findMaxIteration(selectedDiagram);
            for (int i = 1; i <= maxIteration; i++) {
                averageRiskProIteration = new FxIteration(0, 0);
                //Setze Iteration
                averageRiskProIteration.setIteration(i);
                //Setze Durchschnitt pro Bedrohung
                for (Vertex v : (Iterable<Vertex>) graph.command(new OCommandSQL(
                        "SELECT expand(in('targets').out('has_iteration')[iteration=" + i + "].out('metric'))"
                        + " FROM " + fxDfdElement.getRid()
                )).execute()) {
                    //berechne Druchschnitt
                    Double dSchnitt = 0d;
                    for (Dread dread : Dread.values()) {
                        if (v.getProperty(dread.name()) == null) {
                            dSchnitt = dSchnitt + 0d;
                        } else {
                            Integer zw = v.getProperty(dread.name());
                            dSchnitt = dSchnitt + zw.doubleValue();
//                            dSchnitt = dSchnitt + (Double) v.getProperty(dread.name());
                        }
                    }
                    averageRiskProIteration.setRisk(dSchnitt / 5); //Durchschnitt
                }
                dProIt.put(i, averageRiskProIteration);
            }
        } catch (Exception e) {
            System.err.println(e);
            graph.shutdown();
        }
        return dProIt;
    }

    public Map<Integer, FxIteration> calculateRiskOfParentElement(FxDfdElement fxDfdElement, String selectedDiagram) {
        Map<Integer, FxIteration> dProIt = new HashMap<>();
        //prüfe ob Sonderfall rootNode/ganzes DfdDiagramm oder(else) einzelnes Boundary
        if (fxDfdElement.getType().contentEquals("DfdDiagram")) {
            Map<Integer, FxIteration> childDProIt;
//        FxIteration averageRiskProIteration;
            Integer addedElements = 0;
            OrientGraphNoTx graph = ogf().getNoTx();
            try {
                Integer maxIteration = odb2It.findMaxIteration(selectedDiagram);
                //erstelle leere Objekte für Parent
                for (int i = 1; i <= maxIteration; i++) {
                    dProIt.put(i, new FxIteration(i, 0));
                }
//            for (int i = 1; i <= maxIteration; i++) {
//                averageRiskProIteration = new FxIteration(0, 0);
//                //Setze Iteration
//                averageRiskProIteration.setIteration(i);
                //find children
                for (Vertex v : (Iterable<Vertex>) graph.command(new OCommandSQL(
                        "SELECT"
                        + " FROM DfdElement"
                        + " WHERE diagram='" + selectedDiagram + "' AND type!='Boundary'"
                )).execute()) {
                    childDProIt = calculateRiskOfLeafElement(odb2helper.vertexToFxDfdElement(v), selectedDiagram);
                    //remember how many Elements were added
                    addedElements++;
                    //adding old and new Risk for every Iteration
                    for (Integer iteration : childDProIt.keySet()) {
                        FxIteration oldValue = dProIt.get(iteration);
                        FxIteration newValue = childDProIt.get(iteration);
                        //adding old and new Risk
                        oldValue.setRisk(oldValue.getRisk().doubleValue() + newValue.getRisk().doubleValue());
                    }
                }
                //calculate Average
                for (Map.Entry<Integer, FxIteration> entry : dProIt.entrySet()) {
                    Integer key = entry.getKey();
                    FxIteration value = entry.getValue();
                    value.setRisk(value.getRisk().doubleValue() / addedElements);
                }
//            }
            } catch (Exception e) {
                System.err.println(e);
                graph.shutdown();
            }
        } else {
            Map<Integer, FxIteration> childDProIt;
//        FxIteration averageRiskProIteration;
            Integer addedElements = 0;
            OrientGraphNoTx graph = ogf().getNoTx();
            try {
                Integer maxIteration = odb2It.findMaxIteration(selectedDiagram);
                //erstelle leere Objekte für Parent
                for (int i = 1; i <= maxIteration; i++) {
                    dProIt.put(i, new FxIteration(i, 0));
                }
//            for (int i = 1; i <= maxIteration; i++) {
//                averageRiskProIteration = new FxIteration(0, 0);
//                //Setze Iteration
//                averageRiskProIteration.setIteration(i);
                //find children
                for (Vertex v : (Iterable<Vertex>) graph.command(new OCommandSQL(
                        //                    "SELECT expand(in('inBoundary')) FROM " + fxDfdElement.getRid()
                        "SELECT "
                        + " FROM "
                        + " ( "
                        + " TRAVERSE in('inBoundary') "
                        + " FROM " + fxDfdElement.getRid()
                        + " ) "
                        + " WHERE type!='Boundary'"
                )).execute()) {
                    childDProIt = calculateRiskOfLeafElement(odb2helper.vertexToFxDfdElement(v), selectedDiagram);
                    //remember how many Elements were added
                    addedElements++;
                    //adding old and new Risk for every Iteration
                    for (Integer iteration : childDProIt.keySet()) {
                        FxIteration oldValue = dProIt.get(iteration);
                        FxIteration newValue = childDProIt.get(iteration);
                        //adding old and new Risk
                        oldValue.setRisk(oldValue.getRisk().doubleValue() + newValue.getRisk().doubleValue());
                    }
                }
                //calculate Average
                for (Map.Entry<Integer, FxIteration> entry : dProIt.entrySet()) {
                    Integer key = entry.getKey();
                    FxIteration value = entry.getValue();
                    value.setRisk(value.getRisk().doubleValue() / addedElements);
                }
//            }
            } catch (Exception e) {
                System.err.println(e);
                graph.shutdown();
            }
        }
        return dProIt;
    }

}
