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
package org.nabucco.testautomation.config.ui.rcp.command.config.execute;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;

import org.nabucco.testautomation.result.facade.datatype.TestConfigurationResult;
import org.nabucco.testautomation.result.ui.rcp.multipage.result.maintenance.TestConfigurationResultMaintenanceMultiPageEditView;
import org.nabucco.testautomation.result.ui.rcp.multipage.result.maintenance.model.TestConfigurationResultMaintenanceMultiPageEditViewModel;

/**
 * TestResultViewUpdateThread
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
final class TestResultModelUpdateThread implements Runnable {

	private TestConfigurationResultMaintenanceMultiPageEditView view;

	private TestConfigurationResult result;

	private TreeErrorExpander treeErrorExpander;

	/**
	 * Creates a new {@link TestResultModelUpdateThread} instance.
	 * 
	 * @param view
	 *            the view model to update
	 * @param result
	 *            result
	 */
	TestResultModelUpdateThread(TestConfigurationResultMaintenanceMultiPageEditView view,
			TestConfigurationResult result) {
		this.view = view;
		this.result = result;
		this.treeErrorExpander = new TreeErrorExpander();
	}

	@Override
	public void run() {
		if (this.view == null || this.view.isDisposed()) {
			Activator.getDefault().logWarning("Cannot update disposed result view.");
			return;
		}

		TestConfigurationResultMaintenanceMultiPageEditViewModel model = this.view.getModel();

		if (model == null) {
			Activator.getDefault().logWarning("Cannot update result model [null].");
			return;
		}

		model.setTestConfigurationResult(this.result);
		try {
            this.treeErrorExpander.expandTree(this.view, this.result);
        } catch (ClientException e) {
            Activator.getDefault().logWarning("Cannot expand TreePath.");
        }

	}


}
