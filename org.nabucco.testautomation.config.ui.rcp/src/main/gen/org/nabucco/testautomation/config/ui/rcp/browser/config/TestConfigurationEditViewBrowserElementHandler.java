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

import org.nabucco.framework.plugin.base.command.CommandHandler;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationMaintenanceMultiPageEditViewModel;


/**
 * TestConfigurationEditViewBrowserElementHandler
 *
 * @author Undefined
 */
public interface TestConfigurationEditViewBrowserElementHandler extends CommandHandler {

    /**
     * LoadFull.
     *
     * @param testConfiguration the TestConfigurationEditViewModel.
     * @return the TestConfigurationEditViewModel.
     */
	TestConfigurationMaintenanceMultiPageEditViewModel loadFull(final TestConfigurationMaintenanceMultiPageEditViewModel testConfiguration);

    /**
     * CreateChildren.
     *
     * @param element the TestConfigurationEditViewBrowserElement.
     * @param viewModel the TestConfigurationEditViewModel.
     */
    void createChildren(final TestConfigurationMaintenanceMultiPageEditViewModel viewModel,
            final TestConfigurationEditViewBrowserElement element);
}
