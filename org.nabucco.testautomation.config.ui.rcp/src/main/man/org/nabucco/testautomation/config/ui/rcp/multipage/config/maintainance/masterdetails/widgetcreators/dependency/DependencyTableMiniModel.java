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

import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Order;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.detail.model.MiniViewModel;
import org.nabucco.framework.plugin.base.model.ViewModel;
import org.nabucco.testautomation.config.facade.datatype.Dependency;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;


/**
 * DependencyTableMiniModel
 * 
 * @author Marco Sussek, PRODYNA AG
 */
public class DependencyTableMiniModel extends MiniViewModel {

	private List<Dependency> dependencyList;

	public static final String PROPERTY_DEPENDS_ON_LIST = "dependencyList";

	/**
	 * Creates a new {@link DependencyTableMiniModel} instance.
	 * 
	 * @param externalViewModel
	 *            the external view model
	 * @param datatype
	 *            the parent datatype
	 */
	public DependencyTableMiniModel(ViewModel externalViewModel,
			TestConfigElement datatype) {
		super(externalViewModel, datatype);

		if (datatype == null) {
			throw new IllegalArgumentException(
					"Cannot create DependencyTableMiniModel for TestConfigElement [null].");
		}

		this.dependencyList = datatype.getDependencyList();
		super.setInitialized();
	}

	@Override
	public void updateProperty(String propertyName, Object oldValue,
			Object newValue) {
		this.resetOrderIndex();

		super.updateProperty(propertyName, oldValue, newValue);
	}

	/**
	 * Resets the order index of each test config element container appropriate
	 * to its order in the dependencyList.
	 */
	private void resetOrderIndex() {
		for (int index = 0; index < this.dependencyList.size(); index++) {
			Dependency container = this.dependencyList
					.get(index);
			container.setOrderIndex(index);
		}
	}

	/**
	 * Getter for the test config element set.
	 * 
	 * @return the test config element set
	 */
	public List<Dependency> getDependencyList() {
		return Collections.unmodifiableList(dependencyList);
	}

	/**
	 * Remove an element from the test config element list.
	 * 
	 * @param container
	 */
	public void addElement(Dependency container) {
		Object oldValue = dependencyList.hashCode();

		if (container != null) {
			dependencyList.add(container);
		}

		Object newValue = dependencyList.hashCode();

		this.updateProperty(PROPERTY_DEPENDS_ON_LIST, oldValue, newValue);
	}

	/**
	 * Add a test config element to the model.
	 * 
	 * @param container
	 *            the test config element to add
	 */
	public void removeElement(Dependency container) {
		Object oldValue = dependencyList.hashCode();

		// decrease order index of hind elements
		if (dependencyList.contains(container)) {
			List<Dependency> subList = dependencyList
					.subList(dependencyList.indexOf(container),
							dependencyList.size());
			for (Dependency testConfigElementContainer : subList) {
				testConfigElementContainer
						.setOrderIndex(testConfigElementContainer
								.getOrderIndex().getValue() - 1);
			}
		}
		if (container != null) {
			dependencyList.remove(container);
		}

		Object newValue = dependencyList.hashCode();

		this.updateProperty(PROPERTY_DEPENDS_ON_LIST, oldValue, newValue);
	}

	/**
	 * Pushs the element up.
	 * 
	 * @param container
	 *            the test config element to push
	 */
	public void pushUp(Dependency container) {
		Object oldValue = dependencyList.hashCode();

		if (container != null) {

			Order order = container.getOrderIndex();

			if (order == null || order.getValue() == null) {
				Activator.getDefault().logError(
						"Cannot push up TestScript with index " + order + ".");
				return;
			}

			if (order.getValue() < 1) {
				Activator.getDefault().logError(
						"Cannot push up TestScript with index " + order + ".");
				return;
			}

			int oldIndex = order.getValue();
			int newIndex = oldIndex - 1;

			Dependency oldElement = dependencyList
					.get(newIndex);
			dependencyList.set(newIndex, container);
			container.setOrderIndex(newIndex);
			if(container.getDatatypeState() == DatatypeState.PERSISTENT){
				container.setDatatypeState(DatatypeState.MODIFIED);
			}
			dependencyList.set(oldIndex, oldElement);
			oldElement.setOrderIndex(oldIndex);
			if(oldElement.getDatatypeState() == DatatypeState.PERSISTENT){
				oldElement.setDatatypeState(DatatypeState.MODIFIED);
			}
		}

		Object newValue = dependencyList.hashCode();

		this.updateProperty(PROPERTY_DEPENDS_ON_LIST, oldValue, newValue);
	}

	/**
	 * Pushs the element down.
	 * 
	 * @param container
	 *            the test config element to push
	 */
	public void pullDown(Dependency container) {
		Object oldValue = dependencyList.hashCode();

		if (container != null) {

			Order order = container.getOrderIndex();

			if (order == null || order.getValue() == null) {
				Activator.getDefault()
						.logError(
								"Cannot pull down TestScript with index "
										+ order + ".");
				return;
			}

			if (order.getValue() >= dependencyList.size() - 1) {
				Activator.getDefault()
						.logError(
								"Cannot pull down TestScript with index "
										+ order + ".");
				return;
			}

			int oldIndex = order.getValue();
			int newIndex = oldIndex + 1;

			Dependency oldElement = dependencyList
					.get(newIndex);
			dependencyList.set(newIndex, container);
			container.setOrderIndex(newIndex);
			if(container.getDatatypeState() == DatatypeState.PERSISTENT){
				container.setDatatypeState(DatatypeState.MODIFIED);
			}
			dependencyList.set(oldIndex, oldElement);
			oldElement.setOrderIndex(oldIndex);
			if(oldElement.getDatatypeState() == DatatypeState.PERSISTENT){
				oldElement.setDatatypeState(DatatypeState.MODIFIED);
			}
		}

		Object newValue = dependencyList.hashCode();

		this.updateProperty(PROPERTY_DEPENDS_ON_LIST, oldValue, newValue);
	}

}
