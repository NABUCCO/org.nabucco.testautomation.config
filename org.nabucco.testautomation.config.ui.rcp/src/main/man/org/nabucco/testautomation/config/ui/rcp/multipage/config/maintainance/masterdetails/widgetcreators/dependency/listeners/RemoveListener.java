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

import org.eclipse.swt.events.TypedEvent;
import org.nabucco.framework.plugin.base.component.picker.dialog.ElementPickerListener;
import org.nabucco.testautomation.config.facade.datatype.Dependency;
import org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.widgetcreators.dependency.DependencyTableMiniModel;


/**
 * RemoveListener
 * 
 * @author Marco Sussek, PRODYNA AG
 */
public class RemoveListener implements ElementPickerListener {
	
	private DependencyTableMiniModel model;

    /**
     * Creates a new {@link RemoveListener} instance.
     * 
     * @param model
     *            the model to modify
     */
	public RemoveListener(DependencyTableMiniModel model) {
		 this.model = model;
	}

	@Override
	public void elementSelected(TypedEvent event) {
		if (event.data instanceof Dependency) {
			Dependency container = (Dependency) event.data;
            this.model.removeElement(container);
        }
		
	}

}