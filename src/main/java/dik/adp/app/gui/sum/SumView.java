/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dik.adp.app.gui.sum;

import com.airhacks.afterburner.views.FXMLView;

/**
 *
 * @author gu35nxt
 */
public class SumView extends FXMLView {
    
    public SumPresenter getRealPresenter() {
        return (SumPresenter) super.getPresenter();
    }
    
}
