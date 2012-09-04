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
package org.nabucco.testautomation.config.impl.service.maintain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionAccessor;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.impl.service.maintain.PersistenceExceptionMapper;
import org.nabucco.testautomation.config.facade.datatype.Dependency;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.config.facade.datatype.attribute.CodeAttributeValue;
import org.nabucco.testautomation.config.facade.datatype.comparator.TestConfigElementSorter;
import org.nabucco.testautomation.config.facade.datatype.comparator.TestScriptSorter;
import org.nabucco.testautomation.config.facade.message.TestConfigurationMsg;
import org.nabucco.testautomation.config.impl.service.AttributeSupport;
import org.nabucco.testautomation.config.impl.service.PropertySupport;
import org.nabucco.testautomation.config.impl.service.ScriptSupport;
import org.nabucco.testautomation.config.impl.service.maintain.support.ConfigMaintainSupport;
import org.nabucco.testautomation.config.impl.service.maintain.visitor.SchemaElementCollector;
import org.nabucco.testautomation.config.impl.service.maintain.visitor.TestConfigElementModificationVisitor;
import org.nabucco.testautomation.config.impl.service.resolve.Attribute2ElementLink;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyUsageType;
import org.nabucco.testautomation.property.facade.datatype.visitor.PropertyModificationVisitor;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

