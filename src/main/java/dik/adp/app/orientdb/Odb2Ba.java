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
import dik.adp.app.orientdb.odb2Klassen.FxAT;
import dik.adp.app.orientdb.odb2Klassen.FxStride;
import dik.adp.app.orientdb.odb2Klassen.Stride;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author gu35nxt
 */
public class Odb2Ba {

    @Inject
    Odb2Helper odb2helper;

    private OrientGraph odbGraph() {
        OrientGraphFactory factory = new OrientGraphFactory("remote:localhost/ThreatModelDB", "admin", "admin").setupPool(1, 10); //ACHTUNG PASSWORT AUF GITHUB SICHTBAR
        OrientGraph graph = factory.getTx();
        return graph;
    }

    public List<FxStride> queryDfdElements(FxAT at, String selectedDiagram, String type) {
        List<FxStride> result = new ArrayList<>();
        try {
            for (Vertex v : (Iterable<Vertex>) odbGraph().command(new OCommandSQL(
                    "SELECT "
                    + "FROM DfdElement "
                    + "WHERE diagram='" + selectedDiagram + "' "
                    + "and type='" + type + "'"
            )).execute()) {
                FxStride fxs = new FxStride(odb2helper.vertexToFxDfdElement(v), at, Stride.EMPTY);
                result.add(fxs);
            }
        } catch (Exception e) {
            odbGraph().rollback();
            odbGraph().shutdown();
        }
        return result;
    }
}
