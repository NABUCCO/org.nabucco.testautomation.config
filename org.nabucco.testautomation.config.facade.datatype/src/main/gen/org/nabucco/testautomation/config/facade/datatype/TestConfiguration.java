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
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;
import org.nabucco.testautomation.property.facade.datatype.base.TestAutomationDatatype;
import org.nabucco.testautomation.schema.facade.datatype.SchemaConfig;
import org.nabucco.testautomation.settings.facade.datatype.engine.TestEngineConfiguration;

/**
 * TestConfiguration<p/>A Configuration containing a complete Test<p/>
 *
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-14
 */
public class TestConfiguration extends TestAutomationDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,255;u0,n;m0,1;", "m0,n;", "m1,1;",
            "m0,1;", "m0,1;", "m0,1;" };

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String TESTCONFIGELEMENTLIST = "testConfigElementList";

    public static final String SCHEMACONFIG = "schemaConfig";

    public static final String ENVIRONMENTTYPE = "environmentType";

    public static final String RELEASETYPE = "releaseType";

    public static final String TESTENGINECONFIGURATION = "testEngineConfiguration";

    private Name name;

    private Description description;

    private NabuccoList<TestConfigElementContainer> testConfigElementList;

    private SchemaConfig schemaConfig;

    private Long schemaConfigRefId;

    /** Environment of the TestConfiguration */
    private Code environmentType;

    private Long environmentTypeRefId;

    protected static final String ENVIRONMENTTYPE_CODEPATH = "nabucco.testautomation.environment";

    /** Release of the TestConfiguration */
    private Code releaseType;

    private Long releaseTypeRefId;

    protected static final String RELEASETYPE_CODEPATH = "nabucco.testautomation.release";

    private TestEngineConfiguration testEngineConfiguration;

    /** Constructs a new TestConfiguration instance. */
    public TestConfiguration() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TestConfiguration.
     */
    protected void cloneObject(TestConfiguration clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.testConfigElementList != null)) {
            clone.testConfigElementList = this.testConfigElementList.cloneCollection();
        }
        if ((this.getSchemaConfig() != null)) {
            clone.setSchemaConfig(this.getSchemaConfig().cloneObject());
        }
        if ((this.getSchemaConfigRefId() != null)) {
            clone.setSchemaConfigRefId(this.getSchemaConfigRefId());
        }
        if ((this.getEnvironmentType() != null)) {
            clone.setEnvironmentType(this.getEnvironmentType().cloneObject());
        }
        if ((this.getEnvironmentTypeRefId() != null)) {
            clone.setEnvironmentTypeRefId(this.getEnvironmentTypeRefId());
        }
        if ((this.getReleaseType() != null)) {
            clone.setReleaseType(this.getReleaseType().cloneObject());
        }
        if ((this.getReleaseTypeRefId() != null)) {
            clone.setReleaseTypeRefId(this.getReleaseTypeRefId());
        }
        if ((this.getTestEngineConfiguration() != null)) {
            clone.setTestEngineConfiguration(this.getTestEngineConfiguration().cloneObject());
        }
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
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TestAutomationDatatype.class).getPropertyMap());
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 4, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 5,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(TESTCONFIGELEMENTLIST, PropertyDescriptorSupport.createCollection(TESTCONFIGELEMENTLIST,
                TestConfigElementContainer.class, 6, PROPERTY_CONSTRAINTS[2], false,
                PropertyAssociationType.COMPOSITION));
        propertyMap.put(SCHEMACONFIG, PropertyDescriptorSupport.createDatatype(SCHEMACONFIG, SchemaConfig.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPONENT));
        propertyMap.put(ENVIRONMENTTYPE, PropertyDescriptorSupport.createDatatype(ENVIRONMENTTYPE, Code.class, 8,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPONENT, ENVIRONMENTTYPE_CODEPATH));
        propertyMap.put(RELEASETYPE, PropertyDescriptorSupport.createDatatype(RELEASETYPE, Code.class, 9,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPONENT, RELEASETYPE_CODEPATH));
        propertyMap.put(TESTENGINECONFIGURATION, PropertyDescriptorSupport.createDatatype(TESTENGINECONFIGURATION,
                TestEngineConfiguration.class, 10, PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPONENT));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TestConfiguration.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(TestConfiguration.getPropertyDescriptor(DESCRIPTION), this.description,
                null));
        properties.add(super.createProperty(TestConfiguration.getPropertyDescriptor(TESTCONFIGELEMENTLIST),
                this.testConfigElementList, null));
        properties.add(super.createProperty(TestConfiguration.getPropertyDescriptor(SCHEMACONFIG),
                this.getSchemaConfig(), this.schemaConfigRefId));
        properties.add(super.createProperty(TestConfiguration.getPropertyDescriptor(ENVIRONMENTTYPE),
                this.getEnvironmentType(), this.environmentTypeRefId));
        properties.add(super.createProperty(TestConfiguration.getPropertyDescriptor(RELEASETYPE),
                this.getReleaseType(), this.releaseTypeRefId));
        properties.add(super.createProperty(TestConfiguration.getPropertyDescriptor(TESTENGINECONFIGURATION),
                this.getTestEngineConfiguration(), null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TESTCONFIGELEMENTLIST) && (property.getType() == TestConfigElementContainer.class))) {
            this.testConfigElementList = ((NabuccoList<TestConfigElementContainer>) property.getInstance());
            return true;
        } else if ((property.getName().equals(SCHEMACONFIG) && (property.getType() == SchemaConfig.class))) {
            this.setSchemaConfig(((SchemaConfig) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ENVIRONMENTTYPE) && (property.getType() == Code.class))) {
            this.setEnvironmentType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(RELEASETYPE) && (property.getType() == Code.class))) {
            this.setReleaseType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TESTENGINECONFIGURATION) && (property.getType() == TestEngineConfiguration.class))) {
            this.setTestEngineConfiguration(((TestEngineConfiguration) property.getInstance()));
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
        final TestConfiguration other = ((TestConfiguration) obj);
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
        if ((this.schemaConfig == null)) {
            if ((other.schemaConfig != null))
                return false;
        } else if ((!this.schemaConfig.equals(other.schemaConfig)))
            return false;
        if ((this.schemaConfigRefId == null)) {
            if ((other.schemaConfigRefId != null))
                return false;
        } else if ((!this.schemaConfigRefId.equals(other.schemaConfigRefId)))
            return false;
        if ((this.environmentType == null)) {
            if ((other.environmentType != null))
                return false;
        } else if ((!this.environmentType.equals(other.environmentType)))
            return false;
        if ((this.environmentTypeRefId == null)) {
            if ((other.environmentTypeRefId != null))
                return false;
        } else if ((!this.environmentTypeRefId.equals(other.environmentTypeRefId)))
            return false;
        if ((this.releaseType == null)) {
            if ((other.releaseType != null))
                return false;
        } else if ((!this.releaseType.equals(other.releaseType)))
            return false;
        if ((this.releaseTypeRefId == null)) {
            if ((other.releaseTypeRefId != null))
                return false;
        } else if ((!this.releaseTypeRefId.equals(other.releaseTypeRefId)))
            return false;
        if ((this.testEngineConfiguration == null)) {
            if ((other.testEngineConfiguration != null))
                return false;
        } else if ((!this.testEngineConfiguration.equals(other.testEngineConfiguration)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.schemaConfig == null) ? 0 : this.schemaConfig.hashCode()));
        result = ((PRIME * result) + ((this.schemaConfigRefId == null) ? 0 : this.schemaConfigRefId.hashCode()));
        result = ((PRIME * result) + ((this.environmentType == null) ? 0 : this.environmentType.hashCode()));
        result = ((PRIME * result) + ((this.environmentTypeRefId == null) ? 0 : this.environmentTypeRefId.hashCode()));
        result = ((PRIME * result) + ((this.releaseType == null) ? 0 : this.releaseType.hashCode()));
        result = ((PRIME * result) + ((this.releaseTypeRefId == null) ? 0 : this.releaseTypeRefId.hashCode()));
        result = ((PRIME * result) + ((this.testEngineConfiguration == null) ? 0 : this.testEngineConfiguration
                .hashCode()));
        return result;
    }

    @Override
    public TestConfiguration cloneObject() {
        TestConfiguration clone = new TestConfiguration();
        this.cloneObject(clone);
        return clone;
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
     * Missing description at method setSchemaConfig.
     *
     * @param schemaConfig the SchemaConfig.
     */
    public void setSchemaConfig(SchemaConfig schemaConfig) {
        this.schemaConfig = schemaConfig;
        if ((schemaConfig != null)) {
            this.setSchemaConfigRefId(schemaConfig.getId());
        } else {
            this.setSchemaConfigRefId(null);
        }
    }

    /**
     * Missing description at method getSchemaConfig.
     *
     * @return the SchemaConfig.
     */
    public SchemaConfig getSchemaConfig() {
        return this.schemaConfig;
    }

    /**
     * Getter for the SchemaConfigRefId.
     *
     * @return the Long.
     */
    public Long getSchemaConfigRefId() {
        return this.schemaConfigRefId;
    }

    /**
     * Setter for the SchemaConfigRefId.
     *
     * @param schemaConfigRefId the Long.
     */
    public void setSchemaConfigRefId(Long schemaConfigRefId) {
        this.schemaConfigRefId = schemaConfigRefId;
    }

    /**
     * Environment of the TestConfiguration
     *
     * @param environmentType the Code.
     */
    public void setEnvironmentType(Code environmentType) {
        this.environmentType = environmentType;
        if ((environmentType != null)) {
            this.setEnvironmentTypeRefId(environmentType.getId());
        } else {
            this.setEnvironmentTypeRefId(null);
        }
    }

    /**
     * Environment of the TestConfiguration
     *
     * @return the Code.
     */
    public Code getEnvironmentType() {
        return this.environmentType;
    }

    /**
     * Getter for the EnvironmentTypeRefId.
     *
     * @return the Long.
     */
    public Long getEnvironmentTypeRefId() {
        return this.environmentTypeRefId;
    }

    /**
     * Setter for the EnvironmentTypeRefId.
     *
     * @param environmentTypeRefId the Long.
     */
    public void setEnvironmentTypeRefId(Long environmentTypeRefId) {
        this.environmentTypeRefId = environmentTypeRefId;
    }

    /**
     * Release of the TestConfiguration
     *
     * @param releaseType the Code.
     */
    public void setReleaseType(Code releaseType) {
        this.releaseType = releaseType;
        if ((releaseType != null)) {
            this.setReleaseTypeRefId(releaseType.getId());
        } else {
            this.setReleaseTypeRefId(null);
        }
    }

    /**
     * Release of the TestConfiguration
     *
     * @return the Code.
     */
    public Code getReleaseType() {
        return this.releaseType;
    }

    /**
     * Getter for the ReleaseTypeRefId.
     *
     * @return the Long.
     */
    public Long getReleaseTypeRefId() {
        return this.releaseTypeRefId;
    }

    /**
     * Setter for the ReleaseTypeRefId.
     *
     * @param releaseTypeRefId the Long.
     */
    public void setReleaseTypeRefId(Long releaseTypeRefId) {
        this.releaseTypeRefId = releaseTypeRefId;
    }

    /**
     * Missing description at method setTestEngineConfiguration.
     *
     * @param testEngineConfiguration the TestEngineConfiguration.
     */
    public void setTestEngineConfiguration(TestEngineConfiguration testEngineConfiguration) {
        this.testEngineConfiguration = testEngineConfiguration;
    }

    /**
     * Missing description at method getTestEngineConfiguration.
     *
     * @return the TestEngineConfiguration.
     */
    public TestEngineConfiguration getTestEngineConfiguration() {
        return this.testEngineConfiguration;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TestConfiguration.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TestConfiguration.class).getAllProperties();
    }

    /**
     * Getter for the EnvironmentTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getEnvironmentTypeCodePath() {
        return new CodePath(ENVIRONMENTTYPE_CODEPATH);
    }

    /**
     * Getter for the ReleaseTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getReleaseTypeCodePath() {
        return new CodePath(RELEASETYPE_CODEPATH);
    }
}
