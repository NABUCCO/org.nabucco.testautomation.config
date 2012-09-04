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
package org.nabucco.testautomation.config.ui.rcp.multipage.config.maintenance.masterdetails.widgetcreators.script;

import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Order;
import org.nabucco.framework.plugin.base.Activator;
import org.nabucco.framework.plugin.base.component.multipage.masterdetail.detail.model.MiniViewModel;
import org.nabucco.framework.plugin.base.model.ViewModel;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;


/**
 * TestScriptContainerMiniModel
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class TestScriptTableMiniModel extends MiniViewModel {

    private List<TestScriptContainer> testScriptList;

    public static final String PROPERTY_TEST_SCRIPT_LIST = "testScriptList";

    /**
     * Creates a new {@link TestScriptContainerTableMiniModel} instance.
     * 
     * @param externalViewModel
     *            the external view model
     * @param datatype
     *            the parent datatype
     */
    public TestScriptTableMiniModel(ViewModel externalViewModel, TestConfigElement datatype) {
        super(externalViewModel, datatype);

        if (datatype == null) {
            throw new IllegalArgumentException(
                    "Cannot create TestScriptContainerTableMiniModel for TestConfigElement [null].");
        }

        this.testScriptList = datatype.getTestScriptList();
        super.setInitialized();
    }

    @Override
    public void updateProperty(String propertyName, Object oldValue, Object newValue) {
        this.resetOrderIndex();

        super.updateProperty(propertyName, oldValue, newValue);
    }

    /**
     * Resets the order index of each test script container appropriate to its order in the
     * testScriptList.
     */
    private void resetOrderIndex() {
        for (int index = 0; index < this.testScriptList.size(); index++) {
            TestScriptContainer container = this.testScriptList.get(index);
            container.setOrderIndex(index);
        }
    }

    /**
     * Getter for the test script set.
     * 
     * @return the test script set
     */
    public List<TestScriptContainer> getTestScriptList() {
        return Collections.unmodifiableList(this.testScriptList);
    }

    /**
     * Remove an element from the test script list.
     * 
     * @param container
     */
    public void addElement(TestScriptContainer container) {
        Object oldValue = testScriptList.hashCode();

        if (container != null) {
            this.testScriptList.add(container);
        }

        Object newValue = testScriptList.hashCode();

        this.updateProperty(PROPERTY_TEST_SCRIPT_LIST, oldValue, newValue);
    }

    /**
     * Add a test script to the model.
     * 
     * @param container
     *            the test script to add
     */
    public void removeElement(TestScriptContainer container) {
        Object oldValue = testScriptList.hashCode();


        // decrease order index of hind elements
        if (this.testScriptList.contains(container)) {
        	List<TestScriptContainer> subList = this.testScriptList.subList(this.testScriptList.indexOf(container), this.testScriptList.size());
        	for (TestScriptContainer testScriptContainer : subList) {
        		testScriptContainer.setOrderIndex(testScriptContainer.getOrderIndex().getValue() - 1);
        	}
        }
        if (container != null) {
        	this.testScriptList.remove(container);
        }
        
        Object newValue = testScriptList.hashCode();

        this.updateProperty(PROPERTY_TEST_SCRIPT_LIST, oldValue, newValue);
    }

    /**
     * Pushs the element up.
     * 
     * @param container
     *            the test script to push
     */
    public void pushUp(TestScriptContainer container) {
        Object oldValue = this.testScriptList.hashCode();

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

            TestScriptContainer oldElement = this.testScriptList.get(newIndex);
            this.testScriptList.set(newIndex, container);
            container.setOrderIndex(newIndex);
            
            if (container.getDatatypeState() == DatatypeState.PERSISTENT) {
            	container.setDatatypeState(DatatypeState.MODIFIED);
            }
            
            this.testScriptList.set(oldIndex, oldElement);
            oldElement.setOrderIndex(oldIndex);
            
            if (oldElement.getDatatypeState() == DatatypeState.PERSISTENT) {
            	oldElement.setDatatypeState(DatatypeState.MODIFIED);
            }
        }

        Object newValue = this.testScriptList.hashCode();

        this.updateProperty(PROPERTY_TEST_SCRIPT_LIST, oldValue, newValue);
    }

    /**
     * Pushs the element down.
     * 
     * @param container
     *            the test script to push
     */
    public void pullDown(TestScriptContainer container) {
        Object oldValue = this.testScriptList.hashCode();

        if (container != null) {

            Order order = container.getOrderIndex();

            if (order == null || order.getValue() == null) {
                Activator.getDefault().logError(
                        "Cannot pull down TestScript with index " + order + ".");
                return;
            }

            if (order.getValue() >= this.testScriptList.size() - 1) {
                Activator.getDefault().logError(
                        "Cannot pull down TestScript with index " + order + ".");
                return;
            }

            int oldIndex = order.getValue();
            int newIndex = oldIndex + 1;

            TestScriptContainer oldElement = this.testScriptList.get(newIndex);
            this.testScriptList.set(newIndex, container);
            container.setOrderIndex(newIndex);
            
            if (container.getDatatypeState() == DatatypeState.PERSISTENT) {
            	container.setDatatypeState(DatatypeState.MODIFIED);
            }
            
            this.testScriptList.set(oldIndex, oldElement);
            oldElement.setOrderIndex(oldIndex);
            
            if (oldElement.getDatatypeState() == DatatypeState.PERSISTENT) {
            	oldElement.setDatatypeState(DatatypeState.MODIFIED);
            }
        }

        Object newValue = this.testScriptList.hashCode();

        this.updateProperty(PROPERTY_TEST_SCRIPT_LIST, oldValue, newValue);
    }

}
