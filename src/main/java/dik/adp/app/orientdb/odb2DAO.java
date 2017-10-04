/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.orientdb;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import dik.adp.app.orientdb.odb2Klassen.DfdDiagram;
import dik.adp.app.orientdb.odb2Klassen.FxDFlow;
import dik.adp.app.orientdb.odb2Klassen.FxDfdElement;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author gu35nxt
 */
public class odb2DAO {

    public ObservableList<DfdDiagram> queryDfdDiagram(ObservableList<DfdDiagram> obsListOfDfds) { //maybe static
        // AT THE BEGINNING
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        // EVERY TIME YOU NEED A GRAPH INSTANCE
        OrientGraph graph = factory.getTx();
        try {
            for (Vertex v : (Iterable<Vertex>) graph.command(
                    new OCommandSQL(
                            "SELECT FROM DfdDiagram")).execute()) {
                System.out.println("Name: " + v + " " + v.getProperty("name"));
                DfdDiagram dV = new DfdDiagram(v.getProperty("name"));
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
            Vertex v = graph.addVertex("class:DfdDiagram");
            v.setProperty("name", newDfd);
            graph.commit();
        } catch (Exception e) {
            graph.rollback();
            graph.shutdown();
        }
    }

    public ObservableList<FxDfdElement> queryDfdElements(ObservableList<FxDfdElement> listDfdElemente, DfdDiagram selectedDiagram) {
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        OrientGraph graph = factory.getTx();
        try {
            for (Vertex v : (Iterable<Vertex>) graph.command(
                    new OCommandSQL(
                            "SELECT FROM DfdElement WHERE " + "diagram = '" + selectedDiagram.getName() + "'"
                    )).execute()) {
                System.out.println("Key: " + v + " " + v.getProperty("key"));
                FxDfdElement vFx = vertexToFxDfdElement(v);
                listDfdElemente.add(vFx);
            }
        } finally {
            graph.shutdown();
        }
        //sortiere Ergebnis nach name Stichowort "Comparator"
        Collections.sort(listDfdElemente, (a, b) -> a.getKey().compareToIgnoreCase(b.getKey()));
        return listDfdElemente;
    }

    private FxDfdElement vertexToFxDfdElement(Vertex v) {
        FxDfdElement vFx = new FxDfdElement(v.getProperty("key"), v.getProperty("type"), v.getProperty("name"), v.getProperty("diagram"), "");
        //den Elementen die Trustboundary hinzufügen
        for (Vertex b : (Iterable<Vertex>) v.getVertices(Direction.OUT, "inBoundary")) {
            vFx.setBoundary(b.getProperty("key"));
        }
        return vFx;
    }

    public void addNewDfdElementToDb(FxDfdElement newDfdElement) {
        // AT THE BEGINNING
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        // EVERY TIME YOU NEED A GRAPH INSTANCE
        OrientGraph graph = factory.getTx();
        try {
            Vertex v = graph.addVertex("class:DfdElement");
            v.setProperty("key", newDfdElement.getKey());
            v.setProperty("type", newDfdElement.getType());
            v.setProperty("name", newDfdElement.getName());
            v.setProperty("diagram", newDfdElement.getDiagram());
            graph.commit();
        } catch (Exception e) {
            graph.rollback();
            graph.shutdown();
        }
    }

    public void deleteDfdElement(FxDfdElement selectedDfdElement) {
        // AT THE BEGINNING
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        // EVERY TIME YOU NEED A GRAPH INSTANCE
        OrientGraph graph = factory.getTx();
        try {
//            for (Vertex v : graph.getVertices("DfdElement.key", selectedDfdElement.getKey()   )) {
//                System.out.println("Delete vertex: " + v);
//                graph.removeVertex(v);
//            }
            for (Vertex v : (Iterable<Vertex>) graph.command(
                    new OCommandSQL(
                            "SELECT FROM DfdElement WHERE diagram = '" + selectedDfdElement.getDiagram() + "' "
                            + "and key = '" + selectedDfdElement.getKey() + "'"
                    )).execute()) {
                System.out.println("Delete vertex: " + v);
                graph.removeVertex(v);
            }
            graph.commit();
        } catch (Exception e) {
            graph.rollback();
        }
    }

    public void updateDfdElement(FxDfdElement selectedDfdElement, FxDfdElement editedDfdElement) {
        // AT THE BEGINNING
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        // EVERY TIME YOU NEED A GRAPH INSTANCE
        OrientGraph graph = factory.getTx();
        try {
//            for (Vertex v : graph.getVertices("DfdElement.key", selectedDfdElement.getKey()   )) {
//                System.out.println("Delete vertex: " + v);
//                graph.removeVertex(v);
//            }
            for (Vertex v : (Iterable<Vertex>) graph.command(
                    new OCommandSQL(
                            "SELECT FROM DfdElement WHERE diagram = '" + selectedDfdElement.getDiagram()
                            + "' and key = '" + selectedDfdElement.getKey() + "'"
                    )).execute()) {
                System.out.println("Edit vertex: " + v);
                //                graph.removeVertex(v);
                v.setProperty("key", editedDfdElement.getKey());
                v.setProperty("type", editedDfdElement.getType());
                v.setProperty("name", editedDfdElement.getName());
                System.out.println("Edit vertex nach edit: " + v);
            }
            graph.commit();
        } catch (Exception e) {
            graph.rollback();
        }
    }

    public void addDFlowToDB(FxDFlow newDFlow) {
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        OrientGraph graph = factory.getTx();
        try {
            Vertex outVertex = null;
            Vertex inVertex = null;
            for (Vertex v : graph.getVertices("DfdElement.key", newDFlow.getFrom())) {
                outVertex = v;
            }
            for (Vertex v : graph.getVertices("DfdElement.key", newDFlow.getTo())) {
                inVertex = v;
            }
            Edge e;
            if (outVertex != null & inVertex != null) {
                e = graph.addEdge(this, outVertex, inVertex, "DFlow");
                e.setProperty("key", newDFlow.getKey());
                e.setProperty("name", newDFlow.getName());
                e.setProperty("diagram", newDFlow.getDiagram());
            }
            graph.commit();
        } catch (Exception e) {
            graph.rollback();
            graph.shutdown();
        }
    }

    public ObservableList<FxDFlow> queryFxDFlows(ObservableList<FxDFlow> listFxDFlow, DfdDiagram selectedDiagram) {
        // AT THE BEGINNING
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        // EVERY TIME YOU NEED A GRAPH INSTANCE
        OrientGraph graph = factory.getTx();
        try {
            for (Edge e : (Iterable<Edge>) graph.command(
                    new OCommandSQL(
                            "SELECT FROM DFlow WHERE " + "diagram = '" + selectedDiagram.getName() + "'"
                    )).execute()) {
                System.out.println("Key: " + e + " " + e.getProperty("key"));
                FxDFlow eE = edgeToFxDFlow(e);
                listFxDFlow.add(eE);
            }
        } finally {
            graph.shutdown();
        }
        //sortiere Ergebnis nach name Stichowort "Comparator"
        Collections.sort(listFxDFlow, (a, b) -> a.getKey().compareToIgnoreCase(b.getKey()));
        return listFxDFlow;
    }

    private FxDFlow edgeToFxDFlow(Edge e) throws IllegalArgumentException {
        FxDFlow eE = new FxDFlow(e.getProperty("key"), e.getProperty("name"), e.getProperty("diagram"), null, null);
        Vertex v;
        v = e.getVertex(Direction.OUT);
        eE.setFrom(v.getProperty("key"));
        v = e.getVertex(Direction.IN);
        eE.setTo(v.getProperty("key"));
        return eE;
    }

    public void deleteDFlow(FxDFlow selectedDFlow) {
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        OrientGraph graph = factory.getTx();
        try {
            for (Edge e : (Iterable<Edge>) graph.command(
                    new OCommandSQL(
                            "SELECT FROM DFlow WHERE diagram = '" + selectedDFlow.getDiagram() + "' "
                            + "and key = '" + selectedDFlow.getKey() + "'"
                    )).execute()) {
                System.out.println("Delete vertex: " + e);
                graph.removeEdge(e);
            }

//            for (Edge e : (Iterable<Edge>) graph.getEdges("DFlow.key", selectedDFlow.getKey())) {
//                System.out.println("Delete edge: " + e);
//                graph.removeEdge(e);
//            }
            graph.commit();
        } catch (Exception e) {
            graph.rollback();
        }
    }

    public void updateDFlow(FxDFlow selectedDFlow, FxDFlow editedDFlow) {
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        OrientGraph graph = factory.getTx();
        try {
            for (Edge e : (Iterable<Edge>) graph.command(
                    new OCommandSQL(
                            "SELECT FROM DFlow WHERE diagram = '" + selectedDFlow.getDiagram() + "' "
                            + "and key = '" + selectedDFlow.getKey() + "'"
                    )).execute()) {
                System.out.println("Edit edge: " + e);
                e.setProperty("name", editedDFlow.getName());
                System.out.println("Edit edge nach edit: " + e);
            }
            graph.commit();
        } catch (Exception e) {
            graph.rollback();
        }
    }

    public ObservableList<String> queryTrustBoundaries(FxDfdElement trustBoundary) {
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        OrientGraph graph = factory.getTx();
        ObservableList<String> listBoundary = FXCollections.<String>observableArrayList();
        try {
            for (Vertex v : (Iterable<Vertex>) graph.command(
                    new OCommandSQL(
                            "SELECT FROM DfdElement WHERE " + "diagram = '" + trustBoundary.getDiagram() + "' "
                            + "and type ='" + trustBoundary.getType() + "'"
                    )).execute()) {
                listBoundary.add(v.getProperty("key"));
                System.out.println(listBoundary);
            }
        } finally {
            graph.shutdown();
        }
        //sortiere Ergebnis nach name Stichowort "Comparator"
        Collections.sort(listBoundary, (a, b) -> a.compareToIgnoreCase(b));
        return listBoundary;
    }

}
