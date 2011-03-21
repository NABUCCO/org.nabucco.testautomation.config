/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.testautomation.config.facade.datatype.TestConfigElementContainer;

/**
 * TestConfigElementContainerMsg<p/>Message for transporting a TestConfigElementContainer<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class TestConfigElementContainerMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String TESTCONFIGELEMENTCONTAINER = "testConfigElementContainer";

    private TestConfigElementContainer testConfigElementContainer;

    /** Constructs a new TestConfigElementContainerMsg instance. */
    public TestConfigElementContainerMsg() {
        super();
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(TESTCONFIGELEMENTCONTAINER, PropertyDescriptorSupport.createDatatype(
                TESTCONFIGELEMENTCONTAINER, TestConfigElementContainer.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public List<NabuccoProperty> getProperties() {
        List<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(
                TestConfigElementContainerMsg.getPropertyDescriptor(TESTCONFIGELEMENTCONTAINER),
                this.testConfigElementContainer));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TESTCONFIGELEMENTCONTAINER) && (property.getType() == TestConfigElementContainer.class))) {
            this.setTestConfigElementContainer(((TestConfigElementContainer) property.getInstance()));
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
        final TestConfigElementContainerMsg other = ((TestConfigElementContainerMsg) obj);
        if ((this.testConfigElementContainer == null)) {
            if ((other.testConfigElementContainer != null))
                return false;
        } else if ((!this.testConfigElementContainer.equals(other.testConfigElementContainer)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.testConfigElementContainer == null) ? 0
                : this.testConfigElementContainer.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getTestConfigElementContainer.
     *
     * @return the TestConfigElementContainer.
     */
    public TestConfigElementContainer getTestConfigElementContainer() {
        return this.testConfigElementContainer;
    }

    /**
     * Missing description at method setTestConfigElementContainer.
     *
     * @param testConfigElementContainer the TestConfigElementContainer.
     */
    public void setTestConfigElementContainer(TestConfigElementContainer testConfigElementContainer) {
        this.testConfigElementContainer = testConfigElementContainer;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TestConfigElementContainerMsg.class)
                .getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TestConfigElementContainerMsg.class)
                .getAllProperties();
    }
}
