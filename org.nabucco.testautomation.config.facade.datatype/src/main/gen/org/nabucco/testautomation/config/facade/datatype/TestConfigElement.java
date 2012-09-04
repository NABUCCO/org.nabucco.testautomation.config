/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.testautomation.config.facade.datatype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.documentation.Documentation;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.testautomation.config.facade.datatype.Dependency;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.property.facade.datatype.base.TestAutomationDatatype;
import org.nabucco.testautomation.result.facade.datatype.ExecutionType;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

/**
 * TestConfigElement<p/>One Element of a test configuration<p/>
 *
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-14
 */
public class TestConfigElement extends TestAutomationDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final ExecutionType EXECUTIONTYPE_DEFAULT = ExecutionType.AUTOMATED;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "l0,255;u0,n;m1,1;", "l0,255;u0,n;m0,1;",
            "l0,n;u0,n;m0,1;", "m0,1;", "m0,n;", "l0,100000;u0,n;m0,1;", "m0,1;", "m1,1;", "m0,n;", "l0,n;u0,n;m0,1;",
            "m0,n;", "m0,n;" };

    public static final String EXECUTIONTYPE = "executionType";

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String SKIP = "skip";

    public static final String BRANDTYPE = "brandType";

    public static final String ATTRIBUTEVALUELIST = "attributeValueList";

    public static final String DOCUMENTATION = "documentation";

    public static final String PROPERTYLIST = "propertyList";

    public static final String SCHEMAELEMENT = "schemaElement";

    public static final String TESTCONFIGELEMENTLIST = "testConfigElementList";

    public static final String REUSED = "reused";

    public static final String TESTSCRIPTLIST = "testScriptList";

    public static final String DEPENDENCYLIST = "dependencyList";

    private ExecutionType executionType;

    private Name name;

    private Description description;

    private Flag skip;

    /** Brand of the TestConfiguration */
    private Code brandType;

    private Long brandTypeRefId;

    protected static final String BRANDTYPE_CODEPATH = "nabucco.testautomation.brand";

    private NabuccoList<AttributeValue> attributeValueList;

    private Documentation documentation;

    private PropertyList propertyList;

    private Long propertyListRefId;

    private SchemaElement schemaElement;

    private Long schemaElementRefId;

    private NabuccoList<TestConfigElementContainer> testConfigElementList;

    private Flag reused;

    private NabuccoList<TestScriptContainer> testScriptList;

    private NabuccoList<Dependency> dependencyList;

    /** Constructs a new TestConfigElement instance. */
    public TestConfigElement() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        executionType = EXECUTIONTYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the TestConfigElement.
     */
    protected void cloneObject(TestConfigElement clone) {
        super.cloneObject(clone);
        clone.setExecutionType(this.getExecutionType());
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getSkip() != null)) {
            clone.setSkip(this.getSkip().cloneObject());
        }
        if ((this.getBrandType() != null)) {
            clone.setBrandType(this.getBrandType().cloneObject());
        }
        if ((this.getBrandTypeRefId() != null)) {
            clone.setBrandTypeRefId(this.getBrandTypeRefId());
        }
        if ((this.attributeValueList != null)) {
            clone.attributeValueList = this.attributeValueList.cloneCollection();
        }
        if ((this.getDocumentation() != null)) {
            clone.setDocumentation(this.getDocumentation().cloneObject());
        }
        if ((this.getPropertyList() != null)) {
            clone.setPropertyList(this.getPropertyList().cloneObject());
        }
        if ((this.getPropertyListRefId() != null)) {
            clone.setPropertyListRefId(this.getPropertyListRefId());
        }
        if ((this.getSchemaElement() != null)) {
            clone.setSchemaElement(this.getSchemaElement().cloneObject());
        }
        if ((this.getSchemaElementRefId() != null)) {
            clone.setSchemaElementRefId(this.getSchemaElementRefId());
        }
        if ((this.testConfigElementList != null)) {
            clone.testConfigElementList = this.testConfigElementList.cloneCollection();
        }
        if ((this.getReused() != null)) {
            clone.setReused(this.getReused().cloneObject());
        }
        if ((this.testScriptList != null)) {
            clone.testScriptList = this.testScriptList.cloneCollection();
        }
        if ((this.dependencyList != null)) {
            clone.dependencyList = this.dependencyList.cloneCollection();
        }
    }

    /**
     * Getter for the AttributeValueListJPA.
     *
     * @return the List<AttributeValue>.
     */
    List<AttributeValue> getAttributeValueListJPA() {
        if ((this.attributeValueList == null)) {
            this.attributeValueList = new NabuccoListImpl<AttributeValue>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<AttributeValue>) this.attributeValueList).getDelegate();
    }

    /**
     * Setter for the AttributeValueListJPA.
     *
     * @param attributeValueList the List<AttributeValue>.
     */
    void setAttributeValueListJPA(List<AttributeValue> attributeValueList) {
        if ((this.attributeValueList == null)) {
            this.attributeValueList = new NabuccoListImpl<AttributeValue>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<AttributeValue>) this.attributeValueList).setDelegate(attributeValueList);
    }

    /**
     * Getter for the TestConfigElementListJPA.
     *
     * @return the List<TestConfigElementContainer>.
     */
    List<TestConfigElementContainer> getTestConfigElementListJPA() {
        if ((this.testConfigElementList == null)) {
            this.testConfigElementList = new NabuccoListImpl<TestConfigElementContainer>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<TestConfigElementContainer>) this.testConfigElementList).getDelegate();
    }

    /**
     * Setter for the TestConfigElementListJPA.
     *
     * @param testConfigElementList the List<TestConfigElementContainer>.
     */
    void setTestConfigElementListJPA(List<TestConfigElementContainer> testConfigElementList) {
        if ((this.testConfigElementList == null)) {
            this.testConfigElementList = new NabuccoListImpl<TestConfigElementContainer>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<TestConfigElementContainer>) this.testConfigElementList).setDelegate(testConfigElementList);
    }

    /**
     * Getter for the TestScriptListJPA.
     *
     * @return the List<TestScriptContainer>.
     */
    List<TestScriptContainer> getTestScriptListJPA() {
        if ((this.testScriptList == null)) {
            this.testScriptList = new NabuccoListImpl<TestScriptContainer>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<TestScriptContainer>) this.testScriptList).getDelegate();
    }

    /**
     * Setter for the TestScriptListJPA.
     *
     * @param testScriptList the List<TestScriptContainer>.
     */
    void setTestScriptListJPA(List<TestScriptContainer> testScriptList) {
        if ((this.testScriptList == null)) {
            this.testScriptList = new NabuccoListImpl<TestScriptContainer>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<TestScriptContainer>) this.testScriptList).setDelegate(testScriptList);
    }

    /**
     * Getter for the DependencyListJPA.
     *
     * @return the List<Dependency>.
     */
    List<Dependency> getDependencyListJPA() {
        if ((this.dependencyList == null)) {
            this.dependencyList = new NabuccoListImpl<Dependency>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<Dependency>) this.dependencyList).getDelegate();
    }

    /**
     * Setter for the DependencyListJPA.
     *
     * @param dependencyList the List<Dependency>.
     */
    void setDependencyListJPA(List<Dependency> dependencyList) {
        if ((this.dependencyList == null)) {
            this.dependencyList = new NabuccoListImpl<Dependency>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<Dependency>) this.dependencyList).setDelegate(dependencyList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TestAutomationDatatype.class).getPropertyMap());
        propertyMap.put(EXECUTIONTYPE, PropertyDescriptorSupport.createEnumeration(EXECUTIONTYPE, ExecutionType.class,
                4, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 5, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 6,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(SKIP,
                PropertyDescriptorSupport.createBasetype(SKIP, Flag.class, 7, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(BRANDTYPE, PropertyDescriptorSupport.createDatatype(BRANDTYPE, Code.class, 8,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPONENT, BRANDTYPE_CODEPATH));
        propertyMap.put(ATTRIBUTEVALUELIST, PropertyDescriptorSupport.createCollection(ATTRIBUTEVALUELIST,
                AttributeValue.class, 9, PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DOCUMENTATION, PropertyDescriptorSupport.createBasetype(DOCUMENTATION, Documentation.class, 10,
                PROPERTY_CONSTRAINTS[6], false));
        propertyMap.put(PROPERTYLIST, PropertyDescriptorSupport.createDatatype(PROPERTYLIST, PropertyList.class, 11,
                PROPERTY_CONSTRAINTS[7], false, PropertyAssociationType.COMPONENT));
        propertyMap.put(SCHEMAELEMENT, PropertyDescriptorSupport.createDatatype(SCHEMAELEMENT, SchemaElement.class, 12,
                PROPERTY_CONSTRAINTS[8], false, PropertyAssociationType.COMPONENT));
        propertyMap.put(TESTCONFIGELEMENTLIST, PropertyDescriptorSupport.createCollection(TESTCONFIGELEMENTLIST,
                TestConfigElementContainer.class, 13, PROPERTY_CONSTRAINTS[9], false,
                PropertyAssociationType.COMPOSITION));
        propertyMap.put(REUSED,
                PropertyDescriptorSupport.createBasetype(REUSED, Flag.class, 14, PROPERTY_CONSTRAINTS[10], false));
        propertyMap.put(TESTSCRIPTLIST, PropertyDescriptorSupport.createCollection(TESTSCRIPTLIST,
                TestScriptContainer.class, 15, PROPERTY_CONSTRAINTS[11], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DEPENDENCYLIST, PropertyDescriptorSupport.createCollection(DEPENDENCYLIST, Dependency.class,
                16, PROPERTY_CONSTRAINTS[12], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TestConfigElement.getPropertyDescriptor(EXECUTIONTYPE),
                this.getExecutionType(), null));
        properties.add(super.createProperty(TestConfigElement.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(TestConfigElement.getPropertyDescriptor(DESCRIPTION), this.description,
                null));
        properties.add(super.createProperty(TestConfigElement.getPropertyDescriptor(SKIP), this.skip, null));
        properties.add(super.createProperty(TestConfigElement.getPropertyDescriptor(BRANDTYPE), this.getBrandType(),
                this.brandTypeRefId));
        properties.add(super.createProperty(TestConfigElement.getPropertyDescriptor(ATTRIBUTEVALUELIST),
                this.attributeValueList, null));
        properties.add(super.createProperty(TestConfigElement.getPropertyDescriptor(DOCUMENTATION), this.documentation,
                null));
        properties.add(super.createProperty(TestConfigElement.getPropertyDescriptor(PROPERTYLIST),
                this.getPropertyList(), this.propertyListRefId));
        properties.add(super.createProperty(TestConfigElement.getPropertyDescriptor(SCHEMAELEMENT),
                this.getSchemaElement(), this.schemaElementRefId));
        properties.add(super.createProperty(TestConfigElement.getPropertyDescriptor(TESTCONFIGELEMENTLIST),
                this.testConfigElementList, null));
        properties.add(super.createProperty(TestConfigElement.getPropertyDescriptor(REUSED), this.reused, null));
        properties.add(super.createProperty(TestConfigElement.getPropertyDescriptor(TESTSCRIPTLIST),
                this.testScriptList, null));
        properties.add(super.createProperty(TestConfigElement.getPropertyDescriptor(DEPENDENCYLIST),
                this.dependencyList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(EXECUTIONTYPE) && (property.getType() == ExecutionType.class))) {
            this.setExecutionType(((ExecutionType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SKIP) && (property.getType() == Flag.class))) {
            this.setSkip(((Flag) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BRANDTYPE) && (property.getType() == Code.class))) {
            this.setBrandType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ATTRIBUTEVALUELIST) && (property.getType() == AttributeValue.class))) {
            this.attributeValueList = ((NabuccoList<AttributeValue>) property.getInstance());
            return true;
        } else if ((property.getName().equals(DOCUMENTATION) && (property.getType() == Documentation.class))) {
            this.setDocumentation(((Documentation) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PROPERTYLIST) && (property.getType() == PropertyList.class))) {
            this.setPropertyList(((PropertyList) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SCHEMAELEMENT) && (property.getType() == SchemaElement.class))) {
            this.setSchemaElement(((SchemaElement) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TESTCONFIGELEMENTLIST) && (property.getType() == TestConfigElementContainer.class))) {
            this.testConfigElementList = ((NabuccoList<TestConfigElementContainer>) property.getInstance());
            return true;
        } else if ((property.getName().equals(REUSED) && (property.getType() == Flag.class))) {
            this.setReused(((Flag) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TESTSCRIPTLIST) && (property.getType() == TestScriptContainer.class))) {
            this.testScriptList = ((NabuccoList<TestScriptContainer>) property.getInstance());
            return true;
        } else if ((property.getName().equals(DEPENDENCYLIST) && (property.getType() == Dependency.class))) {
            this.dependencyList = ((NabuccoList<Dependency>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final TestConfigElement other = ((TestConfigElement) obj);
        if ((this.executionType == null)) {
            if ((other.executionType != null))
                return false;
        } else if ((!this.executionType.equals(other.executionType)))
            return false;
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.skip == null)) {
            if ((other.skip != null))
                return false;
        } else if ((!this.skip.equals(other.skip)))
            return false;
        if ((this.brandType == null)) {
            if ((other.brandType != null))
                return false;
        } else if ((!this.brandType.equals(other.brandType)))
            return false;
        if ((this.brandTypeRefId == null)) {
            if ((other.brandTypeRefId != null))
                return false;
        } else if ((!this.brandTypeRefId.equals(other.brandTypeRefId)))
            return false;
        if ((this.documentation == null)) {
            if ((other.documentation != null))
                return false;
        } else if ((!this.documentation.equals(other.documentation)))
            return false;
        if ((this.propertyList == null)) {
            if ((other.propertyList != null))
                return false;
        } else if ((!this.propertyList.equals(other.propertyList)))
            return false;
        if ((this.propertyListRefId == null)) {
            if ((other.propertyListRefId != null))
                return false;
        } else if ((!this.propertyListRefId.equals(other.propertyListRefId)))
            return false;
        if ((this.schemaElement == null)) {
            if ((other.schemaElement != null))
                return false;
        } else if ((!this.schemaElement.equals(other.schemaElement)))
            return false;
        if ((this.schemaElementRefId == null)) {
            if ((other.schemaElementRefId != null))
                return false;
        } else if ((!this.schemaElementRefId.equals(other.schemaElementRefId)))
            return false;
        if ((this.reused == null)) {
            if ((other.reused != null))
                return false;
        } else if ((!this.reused.equals(other.reused)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.executionType == null) ? 0 : this.executionType.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.skip == null) ? 0 : this.skip.hashCode()));
        result = ((PRIME * result) + ((this.brandType == null) ? 0 : this.brandType.hashCode()));
        result = ((PRIME * result) + ((this.brandTypeRefId == null) ? 0 : this.brandTypeRefId.hashCode()));
        result = ((PRIME * result) + ((this.documentation == null) ? 0 : this.documentation.hashCode()));
        result = ((PRIME * result) + ((this.propertyList == null) ? 0 : this.propertyList.hashCode()));
        result = ((PRIME * result) + ((this.propertyListRefId == null) ? 0 : this.propertyListRefId.hashCode()));
        result = ((PRIME * result) + ((this.schemaElement == null) ? 0 : this.schemaElement.hashCode()));
        result = ((PRIME * result) + ((this.schemaElementRefId == null) ? 0 : this.schemaElementRefId.hashCode()));
        result = ((PRIME * result) + ((this.reused == null) ? 0 : this.reused.hashCode()));
        return result;
    }

    @Override
    public TestConfigElement cloneObject() {
        TestConfigElement clone = new TestConfigElement();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getExecutionType.
     *
     * @return the ExecutionType.
     */
    public ExecutionType getExecutionType() {
        return this.executionType;
    }

    /**
     * Missing description at method setExecutionType.
     *
     * @param executionType the ExecutionType.
     */
    public void setExecutionType(ExecutionType executionType) {
        this.executionType = executionType;
    }

    /**
     * Missing description at method setExecutionType.
     *
     * @param executionType the String.
     */
    public void setExecutionType(String executionType) {
        if ((executionType == null)) {
            this.executionType = null;
        } else {
            this.executionType = ExecutionType.valueOf(executionType);
        }
    }

    /**
     * Missing description at method getName.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Missing description at method setName.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Missing description at method setName.
     *
     * @param name the String.
     */
    public void setName(String name) {
        if ((this.name == null)) {
            if ((name == null)) {
                return;
            }
            this.name = new Name();
        }
        this.name.setValue(name);
    }

    /**
     * Missing description at method getDescription.
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Missing description at method setDescription.
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Missing description at method setDescription.
     *
     * @param description the String.
     */
    public void setDescription(String description) {
        if ((this.description == null)) {
            if ((description == null)) {
                return;
            }
            this.description = new Description();
        }
        this.description.setValue(description);
    }

    /**
     * Missing description at method getSkip.
     *
     * @return the Flag.
     */
    public Flag getSkip() {
        return this.skip;
    }

    /**
     * Missing description at method setSkip.
     *
     * @param skip the Flag.
     */
    public void setSkip(Flag skip) {
        this.skip = skip;
    }

    /**
     * Missing description at method setSkip.
     *
     * @param skip the Boolean.
     */
    public void setSkip(Boolean skip) {
        if ((this.skip == null)) {
            if ((skip == null)) {
                return;
            }
            this.skip = new Flag();
        }
        this.skip.setValue(skip);
    }

    /**
     * Brand of the TestConfiguration
     *
     * @param brandType the Code.
     */
    public void setBrandType(Code brandType) {
        this.brandType = brandType;
        if ((brandType != null)) {
            this.setBrandTypeRefId(brandType.getId());
        } else {
            this.setBrandTypeRefId(null);
        }
    }

    /**
     * Brand of the TestConfiguration
     *
     * @return the Code.
     */
    public Code getBrandType() {
        return this.brandType;
    }

    /**
     * Getter for the BrandTypeRefId.
     *
     * @return the Long.
     */
    public Long getBrandTypeRefId() {
        return this.brandTypeRefId;
    }

    /**
     * Setter for the BrandTypeRefId.
     *
     * @param brandTypeRefId the Long.
     */
    public void setBrandTypeRefId(Long brandTypeRefId) {
        this.brandTypeRefId = brandTypeRefId;
    }

    /**
     * Missing description at method getAttributeValueList.
     *
     * @return the NabuccoList<AttributeValue>.
     */
    public NabuccoList<AttributeValue> getAttributeValueList() {
        if ((this.attributeValueList == null)) {
            this.attributeValueList = new NabuccoListImpl<AttributeValue>(NabuccoCollectionState.INITIALIZED);
        }
        return this.attributeValueList;
    }

    /**
     * Missing description at method getDocumentation.
     *
     * @return the Documentation.
     */
    public Documentation getDocumentation() {
        return this.documentation;
    }

    /**
     * Missing description at method setDocumentation.
     *
     * @param documentation the Documentation.
     */
    public void setDocumentation(Documentation documentation) {
        this.documentation = documentation;
    }

    /**
     * Missing description at method setDocumentation.
     *
     * @param documentation the String.
     */
    public void setDocumentation(String documentation) {
        if ((this.documentation == null)) {
            if ((documentation == null)) {
                return;
            }
            this.documentation = new Documentation();
        }
        this.documentation.setValue(documentation);
    }

    /**
     * Missing description at method setPropertyList.
     *
     * @param propertyList the PropertyList.
     */
    public void setPropertyList(PropertyList propertyList) {
        this.propertyList = propertyList;
        if ((propertyList != null)) {
            this.setPropertyListRefId(propertyList.getId());
        } else {
            this.setPropertyListRefId(null);
        }
    }

    /**
     * Missing description at method getPropertyList.
     *
     * @return the PropertyList.
     */
    public PropertyList getPropertyList() {
        return this.propertyList;
    }

    /**
     * Getter for the PropertyListRefId.
     *
     * @return the Long.
     */
    public Long getPropertyListRefId() {
        return this.propertyListRefId;
    }

    /**
     * Setter for the PropertyListRefId.
     *
     * @param propertyListRefId the Long.
     */
    public void setPropertyListRefId(Long propertyListRefId) {
        this.propertyListRefId = propertyListRefId;
    }

    /**
     * Missing description at method setSchemaElement.
     *
     * @param schemaElement the SchemaElement.
     */
    public void setSchemaElement(SchemaElement schemaElement) {
        this.schemaElement = schemaElement;
        if ((schemaElement != null)) {
            this.setSchemaElementRefId(schemaElement.getId());
        } else {
            this.setSchemaElementRefId(null);
        }
    }

    /**
     * Missing description at method getSchemaElement.
     *
     * @return the SchemaElement.
     */
    public SchemaElement getSchemaElement() {
        return this.schemaElement;
    }

    /**
     * Getter for the SchemaElementRefId.
     *
     * @return the Long.
     */
    public Long getSchemaElementRefId() {
        return this.schemaElementRefId;
    }

    /**
     * Setter for the SchemaElementRefId.
     *
     * @param schemaElementRefId the Long.
     */
    public void setSchemaElementRefId(Long schemaElementRefId) {
        this.schemaElementRefId = schemaElementRefId;
    }

    /**
     * Missing description at method getTestConfigElementList.
     *
     * @return the NabuccoList<TestConfigElementContainer>.
     */
    public NabuccoList<TestConfigElementContainer> getTestConfigElementList() {
        if ((this.testConfigElementList == null)) {
            this.testConfigElementList = new NabuccoListImpl<TestConfigElementContainer>(
                    NabuccoCollectionState.INITIALIZED);
        }
        return this.testConfigElementList;
    }

    /**
     * Missing description at method getReused.
     *
     * @return the Flag.
     */
    public Flag getReused() {
        return this.reused;
    }

    /**
     * Missing description at method setReused.
     *
     * @param reused the Flag.
     */
    public void setReused(Flag reused) {
        this.reused = reused;
    }

    /**
     * Missing description at method setReused.
     *
     * @param reused the Boolean.
     */
    public void setReused(Boolean reused) {
        if ((this.reused == null)) {
            if ((reused == null)) {
                return;
            }
            this.reused = new Flag();
        }
        this.reused.setValue(reused);
    }

    /**
     * Missing description at method getTestScriptList.
     *
     * @return the NabuccoList<TestScriptContainer>.
     */
    public NabuccoList<TestScriptContainer> getTestScriptList() {
        if ((this.testScriptList == null)) {
            this.testScriptList = new NabuccoListImpl<TestScriptContainer>(NabuccoCollectionState.INITIALIZED);
        }
        return this.testScriptList;
    }

    /**
     * Missing description at method getDependencyList.
     *
     * @return the NabuccoList<Dependency>.
     */
    public NabuccoList<Dependency> getDependencyList() {
        if ((this.dependencyList == null)) {
            this.dependencyList = new NabuccoListImpl<Dependency>(NabuccoCollectionState.INITIALIZED);
        }
        return this.dependencyList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TestConfigElement.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TestConfigElement.class).getAllProperties();
    }

    /**
     * Getter for the BrandTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getBrandTypeCodePath() {
        return new CodePath(BRANDTYPE_CODEPATH);
    }
}
