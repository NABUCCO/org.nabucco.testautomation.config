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
package org.nabucco.testautomation.config.ui.rcp.command.config;

import java.util.List;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.command.AbstractOpenEditViewHandler;
import org.nabucco.framework.plugin.base.model.browser.BrowserElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.browser.config.TestConfigurationEditViewBrowserElement;
import org.nabucco.testautomation.config.ui.rcp.command.config.ReadTestConfigHandler;
import org.nabucco.testautomation.config.ui.rcp.edit.config.model.TestConfigurationEditViewBusinessModel;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.TestConfigurationMaintenanceMultiPageEditView;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.model.TestConfigurationMaintenanceMultiPageEditViewModel;


/**
 * ReadTestConfigHandlerImpl
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class ReadTestConfigHandlerImpl
        extends
        AbstractOpenEditViewHandler<TestConfigurationMaintenanceMultiPageEditViewModel, TestConfigurationMaintenanceMultiPageEditView>
        implements ReadTestConfigHandler {

    @Override
    public void readTestConfig() {
        run();
    }

    @Override
    public void run() {
        super.openView(getEditViewId());
    }

    @Override
    protected String getEditViewId() {
        return TestConfigurationMaintenanceMultiPageEditView.ID;
    }

    @Override
    protected void updateModel(TestConfigurationMaintenanceMultiPageEditViewModel model) throws ClientException {
        TestConfiguration selection = getSelection();
        if (selection != null) {
            final TestConfigurationEditViewBusinessModel businessModel = new TestConfigurationEditViewBusinessModel();
            selection = businessModel.readTestConfiguration(selection);
            model.setTestConfiguration(selection);
        }

    }

    private TestConfiguration getSelection() {
        TestConfiguration result = null;
        // get browser view
        final List<BrowserElement> selected = Activator.getDefault().getModel().getBrowserModel()
                .getSelected();
        final BrowserElement firstElement = selected.get(0);
        result = ((TestConfigurationEditViewBrowserElement) firstElement).getViewModel()
                .getTestConfiguration();

        return result;
    }
}
