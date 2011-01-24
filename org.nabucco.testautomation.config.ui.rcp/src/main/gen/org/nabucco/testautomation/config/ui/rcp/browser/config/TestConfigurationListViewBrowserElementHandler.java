/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.ui.rcp.browser.config;

import org.nabucco.framework.plugin.base.command.CommandHandler;
import org.nabucco.framework.plugin.base.model.browser.BrowserElement;
import org.nabucco.testautomation.config.ui.rcp.list.config.model.TestConfigurationListViewModel;

/**
 * TestConfigurationListViewBrowserElementHandler
 *
 * @author Undefined
 */
public interface TestConfigurationListViewBrowserElementHandler extends CommandHandler {

    /**
     * CreateChildren.
     *
     * @param element the TestConfigurationListViewBrowserElement.
     * @param viewModel the TestConfigurationListViewModel.
     */
    void createChildren(final TestConfigurationListViewModel viewModel,
            final TestConfigurationListViewBrowserElement element);

    /**
     * RemoveChild.
     *
     * @param element the TestConfigurationListViewBrowserElement.
     * @param toBeRemoved the BrowserElement.
     */
    void removeChild(final BrowserElement toBeRemoved,
            final TestConfigurationListViewBrowserElement element);
}
