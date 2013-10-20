package com.teaminabox.eclipse.accelerate;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public final class AccelerateView extends ViewPart {
    private TableViewer viewer;

    public AccelerateView() {
    }

    public void createPartControl(Composite parent) {
        viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        viewer.setContentProvider(new ViewContentProvider());
        viewer.setLabelProvider(new ViewLabelProvider());
        viewer.setSorter(new NameSorter());
        viewer.setInput(ResourcesPlugin.getWorkspace());
        MenuInfo.add(this);
    }

    public void refresh() {
        viewer.refresh();
    }

    public void dispose() {
        MenuInfo.remove(this);
        super.dispose();
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus() {
        viewer.getControl().setFocus();
    }
}