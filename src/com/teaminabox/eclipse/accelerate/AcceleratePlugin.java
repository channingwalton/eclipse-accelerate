package com.teaminabox.eclipse.accelerate;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public final class AcceleratePlugin extends AbstractUIPlugin {
    private static AcceleratePlugin plugin;
    private ResourceBundle resourceBundle;

    public AcceleratePlugin(IPluginDescriptor descriptor) {
        super(descriptor);
        plugin = this;
        initialiseResources();
        MenuInfo.initialiseListeners();
    }

    private void initialiseResources() {
        try {
            resourceBundle = ResourceBundle.getBundle("com.teaminabox.eclipse.accelerate.AcceleratePluginResources");
        } catch (MissingResourceException x) {
            resourceBundle = null;
        }
    }

    public static AcceleratePlugin getDefault() {
        return plugin;
    }

    public static IWorkspace getWorkspace() {
        return ResourcesPlugin.getWorkspace();
    }

    /**
     * Returns the string from the plugin's resource bundle, or 'key' if not
     * found.
     */
    public static String getResourceString(String key) {
        ResourceBundle bundle = AcceleratePlugin.getDefault().getResourceBundle();
        try {
            return (bundle != null ? bundle.getString(key) : key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
