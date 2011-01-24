/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.testautomation.config.facade.message;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
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

    private static final String[] PROPERTY_NAMES = { "testConfigElementContainer" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    private TestConfigElementContainer testConfigElementContainer;

    /** Constructs a new TestConfigElementContainerMsg instance. */
    public TestConfigElementContainerMsg() {
        super();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new DatatypeProperty<TestConfigElementContainer>(PROPERTY_NAMES[0],
                TestConfigElementContainer.class, PROPERTY_CONSTRAINTS[0],
                this.testConfigElementContainer));
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
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<TestConfigElementContainerMsg>\n");
        appendable.append(super.toString());
        appendable
                .append((("<testConfigElementContainer>" + this.testConfigElementContainer) + "</testConfigElementContainer>\n"));
        appendable.append("</TestConfigElementContainerMsg>\n");
        return appendable.toString();
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
}
