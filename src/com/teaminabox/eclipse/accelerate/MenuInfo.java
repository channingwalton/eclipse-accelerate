package com.teaminabox.eclipse.accelerate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.commands.ICommand;

public final class MenuInfo {

    private static final HashMap menus = new HashMap();
    private static final HashSet viewers = new HashSet();

    private String text;
    private int count;
    private int accelerator;
    private String actionDefinitionId;

    static void initialiseListeners() {
        Display.getCurrent().addFilter(SWT.Arm, new Listener() {
            public void handleEvent(Event event) {
                if (event.widget != null && event.widget instanceof MenuItem && !event.widget.isDisposed()) {
                    listen((MenuItem) event.widget);
                }
            }
        });
    }

    private static void listen(final MenuItem menuItem) {
        if (!MenuInfo.contains(menuItem) && hasKeyBinding(menuItem)) {
            MenuInfo info = MenuInfo.findOrCreate(menuItem);
            menuItem.addSelectionListener(new MenuListener(menuItem));
        }
    }

    private static boolean hasKeyBinding(MenuItem menuItem) {
        if (!(menuItem.getData() instanceof ActionContributionItem)) {
            return false;
        }
       String id = ((ActionContributionItem) menuItem.getData()).getAction().getActionDefinitionId();
       if (id == null) {
           return false;
       }
       ICommand command = AcceleratePlugin.getDefault().getWorkbench().getCommandSupport().getCommandManager().getCommand(id);
       if (command != null) {
           return !command.getKeySequenceBindings().isEmpty();
       } else {
           return false;
       }
    }

    static MenuInfo[] getUsedItems() {
        HashSet used = new HashSet();
        Iterator iterator = menus.values().iterator();
        while (iterator.hasNext()) {
            MenuInfo menu = (MenuInfo) iterator.next();
            if (menu.getCount() > 0) {
                used.add(menu);
            }
        }
        return (MenuInfo[]) used.toArray(new MenuInfo[used.size()]);
    }

    static MenuInfo findOrCreate(MenuItem menuItem) {
        if (menus.containsKey(menuItem.getText())) {
            return (MenuInfo) menus.get(menuItem.getText());
        }
        MenuInfo info = new MenuInfo(menuItem);
        menus.put(menuItem.getText(), info);
        return info;
    }

    static void incrementCount(MenuItem menuItem) {
        findOrCreate(menuItem).incrementCount();
        notifyViewers();
    }

    private static void notifyViewers() {
        Iterator iterator = viewers.iterator();
        while (iterator.hasNext()) {
            AccelerateView view = (AccelerateView) iterator.next();
            view.refresh();
        }
    }

    static boolean contains(MenuItem menuItem) {
        return menus.containsKey(menuItem.getText());
    }

    static void add(AccelerateView view) {
        viewers.add(view);
    }

    static void remove(AccelerateView view) {
        viewers.remove(view);
    }

    private MenuInfo(MenuItem menuItem) {
        text = menuItem.getText();
        accelerator = menuItem.getAccelerator();
        actionDefinitionId = ((ActionContributionItem) menuItem.getData()).getAction().getActionDefinitionId();
    }

    public String getActionDefinitionId() {
        return actionDefinitionId;
    }
    
    public List getKeySequenceBindings() {
        ICommand command = AcceleratePlugin.getDefault().getWorkbench().getCommandSupport().getCommandManager().getCommand(getActionDefinitionId());
        return command.getKeySequenceBindings();
    }

    public String getText() {
        return text;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        MenuInfo info = (MenuInfo) obj;
        return getText().equals(info.getText());
    }

    public int hashCode() {
        return getText().hashCode();
    }

    public String getAccelerator() {
        return Action.convertAccelerator(accelerator);
    }
}
