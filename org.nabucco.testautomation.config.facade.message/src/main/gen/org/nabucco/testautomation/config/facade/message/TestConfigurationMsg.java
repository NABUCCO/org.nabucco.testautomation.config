/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
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

    private static final String[] PROPERTY_NAMES = { "testConfiguration" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    private TestConfiguration testConfiguration;

    /** Constructs a new TestConfigurationMsg instance. */
    public TestConfigurationMsg() {
        super();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new DatatypeProperty<TestConfiguration>(PROPERTY_NAMES[0],
                TestConfiguration.class, PROPERTY_CONSTRAINTS[0], this.testConfiguration));
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
        final TestConfigurationMsg other = ((TestConfigurationMsg) obj);
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
        result = ((PRIME * result) + ((this.testConfiguration == null) ? 0 : this.testConfiguration
                .hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<TestConfigurationMsg>\n");
        appendable.append(super.toString());
        appendable
                .append((("<testConfiguration>" + this.testConfiguration) + "</testConfiguration>\n"));
        appendable.append("</TestConfigurationMsg>\n");
        return appendable.toString();
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
}
