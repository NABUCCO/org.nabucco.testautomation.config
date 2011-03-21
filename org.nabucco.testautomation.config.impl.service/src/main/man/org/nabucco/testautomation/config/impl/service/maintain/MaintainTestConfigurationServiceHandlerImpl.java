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
package org.nabucco.testautomation.config.impl.service.maintain;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionAccessor;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceExceptionMapper;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.impl.service.maintain.PersistenceHelper;
import org.nabucco.testautomation.config.facade.datatype.Dependency;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.config.facade.datatype.comparator.TestConfigElementSorter;
import org.nabucco.testautomation.config.facade.datatype.comparator.TestScriptSorter;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;
import org.nabucco.testautomation.config.impl.service.AttributeSupport;
import org.nabucco.testautomation.config.impl.service.DynamicCodeSupport;
import org.nabucco.testautomation.config.impl.service.PropertySupport;
import org.nabucco.testautomation.config.impl.service.SchemaSupport;
import org.nabucco.testautomation.config.impl.service.ScriptSupport;
import org.nabucco.testautomation.config.impl.service.maintain.visitor.SchemaAllocator;
import org.nabucco.testautomation.config.impl.service.maintain.visitor.SchemaCollector;
import org.nabucco.testautomation.config.impl.service.maintain.visitor.TestConfigElementModificationVisitor;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyUsageType;
import org.nabucco.testautomation.facade.datatype.visitor.PropertyModificationVisitor;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

