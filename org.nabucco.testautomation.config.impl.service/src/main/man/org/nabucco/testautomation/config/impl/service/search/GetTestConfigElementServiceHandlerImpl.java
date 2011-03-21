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
import org.nabucco.testautomation.config.facade.datatype.comparator.TestConfigElementSorter;
import org.nabucco.testautomation.config.facade.message.TestConfigElementMsg;
import org.nabucco.testautomation.config.facade.message.TestConfigElementSearchMsg;
import org.nabucco.testautomation.config.impl.service.AttributeSupport;
import org.nabucco.testautomation.config.impl.service.DynamicCodeSupport;
import org.nabucco.testautomation.config.impl.service.PropertySupport;
import org.nabucco.testautomation.config.impl.service.SchemaSupport;
import org.nabucco.testautomation.config.impl.service.ScriptSupport;

/**
 * GetTestConfigElementServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class GetTestConfigElementServiceHandlerImpl extends GetTestConfigElementServiceHandler {

	private static final long serialVersionUID = 1L;
	
	private static final TestConfigElementSorter elementSorter = new TestConfigElementSorter();
	
	@Override
	public TestConfigElementMsg getTestConfigElement(
			TestConfigElementSearchMsg msg)
			throws SearchException {

		if (msg.getId() == null || msg.getId().getValue() == null) {
			throw new SearchException("Mandatory Identifier is null");
		}
		
		String queryString = "select c from TestConfigElement c where c.id = :id";
		Query query = super.getEntityManager().createQuery(
				queryString.toString());
		query.setParameter("id", msg.getId().getValue());
		TestConfigElement result = (TestConfigElement) query.getSingleResult();
		
		if (result == null) {
			throw new SearchException("TestConfigElement with id '" + msg.getId().getValue() + "' not found");
		}
		
		boolean loadTestScripts = false;
		Flag loadTestScriptsFlag = msg.getLoadTestScripts();
		
		if (loadTestScriptsFlag != null
				&& loadTestScriptsFlag.getValue() != null
				&& loadTestScriptsFlag.getValue().booleanValue()) {
			loadTestScripts = true;
		}
		
		load(result, loadTestScripts);
		
		// Detach Entity
    	this.getEntityManager().clear();
    	try {
			result.accept(new PersistenceCleaner());
		} catch (VisitorException e) {
			throw new SearchException(e);
		}
    	
    	// Sort
    	elementSorter.sort(result);
		
		
		// Check owner and set Editable-Constraint
		if (!result.getOwner().equals(NabuccoInstance.getInstance().getOwner())) {
			try {
				result.addConstraint(ConstraintFactory.getInstance()
						.createEditableConstraint(false), true);
			} catch (VisitorException ex) {
				throw new SearchException(ex);
			}
		}
		
		TestConfigElementMsg rs = new TestConfigElementMsg();
		rs.setTestConfigElement(result);
		return rs;
	}
	
	private void load(TestConfigElement element, boolean loadTestScripts) {
		
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
		try {
			ScriptSupport.getInstance().resolveTestScripts(element, loadTestScripts, super.getContext());
		} catch (Exception e) {
			super.getLogger().error(e, "Could not resolve TestScripts for TestConfigElement '" 
					+ element.getName().getValue() + "' (", element.getId() + ")");
		}
	
		element.setDatatypeState(DatatypeState.PERSISTENT);
		
		// Load children
		for (TestConfigElementContainer child : element.getTestConfigElementList()) {
			child.setDatatypeState(DatatypeState.PERSISTENT);
			load(child.getElement(), loadTestScripts);
		}
	}
	
}
