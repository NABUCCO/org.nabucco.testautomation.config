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
import org.nabucco.testautomation.config.facade.datatype.TestConfigElement;

/**
 * TestConfigElementListMsg<p/>Message containing a list of TestConfigElements<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-04-15
 */
public class TestConfigElementListMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "testConfigElementList" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    private List<TestConfigElement> testConfigElementList;

    /** Constructs a new TestConfigElementListMsg instance. */
    public TestConfigElementListMsg() {
        super();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new ListProperty<TestConfigElement>(PROPERTY_NAMES[0],
                TestConfigElement.class, PROPERTY_CONSTRAINTS[0], this.testConfigElementList));
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
        final TestConfigElementListMsg other = ((TestConfigElementListMsg) obj);
        if ((this.testConfigElementList == null)) {
            if ((other.testConfigElementList != null))
                return false;
        } else if ((!this.testConfigElementList.equals(other.testConfigElementList)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.testConfigElementList == null) ? 0
                : this.testConfigElementList.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<TestConfigElementListMsg>\n");
        appendable.append(super.toString());
        appendable
                .append((("<testConfigElementList>" + this.testConfigElementList) + "</testConfigElementList>\n"));
        appendable.append("</TestConfigElementListMsg>\n");
        return appendable.toString();
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Missing description at method getTestConfigElementList.
     *
     * @return the List<TestConfigElement>.
     */
    public List<TestConfigElement> getTestConfigElementList() {
        if ((this.testConfigElementList == null)) {
            this.testConfigElementList = new ArrayList<TestConfigElement>();
        }
        return this.testConfigElementList;
    }
}
