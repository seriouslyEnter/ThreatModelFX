/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.sharedcommunicationmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author gu35nxt
 */
public class ViewState {

    private final BooleanProperty atShowing = new SimpleBooleanProperty(); //final: is this case means it always refer to the same object but it still can change the value

    public BooleanProperty atShowingProperty() {
        return atShowing;
    }

    public final boolean isAtShowing() {
        return atShowingProperty().get();
    }

    public final void setAtShowing(boolean atShowing) {
        atShowingProperty().set(atShowing);
    }
}
