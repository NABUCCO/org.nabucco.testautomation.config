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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintainance.masterdetails.widgetcreators.dependency;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.nabucco.testautomation.config.facade.datatype.Dependency;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;


/**
 * DependencyTableLabelProvider
 * 
 * @author Marco Sussek, PRODYNA AG
 */
public class DependencyTableNameColumnLabelProvider implements ILabelProvider {

	@Override
	public Image getImage(Object arg0) {
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof Dependency) {
			return labelConfigElementContainer((Dependency) element);
		} else if (element instanceof TestConfigElement) {
			return labelTestConfigElement((TestConfigElement) element);
		}
		return "";
	}

    /**
     * Create label for a test config element container.
     * 
     * @param container
     *            the config element to label
     * 
     * @return the label
     */
	private String labelConfigElementContainer(
			Dependency container) {
		if (container.getElement() != null) {
			return labelTestConfigElement(container.getElement());
		}
		return "";
	}

    /**
     * Create label for a test config element.
     * 
     * @param configElement
     *            the config element to label
     * 
     * @return the label
     */
	private String labelTestConfigElement(TestConfigElement configElement) {
		if (configElement.getName() != null
				&& configElement.getName().getValue() != null) {
			return configElement.getName().getValue();
		}
		return "";
	}

	@Override
	public void addListener(ILabelProviderListener arg0) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
	}

}
