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
import dik.adp.app.orientdb.odb2Klassen.FxStride;
import dik.adp.app.orientdb.odb2Klassen.Stride;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class Odb2Ba {

    @Inject
    Odb2Helper odb2helper;

    private OrientGraphFactory ogf() {
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
//        OrientGraph graph = factory.getTx();
        return factory;
    }

    public List<FxStride> queryDfdElements(FxAT at, String selectedDiagram, String elementType) {
        OrientGraph graph = ogf().getTx();
        List<FxStride> result = new ArrayList<>();
        try {
            for (Vertex v : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT "
                    + "FROM DfdElement "
                    + "WHERE diagram='" + selectedDiagram + "' "
                    + "AND type='" + elementType + "'"
            )).execute()) {
                FxStride fxs = new FxStride(odb2helper.vertexToFxDfdElement(v), at);
                //Hier nach BAs gucken
                getTheBAsToDfdElement(fxs, v, at, selectedDiagram);

                result.add(fxs);
            }
        } catch (Exception e) {
            graph.rollback();
            graph.shutdown();
        }
        
        Collections.sort(result, (a, b) -> a.getDfdElement().getKey().compareToIgnoreCase(b.getDfdElement().getKey()));
        
        return result;
    }

    public void getTheBAsToDfdElement(FxStride fxs, Vertex v, FxAT at, String selectedDiagram) {
        OrientGraph graph = ogf().getTx();
        try {
            for (Vertex ba : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT"
                    + " FROM"
                    + " ("
                    + "SELECT expand(in('targets'))"
                    + " FROM " + v.getId()
                    + ")"
                    + " WHERE out('realized_by').name='" + at.getName() + "' "
                    + "AND out('realized_by').in('hasAT').name='" + selectedDiagram + "'"
            )).execute()) {
                System.out.println(ba.toString());
                for (Stride stride : Stride.values()) {
                    if (stride.name().equals(ba.getProperty("threat"))){
                        fxs.getCbs().get(stride).setSelected(true);
                    }    
                }
            }
        } catch (Exception e) {
            graph.shutdown();
        }
    }

    public void addThreatForElement(FxStride foundFxStrideAndSetBa) {
//                OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
//        OrientGraph graph = factory.getTx();
        OrientGraph graph = ogf().getTx();
        try {
//            Vertex ba = odbGraph().addVertex("class:BA", "threat", foundFxStrideAndSetBa.getBa());
            Vertex ba = graph.addVertex("class:BA");
            ba.setProperty("threat", foundFxStrideAndSetBa.getBa().name());
            Vertex at = queryAT(foundFxStrideAndSetBa);
            Edge baat = graph.addEdge(null, ba, at, "realized_by");
            Vertex dfdElement = queryDfdElement(foundFxStrideAndSetBa);
            Edge badfd = graph.addEdge(null, ba, dfdElement, "targets");
            graph.commit();
        } catch (Exception e) {
            graph.rollback();
            graph.shutdown();
        }
    }

    public void removeThreatForElement(FxStride foundFxStrideAndSetBa) {
        OrientGraph graph = ogf().getTx();
        System.out.println(foundFxStrideAndSetBa.getBa());
        System.out.println(foundFxStrideAndSetBa.getDfdElement().getKey());
        System.out.println(foundFxStrideAndSetBa.getDfdElement().getDiagram());

        try {
            for (Vertex v : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT "
                    + "FROM BA "
                    + "WHERE threat='" + foundFxStrideAndSetBa.getBa().name() + "' "
                    + "AND out('targets').key='" + foundFxStrideAndSetBa.getDfdElement().getKey() + "' "
                    + "AND out('realized_by').in('hasAT').name='" + foundFxStrideAndSetBa.getDfdElement().getDiagram() + "'"
            )).execute()) {
                System.out.println(v.toString());
                graph.removeVertex(v);
            }
            graph.commit();
        } catch (Exception e) {
            graph.rollback();
            graph.shutdown();
        }
    }

    private Vertex queryAT(FxStride foundFxStrideAndSetBa) {
        OrientGraph graph = ogf().getTx();
        Vertex at = null;
        try {
            for (Vertex v : (Iterable<Vertex>) graph.command(new OCommandSQL(
                    "SELECT "
                    + "FROM AT "
                    + "WHERE in('hasAT').name='" + foundFxStrideAndSetBa.getAt().getDiagram() + "' "
                    + "AND name='" + foundFxStrideAndSetBa.getAt().getName() + "'"
            )).execute()) {
                at = v;
            }
        } catch (Exception e) {
            graph.rollback();
            graph.shutdown();
        }
        return at;
    }

    private Vertex queryDfdElement(FxStride foundFxStrideAndSetBa) {
        OrientGraph graph = ogf().getTx();
        Vertex dfdElement = null;
        try {
            for (Vertex v : (Iterable<Vertex>) graph.command(
                    new OCommandSQL(
                            "SELECT "
                            + "FROM DfdElement "
                            + "WHERE diagram = '" + foundFxStrideAndSetBa.getDfdElement().getDiagram() + "' "
                            + "and key = '" + foundFxStrideAndSetBa.getDfdElement().getKey() + "'"
                    )).execute()) {
                dfdElement = v;
            }
        } catch (Exception e) {
            graph.rollback();
            graph.shutdown();
        }
        return dfdElement;
    }

}
