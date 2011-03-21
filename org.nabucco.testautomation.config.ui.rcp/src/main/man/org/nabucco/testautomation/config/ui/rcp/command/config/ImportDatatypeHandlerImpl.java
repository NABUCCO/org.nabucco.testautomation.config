/*
* Copyright 2010 PRODYNA AG
*
* Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.opensource.org/licenses/eclipse-1.0.php or
* http://www.nabucco-source.org/nabucco-license.html
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.nabucco.testautomation.config.ui.rcp.command.config;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.command.NabuccoAbstractImportDatatypeCommandHandlerImpl;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.edit.config.model.TestConfigurationEditViewBusinessModel;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.TestConfigurationMaintainanceMultiplePageEditViewModel;

/**
 * ImportDatatypeHandlerImpl
 * 
 * @author Markus Jorroch, PRODYNA AG
 */
public class ImportDatatypeHandlerImpl extends NabuccoAbstractImportDatatypeCommandHandlerImpl<TestConfigurationEditViewBusinessModel, TestConfigurationMaintainanceMultiplePageEditViewModel>
										implements ImportDatatypeHandler {

	@Override
	public void importDatatype() {
		run();
	}
	
    @Override
    public String getBusinessModelId() {
        return TestConfigurationEditViewBusinessModel.ID;
    }

    @Override
    protected void importDatatype(TestConfigurationMaintainanceMultiplePageEditViewModel viewModel,
            TestConfigurationEditViewBusinessModel businessModel) throws ClientException {
        TestConfiguration response = businessModel.importDatatype(viewModel.getTestConfiguration());
        viewModel.setTestConfiguration(response);
    }
	
	

}
