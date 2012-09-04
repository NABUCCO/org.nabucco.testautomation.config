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

import java.io.Serializable;
import java.util.Map;

import org.nabucco.framework.base.facade.component.injector.NabuccoInjectionReciever;
import org.nabucco.framework.base.facade.component.injector.NabuccoInjector;
import org.nabucco.framework.plugin.base.model.browser.DatatypeBrowserElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationMaintenanceMultiPageEditViewModel;


/**
 * TestConfigurationEditViewBrowserElement
 *
 * @author Undefined
 */
public class TestConfigurationEditViewBrowserElement extends DatatypeBrowserElement implements
        NabuccoInjectionReciever {

    private TestConfigurationEditViewBrowserElementHandler browserHandler;

    private TestConfigurationMaintenanceMultiPageEditViewModel viewModel;

    /**
     * Constructs a new TestConfigurationEditViewBrowserElement instance.
     *
     * @param datatype the TestConfiguration.
     */
    public TestConfigurationEditViewBrowserElement(final TestConfiguration datatype) {
        super();
        NabuccoInjector instance = NabuccoInjector
                .getInstance(TestConfigurationEditViewBrowserElement.class);
        browserHandler = instance.inject(TestConfigurationEditViewBrowserElementHandler.class);
        viewModel = new TestConfigurationMaintenanceMultiPageEditViewModel(datatype);
    }

    @Override
    protected void createChildren() {
        this.clearChildren();
        browserHandler.createChildren(viewModel, this);
    }

    @Override
    protected void fillDatatype() {
        viewModel = browserHandler.loadFull(viewModel);
    }

    /**
     * Getter for the ViewModel.
     *
     * @return the TestConfigurationEditViewModel.
     */
    public TestConfigurationMaintenanceMultiPageEditViewModel getViewModel() {
        return this.viewModel;
    }

    @Override
    public Map<String, Serializable> getValues() {
        return this.viewModel.getValues();
    }

    /**
     * Setter for the ViewModel.
     *
     * @param viewModel the TestConfigurationEditViewModel.
     */
    public void setViewModel(TestConfigurationMaintenanceMultiPageEditViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
