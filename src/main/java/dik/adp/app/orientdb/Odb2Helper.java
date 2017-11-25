/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.orientdb;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import dik.adp.app.orientdb.odb2Klassen.FxDFlow;
import dik.adp.app.orientdb.odb2Klassen.FxDfdElement;

/**
 *
 * @author gu35nxt
 */
public class Odb2Helper {

    public FxDfdElement vertexToFxDfdElement(Vertex v) {
        FxDfdElement vFx = new FxDfdElement(
                v.getId().toString(), 
                v.getProperty("key"), 
                v.getProperty("type"), 
                v.getProperty("name"), 
                v.getProperty("diagram"));
        //den Elementen die Trustboundary hinzufügen
        for (Vertex b : (Iterable<Vertex>) v.getVertices(Direction.OUT, "inBoundary")) {
            vFx.setBoundary(b.getProperty("key"));
        }
        return vFx;
    }

    public FxDFlow vertexToFxDFlow(Vertex v) throws IllegalArgumentException {
        FxDFlow newFxDFlow = new FxDFlow(v.getProperty("key"), v.getProperty("name"), v.getProperty("diagram"), null, null);
        for (Vertex from : (Iterable<Vertex>) v.getVertices(Direction.IN, "dFlow")) {
            newFxDFlow.setFrom(from.getProperty("key"));
        }
        for (Vertex to : (Iterable<Vertex>) v.getVertices(Direction.OUT, "dFlow")) {
            newFxDFlow.setTo(to.getProperty("key"));
        }
        return newFxDFlow;
    }

}
