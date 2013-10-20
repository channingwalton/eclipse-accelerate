package com.teaminabox.eclipse.accelerate;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;


final class NameSorter extends ViewerSorter {
    public int compare(Viewer viewer, Object e1, Object e2) {
        MenuInfo first = (MenuInfo) e1;
        MenuInfo second = (MenuInfo) e2;
        if (first.getCount() == second.getCount()) {
            return first.getText().compareTo(second.getText());
        }
        return compare(second.getCount(), first.getCount());
    }

    private int compare(int first, int second) {
        return (first < second ? -1 : (first == second ? 0 : 1));
    }

}