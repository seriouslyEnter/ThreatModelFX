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
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import dik.adp.app.orientdb.odb2Klassen.Dread;
import dik.adp.app.orientdb.odb2Klassen.FxAT;
import dik.adp.app.orientdb.odb2Klassen.FxBa;
import dik.adp.app.orientdb.odb2Klassen.FxDfdElement;
import dik.adp.app.orientdb.odb2Klassen.FxDfdElement4TreeView;
import dik.adp.app.orientdb.odb2Klassen.FxRa;
import dik.adp.app.orientdb.odb2Klassen.Rating;
import java.util.Map;
import java.util.TreeMap;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class Odb2Ra {

    @Inject
    Odb2Helper odb2helper;

    private OrientGraphFactory ogf() {
        OrientGraphFactory factory = new OrientGraphFactory(
                "remote:localhost/ThreatModelDB", "admin", "admin"
        ).setupPool(1, 10);
        return factory;
    }

    public Map<String, FxDfdElement4TreeView> queryDfdElements(String selectedDiagram) {
        OrientGraphNoTx graph = ogf().getNoTx();
//        FxDfdElement4TreeView treeViewElements = new FxDfdElement4TreeView();
        Map<String, FxDfdElement4TreeView> treeViewMap = new TreeMap<>();
        String key;
        FxDfdElement4TreeView element;
        try {
            for (Vertex el : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT expand(distinct(@rid))"
                    + " FROM"
                    + " ("
                    + " SELECT expand(out('hasAT').in('realized_by').out('targets'))"
                    + " FROM DfdDiagram"
                    + " WHERE name='" + selectedDiagram + "'"
                    + ")"
            )).execute()) {
                element = new FxDfdElement4TreeView(odb2helper.vertexToFxDfdElement(el));
                key = element.getTreeItem().toString();
                treeViewMap.put(key, element);
            }
        } catch (Exception e) {
            graph.shutdown();
        }
        return treeViewMap;
    }

    public FxRa queryBa(FxDfdElement fxDfdElement, FxAT selectedAt) {
        OrientGraphNoTx graph = ogf().getNoTx();
        FxRa fxRa = new FxRa(fxDfdElement);
        try {
            for (Vertex ba : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT"
                    + " FROM"
                    + " ("
                    + "SELECT expand(in('targets'))"
                    + " FROM " + fxDfdElement.getRid()
                    + " )"
                    + " WHERE out('realized_by').name = '" + selectedAt.getName() + "'"
                    + " AND out('realized_by').in('hasAT').name = '" + fxDfdElement.getDiagram() + "'"
            )).execute()) {
                fxRa.addThreat(toFxBa(ba));
            }
        } catch (Exception e) {
            graph.shutdown();
        }
        return fxRa;
    }

    private FxBa toFxBa(Vertex ba) {
        FxBa fxBa = new FxBa(ba.getId().toString(), ba.getProperty("threat").toString());
        return fxBa;
    }

    public void createDREAD(FxBa fxBa, Integer iteration, Rating rating, Dread dread) {
        OrientGraph graph = ogf().getTx();
        Boolean dreadGefunden = false;
        try {
            //finde die Iteration and die Dread angehängt wird
//            System.out.println(fxBa.getRid());
            for (Vertex vDread : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT expand(out('metric'))"
                    + " FROM "
                    + " ("
                    + " SELECT expand(out('has_iteration')[iteration=" + iteration + "])"
                    + " FROM " + fxBa.getRid()
                    + " )"
            )).execute()) {
                vDread.setProperty(dread.name(), rating.toNumber());

                dreadGefunden = true;
            }
            if (dreadGefunden == false) {
                for (Vertex vIteration : (Iterable<Vertex>) graph.command(new OCommandSQL(
                        "SELECT expand(out('has_iteration')[iteration=" + iteration + "])"
                        + " FROM " + fxBa.getRid()
                )).execute()) {

                    Vertex vdread = graph.addVertex("class:DREAD");
                    vdread.setProperty(dread.name(), rating.toNumber());
                    Edge metric = graph.addEdge(null, vIteration, vdread, "metric");
                }
            }
            graph.commit();
        } catch (Exception e) {
            System.out.println(e);
            graph.rollback();
            graph.shutdown();
        }
//        Collections.sort(result, (a, b) -> a.getDfdElement().getKey().compareToIgnoreCase(b.getDfdElement().getKey()));
    }

    public Rating queryMetric(FxBa fxBa, Integer iteration, Dread dread) {
        OrientGraph graph = ogf().getTx();
        Rating rating = null;
        try {
            //finde die Iteration and die Dread angehängt wird
            System.out.println(fxBa.getRid());
            for (Vertex v : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT expand(out('metric'))"
                    + " FROM"
                    + " ("
                    //                    + " SELECT expand(out('has_iteration')[iteration=" + iteration + "])"
                    + " SELECT expand(out('has_iteration')[iteration=" + iteration + "])"
                    + " FROM " + fxBa.getRid()
                    + " )"
            )).execute()) {
                System.out.println(dread.name());

                //TODO: hier loop über die dread ratings um die button zu updaten
                switch (v.getProperty(dread.name()).toString()) {
                    case "3":
                        rating = Rating.HIGH;
                        break;
                    case "2":
                        rating = Rating.MEDIUM;
                        break;
                    case "1":
                        rating = Rating.LOW;
                        break;
                    default:
                        rating = Rating.NONE;
                        break;
                }
            }
        } catch (Exception e) {
            graph.rollback();
            graph.shutdown();
        }
        if (rating == null) {
            rating = Rating.NONE;
        }
        return rating;
    }

}
