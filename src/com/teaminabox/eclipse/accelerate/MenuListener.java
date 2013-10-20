package com.teaminabox.eclipse.accelerate;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MenuItem;


public final class MenuListener implements SelectionListener, DisposeListener {
    
    private MenuItem menuItem;
    
    MenuListener(MenuItem menuItem) {
        this.menuItem = menuItem;
        menuItem.addDisposeListener(this);
    }

    public void widgetSelected(SelectionEvent e) {
        MenuInfo.incrementCount(menuItem);
    }
    public void widgetDefaultSelected(SelectionEvent e) {
        MenuInfo.incrementCount(menuItem);
    }

    public void widgetDisposed(DisposeEvent e) {
        menuItem.removeSelectionListener(this);
        menuItem.removeDisposeListener(this);
    }

}
