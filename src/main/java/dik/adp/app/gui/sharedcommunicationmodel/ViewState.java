/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.sharedcommunicationmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author gu35nxt
 */
public class ViewState {

//    private final BooleanProperty atShowing = new SimpleBooleanProperty(); //final: is this case means it always refer to the same object but it still can change the value
//
//    
//    
//    
//    public BooleanProperty atShowingProperty() {
//        return atShowing;
//    }
//
//    public final boolean isAtShowing() {
//        return atShowingProperty().get();
//    }
//
//    public final void setAtShowing(boolean atShowing) {
//        atShowingProperty().set(atShowing);
//    }

    //------------------------------------Main-----------------------------------
    //final: is this case means it always refer to the same object but it still can change the value
    private final StringProperty mainShow = new SimpleStringProperty();

    public StringProperty mainShowingProperty() {
        return mainShow;
    }

    public final String isMainShowing() {
        return mainShowingProperty().get();
    }

    public final void setMainShowing(String buttonID) {
        mainShowingProperty().set(buttonID);
    }
    //--------------------------------------------------------------------------

}
