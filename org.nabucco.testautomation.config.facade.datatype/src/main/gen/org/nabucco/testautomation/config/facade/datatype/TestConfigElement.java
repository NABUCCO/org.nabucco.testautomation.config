/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.datatype;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.Key;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.documentation.Documentation;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.EnumProperty;
import org.nabucco.framework.base.facade.datatype.property.ListProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.testautomation.config.facade.datatype.Dependency;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.config.facade.datatype.TestScriptContainer;
import org.nabucco.testautomation.config.facade.datatype.attribute.AttributeValue;
import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.result.facade.datatype.ExecutionType;
import org.nabucco.testautomation.schema.facade.datatype.SchemaElement;

/**
 * TestConfigElement<p/>One Element of a test configuration<p/>
 *
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-14
 */
public class TestConfigElement extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "executionType", "skip", "name", "elementKey",
            "brandType", "description", "attributeValueList", "documentation", "propertyList",
            "schemaElement", "testConfigElementList", "dependencyList", "reused", "testScriptList" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "l0,n;m0,1;", "l0,n;m1,1;",
            "l0,16;m0,1;", "m0,1;", "l0,n;m0,1;", "m0,n;", "l0,100000;m0,1;", "m0,1;", "m1,1;",
            "m0,n;", "m0,n;", "l0,n;m0,1;", "m0,n;" };

    private ExecutionType executionType;

    private Flag skip;

    private Name name;

    private Key elementKey;

    /** Brand of the TestConfiguration */
    private Code brandType;

    private Long brandTypeRefId;

    private static final String BRANDTYPE_CODEPATH = "nabucco.testautomation.brand";

    private Description description;

    private List<AttributeValue> attributeValueList;

    private Documentation documentation;

    private PropertyList propertyList;

    private Long propertyListRefId;

    private SchemaElement schemaElement;

    private Long schemaElementRefId;

    private List<TestConfigElementContainer> testConfigElementList;

    private List<Dependency> dependencyList;

    private Flag reused;

    private List<TestScriptContainer> testScriptList;

    /** Constructs a new TestConfigElement instance. */
    public TestConfigElement() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        executionType = ExecutionType.AUTOMATED;
    }

    /**
     * CloneObject.
     *
     * @param clone the TestConfigElement.
     */
    protected void cloneObject(TestConfigElement clone) {
        super.cloneObject(clone);
        clone.setExecutionType(this.getExecutionType());
        if ((this.getSkip() != null)) {
            clone.setSkip(this.getSkip().cloneObject());
        }
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getElementKey() != null)) {
            clone.setElementKey(this.getElementKey().cloneObject());
        }
        if ((this.getBrandType() != null)) {
            clone.setBrandType(this.getBrandType().cloneObject());
        }
        if ((this.getBrandTypeRefId() != null)) {
            clone.setBrandTypeRefId(this.getBrandTypeRefId());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.attributeValueList instanceof NabuccoList<?>)) {
            clone.attributeValueList = ((NabuccoList<AttributeValue>) this.attributeValueList)
                    .cloneCollection();
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
        if ((this.testConfigElementList instanceof NabuccoList<?>)) {
            clone.testConfigElementList = ((NabuccoList<TestConfigElementContainer>) this.testConfigElementList)
                    .cloneCollection();
        }
        if ((this.dependencyList instanceof NabuccoList<?>)) {
            clone.dependencyList = ((NabuccoList<Dependency>) this.dependencyList)
                    .cloneCollection();
        }
        if ((this.getReused() != null)) {
            clone.setReused(this.getReused().cloneObject());
        }
        if ((this.testScriptList instanceof NabuccoList<?>)) {
            clone.testScriptList = ((NabuccoList<TestScriptContainer>) this.testScriptList)
                    .cloneCollection();
        }
    }

    /**
     * Getter for the AttributeValueListJPA.
     *
     * @return the List<AttributeValue>.
     */
    List<AttributeValue> getAttributeValueListJPA() {
        if ((this.attributeValueList == null)) {
            this.attributeValueList = new NabuccoList<AttributeValue>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoList<AttributeValue>) this.attributeValueList).getDelegate();
    }

    /**
     * Setter for the AttributeValueListJPA.
     *
     * @param attributeValueList the List<AttributeValue>.
     */
    void setAttributeValueListJPA(List<AttributeValue> attributeValueList) {
        if ((this.attributeValueList == null)) {
            this.attributeValueList = new NabuccoList<AttributeValue>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoList<AttributeValue>) this.attributeValueList).setDelegate(attributeValueList);
    }

    /**
     * Getter for the TestConfigElementListJPA.
     *
     * @return the List<TestConfigElementContainer>.
     */
    List<TestConfigElementContainer> getTestConfigElementListJPA() {
        if ((this.testConfigElementList == null)) {
            this.testConfigElementList = new NabuccoList<TestConfigElementContainer>(
                    NabuccoCollectionState.LAZY);
        }
        return ((NabuccoList<TestConfigElementContainer>) this.testConfigElementList).getDelegate();
    }

    /**
     * Setter for the TestConfigElementListJPA.
     *
     * @param testConfigElementList the List<TestConfigElementContainer>.
     */
    void setTestConfigElementListJPA(List<TestConfigElementContainer> testConfigElementList) {
        if ((this.testConfigElementList == null)) {
            this.testConfigElementList = new NabuccoList<TestConfigElementContainer>(
                    NabuccoCollectionState.LAZY);
        }
        ((NabuccoList<TestConfigElementContainer>) this.testConfigElementList)
                .setDelegate(testConfigElementList);
    }

    /**
     * Getter for the DependencyListJPA.
     *
     * @return the List<Dependency>.
     */
    List<Dependency> getDependencyListJPA() {
        if ((this.dependencyList == null)) {
            this.dependencyList = new NabuccoList<Dependency>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoList<Dependency>) this.dependencyList).getDelegate();
    }

    /**
     * Setter for the DependencyListJPA.
     *
     * @param dependencyList the List<Dependency>.
     */
    void setDependencyListJPA(List<Dependency> dependencyList) {
        if ((this.dependencyList == null)) {
            this.dependencyList = new NabuccoList<Dependency>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoList<Dependency>) this.dependencyList).setDelegate(dependencyList);
    }

    /**
     * Getter for the TestScriptListJPA.
     *
     * @return the List<TestScriptContainer>.
     */
    List<TestScriptContainer> getTestScriptListJPA() {
        if ((this.testScriptList == null)) {
            this.testScriptList = new NabuccoList<TestScriptContainer>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoList<TestScriptContainer>) this.testScriptList).getDelegate();
    }

    /**
     * Setter for the TestScriptListJPA.
     *
     * @param testScriptList the List<TestScriptContainer>.
     */
    void setTestScriptListJPA(List<TestScriptContainer> testScriptList) {
        if ((this.testScriptList == null)) {
            this.testScriptList = new NabuccoList<TestScriptContainer>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoList<TestScriptContainer>) this.testScriptList).setDelegate(testScriptList);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new EnumProperty<ExecutionType>(PROPERTY_NAMES[0], ExecutionType.class,
                PROPERTY_CONSTRAINTS[0], this.executionType));
        properties.add(new BasetypeProperty<Flag>(PROPERTY_NAMES[1], Flag.class,
                PROPERTY_CONSTRAINTS[1], this.skip));
        properties.add(new BasetypeProperty<Name>(PROPERTY_NAMES[2], Name.class,
                PROPERTY_CONSTRAINTS[2], this.name));
        properties.add(new BasetypeProperty<Key>(PROPERTY_NAMES[3], Key.class,
                PROPERTY_CONSTRAINTS[3], this.elementKey));
        properties.add(new DatatypeProperty<Code>(PROPERTY_NAMES[4], Code.class,
                PROPERTY_CONSTRAINTS[4], this.brandType));
        properties.add(new BasetypeProperty<Description>(PROPERTY_NAMES[5], Description.class,
                PROPERTY_CONSTRAINTS[5], this.description));
        properties.add(new ListProperty<AttributeValue>(PROPERTY_NAMES[6], AttributeValue.class,
                PROPERTY_CONSTRAINTS[6], this.attributeValueList));
        properties.add(new BasetypeProperty<Documentation>(PROPERTY_NAMES[7], Documentation.class,
                PROPERTY_CONSTRAINTS[7], this.documentation));
        properties.add(new DatatypeProperty<PropertyList>(PROPERTY_NAMES[8], PropertyList.class,
                PROPERTY_CONSTRAINTS[8], this.propertyList));
        properties.add(new DatatypeProperty<SchemaElement>(PROPERTY_NAMES[9], SchemaElement.class,
                PROPERTY_CONSTRAINTS[9], this.schemaElement));
        properties.add(new ListProperty<TestConfigElementContainer>(PROPERTY_NAMES[10],
                TestConfigElementContainer.class, PROPERTY_CONSTRAINTS[10],
                this.testConfigElementList));
        properties.add(new ListProperty<Dependency>(PROPERTY_NAMES[11], Dependency.class,
                PROPERTY_CONSTRAINTS[11], this.dependencyList));
        properties.add(new BasetypeProperty<Flag>(PROPERTY_NAMES[12], Flag.class,
                PROPERTY_CONSTRAINTS[12], this.reused));
        properties.add(new ListProperty<TestScriptContainer>(PROPERTY_NAMES[13],
                TestScriptContainer.class, PROPERTY_CONSTRAINTS[13], this.testScriptList));
        return properties;
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
        if ((this.skip == null)) {
            if ((other.skip != null))
                return false;
        } else if ((!this.skip.equals(other.skip)))
            return false;
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.elementKey == null)) {
            if ((other.elementKey != null))
                return false;
        } else if ((!this.elementKey.equals(other.elementKey)))
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
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
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
        result = ((PRIME * result) + ((this.executionType == null) ? 0 : this.executionType
                .hashCode()));
        result = ((PRIME * result) + ((this.skip == null) ? 0 : this.skip.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.elementKey == null) ? 0 : this.elementKey.hashCode()));
        result = ((PRIME * result) + ((this.brandType == null) ? 0 : this.brandType.hashCode()));
        result = ((PRIME * result) + ((this.brandTypeRefId == null) ? 0 : this.brandTypeRefId
                .hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.documentation == null) ? 0 : this.documentation
                .hashCode()));
        result = ((PRIME * result) + ((this.propertyList == null) ? 0 : this.propertyList
                .hashCode()));
        result = ((PRIME * result) + ((this.propertyListRefId == null) ? 0 : this.propertyListRefId
                .hashCode()));
        result = ((PRIME * result) + ((this.schemaElement == null) ? 0 : this.schemaElement
                .hashCode()));
        result = ((PRIME * result) + ((this.schemaElementRefId == null) ? 0
                : this.schemaElementRefId.hashCode()));
        result = ((PRIME * result) + ((this.reused == null) ? 0 : this.reused.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<TestConfigElement>\n");
        appendable.append(super.toString());
        appendable.append((("<executionType>" + this.executionType) + "</executionType>\n"));
        appendable.append((("<skip>" + this.skip) + "</skip>\n"));
        appendable.append((("<name>" + this.name) + "</name>\n"));
        appendable.append((("<elementKey>" + this.elementKey) + "</elementKey>\n"));
        appendable.append((("<brandType>" + this.brandType) + "</brandType>\n"));
        appendable.append((("<brandTypeRefId>" + this.brandTypeRefId) + "</brandTypeRefId>\n"));
        appendable.append((("<description>" + this.description) + "</description>\n"));
        appendable.append((("<documentation>" + this.documentation) + "</documentation>\n"));
        appendable.append((("<propertyList>" + this.propertyList) + "</propertyList>\n"));
        appendable
                .append((("<propertyListRefId>" + this.propertyListRefId) + "</propertyListRefId>\n"));
        appendable.append((("<schemaElement>" + this.schemaElement) + "</schemaElement>\n"));
        appendable
                .append((("<schemaElementRefId>" + this.schemaElementRefId) + "</schemaElementRefId>\n"));
        appendable.append((("<reused>" + this.reused) + "</reused>\n"));
        appendable.append("</TestConfigElement>\n");
        return appendable.toString();
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
            this.skip = new Flag();
        }
        this.skip.setValue(skip);
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
            this.name = new Name();
        }
        this.name.setValue(name);
    }

    /**
     * Missing description at method getElementKey.
     *
     * @return the Key.
     */
    public Key getElementKey() {
        return this.elementKey;
    }

    /**
     * Missing description at method setElementKey.
     *
     * @param elementKey the Key.
     */
    public void setElementKey(Key elementKey) {
        this.elementKey = elementKey;
    }

    /**
     * Missing description at method setElementKey.
     *
     * @param elementKey the String.
     */
    public void setElementKey(String elementKey) {
        if ((this.elementKey == null)) {
            this.elementKey = new Key();
        }
        this.elementKey.setValue(elementKey);
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
            this.description = new Description();
        }
        this.description.setValue(description);
    }

    /**
     * Missing description at method getAttributeValueList.
     *
     * @return the List<AttributeValue>.
     */
    public List<AttributeValue> getAttributeValueList() {
        if ((this.attributeValueList == null)) {
            this.attributeValueList = new NabuccoList<AttributeValue>(
                    NabuccoCollectionState.INITIALIZED);
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
     * @return the List<TestConfigElementContainer>.
     */
    public List<TestConfigElementContainer> getTestConfigElementList() {
        if ((this.testConfigElementList == null)) {
            this.testConfigElementList = new NabuccoList<TestConfigElementContainer>(
                    NabuccoCollectionState.INITIALIZED);
        }
        return this.testConfigElementList;
    }

    /**
     * Missing description at method getDependencyList.
     *
     * @return the List<Dependency>.
     */
    public List<Dependency> getDependencyList() {
        if ((this.dependencyList == null)) {
            this.dependencyList = new NabuccoList<Dependency>(NabuccoCollectionState.INITIALIZED);
        }
        return this.dependencyList;
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
            this.reused = new Flag();
        }
        this.reused.setValue(reused);
    }

    /**
     * Missing description at method getTestScriptList.
     *
     * @return the List<TestScriptContainer>.
     */
    public List<TestScriptContainer> getTestScriptList() {
        if ((this.testScriptList == null)) {
            this.testScriptList = new NabuccoList<TestScriptContainer>(
                    NabuccoCollectionState.INITIALIZED);
        }
        return this.testScriptList;
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
