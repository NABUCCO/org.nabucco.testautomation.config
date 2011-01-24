/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message;

import java.util.ArrayList;
import java.util.List;
import org.nabucco.framework.base.facade.datatype.property.ListProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.testautomation.config.facade.datatype.TestConfiguration;

/**
 * TestConfigurationListMsg<p/>Message containing a list of TestConfigurations<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class TestConfigurationListMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "testConfigList" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    private List<TestConfiguration> testConfigList;

    /** Constructs a new TestConfigurationListMsg instance. */
    public TestConfigurationListMsg() {
        super();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new ListProperty<TestConfiguration>(PROPERTY_NAMES[0],
                TestConfiguration.class, PROPERTY_CONSTRAINTS[0], this.testConfigList));
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
        final TestConfigurationListMsg other = ((TestConfigurationListMsg) obj);
        if ((this.testConfigList == null)) {
            if ((other.testConfigList != null))
                return false;
        } else if ((!this.testConfigList.equals(other.testConfigList)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.testConfigList == null) ? 0 : this.testConfigList
                .hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<TestConfigurationListMsg>\n");
        appendable.append(super.toString());
        appendable.append((("<testConfigList>" + this.testConfigList) + "</testConfigList>\n"));
        appendable.append("</TestConfigurationListMsg>\n");
        return appendable.toString();
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getTestConfigList.
     *
     * @return the List<TestConfiguration>.
     */
    public List<TestConfiguration> getTestConfigList() {
        if ((this.testConfigList == null)) {
            this.testConfigList = new ArrayList<TestConfiguration>();
        }
        return this.testConfigList;
    }
}
