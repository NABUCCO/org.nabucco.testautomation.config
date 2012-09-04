/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.testautomation.config.ui.rcp.browser.config;

import org.nabucco.framework.plugin.base.model.browser.BrowserElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.browser.config.TestConfigurationEditViewBrowserElement;
import org.nabucco.testautomation.config.ui.rcp.browser.config.TestConfigurationListViewBrowserElement;
import org.nabucco.testautomation.config.ui.rcp.browser.config.TestConfigurationListViewBrowserElementHandler;
import org.nabucco.testautomation.config.ui.rcp.list.config.model.TestConfigurationListViewModel;

/**
 * TestConfigurationListViewBrowserHandlerImpl
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class TestConfigurationListViewBrowserHandlerImpl implements
        TestConfigurationListViewBrowserElementHandler {

    @Override
    public void createChildren(TestConfigurationListViewModel viewModel,
            TestConfigurationListViewBrowserElement element) {
        TestConfiguration[] elements = viewModel.getElements();
        for (TestConfiguration testConfiguration : elements) {
            element.addBrowserElement(new TestConfigurationEditViewBrowserElement(testConfiguration));
        }
    }

    @Override
    public void removeChild(BrowserElement toBeRemoved,
            TestConfigurationListViewBrowserElement element) {
        // do nothing
    }

}
