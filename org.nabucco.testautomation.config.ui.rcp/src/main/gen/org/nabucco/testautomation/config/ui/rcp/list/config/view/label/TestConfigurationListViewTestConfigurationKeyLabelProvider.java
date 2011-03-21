/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.list.config.view.label;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;

/**
 * TestConfigurationListViewTestConfigurationKeyLabelProvider
 *
 * @author Undefined
 */
public class TestConfigurationListViewTestConfigurationKeyLabelProvider implements ILabelProvider {

    /** Constructs a new TestConfigurationListViewTestConfigurationKeyLabelProvider instance. */
    public TestConfigurationListViewTestConfigurationKeyLabelProvider() {
        super();
    }

    @Override
    public Image getImage(Object arg0) {
        return null;
    }

    @Override
    public String getText(Object arg0) {
        String result = "";
        if ((arg0 instanceof TestConfiguration)) {
            TestConfiguration testConfiguration = ((TestConfiguration) arg0);
            result = ((testConfiguration.getIdentificationKey() != null) ? testConfiguration
                    .getIdentificationKey().toString() : "");
        }
        return result;
    }

    @Override
    public void addListener(ILabelProviderListener arg0) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean isLabelProperty(Object arg0, String arg1) {
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener arg0) {
    }
}
