package com.teaminabox.eclipse.accelerate;

import java.util.Iterator;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.commands.IKeySequenceBinding;

final class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
    public String getColumnText(Object obj, int index) {
        MenuInfo info = (MenuInfo) obj;
        return info.getCount() + " " + info.getText() + " " +getKeyBindings(info);
    }

    private String getKeyBindings(MenuInfo info) {
        StringBuffer text = new StringBuffer();
        Iterator bindings = info.getKeySequenceBindings().iterator();
        while (bindings.hasNext()) {
            IKeySequenceBinding binding = (IKeySequenceBinding) bindings.next();
            text.append(binding.getKeySequence().toString());
            if (bindings.hasNext()) {
                text.append(", ");
            }
        }
        return text.toString();
    }

    public Image getColumnImage(Object obj, int index) {
        return getImage(obj);
    }

}