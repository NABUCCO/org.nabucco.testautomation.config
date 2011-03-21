/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;

/**
 * TestConfigurationMsg<p/>Message for persisting a TestConfiguration<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class TestConfigurationMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "l0,n;m0,1;" };

    public static final String TESTCONFIGURATION = "testConfiguration";

    public static final String IMPORTCONFIG = "importConfig";

    private TestConfiguration testConfiguration;

    private Flag importConfig;

    /** Constructs a new TestConfigurationMsg instance. */
    public TestConfigurationMsg() {
        super();
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(TESTCONFIGURATION, PropertyDescriptorSupport.createDatatype(
                TESTCONFIGURATION, TestConfiguration.class, 0, PROPERTY_CONSTRAINTS[0], false,
                PropertyAssociationType.COMPOSITION));
        propertyMap.put(IMPORTCONFIG, PropertyDescriptorSupport.createBasetype(IMPORTCONFIG,
                Flag.class, 1, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public List<NabuccoProperty> getProperties() {
        List<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(
                TestConfigurationMsg.getPropertyDescriptor(TESTCONFIGURATION),
                this.testConfiguration));
        properties.add(super.createProperty(
                TestConfigurationMsg.getPropertyDescriptor(IMPORTCONFIG), this.importConfig));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TESTCONFIGURATION) && (property.getType() == TestConfiguration.class))) {
            this.setTestConfiguration(((TestConfiguration) property.getInstance()));
            return true;
        } else if ((property.getName().equals(IMPORTCONFIG) && (property.getType() == Flag.class))) {
            this.setImportConfig(((Flag) property.getInstance()));
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
        final TestConfigurationMsg other = ((TestConfigurationMsg) obj);
        if ((this.testConfiguration == null)) {
            if ((other.testConfiguration != null))
                return false;
        } else if ((!this.testConfiguration.equals(other.testConfiguration)))
            return false;
        if ((this.importConfig == null)) {
            if ((other.importConfig != null))
                return false;
        } else if ((!this.importConfig.equals(other.importConfig)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.testConfiguration == null) ? 0 : this.testConfiguration
                .hashCode()));
        result = ((PRIME * result) + ((this.importConfig == null) ? 0 : this.importConfig
                .hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getTestConfiguration.
     *
     * @return the TestConfiguration.
     */
    public TestConfiguration getTestConfiguration() {
        return this.testConfiguration;
    }

    /**
     * Missing description at method setTestConfiguration.
     *
     * @param testConfiguration the TestConfiguration.
     */
    public void setTestConfiguration(TestConfiguration testConfiguration) {
        this.testConfiguration = testConfiguration;
    }

    /**
     * Missing description at method getImportConfig.
     *
     * @return the Flag.
     */
    public Flag getImportConfig() {
        return this.importConfig;
    }

    /**
     * Missing description at method setImportConfig.
     *
     * @param importConfig the Flag.
     */
    public void setImportConfig(Flag importConfig) {
        this.importConfig = importConfig;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TestConfigurationMsg.class)
                .getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TestConfigurationMsg.class).getAllProperties();
    }
}
