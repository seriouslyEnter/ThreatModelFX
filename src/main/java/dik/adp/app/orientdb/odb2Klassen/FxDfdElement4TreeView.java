/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.orientdb.odb2Klassen;

import javafx.scene.control.TreeItem;

/**
 *
 * @author gu35nxt
 */
//DFD Element als ein JavaFX Bean um in ObservableList zu benutzen
public class FxDfdElement4TreeView {

    private TreeItem<FxDfdElement> treeItem;
    private FxDfdElement fxDfdElement;

    public FxDfdElement4TreeView(FxDfdElement fxDfdElement) {
        this.treeItem = new TreeItem(fxDfdElement.getKey() + ": " + fxDfdElement.getName());
        this.fxDfdElement = fxDfdElement;
    }

    public TreeItem getTreeItem() {
        return treeItem;
    }

    public void setTreeItem(TreeItem treeItem) {
        this.treeItem = treeItem;
    }

    public FxDfdElement getFxDfdElement() {
        return fxDfdElement;
    }

    public void setFxDfdElement(FxDfdElement fxDfdElement) {
        this.fxDfdElement = fxDfdElement;
    }

}