/**
 * MaintainTestConfigurationServiceHandlerImpl
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public class MaintainTestConfigurationServiceHandlerImpl extends MaintainTestConfigurationServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String KEY_SEPARATOR = "-";

    private static final String PREFIX = "TCO-";

    private static final TestConfigElementSorter elementSorter = new TestConfigElementSorter();

    private static final TestScriptSorter scriptSorter = new TestScriptSorter();

    private List<TestConfigElement> elementList = new ArrayList<TestConfigElement>();

    private Map<Long, SchemaElement> schemaElementMap;

    private Map<Long, Code> codeMap;

    private Map<Long, TestConfigElement> elementCache;

    private ConfigMaintainSupport maintainSupport;

    private AttributeSupport attributeSupport;

    private PropertySupport propertySupport;

    @Override
    public TestConfigurationMsg maintainTestConfiguration(TestConfigurationMsg msg) throws MaintainException {

        TestConfiguration testConfiguration = msg.getTestConfiguration();
        SchemaConfig schemaConfig = testConfiguration.getSchemaConfig();
        this.codeMap = new HashMap<Long, Code>();
        this.elementCache = new HashMap<Long, TestConfigElement>();

        try {
            // initialize PersistenceHelper and PropertySupport
            this.maintainSupport = new ConfigMaintainSupport(super.getPersistenceManager());
            this.propertySupport = new PropertySupport(getContext());

            // Collect schema elements
            SchemaElementCollector schemaCollector = new SchemaElementCollector();
            schemaConfig.accept(schemaCollector);
            this.schemaElementMap = schemaCollector.getSchemaElementMap();

            if (testConfiguration.getDatatypeState() == DatatypeState.PERSISTENT) {
                DatatypeVisitor visitor = new TestConfigElementModificationVisitor(testConfiguration);
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
                getLogger().info(
                        "TestConfiguration '"
                                + testConfiguration.getName() + "' [" + testConfiguration.getId() + "] deleted");
                testConfiguration = null;
                break;
            case TRANSIENT:
                break;
            case PERSISTENT:
                break;
            default:
                throw new MaintainException("Datatype state '"
                        + testConfiguration.getDatatypeState() + "' is not valid for TestConfiguration.");
            }

            this.getPersistenceManager().flush();

            if (testConfiguration != null) {
                this.elementList.clear();
                this.attributeSupport = new AttributeSupport();
                resolve(testConfiguration);
                testConfiguration.setSchemaConfig(schemaConfig);

                // Detach Entity
                this.getPersistenceManager().clear();

                // Add new AttributeValues if required
                for (Attribute2ElementLink link : this.attributeSupport.getAttribute2ElementLinkList()) {
                    TestConfigElement element = link.getElement();
                    element.getAttributeValueList().add(link.getAttributeValue());
                    element.setDatatypeState(DatatypeState.MODIFIED);
                }

                // Sort
                elementSorter.sort(testConfiguration.getTestConfigElementList());
                msg.setTestConfiguration(testConfiguration);

                for (TestConfigElement element : this.elementList) {
                    scriptSorter.sort(element.getTestScriptList());
                }

                getLogger().info(
                        "TestConfiguration '"
                                + testConfiguration.getName() + "' [" + testConfiguration.getId()
                                + "] successfully maintained");
            }
        } catch (VisitorException e) {
            throw new MaintainException(e);
        } catch (Exception ex) {
            throw new MaintainException("Error maintaining TestConfiguration", PersistenceExceptionMapper.resolve(ex,
                    TestConfiguration.class.getName(), testConfiguration.getId()));
        } finally {
            this.elementList.clear();
            this.attributeSupport = null;
            this.propertySupport = null;
            this.maintainSupport = null;
            this.elementCache = null;
            this.codeMap = null;
        }

        msg.setTestConfiguration(testConfiguration);
        return msg;
    }

    private TestConfiguration create(TestConfiguration entity) throws MaintainException {

        List<TestConfigElementContainer> testConfigElementList = entity.getTestConfigElementList();

        for (int i = 0; i < testConfigElementList.size(); i++) {
            TestConfigElementContainer updatedElement = create(testConfigElementList.get(i));
            testConfigElementList.set(i, updatedElement);
        }

        if (!this.codeMap.containsKey(entity.getEnvironmentTypeRefId())) {
            this.codeMap.put(entity.getEnvironmentTypeRefId(), entity.getEnvironmentType());
        }

        if (!this.codeMap.containsKey(entity.getReleaseTypeRefId())) {
            this.codeMap.put(entity.getReleaseTypeRefId(), entity.getReleaseType());
        }

        NabuccoCollectionAccessor.getInstance().clearAssignments(testConfigElementList);
        entity = this.maintainSupport.maintain(entity);
        entity.setIdentificationKey(PREFIX + entity.getId());
        entity.setDatatypeState(DatatypeState.MODIFIED);
        entity = this.maintainSupport.maintain(entity);
        return entity;
    }

    private TestConfigElementContainer create(TestConfigElementContainer entity) throws MaintainException {

        TestConfigElement element = entity.getElement();
        List<TestConfigElementContainer> testConfigElementList = element.getTestConfigElementList();
        PropertyList propertyList = element.getPropertyList();

        // Create children
        for (int i = 0; i < testConfigElementList.size(); i++) {
            TestConfigElementContainer updatedElement = create(testConfigElementList.get(i));
            testConfigElementList.set(i, updatedElement);
        }
        NabuccoCollectionAccessor.getInstance().clearAssignments(testConfigElementList);

        if (!this.codeMap.containsKey(element.getBrandTypeRefId())) {
            this.codeMap.put(element.getBrandTypeRefId(), element.getBrandType());
        }

        // Create Dependencies
        create(element.getDependencyList());

        // Create AttributeValues
        maintainAttributeValues(element.getAttributeValueList());

        // Create or update PropertyList
        if (propertyList != null) {
            propertyList = update(propertyList);
            element.setPropertyList(propertyList);
        }

        // Create or update TestScripts
        List<TestScriptContainer> testScriptList = element.getTestScriptList();

        for (int i = 0; i < testScriptList.size(); i++) {
            TestScriptContainer testScriptContainer = this.maintainSupport.maintain(testScriptList.get(i));
            testScriptList.set(i, testScriptContainer);
        }

        // Create or update TestConfigElement
        switch (element.getDatatypeState()) {
        case INITIALIZED:
            SchemaElement schema = element.getSchemaElement();
            element = this.maintainSupport.maintain(element);
            element.setSchemaElement(schema);
            element = this.setElementKey(element);
            break;
        default:
            element = this.maintainSupport.maintain(element);
            break;
        }
        entity.setElement(element);

        // Create or update TestConfigElementContainer
        entity = this.maintainSupport.maintain(entity);

        entity.setElement(element);
        return entity;
    }

    private void create(List<Dependency> dependencyList) throws MaintainException {

        List<Dependency> ignoreList = new ArrayList<Dependency>();

        for (int i = 0; i < dependencyList.size(); i++) {
            Dependency dependency = dependencyList.get(i);

            // Not persisted TestConfigElements cannot be a dependency
            if (dependency.getElement().getId() == null) {
                ignoreList.add(dependency);
            } else {
                dependency = this.maintainSupport.maintain(dependency);
                dependencyList.set(i, dependency);
            }
        }

        for (Dependency ignore : ignoreList) {
            dependencyList.remove(ignore);
        }
        NabuccoCollectionAccessor.getInstance().clearAssignments(dependencyList);
    }

    private TestConfiguration update(TestConfiguration entity) throws MaintainException {

        List<TestConfigElementContainer> children = entity.getTestConfigElementList();
        List<TestConfigElementContainer> deletedContainer = NabuccoCollectionAccessor.getInstance().getUnassignedList(
                children);

        // Update children
        for (int i = 0; i < children.size(); i++) {
            TestConfigElementContainer updatedElement = update(children.get(i));
            children.set(i, updatedElement);
        }
        NabuccoCollectionAccessor.getInstance().clearAssignments(children);

        // Delete unassigned TestConfigElementContainer
        for (TestConfigElementContainer testConfigElementContainer : deletedContainer) {
            if (testConfigElementContainer.getDatatypeState() == DatatypeState.DELETED) {
                testConfigElementContainer = this.maintainSupport.maintain(testConfigElementContainer);
            }
        }

        if (!this.codeMap.containsKey(entity.getEnvironmentTypeRefId())) {
            this.codeMap.put(entity.getEnvironmentTypeRefId(), entity.getEnvironmentType());
        }

        if (!this.codeMap.containsKey(entity.getReleaseTypeRefId())) {
            this.codeMap.put(entity.getReleaseTypeRefId(), entity.getReleaseType());
        }

        // Update TestConfiguration
        entity = this.maintainSupport.maintain(entity);
        return entity;
    }

    private TestConfigElementContainer update(TestConfigElementContainer entity) throws MaintainException {

        TestConfigElement element = entity.getElement();

        if (this.elementCache.containsKey(element.getId())) {
            entity.setElement(this.elementCache.get(element.getId()));
        } else {
            PropertyList propertyList = element.getPropertyList();
            List<TestConfigElementContainer> children = element.getTestConfigElementList();
            List<TestConfigElementContainer> deletedContainer = NabuccoCollectionAccessor.getInstance()
                    .getUnassignedList(children);

            // Update children
            for (int i = 0; i < children.size(); i++) {
                TestConfigElementContainer updatedElement = update(children.get(i));
                children.set(i, updatedElement);
            }
            NabuccoCollectionAccessor.getInstance().clearAssignments(children);

            // Delete unassigned TestConfigElementContainer
            for (TestConfigElementContainer testConfigElementContainer : deletedContainer) {
                if (testConfigElementContainer.getDatatypeState() == DatatypeState.DELETED) {
                    testConfigElementContainer = this.maintainSupport.maintain(testConfigElementContainer);
                }
            }

            if (!this.codeMap.containsKey(element.getBrandTypeRefId())) {
                this.codeMap.put(element.getBrandTypeRefId(), element.getBrandType());
            }

            // Update Dependencies
            update(element.getDependencyList());

            // Create or update AttributeValues
            maintainAttributeValues(element.getAttributeValueList());

            // Update PropertyList
            if (propertyList != null) {
                propertyList = update(propertyList);
                element.setPropertyList(propertyList);
            }

            // Create or update TestScripts
            List<TestScriptContainer> testScriptList = element.getTestScriptList();
            List<TestScriptContainer> removedContainer = NabuccoCollectionAccessor.getInstance().getUnassignedList(
                    testScriptList);

            for (int i = 0; i < testScriptList.size(); i++) {
                TestScriptContainer testScriptContainer = this.maintainSupport.maintain(testScriptList.get(i));
                testScriptList.set(i, testScriptContainer);
            }
            NabuccoCollectionAccessor.getInstance().clearAssignments(testScriptList);

            // Delete unassigned TestScriptContainer
            for (TestScriptContainer testScriptContainer : removedContainer) {
                testScriptContainer.setDatatypeState(DatatypeState.DELETED);
                this.maintainSupport.maintain(testScriptContainer);
            }

            // Update TestConfigElement
            switch (element.getDatatypeState()) {
            case INITIALIZED:
                SchemaElement schema = element.getSchemaElement();
                element = this.maintainSupport.maintain(element);
                element.setSchemaElement(schema);
                element = this.setElementKey(element);
                break;
            default:
                element = this.maintainSupport.maintain(element);
                break;
            }
            entity.setElement(element);
            this.elementCache.put(element.getId(), element);
        }

        // Update TestConfigElementContainer
        entity = this.maintainSupport.maintain(entity);

        entity.setElement(element);
        return entity;
    }

    private void maintainAttributeValues(List<AttributeValue> attributeValueList) throws MaintainException {

        for (int i = 0; i < attributeValueList.size(); i++) {
            AttributeValue attribute = attributeValueList.get(i);

            if (attribute instanceof CodeAttributeValue) {
                CodeAttributeValue codeAttributeValue = (CodeAttributeValue) attribute;
                this.codeMap.put(codeAttributeValue.getValueRefId(), codeAttributeValue.getValue());
            }

            attribute = this.maintainSupport.maintain(attribute);
            attributeValueList.set(i, attribute);
        }
    }

    private void update(List<Dependency> dependencyList) throws MaintainException {

        List<Dependency> unassignedDependencies = NabuccoCollectionAccessor.getInstance().getUnassignedList(
                dependencyList);
        List<Dependency> ignoreList = new ArrayList<Dependency>();

        for (int i = 0; i < dependencyList.size(); i++) {
            Dependency dependency = dependencyList.get(i);

            // Not persisted TestConfigElements cannot be a dependency
            if (dependency.getElement().getId() == null) {
                ignoreList.add(dependency);
            } else {
                dependency = this.maintainSupport.maintain(dependency);
                dependencyList.set(i, dependency);
            }
        }

        // Delete unassigned Dependencies
        for (Dependency dependency : unassignedDependencies) {
            dependency.setDatatypeState(DatatypeState.DELETED);
            this.maintainSupport.maintain(dependency);
        }

        for (Dependency ignore : ignoreList) {
            dependencyList.remove(ignore);
        }
        NabuccoCollectionAccessor.getInstance().clearAssignments(dependencyList);
    }

    private PropertyList update(PropertyList entity) throws MaintainException {

        if (entity != null) {

            try {
                if (entity.getDatatypeState() == DatatypeState.PERSISTENT) {
                    PropertyModificationVisitor visitor = new PropertyModificationVisitor(entity);
                    entity.accept(visitor);
                }

                if (entity.getDatatypeState() != DatatypeState.PERSISTENT) {
                    entity.setUsageType(PropertyUsageType.TEST_CONFIG_ELEMENT_PARAM);
                    entity = this.propertySupport.maintainPropertyList(entity);
                }
            } catch (Exception e) {
                throw new MaintainException("Could not maintain PropertyList '"
                        + entity.getName() + "' [" + entity.getId() + "]");
            }
        }
        return entity;
    }

    private void delete(TestConfiguration entity) throws MaintainException {

        for (TestConfigElementContainer container : entity.getTestConfigElementList()) {
            container.setDatatypeState(DatatypeState.DELETED);
            container = this.maintainSupport.maintain(container);
        }

        entity.getTestConfigElementList().clear();
        entity = this.maintainSupport.maintain(entity);
    }

    private void resolve(TestConfiguration testConfiguration) {

        for (TestConfigElementContainer child : testConfiguration.getTestConfigElementList()) {
            resolve(child);
        }

        if (testConfiguration.getEnvironmentTypeRefId() != null) {
            testConfiguration.setEnvironmentType(this.codeMap.get(testConfiguration.getEnvironmentTypeRefId()));
        }

        if (testConfiguration.getReleaseTypeRefId() != null) {
            testConfiguration.setReleaseType(this.codeMap.get(testConfiguration.getReleaseTypeRefId()));
        }
    }

    private void resolve(TestConfigElementContainer container) {

        TestConfigElement element = container.getElement();

        // Set SchemaElement
        element.setSchemaElement(this.schemaElementMap.get(element.getSchemaElementRefId()));

        try {
            this.propertySupport.resolveProperties(element);
        } catch (Exception e) {
            super.getLogger().error(e,
                    "Could not resolve Properties for TestConfigElement '" + element.getName() + "' [",
                    element.getId() + "]");
        }

        // Resolve Attributes
        try {
            this.attributeSupport.resolveAttributes(element);
        } catch (Exception e) {
            super.getLogger().error(e,
                    "Could not resolve Attributes for TestConfigElement '" + element.getName() + "' [",
                    element.getId() + "]");
        }

        // Resolve DynamicCodes
        if (element.getBrandTypeRefId() != null) {
            element.setBrandType(this.codeMap.get(element.getBrandTypeRefId()));
        }

        for (AttributeValue value : element.getAttributeValueList()) {

            if (value instanceof CodeAttributeValue) {
                CodeAttributeValue codeValue = (CodeAttributeValue) value;
                codeValue.setValue(this.codeMap.get(codeValue.getValueRefId()));
            }
        }

        // Load Dependencies
        element.getDependencyList().size();

        // Resolve TestScripts
        if (hasTestScripts(element)) {
            this.elementList.add(element);
            try {
                ScriptSupport.getInstance().resolveTestScripts(element, false, super.getContext());
            } catch (Exception e) {
                super.getLogger().error(e,
                        "Could not resolve TestScripts for TestConfigElement '" + element.getName() + "' [",
                        element.getId() + "]");
            }
        }

        // Load children
        for (TestConfigElementContainer child : element.getTestConfigElementList()) {
            resolve(child);
        }
    }

    private boolean hasTestScripts(TestConfigElement element) {
        return element.getTestScriptList().size() > 0;
    }

    private TestConfigElement setElementKey(TestConfigElement element) throws MaintainException {

        if (element != null && element.getSchemaElement() != null && element.getSchemaElement().getPrefix() != null) {
            String key = element.getSchemaElement().getPrefix() + KEY_SEPARATOR + element.getId();
            element.setIdentificationKey(key);
            element.setDatatypeState(DatatypeState.MODIFIED);
            element = this.maintainSupport.maintain(element);
        }
        return element;
    }

}