/**
 * MaintainTestConfigurationServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class MaintainTestConfigurationServiceHandlerImpl extends
        MaintainTestConfigurationServiceHandler {

	private static final long serialVersionUID = 1L;
    
	private static final String KEY_SEPARATOR = "-";
	
	private static final String PREFIX = "TCO-";

	private static final TestConfigElementSorter elementSorter = new TestConfigElementSorter();
	
	private static final TestScriptSorter scriptSorter = new TestScriptSorter();
	
	private List<TestConfigElement> elementList = new ArrayList<TestConfigElement>();
	
	private PersistenceHelper persistenceHelper;

    @Override
    public TestConfigurationMsg maintainTestConfiguration(TestConfigurationMsg msg)
            throws MaintainException {
    	
        TestConfiguration testConfiguration = msg.getTestConfiguration();

        try {
        	// initialize PersistenceHelper
			this.persistenceHelper = new PersistenceHelper(super.getEntityManager());
        	
            // Collect schema elements
            SchemaCollector schemaCollector = new SchemaCollector();
            testConfiguration.accept(schemaCollector);
            SchemaConfig schemaConfig = testConfiguration.getSchemaConfig();
            
            if (testConfiguration.getDatatypeState() == DatatypeState.PERSISTENT) {
                DatatypeVisitor visitor = new TestConfigElementModificationVisitor(
                        testConfiguration);
                testConfiguration.accept(visitor);
            }

            switch (testConfiguration.getDatatypeState()) {
            case CONSTRUCTED:
                throw new MaintainException("TestConfiguration is not initialized.");
            case INITIALIZED:
                testConfiguration = this.create(testConfiguration);
                break;
            case MODIFIED:
                testConfiguration = this.update(testConfiguration);
                break;
            case DELETED:
                this.delete(testConfiguration);
                testConfiguration = null;
                break;
            case TRANSIENT:
                break;
            case PERSISTENT:
                break;
            default:
                throw new MaintainException("Datatype state '"
                        + testConfiguration.getDatatypeState()
                        + "' is not valid for TestConfiguration.");
            }
            
            this.persistenceHelper.flush();
            this.persistenceHelper = null;
            
            if (testConfiguration != null) {
            	this.elementList.clear();
            	load(testConfiguration);
            	testConfiguration.setSchemaConfig(schemaConfig);
            	
            	// Detach Entity
            	this.getEntityManager().clear();
            	
            	// Sort
            	elementSorter.sort(testConfiguration.getTestConfigElementList());
	            msg.setTestConfiguration(testConfiguration);

	            for (TestConfigElement element : this.elementList) {
	            	scriptSorter.sort(element.getTestScriptList());
				}
	            this.elementList.clear();
	            
	            // Allocate collected schema elements
	            SchemaAllocator schemaAllocator = schemaCollector.createAllocator();
	            schemaAllocator.allocate(testConfiguration);
            }
        } catch (VisitorException e) {
            throw new MaintainException(e);
		} catch (Exception ex) {
			throw new MaintainException("Error maintaining TestConfiguration",
					PersistenceExceptionMapper.resolve(ex,
							TestConfiguration.class.getName(),
							testConfiguration.getId()));
		}
        
        msg.setTestConfiguration(testConfiguration);
        return msg;
    }
    
    private TestConfiguration create(TestConfiguration entity) throws PersistenceException {

        List<TestConfigElementContainer> testConfigElementList = entity.getTestConfigElementList();

        for (int i = 0; i < testConfigElementList.size(); i++) {
            TestConfigElementContainer updatedElement = create(testConfigElementList.get(i));
            testConfigElementList.set(i, updatedElement);
        }
        
        NabuccoCollectionAccessor.getInstance().clearAssignments(testConfigElementList);
        entity = this.persistenceHelper.persist(entity);
        entity.setIdentificationKey(PREFIX + entity.getId());
        entity.setDatatypeState(DatatypeState.MODIFIED);
        entity = this.persistenceHelper.persist(entity);
        return entity;
    }

    private TestConfigElementContainer create(TestConfigElementContainer entity)
            throws PersistenceException {

        TestConfigElement element = entity.getElement();
        List<TestConfigElementContainer> testConfigElementList = element.getTestConfigElementList();
        PropertyList propertyList = element.getPropertyList();

        // Create children
        for (int i = 0; i < testConfigElementList.size(); i++) {
            TestConfigElementContainer updatedElement = create(testConfigElementList.get(i));
            testConfigElementList.set(i, updatedElement);
        }
        NabuccoCollectionAccessor.getInstance().clearAssignments(testConfigElementList);
        
    	// Create Dependencies
        create(element.getDependencyList());
        
        // Create AttributeValues
        List<AttributeValue> attributeValueList = element.getAttributeValueList();
        
        for (int i = 0; i < attributeValueList.size(); i++) {
        	AttributeValue attribute = this.persistenceHelper.persist(attributeValueList.get(i));
        	attributeValueList.set(i, attribute);
        }

        // Create or update PropertyList
        if (propertyList != null) {
        	propertyList = update(propertyList);
        	element.setPropertyList(propertyList);
        }
        
        // Create or update TestScripts
        List<TestScriptContainer> testScriptList = element.getTestScriptList();
        
        for (int i = 0; i < testScriptList.size(); i++) {
        	TestScriptContainer testScriptContainer = this.persistenceHelper.persist(testScriptList.get(i));
        	testScriptList.set(i, testScriptContainer);
        }

        // Create or update TestConfigElement
        switch (element.getDatatypeState()) {
        case INITIALIZED:
        	SchemaElement schema = element.getSchemaElement();
        	element = this.persistenceHelper.persist(element);
            element.setSchemaElement(schema);
            element = this.setElementKey(element);
            break;
        default:
        	element = this.persistenceHelper.persist(element);
        	break;
        }
        entity.setElement(element);

        // Create or update TestConfigElementContainer
        entity = this.persistenceHelper.persist(entity);
        
        entity.setElement(element);
        return entity;
    }
    
    private void create(List<Dependency> dependencyList) throws PersistenceException {
    	
    	List<Dependency> ignoreList = new ArrayList<Dependency>();
    	
    	for (int i = 0; i < dependencyList.size(); i++) {
        	Dependency dependency = dependencyList.get(i);
        	
        	// Not persisted TestConfigElements cannot be a dependency
        	if (dependency.getElement().getId() == null) {
        		ignoreList.add(dependency);
        	} else {
        		dependency = this.persistenceHelper.persist(dependency);
        		dependencyList.set(i, dependency);
        	}
        }
    	
    	for (Dependency ignore : ignoreList) {
    		dependencyList.remove(ignore);
		}
        NabuccoCollectionAccessor.getInstance().clearAssignments(dependencyList);
    }
    
    private TestConfiguration update(TestConfiguration entity) throws PersistenceException {

        List<TestConfigElementContainer> children = entity.getTestConfigElementList();
        List<TestConfigElementContainer> deletedContainer = NabuccoCollectionAccessor.getInstance().getUnassignedList(children);

        // Update children
        for (int i = 0; i < children.size(); i++) {
            TestConfigElementContainer updatedElement = update(children.get(i));
            children.set(i, updatedElement);
        }
        NabuccoCollectionAccessor.getInstance().clearAssignments(children);
        
        // Delete unassigned TestConfigElementContainer
        for (TestConfigElementContainer testConfigElementContainer : deletedContainer) {
        	testConfigElementContainer.setDatatypeState(DatatypeState.DELETED);
        	testConfigElementContainer = this.persistenceHelper.persist(testConfigElementContainer);
        }
        
        // Update TestConfiguration
        entity = this.persistenceHelper.persist(entity);
        return entity;
    }

    private TestConfigElementContainer update(TestConfigElementContainer entity)
            throws PersistenceException {

        TestConfigElement element = entity.getElement();
        PropertyList propertyList = element.getPropertyList();
        List<TestConfigElementContainer> children = element.getTestConfigElementList();
        List<TestConfigElementContainer> deletedContainer = NabuccoCollectionAccessor.getInstance().getUnassignedList(children);
        
        // Update children
        for (int i = 0; i < children.size(); i++) {
            TestConfigElementContainer updatedElement = update(children.get(i));
            children.set(i, updatedElement);
        }
        NabuccoCollectionAccessor.getInstance().clearAssignments(children);
        
        // Delete unassigned TestConfigElementContainer
        for (TestConfigElementContainer testConfigElementContainer : deletedContainer) {
        	testConfigElementContainer.setDatatypeState(DatatypeState.DELETED);
        	testConfigElementContainer = this.persistenceHelper.persist(testConfigElementContainer);
        }
        
    	// Update Dependencies
        update(element.getDependencyList());

        // Create or update AttributeValues
        List<AttributeValue> attributeValueList = element.getAttributeValueList();
        
        for (int i = 0; i < attributeValueList.size(); i++) {
        	AttributeValue attribute = this.persistenceHelper.persist(attributeValueList.get(i));
        	attributeValueList.set(i, attribute);
        }
        
        // Update PropertyList
        if (propertyList != null) {
        	propertyList = update(propertyList);
        	element.setPropertyList(propertyList);
        }
        
        // Create or update TestScripts
        List<TestScriptContainer> testScriptList = element.getTestScriptList();
        List<TestScriptContainer> removedContainer = NabuccoCollectionAccessor.getInstance().getUnassignedList(testScriptList);
        
        for (int i = 0; i < testScriptList.size(); i++) {
        	TestScriptContainer testScriptContainer = this.persistenceHelper.persist(testScriptList.get(i));
        	testScriptList.set(i, testScriptContainer);
        }
        NabuccoCollectionAccessor.getInstance().clearAssignments(testScriptList);
        
        // Delete unassigned TestScriptContainer
        for (TestScriptContainer testScriptContainer : removedContainer) {
        	testScriptContainer.setDatatypeState(DatatypeState.DELETED);
        	this.persistenceHelper.persist(testScriptContainer);
		}
        
        // Update TestConfigElement
        switch (element.getDatatypeState()) {
        case INITIALIZED:
        	SchemaElement schema = element.getSchemaElement();
            element = this.persistenceHelper.persist(element);
            element.setSchemaElement(schema);
            element = this.setElementKey(element);
            break;
       default:
    	   	element = this.persistenceHelper.persist(element);
            break;
        }
        entity.setElement(element);

        // Update TestConfigElementContainer
        entity = this.persistenceHelper.persist(entity);
        
        entity.setElement(element);
        return entity;
    }
    
    private void update(List<Dependency> dependencyList) throws PersistenceException {
    	
    	List<Dependency> unassignedDependencies = NabuccoCollectionAccessor.getInstance().getUnassignedList(dependencyList);
    	List<Dependency> ignoreList = new ArrayList<Dependency>();
    	
    	for (int i = 0; i < dependencyList.size(); i++) {
        	Dependency dependency = dependencyList.get(i);
        	
        	// Not persisted TestConfigElements cannot be a dependency
        	if (dependency.getElement().getId() == null) {
        		ignoreList.add(dependency);
        	} else {
        		dependency = this.persistenceHelper.persist(dependency);
        		dependencyList.set(i, dependency);
        	}
        }
        
        // Delete unassigned Dependencies
        for (Dependency dependency : unassignedDependencies) {
        	dependency.setDatatypeState(DatatypeState.DELETED);
        	this.persistenceHelper.persist(dependency);
        }
        
        for (Dependency ignore : ignoreList) {
    		dependencyList.remove(ignore);
		}
        NabuccoCollectionAccessor.getInstance().clearAssignments(dependencyList);
    }
    
	private PropertyList update(PropertyList entity) throws PersistenceException {
		
		if (entity != null) {
			
			try {
				if (entity.getDatatypeState() == DatatypeState.PERSISTENT) {
					PropertyModificationVisitor visitor = new PropertyModificationVisitor(entity);
					entity.accept(visitor);
				}
			
				if (entity.getDatatypeState() != DatatypeState.PERSISTENT) {
					entity.setUsageType(PropertyUsageType.TEST_CONFIG_ELEMENT_PARAM);
					entity = PropertySupport.getInstance().maintainPropertyList(entity, getContext());
				}
			} catch (Exception e) {
				this.getLogger().error("Could not maintain PropertyList '" + entity.getName().getValue() + "'");
			}
		}
		return entity;
	}
    
    private void delete(TestConfiguration entity) throws PersistenceException {

        for (TestConfigElementContainer container : entity.getTestConfigElementList()) {
        	container.setDatatypeState(DatatypeState.DELETED);
        	container = this.persistenceHelper.persist(container);
        }
        
        entity.getTestConfigElementList().clear();
        entity = this.persistenceHelper.persist(entity);
    }
    
    private void load(TestConfiguration testConfiguration) {
    	resolveSchemaConfig(testConfiguration);
		resolveDynamicCodes(testConfiguration);
        
		for (TestConfigElementContainer child : testConfiguration.getTestConfigElementList()) {
			load(child);
		}
    }

    private void load(TestConfigElementContainer container) {
		
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
	
		// Load children
		for (TestConfigElementContainer child : element.getTestConfigElementList()) {
			load(child);
		}
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
	
	private boolean hasTestScripts(TestConfigElement element) {
		return element.getTestScriptList().size() > 0;
	}
    
	private TestConfigElement setElementKey(TestConfigElement element) throws PersistenceException {
		
		if (element != null && element.getSchemaElement() != null && element.getSchemaElement().getPrefix() != null) {
			String key = element.getSchemaElement().getPrefix().getValue() + KEY_SEPARATOR + element.getId();
			element.setIdentificationKey(key);
			element.setDatatypeState(DatatypeState.MODIFIED);
			element = this.persistenceHelper.persist(element);
		}
		return element;
	}

}
