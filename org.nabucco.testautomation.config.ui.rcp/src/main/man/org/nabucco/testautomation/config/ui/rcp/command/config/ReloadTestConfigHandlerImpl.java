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

import org.nabucco.framework.plugin.base.command.refresh.AbstractRefreshViewHandler;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.ui.rcp.command.config.ReloadTestConfigHandler;
import org.nabucco.testautomation.config.ui.rcp.edit.config.model.TestConfigurationEditViewBusinessModel;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.TestConfigurationMaintainanceMultiplePageEditView;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.model.TestConfigurationMaintainanceMultiplePageEditViewModel;


/**
 * ReloadTestConfigHandlerImpl
 * 
 * @author Marco Sussek, PRODYNA AG
 */
public class ReloadTestConfigHandlerImpl
		extends
		AbstractRefreshViewHandler<TestConfigurationMaintainanceMultiplePageEditViewModel, TestConfigurationMaintainanceMultiplePageEditView>
		implements ReloadTestConfigHandler {

	@Override
	public void reloadTestConfig() {
		run();
	}

	@Override
	protected String getEditViewId() {
		return TestConfigurationMaintainanceMultiplePageEditView.ID;
	}

	@Override
	protected void updateModel(
			TestConfigurationMaintainanceMultiplePageEditViewModel model) {
		if (model.getTestConfiguration() != null
				&& model.getTestConfiguration().getId() != null) {
			final TestConfiguration testConfiguration = new TestConfigurationEditViewBusinessModel()
					.readTestConfiguration(model.getTestConfiguration());
			model.setTestConfiguration(testConfiguration);
			model.setDirty(false);
		}
	}
}
