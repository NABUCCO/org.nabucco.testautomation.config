/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.browser.config;

import java.util.List;
import org.nabucco.framework.base.facade.component.injector.NabuccoInjectionReciever;
import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.model.browser.BrowserElement;
import org.nabucco.framework.plugin.base.model.browser.BrowserListElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.list.config.model.TestConfigurationListViewModel;

/**
 * TestConfigurationListViewBrowserElement
 *
 * @author Undefined
 */
public class TestConfigurationListViewBrowserElement extends
        BrowserListElement<TestConfigurationListViewModel> implements NabuccoInjectionReciever {

    private TestConfigurationListViewBrowserElementHandler listViewBrowserElementHandler;

    /**
     * Constructs a new TestConfigurationListViewBrowserElement instance.
     *
     * @param datatypeList the List<TestConfiguration>.
     */
    public TestConfigurationListViewBrowserElement(final List<TestConfiguration> datatypeList) {
        this(datatypeList.toArray(new TestConfiguration[datatypeList.size()]));
    }

    /**
     * Constructs a new TestConfigurationListViewBrowserElement instance.
     *
     * @param datatypeArray the TestConfiguration[].
     */
    public TestConfigurationListViewBrowserElement(final TestConfiguration[] datatypeArray) {
        super();
        NabuccoInjector instance = NabuccoInjector
                .getInstance(TestConfigurationListViewBrowserElement.class);
        listViewBrowserElementHandler = instance
                .inject(TestConfigurationListViewBrowserElementHandler.class);
        viewModel = new TestConfigurationListViewModel();
        viewModel.setElements(datatypeArray);
    }

    @Override
    protected void createChildren() {
        this.clearChildren();
        listViewBrowserElementHandler.createChildren(viewModel, this);
    }

    @Override
    public void removeBrowserElement(final BrowserElement element) {
        super.removeBrowserElement(element);
        listViewBrowserElementHandler.removeChild(element, this);
    }
}
