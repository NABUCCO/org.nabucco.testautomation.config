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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.nabucco.testautomation.config.facade.datatype.Dependency;


/**
 * DependencyTableContentProvider
 * 
 * @author Marco Sussek, PRODYNA AG
 */
public class DependencyTableContentProvider implements
		IStructuredContentProvider {

	private DependencyTableMiniModel model;

	private List<Dependency> values = new ArrayList<Dependency>();

	/**
	 * Creates a new {@link DependencyTableContentProvider} instance.
	 */
	public DependencyTableContentProvider(DependencyTableMiniModel model) {
		this.model = model;
	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
	}

	@Override
	public Object[] getElements(Object element) {
		this.initValues();

		if (element instanceof String) {
			return this.values.toArray(new Object[this.values.size()]);
		}

		return new Object[0];
	}

	@Override
	public void dispose() {
		this.values = null;
	}

	/**
	 * Initialize the config element this.values.
	 */
	private void initValues() {
		this.values.clear();

		this.values.addAll(this.model.getDependencyList());
	}

}
