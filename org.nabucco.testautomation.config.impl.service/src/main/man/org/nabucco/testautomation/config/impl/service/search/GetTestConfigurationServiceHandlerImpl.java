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
package org.nabucco.testautomation.config.impl.service.search;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.nabucco.framework.base.facade.component.NabuccoInstance;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.ConstraintFactory;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.impl.service.maintain.PersistenceCleaner;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.datatype.comparator.TestConfigElementSorter;
import org.nabucco.testautomation.config.facade.datatype.comparator.TestScriptSorter;
import org.nabucco.testautomation.config.facade.message.TestConfigurationListMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigurationSearchMsg;
import org.nabucco.testautomation.config.impl.service.AttributeSupport;
import org.nabucco.testautomation.config.impl.service.DynamicCodeSupport;
import org.nabucco.testautomation.config.impl.service.PropertySupport;
import org.nabucco.testautomation.config.impl.service.SchemaSupport;
import org.nabucco.testautomation.config.impl.service.ScriptSupport;

/**
 * GetTestConfigurationServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class GetTestConfigurationServiceHandlerImpl extends GetTestConfigurationServiceHandler {

	private static final long serialVersionUID = 1L;
	
	private static final TestConfigElementSorter elementSorter = new TestConfigElementSorter();
	
	private static final TestScriptSorter scriptSorter = new TestScriptSorter();
	
	private List<TestConfigElement> elementList = new ArrayList<TestConfigElement>();
	
	@Override
	public TestConfigurationListMsg getTestConfiguration(
			TestConfigurationSearchMsg msg)
			throws SearchException {

		if (msg.getId() == null || msg.getId().getValue() == null) {
			throw new SearchException("Mandatory Identifier is null");
		}
		
		String queryString = "select c from TestConfiguration c where c.id = :id";
		Query query = super.getEntityManager().createQuery(
				queryString.toString());
		query.setParameter("id", msg.getId().getValue());
		TestConfiguration result = (TestConfiguration) query.getSingleResult();

		if (result == null) {
			throw new SearchException("TestConfiguration with id '" + msg.getId().getValue() + "' not found");
		}
		
		boolean loadTestScripts = false;
		Flag loadTestScriptsFlag = msg.getLoadTestScripts();
		
		if (loadTestScriptsFlag != null
				&& loadTestScriptsFlag.getValue() != null
				&& loadTestScriptsFlag.getValue().booleanValue()) {
			loadTestScripts = true;
		}
		
		this.elementList.clear();
		load(result, loadTestScripts);
		
		// Detach Entity
    	this.getEntityManager().clear();
    	try {
			result.accept(new PersistenceCleaner());
		} catch (VisitorException e) {
			throw new SearchException(e);
		}
    	
    	// Sort
    	elementSorter.sort(result.getTestConfigElementList());
    	
    	for (TestConfigElement element : this.elementList) {
        	scriptSorter.sort(element.getTestScriptList());
		}
        this.elementList.clear();
		
		// Check owner and set Editable-Constraint
		if (!result.getOwner().equals(NabuccoInstance.getInstance().getOwner())) {
			try {
				result.addConstraint(ConstraintFactory.getInstance()
						.createEditableConstraint(false), true);
			} catch (VisitorException ex) {
				throw new SearchException(ex);
			}
		}
		
		TestConfigurationListMsg rs = new TestConfigurationListMsg();
		rs.getTestConfigList().add(result);
		return rs;
	}
	
	private void resolveSchemaConfig(TestConfiguration testConfiguration) {
		try {
			SchemaSupport.getInstance().resolveSchemaConfig(testConfiguration, super.getContext());
		} catch (Exception e) {
			super.getLogger().error(e, "Could not resolve SchemaConfig for TestConfiguration '" 
					+ testConfiguration.getName().getValue() + "' (", testConfiguration.getId() + ")");
		}
	}
	
	private void resolveDynamicCodes(TestConfiguration testConfiguration) {
		try {
			DynamicCodeSupport.getInstance().resolveDynamicCodes(testConfiguration, super.getContext());
		} catch (Exception e) {
			super.getLogger().error(e, "Could not resolve DynamicCodes for TestConfiguration '" 
					+ testConfiguration.getName().getValue() + "' (", testConfiguration.getId() + ")");
		}
	}
	
	private void load(TestConfiguration testConfiguration, boolean loadTestScripts) {
    	testConfiguration.setDatatypeState(DatatypeState.PERSISTENT);
    	resolveSchemaConfig(testConfiguration);
		resolveDynamicCodes(testConfiguration);
        
		for (TestConfigElementContainer child : testConfiguration.getTestConfigElementList()) {
			load(child, loadTestScripts);
		}
    }
	
	private void load(TestConfigElementContainer container, boolean loadTestScripts) {
		
    	TestConfigElement element = container.getElement();
    	
		// resolve SchemaElement, Properties and DynamicCodes
		try {
			SchemaSupport.getInstance().resolveSchemaElement(element, super.getContext());
		} catch (Exception e) {
			super.getLogger().error(e, "Could not resolve SchemaElement for TestConfigElement '" 
					+ element.getName().getValue() + "' (", element.getId() + ")");
		}
		try {
			PropertySupport.getInstance().resolveProperties(element, super.getContext());
		} catch (Exception e) {
			super.getLogger().error(e, "Could not resolve Properties for TestConfigElement '" 
					+ element.getName().getValue() + "' (", element.getId() + ")");
		}
		try {
			DynamicCodeSupport.getInstance().resolveDynamicCodes(element, super.getContext());
		} catch (Exception e) {
			super.getLogger().error(e, "Could not resolve DynamicCodes for TestConfigElement '" 
					+ element.getName().getValue() + "' (", element.getId() + ")");
		}
		
		// resolve Attributes
		try {
			AttributeSupport.getInstance().resolveAttributes(element, super.getContext());
		} catch (Exception e) {
			super.getLogger().error(e, "Could not resolve Attributes for TestConfigElement '" 
					+ element.getName().getValue() + "' (", element.getId() + ")");
		}
		
		// Load Dependencies
		element.getDependencyList().size();
		
		// resolve TestScripts
		if (hasTestScripts(element)) {
			this.elementList.add(element);			
			try {
				ScriptSupport.getInstance().resolveTestScripts(element, false, super.getContext());
			} catch (Exception e) {
				super.getLogger().error(e, "Could not resolve TestScripts for TestConfigElement '" 
						+ element.getName().getValue() + "' (", element.getId() + ")");
			}
		}
	
		container.setDatatypeState(DatatypeState.PERSISTENT);
		element.setDatatypeState(DatatypeState.PERSISTENT);
		
		// Load children
		for (TestConfigElementContainer child : element.getTestConfigElementList()) {
			load(child, loadTestScripts);
		}
	}
	
	private boolean hasTestScripts(TestConfigElement element) {
		return element.getTestScriptList().size() > 0;
	}
	
}
