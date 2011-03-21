/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.security.User;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;

/**
 * TestExecutionMsg<p/>Message to start the execution of a TestConfiguration<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-06-25
 */
public class TestExecutionMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String USER = "user";

    public static final String TESTCONFIGURATION = "testConfiguration";

    private User user;

    private TestConfiguration testConfiguration;

    /** Constructs a new TestExecutionMsg instance. */
    public TestExecutionMsg() {
        super();
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(USER, PropertyDescriptorSupport.createDatatype(USER, User.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPONENT));
        propertyMap.put(TESTCONFIGURATION, PropertyDescriptorSupport.createDatatype(
                TESTCONFIGURATION, TestConfiguration.class, 1, PROPERTY_CONSTRAINTS[1], false,
                PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public List<NabuccoProperty> getProperties() {
        List<NabuccoProperty> properties = super.getProperties();
        properties
                .add(super.createProperty(TestExecutionMsg.getPropertyDescriptor(USER), this.user));
        properties.add(super.createProperty(
                TestExecutionMsg.getPropertyDescriptor(TESTCONFIGURATION), this.testConfiguration));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(USER) && (property.getType() == User.class))) {
            this.setUser(((User) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TESTCONFIGURATION) && (property.getType() == TestConfiguration.class))) {
            this.setTestConfiguration(((TestConfiguration) property.getInstance()));
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
        final TestExecutionMsg other = ((TestExecutionMsg) obj);
        if ((this.user == null)) {
            if ((other.user != null))
                return false;
        } else if ((!this.user.equals(other.user)))
            return false;
        if ((this.testConfiguration == null)) {
            if ((other.testConfiguration != null))
                return false;
        } else if ((!this.testConfiguration.equals(other.testConfiguration)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.user == null) ? 0 : this.user.hashCode()));
        result = ((PRIME * result) + ((this.testConfiguration == null) ? 0 : this.testConfiguration
                .hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getUser.
     *
     * @return the User.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Missing description at method setUser.
     *
     * @param user the User.
     */
    public void setUser(User user) {
        this.user = user;
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
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TestExecutionMsg.class)
                .getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TestExecutionMsg.class).getAllProperties();
    }
}
