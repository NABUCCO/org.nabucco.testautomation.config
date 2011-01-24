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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.widgetcreators.dependency.listeners;

import java.util.Set;

import org.eclipse.swt.events.TypedEvent;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.newpicker.dialog.PickerDialogSelectionListener;
import org.nabucco.testautomation.config.facade.datatype.Dependency;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.message.DependencyMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.ui.rcp.communication.ConfigComponentServiceDelegateFactory;
import org.nabucco.testautomation.config.ui.rcp.communication.produce.ProduceDependencyDelegate;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.widgetcreators.dependency.DependencyTableMiniModel;


/**
 * TestConfigElementTreePickerDialogListener
 * 
 * @author Marco Sussek, PRODYNA AG
 */
public class TestConfigElementTreePickerDialogListener implements
		PickerDialogSelectionListener {

	private DependencyTableMiniModel model;

	/**
	 * Creates a new {@link TestConfigElementTreePickerDialogListener} instance.
	 * 
	 * @param model
	 *            the mini model
	 */
	public TestConfigElementTreePickerDialogListener(
			DependencyTableMiniModel model) {
		if (model == null) {
			throw new IllegalArgumentException(
					"Cannot create TestConfigElementTreePickerDialogListener for model [null].");
		}
		this.model = model;
	}

	/**
	 * Adds a script to the model.
	 * 
	 * @param data
	 *            the script
	 */
	private void addTestConfigElement(TestConfigElement data) {
		try {
			ProduceDependencyDelegate produceService = ConfigComponentServiceDelegateFactory
					.getInstance().getProduceDependency();

			TestConfigElementMsg rq = new TestConfigElementMsg();
			rq.setTestConfigElement(data);

			DependencyMsg rs = produceService
					.produceDependency(rq);

			Dependency container = rs
					.getDependency();
			container.setElement(data);

			this.model.addElement(container);

		} catch (ClientException e) {
			Activator.getDefault().logError(
					"Cannot add TestConfigElement" + data.getName() + ".");
			Activator.getDefault().logError(e);
		}
	}

	@Override
	public void elementSelected(TypedEvent event) {
		if (event.data instanceof Set<?>) {
			for (Object object : (Set<?>) event.data) {
				if (object instanceof TestConfigElement) {
					this.addTestConfigElement((TestConfigElement) object);
				}
			}
		} else if (event.data instanceof TestConfigElement) {
			this.addTestConfigElement((TestConfigElement) event.data);
		}
	}

}
